package com.yuansong.cronjob.bootdemocron.cron.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CronTaskOne extends AbstractTask {
	
	private static final Logger logger = LoggerFactory.getLogger(CronTaskOne.class);

	@Override
	public long getInterval() {
		return 15 * 1000;
	}

	@Override
	public String getType() {
		return "TestTaskOne";
	}

	@Override
	public void job() {
		logger.debug("run job " + this.getType());
		try {
			Thread.sleep(12 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
