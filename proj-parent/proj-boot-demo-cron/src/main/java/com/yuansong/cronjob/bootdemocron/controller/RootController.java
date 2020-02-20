package com.yuansong.cronjob.bootdemocron.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuansong.cronjob.bootdemocron.cron.DynamicTaskConfig;
import com.yuansong.cronjob.bootdemocron.cron.DynamicTaskManager;
import com.yuansong.cronjob.bootdemocron.cron.task.CronTaskOne;
import com.yuansong.cronjob.bootdemocron.scheduler.ScheduleTaskOne;
import com.yuansong.cronjob.module.config.ProjectConfig;
import com.yuansong.tools.common.DateTool;

@RestController
@RequestMapping("/")
public class RootController {
	
	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	
	@Autowired
	private ProjectConfig projectConfig;
	
	@Autowired
	private CronTaskOne cronTaskOne;
	
	@Autowired
	private ScheduleTaskOne scheduleTaskOne;
	
	@Autowired
	private DynamicTaskManager manager;
	
	private String strTaskKey = "test1";
	
	@GetMapping("/root")
	public String root() {
		logger.debug("root");
		this.scheduleTaskOne.setCron("123");
		DateTool dt = new DateTool();
		return this.projectConfig.getName() + "\n" + "root " + dt.GetDatetimeWithMillionsecond();
	}
	
	@GetMapping("/addTask")
	public String addTask() {
		logger.debug("add task");
		try {
			this.manager.addTask(new DynamicTaskConfig(this.cronTaskOne.getType(), this.cronTaskOne, "0/5 * * * * ?"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "OK";
	}
	
	@GetMapping("/pauseTask")
	public String pauseTask() {
		logger.debug("pause task");
		try {
			this.manager.pauseTask(this.strTaskKey);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "OK";
	}
	
	@GetMapping("/resumeTask")
	public String resumeTask() {
		logger.debug("resume task");
		try {
			this.manager.resumeTask(this.strTaskKey);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "OK";
	}
	
	@GetMapping("/removeTask")
	public String removeTask() {
		logger.debug("remove task");
		try {
			this.manager.removeTask(cronTaskOne.getType());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "OK";
	}

}
