package com.hallak.TransactionFlowService.controllers;

import com.hallak.TransactionFlowService.dtos.TX;
import com.hallak.TransactionFlowService.dtos.TXRequest;
import com.hallak.TransactionFlowService.dtos.TXResponse;
import com.hallak.TransactionFlowService.services.TransactionService;
import com.rabbitmq.client.AMQP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class TransactionFlowController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionFlowController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TXResponse> getHashFromTransaction(@RequestBody TXRequest txRequest){
        return new ResponseEntity<>(transactionService.getHashFromTransaction(txRequest), HttpStatus.CREATED);

    }

    @PostMapping(value = "newTX")
    public ResponseEntity<TX> newTransaction(@RequestBody TXRequest txRequest){
        return new ResponseEntity<>(transactionService.newTransaction(txRequest), HttpStatus.CREATED);
    }



}
