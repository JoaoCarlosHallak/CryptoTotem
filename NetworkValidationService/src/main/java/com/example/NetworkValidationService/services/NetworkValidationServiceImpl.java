package com.example.NetworkValidationService.services;

import com.hallak.shared_libraries.dtos.TX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class NetworkValidationServiceImpl implements NetworkValidationService {

    private static final Logger log = LoggerFactory.getLogger(NetworkValidationServiceImpl.class);


    @RabbitListener(queues = "${rabbitmq.queue.tx}")
    public void txValidator(@Payload TX tx){
        //Vai ficar faltando verificacao de saldo. (Criacao de ledgeService
        log.info("Received TX -> {}", tx);
        


    }





}
