package com.yuansong.cronjob.bootdemocron.cron;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * 动态任务管理
 * @author yuansong6
 *
 */
@Component
public class DynamicTaskManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicTaskManager.class);
	
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
//	@Autowired
	private Map<String, ScheduledFuture<?>> futureMap = new HashMap<String, ScheduledFuture<?>>();
	
//	@Autowired
	private Map<String, DynamicTaskConfig> configMap = new HashMap<String, DynamicTaskConfig>();
	
	@Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(10);
        return threadPoolTaskScheduler;
    }
	
	@Bean
	public Map<String, ScheduledFuture<?>> futureMap() {
		return new HashMap<String, ScheduledFuture<?>>();
	}
	
	@Bean
	public Map<String, DynamicTaskConfig> configMap() {
		return new HashMap<String, DynamicTaskConfig>();
	}
	
	/**
	 * 添加任务
	 * @param config
	 * @throws Exception
	 */
	public synchronized void addTask(DynamicTaskConfig config) throws Exception {
		this.testCron(config.getCron());
		if(this.futureMap.containsKey(config.getType())) {
			logger.warn("task {} is already exist", config.getType());
			this.removeTask(config.getType());
		}
		this.subAddTask(config);
		this.configMap.put(config.getType(), config);
	}
	
	
	/**
	 * 移除任务
	 * @param type
	 * @throws Exception
	 */
	public synchronized void removeTask(String type) throws Exception {
		if(this.futureMap.containsKey(type)) {
			this.subRemoveTask(type);				
		}
		if(this.configMap.containsKey(type)) {
			this.configMap.remove(type);
		}
	}
	
	
	
	/**
	 * 暂停任务
	 * @param type
	 * @throws Exception 
	 */
	public void pauseTask(String type) throws Exception {
		if(this.futureMap.containsKey(type)) {
			this.subRemoveTask(type);
		}
	}
	
	/**
	 * 恢复任务
	 * @param type
	 * @throws Exception 
	 */
	public void resumeTask(String type) throws Exception {
		if(!this.configMap.containsKey(type)) {
			throw new Exception("can not find task config with key : " + type);
		}
		if(this.futureMap.containsKey(type)) {
			logger.warn("task {} is not pause", type);
		} else {
			this.subAddTask(this.configMap.get(type));
		}
	}
	
	/**
	 * 测试 cron 公式是否有效
	 * @param cron
	 * @throws Exception
	 */
	private void testCron(String cron) throws Exception {
		logger.debug("test cron: {}", cron);
		ScheduledFuture<?> future = null;
		try{
			future = this.threadPoolTaskScheduler.schedule(new Runnable() {
				@Override
				public void run() {
					logger.debug("job cron: {}", cron);
				}
			}, new CronTrigger(cron));
		} catch(Exception e) {
			throw e;
		} finally {
			if(future != null) {
				future.cancel(true);
			}
		}
	}
	
	private void subAddTask(DynamicTaskConfig config) throws Exception {
		ScheduledFuture<?> future = this.threadPoolTaskScheduler.schedule(
				config.getTask(), new CronTrigger(config.getCron()));
		if(future != null) {
			this.futureMap.put(config.getType(), future);
		} else {
			throw new Exception("add task failed");
		}
	}
	
	private void subRemoveTask(String type) throws Exception {
		ScheduledFuture<?> future = this.futureMap.get(type);
		if(future == null) {
			throw new Exception("future can not be null");
		}
		boolean result = future.cancel(false);
		if(!result) {
			throw new Exception("cancel task failed");
		}
		this.futureMap.remove(type);
	}
	
}
