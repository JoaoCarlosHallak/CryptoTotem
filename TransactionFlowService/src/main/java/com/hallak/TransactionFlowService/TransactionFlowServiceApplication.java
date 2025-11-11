package com.hallak.TransactionFlowService;

import com.hallak.shared_libraries.config.Async.SharedMQCommonConfig;
import com.hallak.shared_libraries.config.Async.SharedMQCommonProducerConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableRabbit
@Import({SharedMQCommonConfig.class, SharedMQCommonProducerConfig.class})
public class TransactionFlowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionFlowServiceApplication.class, args);
	}

}
