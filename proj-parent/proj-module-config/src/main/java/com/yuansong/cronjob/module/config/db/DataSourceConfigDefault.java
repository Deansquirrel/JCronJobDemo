package com.yuansong.cronjob.module.config.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "spring.datasource")
@Configuration(value="DataSourceConfigDefault")
public class DataSourceConfigDefault extends DataSourceConfig {

}
