package com.eabax.hospital.integration.task.model;

import java.sql.Timestamp;

public class InLog {
  public Timestamp processTime;
  public Long instrmSetId;
  public Long mmActivityId;
  
  @Override
  public String toString() {
    return "InLog [processTime=" + processTime + ", instrmSetId=" + instrmSetId
        + ", mmActivityId=" + mmActivityId + "]";
  }
}
