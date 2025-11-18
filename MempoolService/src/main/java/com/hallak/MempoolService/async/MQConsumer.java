package com.hallak.MempoolService.async;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConsumer {

    @Value("${rabbitmq.queue.to.valid.tx}")
    private String deliveryQueueName;


    @Bean
    public Declarables declarables() {
        Queue delivery = new Queue(deliveryQueueName, true, false, false);
        DirectExchange exchange = ExchangeBuilder.directExchange("app.exchange").durable(true).build();
        Binding bDelivery = BindingBuilder.bind(delivery).to(exchange).with("tx.routing");
        return new Declarables(delivery, exchange, bDelivery);
    }


}


