package com.yuansong.cronjob.bootdemo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yuansong.cronjob.module.task.AbstractTask;

@Component
public class TaskTwo extends AbstractTask {

	private static final Logger logger = LoggerFactory.getLogger(TaskTwo.class);
	
	@Override
	public String getType() {
		return this.getClass().getName();
	}

	@Override
	public String getCron() {
		return "0/7 * * * * ?";
	}

	@Override
	public void job() {
		logger.debug("task {} start", this.getType());
		try {
			Thread.sleep(9 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.debug("task {} stop", this.getType());
	}
	
}
