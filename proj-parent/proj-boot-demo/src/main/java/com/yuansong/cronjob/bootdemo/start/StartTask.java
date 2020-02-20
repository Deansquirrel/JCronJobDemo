package com.yuansong.cronjob.bootdemo.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.yuansong.cronjob.bootdemo.task.TaskOne;
import com.yuansong.cronjob.module.task.DynamicTaskManager;

@Component
public class StartTask implements ApplicationRunner {
	
	@Autowired
	private TaskOne taskOne;
	
//	@Autowired
//	private TaskTwo taskTwo;
	
	@Autowired
	private DynamicTaskManager dynamicTaskManager;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.dynamicTaskManager.addTask(taskOne);
//		this.dynamicTaskManager.addTask(taskTwo);
	}

}
