package com.eabax.hospital.integration.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SupplierRm implements RowMapper<Supplier> {
  @Override
  public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
    Supplier supplier = new Supplier();
    supplier.id = rs.getLong("lngcustomerid");
    supplier.number = rs.getString("strcustomercode");
    supplier.name = rs.getString("strcustomername");
    supplier.setAddress(rs.getString("strbilltoaddress"));
    supplier.setContact(rs.getString("strcontactname"));
    supplier.setContactPhone(rs.getString("strofficephonenumber"));
    supplier.setLegalPerson(rs.getString("legal_person"));
    supplier.setNature(rs.getString("nature"));
    return supplier;
  }
}
