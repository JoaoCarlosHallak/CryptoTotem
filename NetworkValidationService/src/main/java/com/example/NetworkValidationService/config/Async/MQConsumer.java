package com.example.NetworkValidationService.config.Async;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConsumer {

    @Value("${rabbitmq.queue.tx}")
    private String deliveryQueueName;


    @Bean
    public Declarables declarables() {
        Queue delivery = new Queue(deliveryQueueName, true, false, false);
        DirectExchange exchange = ExchangeBuilder.directExchange("app.exchange").durable(true).build();
        Binding bDelivery = BindingBuilder.bind(delivery).to(exchange).with("tx.routing");
        return new Declarables(delivery, exchange, bDelivery);
    }


}


