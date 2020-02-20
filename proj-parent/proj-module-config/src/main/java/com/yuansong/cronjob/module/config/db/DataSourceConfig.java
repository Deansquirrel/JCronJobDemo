package com.yuansong.cronjob.module.config.db;

public class DataSourceConfig {
	
	private String name;
	private String url;
	private String username;
	private String password;
	private Integer maxWait;
	private Integer maxConn;
	private Integer minConn;
	private Integer queryTimeout;
	private String validationQuery;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}
	public Integer getMaxConn() {
		return maxConn;
	}
	public void setMaxConn(Integer maxConn) {
		this.maxConn = maxConn;
	}
	public Integer getMinConn() {
		return minConn;
	}
	public void setMinConn(Integer minConn) {
		this.minConn = minConn;
	}
	public Integer getQueryTimeout() {
		return queryTimeout;
	}
	public void setQueryTimeout(Integer queryTimeout) {
		this.queryTimeout = queryTimeout;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
}
