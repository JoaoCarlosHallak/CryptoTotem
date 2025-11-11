package com.example.NetworkValidationService.config.Async;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQProducer {

    @Bean
    public Queue queueToSaveNewDelivery(@Value("${rabbitmq.queue.to.valid.tx}") String queueName) {
        return QueueBuilder.durable(queueName).build();
    }
}

