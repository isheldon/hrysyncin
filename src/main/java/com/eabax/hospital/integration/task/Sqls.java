package com.eabax.hospital.integration.task;

class Sqls {

  static String selLastOutLog = "select top 1 * from EabaxOutLog order by id desc";

  static String selLastInLog = "select top 1 * from EabaxInLog order by id desc";

  static String insOutLog = "insert into EabaxOutLog "
      + "(process_time, department_id, disposible_item_id, supplier_id, activity_id) "
      + "values (?, ?, ?, ?, ?)";

  static String insInLog = "insert into EabaxInLog "
      + "(process_time, instrm_set_id, mm_activity_id) "
      + "values (?, ?, ?)";

  static String selDept = "select lngdepartmentid, strdepartmentcode, strfullname from department "
      + "where lngdepartmentid > ? order by lngdepartmentid";

  static String insDept = "insert into department (number, name) values (?, ?)";


  static String selDisposibleItems = 
      "select i.lngitemid, i.stritemcode, i.stritemname, i.strpackunit, i.stritemstyle, '' as itemmodel, "
      + "c.strcustomercode, c.strcustomername, i.strmadefactname, i.strregisterno, i.dblpurchaseprice, i.dblsaleprice "
      + "from item i, itemtype it, customer c "
      + "where i.lngitemtypeid = it.lngitemtypeid "
      + "and i.lngcustomerid = c.lngcustomerid "
      + "and (it.stritemtypecode like '1-1-05%' or it.stritemtypecode like '1-1-06%') "
      + "and i.lngitemid > ? "
      + "order by i.lngitemid";
  
  static String insDisposibleItem = "insert into DisposableItem "
      + "(number, name, unit, specification, model, supplier_name, supplier_no, "
      + "producer_name, registration_no, purchase_price, sales_price) "
      + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  static String selSuppliers = "select lngcustomerid, strcustomercode, strcustomername, strcontactname, "
        + "strofficephonenumber, strbilltoaddress, '' as nature, '' as legal_person "
        + "from customer where lngcustomerid > ? order by lngcustomerid";

  public static String insSupplier = "insert into Supplier "
      + "(number, name, contact, contact_phone, address, enterprise_nature, legal_person) "
      + "values (?, ?, ?, ?, ?, ?, ?)";

  public static String selApplyActivities = 
      "select a.lngdrawapplyid, a.strreceiptno, a.lngreceiptno, a.strdate, "
      + "d.strdepartmentcode, a.lngoperatorid, o.stroperatorcode, o.stroperatorname, "
      + "a.strapprovedate, ao.stroperatorcode as approver_code, ao.stroperatorname as approver_name, "
      + "ro.stroperatorcode as receiver_code, ro.stroperatorname as receiver_name, "
      + "i.stritemcode, i.stritemname, it.stritemtypecode, iu.strunitname, ad.dblapplyquantity "
      + "from drawapply a, department d, operator o, operator ao, operator ro, "
      + "drawapplydetail ad, item i, itemtype it, itemunit iu "
      + "where a.lngdepartmentid = d.lngdepartmentid "
      + "and a.lngoperatorid = o.lngoperatorid "
      + "and a.lngapproveid = ao.lngoperatorid "
      + "and a.lngemployeeid = ro.lngoperatorid (+) "
      + "and ad.lngdrawapplyid = a.lngdrawapplyid "
      + "and ad.lngitemid = i.lngitemid "
      + "and ad.lngunitid = iu.lngunitid "
      + "and i.lngitemtypeid = it.lngitemtypeid "
      + "and a.lngdrawapplyid > ?";
  
  public static String insApplyActivity = "insert into JspActivity " 
     + "(apply_number, apply_date, apply_dept_no, apply_person, approve_date, approve_person, "
     + "item_name, item_type, item_no, item_unit, item_qty, receiver_person) "
     + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  public static String selInstrmSets = "select * from InstrumentSet where update_time > ?";
  
  public static String insInstrmSet = "insert into item (lngitemid, stritemcode, stritemname, "
      + "lngpurchaseunitid, lngsaleunitid, lngstockunitid, lngcalcunitid, "
      + "dblsaleprice, dblsaleprice1, dblpurchaseprice, dblpurchaseprice1, "
      + "lngitemtypeid, lngitemnatureid, "
      + "strpackunit, bytstatus, lngorganizationid, lngitemunitgroupid, dblpackfactor) "
      + "values (item_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  public static String updInstrmSet = "update item set bytstatus = ? where stritemcode = ?";
  
  public static String selMmActivities = "select * from MmActivity where update_time > ? order by id";
  
  public static String insInOutActivity =
      "insert into itemactivity (lngactivityid, lngactivitytypeid, lngreceipttypeid, lngtemplateid, "
      + "lngdepartmentid, intyear, bytperiod, strreceiptno, lngreceiptno, "
      + "strreceiptdate, strduedate, strdate, lngcurrencyid, dblrate, "
      + "lngoperatorid, lngcheckerid, lngusetypeid, lngorganizationid, bytstatus, bythishrpstatus)"
      + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  public static String insOutActivityDetail = 
      "insert into itemactivitydetail (lngactivitydetailid, lngactivityid, lngrowid, lngitemid, "
      + "lngunitid, lngpositionid, dblquantity, lngcostorder, strproducedate, strvaliddate, dblcurrprice, dblcurramount) "
      + "values (itemactivitydetail_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  public static String insInActivityDetail = 
      "insert into itemactivitydetail (lngactivitydetailid, lngactivityid, lngrowid, lngitemid, "
      + "lngunitid, lngpositionid, dblquantity, strproducedate, strvaliddate, "
      + "dblcurrprice, dblcurrpricetax, dblcurramount, dblamount, dblcostamount, dblavgcostamount) "
      + "values (itemactivitydetail_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  public static String selUnit = "select lngunitid, lngitemunitgroupid from itemunit "
      + "where strunitname = ? and rownum = 1 order by lngunitid";
  
  public static String selOperatorName = "select lngoperatorid from operator where stroperatorcode = ?";
  
  public static String selMaxReceiptNo = "select max(lngreceiptno) "
      + "from itemactivity where lngreceipttypeid = ? and strreceiptno = ?";
  
  public static String selDepartmentId = "select lngdepartmentid from department where strdepartmentcode = ?";
  
  public static String selItem = "select * from item where stritemcode = ?";
  
  public static String selItemTypeCode = 
      "select it.stritemtypecode from item i, itemtype it "
      + "where i.lngitemtypeid = it.lngitemtypeid and i.stritemcode = ?";
}
