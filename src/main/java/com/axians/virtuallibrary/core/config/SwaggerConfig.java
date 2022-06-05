package com.axians.virtuallibrary.core.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private final String APIPACKAGE = "com.axians.virtuallibrary.api.rest";
	
	private final String DEVNAME = "Rafael Brito";
	private final String DEVURL = "https://www.linkedin.com/in/rafabrito7";
	private final String DEVEMAIL = "rafabrito.office@gmail.com";
	
	@Bean
	public Docket virtualLibraryApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(APIPACKAGE))
				.paths(PathSelectors.regex("/api.*"))
				.build()
				.apiInfo(metaInfo());
	}
	
	private ApiInfo metaInfo() {
		return new ApiInfo(
				"Virtual Library API", 
				"Challenge of Axians with Spring Family", 
				"1.0", 
				"Terms of Service", 
				new Contact(DEVNAME, DEVURL, DEVEMAIL), 
				"License for Educational", 
				"empty", new ArrayList<>());
	}

}
