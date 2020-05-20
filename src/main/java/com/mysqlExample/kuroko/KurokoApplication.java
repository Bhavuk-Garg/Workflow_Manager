package com.mysqlExample.kuroko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KurokoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KurokoApplication.class, args);
	}

}
