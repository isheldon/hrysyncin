package com.eabax.hospital.integration.task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {


  private static final String TASK_VIEW_NAME = "task/task";

	@RequestMapping(value = "task")
	public String task(Model model) {
	  this.setAttrs(model);
    return TASK_VIEW_NAME;
	}
	
	@RequestMapping(value = "switchTask")
	public String switchTask(Model model) {
	  ScheduledTasks.enabled = !ScheduledTasks.enabled;
	  this.setAttrs(model);
    return TASK_VIEW_NAME;
	}
	
	@RequestMapping(value = "taskRate") 
	public String taskRate(@RequestParam("taskRate") String rate, Model model) {
	    ScheduledTasks.rate = Integer.parseInt(rate);
	    this.setAttrs(model);
	    return TASK_VIEW_NAME;
	}
	
	private void setAttrs(Model model) {
	  model.addAttribute("taskEnabled", ScheduledTasks.enabled);
    model.addAttribute("taskRate", ScheduledTasks.rate);
	}

}
