package com.eabax.hospital.integration.task;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.xfire.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eabax.hospital.integration.task.model.ApplyActivity;
import com.eabax.hospital.integration.task.model.InLog;
import com.eabax.hospital.integration.task.model.InstrmSet;
import com.eabax.hospital.integration.task.model.MmActivity;
import com.eabax.hospital.integration.task.model.RowMappers;

@Repository
@Transactional(readOnly = true)
public class InTaskRepository {
  static final Logger LOG = LoggerFactory.getLogger(InTaskRepository.class);

  @Autowired
  JdbcTemplate eabaxJdbc;

  @Autowired
  JdbcTemplate inteJdbc;
  
  @Value("${rollbackws.url}")
  private String wsUrl;
  
  /**
   * Write MM data to EabaxDb
   * @param data MM data container
   * @param log Last inLog
   */
  @Transactional(value="eabaxTxManager",
      rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED)
  public void writeToEabaxDb() {
    Timestamp currentSyncTime = new Timestamp(System.currentTimeMillis());
    // get last log
    InLog lastLog = getLastInLog();
    LOG.debug("Last in log: " + lastLog);
    
    // construct instrument sets
    List<InstrmSet> instrmSets = this.getInteInstrmSets(lastLog);
    this.refineInstrmSets(instrmSets);
    LOG.debug("There are " + instrmSets.size() + " instrument sets to be sync.");
    
    // begin to sync
    boolean hasNew = false;

    // sync instrument sets
    Long lastInstrmSetId = lastLog.instrmSetId;
    if (instrmSets.size() > 0) {
      lastInstrmSetId = this.writeEabaxInstrmSets(instrmSets);
      hasNew = true;
      LOG.debug("Instrument sets cynced.");
    }
    
    // construct mmActivities
    List<MmActivity> mmActivities = this.getInteMmActivities(lastLog);
    this.refineMmActivities(mmActivities);
    LOG.debug("There are " + mmActivities.size() + " mmActivities to be sync.");
    
    // sync mmActivities
    Long lastMmActivityId = lastLog.mmActivityId;
    if (mmActivities.size() > 0) {
      Long maxInReceiptNo = this.getMaxReceiptNo(11L, "GYS");
      Long maxOutReceiptNo = this.getMaxReceiptNo(21L, "GYS");
      lastMmActivityId = this.writeEabaxActivities(
          mmActivities, maxInReceiptNo, maxOutReceiptNo);
      hasNew = true;
      LOG.debug("MmActivities cynced.");
    }
    
    //整单回退未出库一次性物品, 用于修改输错的单据
    Long lastEabaxRevertApplyId = this.revertEabaxApplys(lastLog);
    if (lastEabaxRevertApplyId == null) { //没有需要回退的单据
      lastEabaxRevertApplyId = lastLog.eabaxRevertApplyId;
    } else {
      hasNew = true;
      LOG.debug("已整单回退未出库一次性物品");
    }
    
    //一次性物品已出库申请有误,回退
    Long lastEabaxReturnApplyId = this.returnEabaxApplys(lastLog);
    if (lastEabaxReturnApplyId == null) { //没有需要回退的单据
      lastEabaxReturnApplyId = lastLog.eabaxReturnApplyId;
    } else {
      hasNew = true;
      LOG.debug("已回退一次性物品已出库申请");
    }
    

    if (hasNew) {
      InLog newLog = new InLog();
      newLog.processTime = currentSyncTime;
      newLog.instrmSetId = lastInstrmSetId;
      newLog.mmActivityId = lastMmActivityId;
      newLog.eabaxRevertApplyId = lastEabaxRevertApplyId;
      newLog.eabaxReturnApplyId = lastEabaxReturnApplyId; 
      this.writeInLog(newLog);
      LOG.debug("New in log created: " + newLog);
    }
  }
  
  private InLog getLastInLog() {
    return inteJdbc.queryForObject(Sqls.selLastInLog, RowMappers.inLog);
  }
  
  private List<InstrmSet> getInteInstrmSets(InLog log) {
    return inteJdbc.query(Sqls.selInstrmSets, new Object[] { log.processTime }, RowMappers.instrmSet);
  }
  
  private Long writeEabaxInstrmSets(List<InstrmSet> sets) {
    for (InstrmSet set: sets) {
      // Update status first if the instrmSet exists
      int updated = eabaxJdbc.update(Sqls.updInstrmSet, new Object[] {set.status, set.no});
      // If not exists, insert it
      if (updated == 0) {
        eabaxJdbc.update(Sqls.insInstrmSet,
            new Object[] {set.no, set.name,
            set.unitId, set.unitId, set.unitId, set.unitId,
            set.price, set.price, set.price, set.price,
            set.type, set.nature,
            set.unit, set.status, set.orgId, set.unitGroupId, set.packFactor} );
      }
    }
    return sets.get(sets.size() - 1).id;
  }

  private void refineInstrmSets(List<InstrmSet> sets) {
    for (InstrmSet set:sets) {
      Map<String, Long> unit = this.getUnitByName(set.unit);
      set.unitId  = unit.get("unitId");
      set.unitGroupId = unit.get("unitGroupId");
    }
  }
  
  private List<MmActivity> getInteMmActivities(InLog log) {
    return inteJdbc.query(Sqls.selMmActivities, 
        new Object[] { log.processTime }, RowMappers.mmActivity);
  }
  
  private void refineMmActivities(List<MmActivity> activities) {
    for (MmActivity act: activities) {
      Map<String, Object> info = this.getItemInfobyNo(act.itemNo);
      act.itemId = (Long) info.get("id");
      act.itemUnitId = (Long) info.get("unitId");
      act.itemPositionId = (Long) info.get("positionId");
      act.itemPrice = (BigDecimal) info.get("price");
      act.itemAmount = act.itemPrice.multiply(new BigDecimal(act.itemQty));
      act.receiveDeptId = this.getDeptIdByNo(act.receiveDeptNo);
      act.approvePersonId = this.getPersonIdByNo(act.approvePersonNo);
      act.billmakerId = this.getPersonIdByNo(act.billmakerNo);
      act.billYear = Utils.getYear(act.billDate);
      act.billMonth = Utils.getMonth(act.billDate);
    }
  }
  
  private Long writeEabaxActivities(List<MmActivity> activities, 
      long maxInReceiptNo, long maxOutReceiptNo) {
    for (MmActivity act: activities) {
      if (act.dataType == 1) { // InstrumentSet
        act.activityTypeId = 10L;
        act.receiptTypeId = 11L;
        act.templateId = 10059L;
        act.useTypeId = 120;
        act.hrpStatus = 0;
        this.writeEabaxActivity(act, maxInReceiptNo + 1);
        maxInReceiptNo++;
      }

      act.activityTypeId = 19L;
      act.receiptTypeId = 21L;
      act.templateId = 10381L;
      act.useTypeId = 100;
      act.hrpStatus = 1;
      this.writeEabaxActivity(act, maxOutReceiptNo + 1);
      maxOutReceiptNo++;
    }
    return activities.get(activities.size() - 1).id;
  }
  
  private void writeEabaxActivity(MmActivity act, long receiptNo) {
    act.seqValue = this.getNextSeqValue("itemactivity_seq");
    eabaxJdbc.update(Sqls.insInOutActivity, new Object[] {
        act.seqValue, act.activityTypeId, act.receiptTypeId, act.templateId,
        act.receiveDeptId, act.billYear, act.billMonth, "GYS", receiptNo,
        Utils.dateString(act.billDate), Utils.dateString(act.billDate), Utils.dateString(act.billDate), 1L, 1.0D,
        act.billmakerId, act.approvePersonId, act.useTypeId, 1L, 2L, act.hrpStatus 
    });

    if (act.activityTypeId.intValue() == 1) { // InstrumentSet
      eabaxJdbc.update(Sqls.insInActivityDetail, new Object[] {
          act.seqValue, 1, act.itemId,
          act.itemUnitId, act.itemPositionId, act.itemQty, 
          Utils.dateString(act.produceDate), Utils.dateString(act.dueDate),
          act.itemPrice, act.itemPrice, act.itemAmount, act.itemAmount, act.itemAmount, act.itemAmount
      });
    } else {
      eabaxJdbc.update(Sqls.insOutActivityDetail, new Object[] {
          act.seqValue, 1, act.itemId,
          act.itemUnitId, act.itemPositionId, act.itemQty, 302,
          Utils.dateString(act.produceDate), Utils.dateString(act.dueDate), 
          act.itemPrice, act.itemAmount
      });
    }
  }
  
  private Long getPersonIdByNo(String no) {
    if (no == null || no.length() == 0) return null;
    return eabaxJdbc.queryForObject(Sqls.selOperatorName, new Object[] { no }, Long.class);
  }
  
  private Map<String, Object> getItemInfobyNo(String no) {
    return eabaxJdbc.queryForObject( Sqls.selItem, new Object[] { no }, 

        new RowMapper<Map<String, Object>>() {
          @Override
          public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, Object> info = new HashMap<String, Object>();
            info.put("id", rs.getLong("lngitemid"));
            info.put("unitId", rs.getLong("lngstockunitid"));
            info.put("positionId", rs.getLong("lngpositionid"));
            info.put("price", rs.getBigDecimal("dblpurchaseprice"));
            return info;
          }
        } );
  }
  
  private Map<String, Long> getUnitByName(String name) {
    return eabaxJdbc.queryForObject(Sqls.selUnit, new Object[] { name }, 
        new RowMapper<Map<String, Long>>() {
          @Override
          public Map<String, Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, Long> info = new HashMap<String, Long>();
            info.put("unitId", rs.getLong("lngunitid"));
            info.put("unitGroupId", rs.getLong("lngitemunitgroupid"));
            return info;
          }
      
        } );
  }

  /* Maybe not need this
  private Long getUnitGroupIdByName(String unitName) {
    return null;
  }
  */
  
  private Long getDeptIdByNo(String no) {
    return eabaxJdbc.queryForObject(Sqls.selDepartmentId, new Object[] {no}, Long.class);
  }
  
  private Long getMaxReceiptNo(Long receiptTypeId, String prefix) {
    Long no = eabaxJdbc.queryForObject(Sqls.selMaxReceiptNo, new Object[] { receiptTypeId, prefix }, Long.class);
    if (no == null) { return 0L; }
    else { return no; }
  }
  
  private Long getNextSeqValue(String seq) {
    return eabaxJdbc.queryForObject("select " + seq + ".nextval from dual", Long.class);
  }
  
  private void writeInLog(InLog log) {
    inteJdbc.update(Sqls.insInLog,
        new Object[] { log.processTime, log.instrmSetId, log.mmActivityId, 
            log.eabaxRevertApplyId, log.eabaxReturnApplyId } );
  }
  
  //整单回退未出库一次性物品
  private Long revertEabaxApplys(InLog log) {
    //查询需要回退的单据号
    List<Long> applyIds = inteJdbc.queryForList(Sqls.selRevertApplyIds, 
        new Object[] { log.processTime }, Long.class);
    if (applyIds == null || applyIds.isEmpty()) return null;
    
    //单据回退的webservice URL
    //先从配置取值,没有的话用localhost
    if (wsUrl == null) {
      wsUrl = "http://localhost:8080/core/services/BillRollBack?wsdl";
    }
    LOG.debug("单据回退的webservice URL: " + wsUrl);
    
    //把单据号存入log, 供回退后重新提交时处理
    for (Long applyId: applyIds) {
      eabaxJdbc.update(Sqls.insRevertLog, new Object[] { applyId });

      //调用单据回退的webservice
      try {
        //call webservice
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><packet><billId>" + applyId 
            + "</billId><type>lyReq</type><rejectIdea>RevertLy</rejectIdea></packet>";
        Client client = new Client(new URL(wsUrl));
        final Object[] results = client.invoke("doRollBackToMake", new Object[]{xml});
        String ret = (String) results[0];
        LOG.debug("单据回退WebService调用结果:" + ret);
      } catch (Exception e) {
        throw new RuntimeException("调用单据回退WebService失败: " + e.getMessage());
      }
    }
    return applyIds.get(applyIds.size() - 1);
  }
  
  //一次性物品已出库申请有误,回退
  //生成负数的领用出库单
  private Long returnEabaxApplys(InLog log) {
    List<ApplyActivity> acts = inteJdbc.query(Sqls.selReturnJspActivities, 
        new Object[] { log.processTime }, RowMappers.applyActivity);
    if (acts == null || acts.isEmpty()) return null;
    
    Long activityTypeId = 19L;
    Long receiptTypeId = 21L;
    Long templateId = 10381L;
    Integer useTypeId = 100;
    Integer hrpStatus = 1;
    Long organizationId = 1L;
    Integer status = 2;
    Long receiptNo = getMaxReceiptNo(21L, "GYS") + 1;
    String today = Utils.dateStringNow();
    Long currencyid = 1L;
    Double rate = 1.0D;
    for (ApplyActivity act: acts) {
      Long seq = getNextSeqValue("itemactivity_seq");
      Long deptId = getDeptIdByNo(act.applyDeptNo);
      Long billmakerId = getPersonIdByNo(Utils.personNo(act.applyPerson));
      Long approvePersonId = getPersonIdByNo(Utils.personNo(act.approvePerson));
      Map<String, Object> itemInfo = getItemInfobyNo(act.itemNo);
      Long itemId = (Long) itemInfo.get("id");
      Long unitId = (Long) itemInfo.get("unitId");
      Long positionId = (Long) itemInfo.get("positionId");
      BigDecimal price = (BigDecimal) itemInfo.get("price");
      
      eabaxJdbc.update(Sqls.insInOutActivity, new Object[] {
          seq, activityTypeId, receiptTypeId, templateId, deptId,
          Utils.getCurrentYear(), Utils.getCurrentMonth(), "GYS", receiptNo,
          today, today, today, currencyid, rate,
          billmakerId, approvePersonId, useTypeId, organizationId, status, hrpStatus
      });
      receiptNo++;
      
      eabaxJdbc.update(Sqls.insOutActivityDetail, new Object[] {
          seq, 1, itemId, unitId, positionId, -act.itemQty, 302,
          " ", " ", //produce_date, due_date
          price, price.multiply(BigDecimal.valueOf(-act.itemQty.longValue()))
      });
    }
    return acts.get(acts.size() - 1).drawapplyId;
  }
}
