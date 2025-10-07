package com.hallak.TransactionFlowService.controllers;

import com.hallak.TransactionFlowService.dtos.TX;
import com.hallak.TransactionFlowService.dtos.TXRequest;
import com.hallak.TransactionFlowService.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class TransactionFlowController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionFlowController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping
    public ResponseEntity<TX> newTransaction(@RequestBody TXRequest txRequest){
        return new ResponseEntity<>(transactionService.newTransaction(txRequest), HttpStatus.CREATED);

    }



}
