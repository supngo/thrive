package com.naturecode.thrive.properties;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class PropertiesApplication {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy");

	public static void main(String[] args) {
		SpringApplication.run(PropertiesApplication.class, args);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		log.info("Properties Application started at {}", sdf.format(timestamp));
	}

	@Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
			.info(new Info().title("Property Site Integration Management API")
			.description("Api services for managing Site Integration")
			.version("v1.0.0")
			.license(new License().name("Api License Name").url("API License URL")));
  }
}
