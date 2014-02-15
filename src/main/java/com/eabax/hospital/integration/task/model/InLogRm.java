package com.eabax.hospital.integration.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InLogRm implements RowMapper<InLog> {

  @Override
  public InLog mapRow(ResultSet rs, int rowNum) throws SQLException {
    InLog log = new InLog();
    log.processTime = rs.getTimestamp("process_time");
    log.instrmSetId = rs.getLong("instrm_set_id");
    log.mmActivityId = rs.getLong("mm_activity_id");
    return log;
  }

}
