package com.aplazo.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EnableAutoConfiguration
@ServletComponentScan
@SpringBootApplication
public class AplazoOnlineShoppingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AplazoOnlineShoppingSystemApplication.class, args);
	}

}
