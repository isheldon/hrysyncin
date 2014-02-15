package com.eabax.hospital.integration.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MmActivityRm implements RowMapper<MmActivity> {
  
//  [id] [bigint] PRIMARY KEY IDENTITY(1,1) NOT NULL, /*自增ID*/
//  [data_type] [int] NOT NULL, /*数据类型  [int] NOT NULL, (取值1,2: 1-器械包出库；2-一次性物品出库）*/
//  [item_no] [varchar](50) NOT NULL, /*物品编码*/
//  [item_unit] [varchar](50) NOT NULL, /*计量单位*/
//  [item_price] [numeric](18, 3) NOT NULL, /*原币单价*/
//  [item_qyt] [int] NOT NULL, /*数量*/
//  [item_amount] [numeric](18, 3) NOT NULL, /*原币金额*/
//  [produce_date] [datetime] NOT NULL,  /*生产日期*/
//  [due_date] [datetime] NOT NULL,  /*到期日期 （器械包/一次性物品的有效日期）*/
//  [receive_dept_no] [varchar](50) NOT NULL, /*领用部门编码*/
//  [approve_person_no] [varchar](50) NOT NULL, /*没有发放审核，直接写发放人*/
//  [fee_amount] [numeric](18, 3) NOT NULL, /*费用金额 （器械包的灭菌价格，一次性物品直接写0）*/
//  [billmaker_no] [varchar](50) NOT NULL, /*制单人编码*/
//  [bill_date] [datetime] NOT NULL  /*制单日期*/

  @Override
  public MmActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
    MmActivity act = new MmActivity();
    act.id = rs.getLong("id");
    act.dataType = rs.getInt("data_type");
    act.itemNo = rs.getString("item_no");
    act.itemUnit = rs.getString("item_unit");
    act.itemPrice = rs.getBigDecimal("item_price");
    act.itemQty = rs.getInt("item_qty");
    act.itemAmount = rs.getBigDecimal("item_amount");
    act.produceDate = rs.getDate("produce_date");
    act.dueDate = rs.getDate("due_date");
    act.receiveDeptNo = rs.getString("receive_dept_no");
    act.approvePersonNo = rs.getString("approve_person_no");
    act.feeAmount = rs.getBigDecimal("fee_amount");
    act.billmakerNo = rs.getString("billmaker_no");
    act.billDate = rs.getDate("bill_date");
    return act;
  }

}
