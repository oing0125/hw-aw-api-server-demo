package com.hanhwalife.gateway;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	RouteDefinitionLocator locator;

	@Bean
	public CommandLineRunner registerVisibleDefinition(SwaggerUiConfigParameters swaggerUiConfigParameters){
		String visibleDefinitionTxt = env.getProperty("hw.swagger.visible-definition");
		System.out.println("visible definition: " + visibleDefinitionTxt);
		System.out.println("swagger config url: " + swaggerUiConfigParameters.getConfigUrl());
		System.out.println("swagger ui root path: " + swaggerUiConfigParameters.getUiRootPath());
		System.out.println("swagger url: " + swaggerUiConfigParameters.getUrl());
		System.out.println("swagger path: " + swaggerUiConfigParameters.getPath());
		System.out.println("swagger validator url: " + swaggerUiConfigParameters.getValidatorUrl());
		if(visibleDefinitionTxt != null && !"".equals(visibleDefinitionTxt.trim())){
			String[] visibleDefinitionArr = visibleDefinitionTxt.split(",");
			return args -> Arrays.stream(visibleDefinitionArr)
					.map(txt -> txt.trim())
					.forEach(swaggerUiConfigParameters::addGroup);
		}else{
			return null;
		}
	}
//	@Bean
//	public CommandLineRunner openApiGroups(RouteDefinitionLocator locator, SwaggerUiConfigParameters swaggerUiParameters) {
//		return args -> locator
//				.getRouteDefinitions().collectList().block()
//				.stream()
//				.map(RouteDefinition::getId)
//				.forEach(swaggerUiParameters::addGroup);
//	}
}
