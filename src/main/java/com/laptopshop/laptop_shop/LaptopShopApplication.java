package com.laptopshop.laptop_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
// @SpringBootApplication(exclude = { SecurityAutoConfiguration.class,
// ManagementWebSecurityAutoConfiguration.class })

public class LaptopShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptopShopApplication.class, args);
	}

}
