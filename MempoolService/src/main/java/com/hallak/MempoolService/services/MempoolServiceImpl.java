package com.hallak.MempoolService.services;

import com.hallak.MempoolService.repositories.TXRepository;
import com.hallak.shared_libraries.dtos.TX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MempoolServiceImpl implements MempoolService {

    private static final Logger log = LoggerFactory.getLogger(MempoolServiceImpl.class);
    private final TXRepository mempoolRepository;
    private static final long DEFAULT_TTL = 60 * 30; // 30 min


    @Autowired
    public MempoolServiceImpl(TXRepository mempoolRepository) {
        this.mempoolRepository = mempoolRepository;
    }

    @RabbitListener(queues = "${rabbitmq.queue.to.valid.tx}")
    public void listen(@Payload TX tx){
        log.info("🟢 Received valid TX -> {}", tx);
        mempoolRepository.save(tx, DEFAULT_TTL);
        log.info("🟢 TX stored in mempool");
    }
}


