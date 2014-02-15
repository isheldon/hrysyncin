package com.eabax.hospital.integration.task.model;

import java.sql.Timestamp;

public class OutLog {
  public Timestamp processTime;
  public Long departmentId;
  public Long disposibleItemId;
  public Long supplierId;
  public Long applyActivityId;

  public OutLog(Timestamp processTime, Long departmentId,
      Long disposibleItemId, Long supplierId, Long applyActivityId) {
    this.processTime = processTime;
    this.departmentId = departmentId;
    this.disposibleItemId = disposibleItemId;
    this.supplierId = supplierId;
    this.applyActivityId = applyActivityId;
  }
  
  public OutLog() {
    this.processTime = new Timestamp(System.currentTimeMillis());
  }

  @Override
  public String toString() {
    return "OutLog [processTime=" + processTime + ", departmentId="
        + departmentId + ", disposibleItemId=" + disposibleItemId
        + ", supplierId=" + supplierId + ", applyActivityId=" + applyActivityId + "]";
  }

}
