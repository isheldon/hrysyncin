package com.eabax.hospital.integration.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OutLogRm implements RowMapper<OutLog> {
  @Override
  public OutLog mapRow(ResultSet rs, int num) throws SQLException {
    // TODO add new column
    return new OutLog(
        rs.getTimestamp("process_time"), rs.getLong("department_id"),
        rs.getLong("disposible_item_id"), rs.getLong("supplier_id"), rs.getLong("activity_id"));
  }
}
