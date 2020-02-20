package com.yuansong.cronjob.bootdemocron.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * 配置定时任务
 * 说明：
 * 	1、允许修改cron
 * 	2、cron的修改，是在原cron计划执行一次后才生效
 * @author yuansong
 *
 */
@Component
public class ScheduleTaskOne implements SchedulingConfigurer {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleTaskOne.class);
	
	private String cron = "0/5 * * * * ?";
	
	public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.debug("task run");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Trigger() {

            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                System.out.println("-----------"+cron);
                if ("".equals(cron) || cron == null)
                    return null;
                CronTrigger trigger = null;
                try {
                	trigger = new CronTrigger(cron);// 定时任务触发，可修改定时任务的执行周期
                } catch(Exception e) {
                	e.printStackTrace();
                	return null;
                }                
            	Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                return nextExecDate;                	
            }
        });
    }


}
