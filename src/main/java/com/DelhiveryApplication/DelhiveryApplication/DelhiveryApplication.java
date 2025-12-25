package com.DelhiveryApplication.DelhiveryApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DelhiveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DelhiveryApplication.class, args);
	}

}
