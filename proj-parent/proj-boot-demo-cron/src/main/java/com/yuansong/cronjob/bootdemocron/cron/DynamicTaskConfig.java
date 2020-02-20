package com.yuansong.cronjob.bootdemocron.cron;

public class DynamicTaskConfig {
	
	private String type;
	private Runnable task;
	private String cron;
	
	public DynamicTaskConfig() {
		
	}
	
	public DynamicTaskConfig(String type, Runnable task, String cron) {
		this.type = type;
		this.task = task;
		this.cron = cron;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public Runnable getTask() {
		return task;
	}
	public void setTask(Runnable task) {
		this.task = task;
	}

}
