package com.knullci.knull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.knullci.knull",
		"com.knullci.necrosword"
})
public class KnullApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnullApplication.class, args);
	}

}
