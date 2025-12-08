package com.hallak.MempoolService.services;


import com.hallak.MempoolService.repositories.TXRepository;
import com.hallak.shared_libraries.dtos.TX;
import com.hallak.shared_libraries.dtos.TXtoMinerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MempoolServiceImpl implements MempoolService {

    private static final Logger log = LoggerFactory.getLogger(MempoolServiceImpl.class);
    private final TXRepository mempoolRepository;
    private static final long DEFAULT_TTL = 60 * 30;// 30 min
    private final ModelMapper modelMapper;



    @Autowired
    public MempoolServiceImpl(TXRepository mempoolRepository, ModelMapper modelMapper) {
        this.mempoolRepository = mempoolRepository;
        this.modelMapper = modelMapper;
    }

    @RabbitListener(queues = "${rabbitmq.queue.to.valid.tx}")
    public void listen(@Payload TX tx){
        log.info("🟢 Received valid TX -> {}", tx);
        mempoolRepository.save(tx, DEFAULT_TTL);
        log.info("🟢 TX stored in mempool");
    }

    @Override
    public List<TXtoMinerService> findByTopFees(int limit) {
        return mempoolRepository.findTopNByFee(limit).stream().map(x -> modelMapper.map(x, TXtoMinerService.class)).toList();
    }
}


