package com.yuansong.cronjob.bootdemo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yuansong.cronjob.bootdemo.global.Global;
import com.yuansong.cronjob.module.task.AbstractTask;

@Component
public class TaskOne extends AbstractTask {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskOne.class);

	@Override
	public String getType() {
		return this.getClass().getName();
	}

	@Override
	public String getCron() {
		return Global.TASK_ONE_CRON;
	}

	@Override
	public void job() {
		logger.debug("task {} start", this.getType());
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.debug("task {} stop", this.getType());
	}

}
