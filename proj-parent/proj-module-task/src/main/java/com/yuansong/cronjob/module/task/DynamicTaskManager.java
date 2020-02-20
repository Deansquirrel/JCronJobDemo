package com.yuansong.cronjob.module.task;

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
	
	private Map<String, ScheduledFuture<?>> futureMap = new HashMap<String, ScheduledFuture<?>>();
	
	private Map<String, AbstractTask> taskMap = new HashMap<String, AbstractTask>();
	
	@Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(10);
        return threadPoolTaskScheduler;
    }
	
	/**
	 * 添加任务
	 * @param task
	 * @throws Exception
	 */
	public synchronized void addTask(AbstractTask task) throws Exception {
		this.checkTask(task);
		if(this.futureMap.containsKey(task.getType())) {
			logger.warn("task {} is already exist", task.getType());
			this.removeTask(task.getType());
		}
		this.subAddTask(task.getType(), task.getCron(), task);
		this.taskMap.put(task.getType(), task);
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
		if(this.taskMap.containsKey(type)) {
			this.taskMap.remove(type);
		}
	}
	
	/**
	 * 刷新任务
	 * 	如：当cron配置发生变化后，需重新添加任务
	 * @param type
	 * @throws Exception
	 */
	public synchronized void refreshTask(String type) throws Exception {
		if(this.taskMap.containsKey(type)) {
			AbstractTask task = this.taskMap.get(type);
			this.checkTask(task);
			if(this.futureMap.containsKey(type)) {
				this.subRemoveTask(type);
				this.subAddTask(type, task.getCron(), task);
			}
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
		if(!this.futureMap.containsKey(type)) {
			throw new Exception("can not find task by key : " + type);
		}
		if(this.futureMap.containsKey(type)) {
			logger.warn("task {} is not pause", type);
		} else {
			AbstractTask task = this.taskMap.get(type);
			this.checkTask(task);
			this.subAddTask(task.getType(), task.getCron(), task);
		}
	}

	/**
	 * 任务检测
	 * @param task
	 * @throws Exception
	 */
	private void checkTask(AbstractTask task) throws Exception {
		this.testCron(task.getCron());
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
	
	private void subAddTask(String type, String cron, Runnable task) throws Exception {
		ScheduledFuture<?> future = this.threadPoolTaskScheduler.schedule(
				task, new CronTrigger(cron));
		if(future != null) {
			this.futureMap.put(type, future);
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
