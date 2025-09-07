package com.faxriafrasiyab.reliable.orders;

import com.faxriafrasiyab.reliable.orders.config.AppTopics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppTopics.class)
public class ReliableOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReliableOrdersApplication.class, args);
	}

}
