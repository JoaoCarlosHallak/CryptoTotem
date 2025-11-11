package com.example.NetworkValidationService;

import com.hallak.shared_libraries.config.Async.SharedMQCommonConfig;
import com.hallak.shared_libraries.config.Async.SharedMQCommonConsumerConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
@FeignClient
@Import({SharedMQCommonConfig.class, SharedMQCommonConsumerConfig.class})
public class NetworkValidationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkValidationServiceApplication.class, args);
	}

}
