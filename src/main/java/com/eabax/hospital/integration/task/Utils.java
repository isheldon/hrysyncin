package com.eabax.hospital.integration.task;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
  public static String billNo(String prefix, long no) {
    if (prefix == null) { prefix = ""; }
    int zeros = 0;
    if (no < 10) { zeros = 3;
    } else if (no < 100) { zeros = 2;
    } else if (no < 1000) { zeros = 1; }
    while (zeros < 0) {
      prefix = prefix + "0";
    }
    return prefix + no;
  }
  
  public static String personNameNo(String name, String no) {
    if ((name == null || name.length() == 0) && (no == null || no.length() == 0)) {
      return "";
    }
    return name + "/" + no;
  }
  
  //把name/no格式的字符串中的no提取出来
  public static String personNo(String nameNo) {
    if (nameNo == null || nameNo.length() == 0) return null;
    return nameNo.substring(nameNo.indexOf("/") + 1);
  }
  
  public static int getYear(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    return cal.get(Calendar.YEAR);
  }

  public static int getMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    return cal.get(Calendar.MONTH) + 1;
  }
  
  public static int getCurrentYear() {
    return getYear(new Date(System.currentTimeMillis()));
  }
  
  public static int getCurrentMonth() {
    return getMonth(new Date(System.currentTimeMillis()));
  }
  
  public static String dateString(Date date) {
    return new SimpleDateFormat("yyyy-MM-dd").format(date);
  }
  
  public static String dateStringNow() {
    return dateString(new Date(System.currentTimeMillis()));
  }
}
