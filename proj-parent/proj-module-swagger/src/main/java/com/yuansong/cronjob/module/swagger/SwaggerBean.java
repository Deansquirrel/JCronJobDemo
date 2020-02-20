package com.yuansong.cronjob.module.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yuansong.cronjob.module.config.ProjectConfig;
import com.yuansong.cronjob.module.config.SwaggerConfig;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerBean {
	
	@Autowired
	private ProjectConfig projectConfig;
	
	@Autowired
	private SwaggerConfig swaggerConfig;
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(this.swaggerConfig==null ? false :
					this.swaggerConfig.isEnable() == null ? false : this.swaggerConfig.isEnable())
				.apiInfo(this.apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.yuansong.cronjob"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		String title = "";
		title = this.projectConfig == null ? title : 
			this.projectConfig.getName() == null ? title : this.projectConfig.getName();
		title = this.swaggerConfig == null ? title : 
			this.swaggerConfig.getTitle() == null ? title : this.swaggerConfig.getTitle();
		title = title.trim();

		String description = "";
		description = this.projectConfig == null ? "" : 
			this.projectConfig.getDescription() == null ? description : this.projectConfig.getDescription();
		description = this.swaggerConfig == null ? description : 
			this.swaggerConfig.getDescription() == null ? description : this.swaggerConfig.getDescription();
		description = description.trim();
		
		String version = "";
		version = this.projectConfig == null ? "" : 
			this.projectConfig.getVersion() == null ? version : this.projectConfig.getVersion();
		version = this.swaggerConfig == null ? version : 
			this.swaggerConfig.getVersion() == null ? version : this.swaggerConfig.getVersion();
		version = version.trim();
		
		return new ApiInfoBuilder()
				.title(title == "" ? "API" : title)
				.description(description)
				.version(version)
				.build();
	}
}
