package com.eabax.hospital.integration.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DepartmentRm implements RowMapper<Department> {
  @Override
  public Department mapRow(ResultSet rs, int num) throws SQLException {
    return new Department(
        rs.getLong("lngdepartmentid"), rs.getString("strdepartmentcode"),
        rs.getString("strfullname"));
  }
}
