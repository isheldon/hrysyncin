package com.eabax.hospital.integration.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InstrmSetRm implements RowMapper<InstrmSet>{

  @Override
  public InstrmSet mapRow(ResultSet rs, int rowNum) throws SQLException {
    InstrmSet set = new InstrmSet();
    set.id = rs.getLong("id");
    set.no = rs.getString("no");
    set.name = rs.getString("name");
    set.unit = rs.getString("unit");
    set.price = rs.getBigDecimal("price");
    return set;
  }

}
