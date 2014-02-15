package com.eabax.hospital.integration.task.model;

import java.math.BigDecimal;

public class InstrmSet {
  public Long id;
  public String no;
  public String name;
  public String unit;
  public BigDecimal price;
  
  public Long unitId;

  @Override
  public String toString() {
    return "InstrmSet [id=" + id + ", no=" + no + ", name=" + name + ", unit="
        + unit + ", price=" + price + ", unitId=" + unitId + "]";
  }

}
