package com.yuansong.cronjob.bootdemocron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 功能测试项目
 * 	task 定时任务，通过管理器控制，支持暂停、恢复
 * 	scheduler 定时任务，不支持暂停，可更新运行时间（下次运行后生效）
 * @author yuansong
 *
 */
@SpringBootApplication(scanBasePackages="com.yuansong.cronjob")
@EnableScheduling
public class BootDemoCronApplication {

	public static void main(String[] args) {
		
		long beginTime = System.currentTimeMillis();
		
		SpringApplication.run(BootDemoCronApplication.class, args);
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("\n" + 
				"-----------------------------------------------------------------------\n" +
                "-----------------------------------------------------------------------\n" +
                "                               _     _\n" +
                "                              ( \\---/ )\n" +
                "                               ) . . (\n" +
                "-------------------------,--._(___Y___)_,--.---------------------------\n" +
                "                         `--'           `--'\n" +
                "                        Demo 启动成功                         \n" +
                "\n" +
                " 启动耗时: " + (endTime - beginTime) + "毫秒 \n" +
//                " 端口: 7060 \n" +
//                " 监控系统访问地址: http://127.0.0.1:7050/shangzhou_20190801_0000.html \n" +
                "-----------------------------------------------------------------------\n" +
                "-----------------------------------------------------------------------\n");
	}
	
}
