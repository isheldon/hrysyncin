package com.eabax.hospital.integration.task.model;

import java.math.BigDecimal;
import java.sql.Date;

public class MmActivity {
  public Long id;
  public int dataType;
  public String itemNo;
  public String itemUnit;
  public BigDecimal itemPrice;
  public int itemQty;
  public BigDecimal itemAmount;
  public Date produceDate;
  public Date dueDate;
  public String receiveDeptNo;
  public String approvePersonNo;
  public BigDecimal feeAmount; // TODO: what does it mean
  public String billmakerNo;
  public Date billDate;
  
  public Long seqValue;
  
  public Long itemId;
  public Long itemUnitId;
  public Long itemPositionId;
  public Long receiveDeptId;
  public Long approvePersonId;
  public Long billmakerId;
  
  public int billYear;
  public int billMonth;
  public Long activityTypeId;
  public Long receiptTypeId;
  public Long templateId;

  public int useTypeId;
  public int hrpStatus = 0;

  @Override
  public String toString() {
    return "MmActivity [id=" + id + ", dataType=" + dataType + ", itemNo="
        + itemNo + ", itemUnit=" + itemUnit + ", itemPrice=" + itemPrice
        + ", itemQty=" + itemQty + ", itemAmount=" + itemAmount
        + ", produceDate=" + produceDate + ", dueDate=" + dueDate
        + ", receiveDeptNo=" + receiveDeptNo + ", approvePersonNo="
        + approvePersonNo + ", feeAmount=" + feeAmount + ", billmakerNo="
        + billmakerNo + ", billDate=" + billDate + ", itemId=" + itemId
        + ", itemUnitId=" + itemUnitId + ", itemPositionId=" + itemPositionId
        + ", receiveDeptId=" + receiveDeptId + ", approvePersonId="
        + approvePersonId + ", billmakerId=" + billmakerId + ", billYear="
        + billYear + ", billMonth=" + billMonth + ", activityTypeId="
        + activityTypeId + ", receiptTypeId=" + receiptTypeId + ", templateId="
        + templateId + "]";
  }
 
}
