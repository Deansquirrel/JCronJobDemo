package com.yuansong.cronjob.bootdemocron.cron.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuansong.tools.common.CommonTool;

public abstract class AbstractTask implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractTask.class);

	@Override
	public void run() {
		CommonTool ct = new CommonTool();
		String taskId = ct.UUID();
		logger.debug("Job {} [{}] start",this.getType(), taskId);
		this.job();
		logger.debug("Job {} [{}] end",this.getType(), taskId);
	}
	
	public abstract long getInterval();
	public abstract String getType();
	public abstract void job();

}
