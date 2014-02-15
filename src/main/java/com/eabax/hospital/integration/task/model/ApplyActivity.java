package com.eabax.hospital.integration.task.model;

import java.sql.Date;

public class ApplyActivity {
  public Long id;
  public String applyNumber;
  public Date applyDate;
  public String applyDeptNo;
  public String applyPerson;
  public Date approveDate;
  public String approvePerson;
  public String itemName;
  public Integer itemType;
  public String itemNo;
  public String itemUnit;
  public Integer itemQty;
  public String receiverPerson;
  public String billType;
  public Integer receiveType;
  public Integer applyType;
  
  public void setApproveDate(String aprDate) {
    if (aprDate == null) {
      this.approveDate = null;
    } else {
      this.approveDate = Date.valueOf(aprDate);
    }
  }

  @Override
  public String toString() {
    return "ApplyActivity [id=" + id + ", applyNumber=" + applyNumber
        + ", applyDate=" + applyDate + ", applyDeptNo=" + applyDeptNo
        + ", applyPerson=" + applyPerson + ", approveDate=" + approveDate
        + ", approvePerson=" + approvePerson + ", itemName=" + itemName
        + ", itemType=" + itemType + ", itemNo=" + itemNo + ", itemUnit="
        + itemUnit + ", itemQty=" + itemQty + ", receiverPerson="
        + receiverPerson + ", billType=" + billType + ", receiveType="
        + receiveType + ", applyType=" + applyType + "]";
  }
  
}
