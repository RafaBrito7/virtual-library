package com.axians.virtuallibrary.core.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.security.SecurityScheme.In;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
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
				.useDefaultResponseMessages(false)
		        .securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
		        .securityContexts(Arrays.asList(securityContext()))
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
	
	private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .forPaths(PathSelectors.ant("/api/**"))
	        .build();
	}
	
	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	        = new AuthorizationScope("ROOT", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Arrays.asList(
	        new SecurityReference("Token Access", authorizationScopes));
	}

}
