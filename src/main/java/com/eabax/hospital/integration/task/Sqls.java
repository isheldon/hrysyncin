package com.eabax.hospital.integration.task;

class Sqls {

  static String selLastInLog = "select top 1 * from EabaxInLog order by id desc";

  static String insInLog = "insert into EabaxInLog "
      + "(process_time, instrm_set_id, mm_activity_id) "
      + "values (?, ?, ?)";

  static String insDisposibleItem = "insert into DisposableItem "
      + "(number, name, unit, specification, model, supplier_name, supplier_no, "
      + "producer_name, registration_no, purchase_price, sales_price) "
      + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
