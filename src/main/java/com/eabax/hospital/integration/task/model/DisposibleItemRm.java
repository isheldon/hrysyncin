package com.eabax.hospital.integration.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DisposibleItemRm implements RowMapper<DisposibleItem> {
  @Override
  public DisposibleItem mapRow(ResultSet rs, int rowNum) throws SQLException {
    DisposibleItem item = new DisposibleItem();
    item.id = rs.getLong("lngitemid");
    item.number = rs.getString("stritemcode");
    item.name = rs.getString("stritemname");
    item.unit = rs.getString("strpackunit");
    item.setSpecification(rs.getString("stritemstyle"));
    item.model = rs.getString("itemmodel");
    item.setSupplierName(rs.getString("strcustomername"));
    item.setSupplierNo(rs.getString("strcustomercode"));
    item.setProducerName(rs.getString("strmadefactname"));
    item.setRegistrationNo(rs.getString("strregisterno"));
    item.purchasePrice = rs.getBigDecimal("dblpurchaseprice");
    item.salesPrice = rs.getBigDecimal("dblsaleprice");
    return item;
  }
}
