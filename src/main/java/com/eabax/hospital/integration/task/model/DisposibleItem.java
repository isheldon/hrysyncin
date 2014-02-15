package com.eabax.hospital.integration.task.model;

import java.math.BigDecimal;

public class DisposibleItem {
  public Long id;
  public String number;
  public String name;
  public String unit;
  public String specification;
  public String model;
  public String supplierName;
  public String supplierNo;
  public String producerName;
  public String registrationNo;
  public BigDecimal purchasePrice;
  public BigDecimal salesPrice;
  
  public void setSpecification(String specification) {
    if (specification == null) {
      specification = "";
    }
    this.specification = specification;
  }

  public void setSupplierName(String supplierName) {
    if (supplierName == null) {
      supplierName= "";
    }
    this.supplierName = supplierName;
  }

  public void setSupplierNo(String supplierNo) {
    if (supplierNo == null) {
      supplierNo= "";
    }
    this.supplierNo = supplierNo;
  }

  public void setProducerName(String producerName) {
    if (producerName == null) {
      producerName= "";
    }
    this.producerName = producerName;
  }

  public void setRegistrationNo(String registrationNo) {
    if (registrationNo == null) {
      registrationNo = "";
    }
    this.registrationNo = registrationNo;
  }

  @Override
  public String toString() {
    return "DisposibleItem [id=" + id + ", number=" + number + ", name=" + name + ", unit="
        + unit + ", specification=" + specification + ", model=" + model
        + ", supplierName=" + supplierName + ", supplierNo=" + supplierNo
        + ", producerName=" + producerName + ", registrationNo="
        + registrationNo + ", purchasePrice=" + purchasePrice + ", salesPrice="
        + salesPrice + "]";
  }
}