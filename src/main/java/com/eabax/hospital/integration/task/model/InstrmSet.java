package com.eabax.hospital.integration.task.model;

import java.math.BigDecimal;

public class InstrmSet {
  public Long id;
  public String no;
  public String name;
  public String unit;
  public BigDecimal price;
  
  public Long unitId;
  public Long unitGroupId;
  
  public Long type = 140L; //其它卫生材料
  public Long nature = 107L;
  public int status = 2;
  public int packFactor = 1;
  public Long orgId = 1L;
  
  @Override
  public String toString() {
    return "InstrmSet [id=" + id + ", no=" + no + ", name=" + name + ", unit="
        + unit + ", price=" + price + ", unitId=" + unitId + ", unitGroupId="
        + unitGroupId + ", status=" + status + ", packFactor=" + packFactor
        + ", orgId=" + orgId + "]";
  }

}
