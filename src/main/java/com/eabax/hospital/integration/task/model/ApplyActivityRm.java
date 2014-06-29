package com.eabax.hospital.integration.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ApplyActivityRm implements RowMapper<ApplyActivity> {

  @Override
  public ApplyActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
    ApplyActivity activity = new ApplyActivity();
    activity.id = rs.getLong("id");
    activity.drawapplyId = rs.getLong("drawapply_id");
    activity.applyDetailId = rs.getLong("apply_detail_id");
    activity.applyNumber = rs.getString("apply_number");
    activity.applyDate = rs.getDate("apply_date");
    activity.applyDeptNo = rs.getString("apply_dept_no");
    activity.applyPerson = rs.getString("apply_person");
    activity.approveDate = rs.getDate("approve_date");
    activity.approvePerson = rs.getString("approve_person");
    activity.itemName = rs.getString("item_name");
    activity.itemType = rs.getInt("item_type");
    activity.itemNo = rs.getString("item_no");
    activity.itemUnit = rs.getString("item_unit");
    activity.itemQty = rs.getInt("item_qty");
    activity.receiverPerson = rs.getString("receiver_person");
    activity.applyType = rs.getInt("apply_type");
    return activity;
  }

}
