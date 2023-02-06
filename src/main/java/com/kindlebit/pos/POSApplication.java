package com.kindlebit.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class POSApplication {

	public static void main(String[] args) {
    SpringApplication.run(POSApplication.class, args);
	}

}
