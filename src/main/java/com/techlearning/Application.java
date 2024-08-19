package com.techlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
//@OpenAPIDefinition(info = @Info(title = "Demo API", version = "2.0", description = "Swagger API Demo"))
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
