package com.eabax.hospital.integration.task;

import java.sql.Timestamp;
import java.util.List;

import com.eabax.hospital.integration.task.model.*;

public class MmData {
  Timestamp currentSyncTime;
  InLog lastLog;

  long maxInReceiptNo;
  long maxOutReceiptNo;
  
  List<InstrmSet> instrmSets;
  List<MmActivity> mmActivities;
}
