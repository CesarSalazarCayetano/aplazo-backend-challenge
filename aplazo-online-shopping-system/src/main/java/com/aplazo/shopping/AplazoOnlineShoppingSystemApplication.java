package com.aplazo.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AplazoOnlineShoppingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AplazoOnlineShoppingSystemApplication.class, args);
	}

}
