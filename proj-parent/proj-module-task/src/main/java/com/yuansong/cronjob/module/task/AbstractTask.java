package com.yuansong.cronjob.module.task;

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
	
	/**
	 * Task类型（相同类型任务的唯一标记）
	 * @return
	 */
	public abstract String getType();
	
	/**
	 * 运行时间 cron公式
	 * @return
	 */
	public abstract String getCron();
	
	/**
	 * Task服务内容
	 */
	public abstract void job();

}
