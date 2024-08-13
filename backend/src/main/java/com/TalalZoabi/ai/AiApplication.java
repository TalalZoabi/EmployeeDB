package com.TalalZoabi.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.TalalZoabi.ai")
@EntityScan(basePackages = "com.TalalZoabi.ai")
public class AiApplication {


	public static void main(String[] args) {
		SpringApplication.run(AiApplication.class, args);
	}
}
