package com.axians.virtuallibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableJpaRepositories
@SpringBootApplication
public class VirtualLibraryApplication{

	public static void main(String[] args) {
		SpringApplication.run(VirtualLibraryApplication.class, args);
	}

}
