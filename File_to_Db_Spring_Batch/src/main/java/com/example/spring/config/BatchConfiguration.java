package com.example.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@RefreshScope
public class BatchConfiguration {

	@Value("${filetodb.datasource.url}")
	private String url;

	@Value("${filetodb.datasource.username}")
	private String username;

	@Value("${filetodb.datasource.password}")
	private String password;

	@Value("${filetodb.datasource.driverClassName}")
	private String driverClassName;

	@Value("${filetodb.datasource.sqlquery}")
	private String sqlquery;

	@Value("${filetodb.sourcepath}")
	private String sourcepath;

	@Value("${filetodb.chunksize}")
	private Integer chunksize;

}
