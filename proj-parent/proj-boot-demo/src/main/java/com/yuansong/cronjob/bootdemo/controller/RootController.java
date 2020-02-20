package com.yuansong.cronjob.bootdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuansong.cronjob.bootdemo.global.Global;
import com.yuansong.cronjob.bootdemo.task.TaskOne;
import com.yuansong.cronjob.module.config.ProjectConfig;
import com.yuansong.cronjob.module.task.DynamicTaskManager;
import com.yuansong.tools.common.DateTool;

@RestController
@RequestMapping("/")
public class RootController {
	
	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	
	@Autowired
	private ProjectConfig projectConfig;
	
	@Autowired
	private TaskOne taskOne;
	
	@Autowired
	private DynamicTaskManager dynamicTaskManager;
	
	@GetMapping("/root")
	public String root() {
		logger.debug("root");
		Global.TASK_ONE_CRON = "1123 * * * * ?";
		DateTool dt = new DateTool();
		return this.projectConfig.getName() + "\n" + "root " + dt.GetDatetimeWithMillionsecond();
	}
	
	@GetMapping("/refresh")
	public String refresh() {
		logger.debug("refresh");
		try {
			dynamicTaskManager.refreshTask(taskOne.getType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateTool dt = new DateTool();
		return this.projectConfig.getName() + "\n" + "root " + dt.GetDatetimeWithMillionsecond();
	}

}
