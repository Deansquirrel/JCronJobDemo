package com.yuansong.cronjob.bootdemocron.scheduler;

import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Component
public class Config implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
	}

}
