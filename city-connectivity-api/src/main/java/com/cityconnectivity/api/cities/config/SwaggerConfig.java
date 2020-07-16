package com.cityconnectivity.api.cities.config;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket connectivityService() {

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/connected"))
				.build()
				.apiInfo(serviceMetadata());
	}

	private ApiInfo serviceMetadata() {

		return new ApiInfo("City Connectivity API", 
				"Check connectivity between 2 cities.", 
				"1.0", 
				StringUtils.EMPTY, 
				new Contact("Ajay Venkataraman", StringUtils.EMPTY, StringUtils.EMPTY), 
				StringUtils.EMPTY, 
				StringUtils.EMPTY, 
				Collections.emptyList());
	}
}
