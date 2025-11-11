package com.hallak.MempoolService;

import com.hallak.shared_libraries.config.Async.SharedMQCommonConfig;
import com.hallak.shared_libraries.config.Async.SharedMQCommonConsumerConfig;
import com.hallak.shared_libraries.config.Async.SharedMQCommonProducerConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
@Import({SharedMQCommonConfig.class, SharedMQCommonConsumerConfig.class})
public class MempoolServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MempoolServiceApplication.class, args);
	}

}
