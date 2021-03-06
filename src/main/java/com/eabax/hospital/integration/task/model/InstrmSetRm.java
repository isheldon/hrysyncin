package com.eabax.hospital.integration.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InstrmSetRm implements RowMapper<InstrmSet>{

  @Override
  public InstrmSet mapRow(ResultSet rs, int rowNum) throws SQLException {
    InstrmSet set = new InstrmSet();
    set.id = rs.getLong("id");
    set.no = rs.getString("number");
    set.name = rs.getString("name");
    set.unit = rs.getString("unit");
    set.price = rs.getBigDecimal("price");
    if (rs.getInt("is_valid") == 0) {
      set.status = 0;
    }
    return set;
  }

}
