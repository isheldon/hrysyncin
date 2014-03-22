package com.eabax.hospital.integration.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduledTasks {
  static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);
  
  public static boolean enabled = false;
  public static int rate = 5;
  private static long count = 1;

  @Autowired
  private InTaskRepository taskRepository;

  @Scheduled(fixedRate = 60000)
  public void reportCurrentTime() {
    if (enabled && count % rate == 0) {
      LOG.debug("Start sync from Inte to Eabax DB...");
      LOG.debug("..........................................");
      MmData data = new MmData();
      taskRepository.constructMmData(data);
      taskRepository.writeToEabaxDb(data);
      LOG.debug("..........................................");
      LOG.debug("End sync from Inte to Eabax DB...");
    }
  }
}
