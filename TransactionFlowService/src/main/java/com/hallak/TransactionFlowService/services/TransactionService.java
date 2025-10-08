package com.hallak.TransactionFlowService.services;


import com.hallak.TransactionFlowService.dtos.TX;
import com.hallak.TransactionFlowService.dtos.TXRequest;

public interface TransactionService {
    TX newTransaction(TXRequest txRequest);
}
