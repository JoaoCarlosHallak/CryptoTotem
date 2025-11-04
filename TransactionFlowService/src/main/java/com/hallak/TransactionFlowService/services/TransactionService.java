package com.hallak.TransactionFlowService.services;


import com.hallak.shared_libraries.dtos.TX;
import com.hallak.TransactionFlowService.dtos.TXRequest;
import com.hallak.TransactionFlowService.dtos.TXResponse;

public interface TransactionService {
    TXResponse getHashFromTransaction(TXRequest txRequest);
    TX newTransaction(TXRequest txRequest);
}
