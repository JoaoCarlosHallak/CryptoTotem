package com.example.NetworkValidationService;

import com.hallak.shared_libraries.dtos.config.Async.SharedMQCommonConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
@Import({SharedMQCommonConfig.class})
public class NetworkValidationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkValidationServiceApplication.class, args);
	}

}
