package com.hallak.TransactionFlowService.services;

import com.hallak.TransactionFlowService.dtos.TX;
import com.hallak.TransactionFlowService.dtos.TXRequest;
import com.hallak.shared_libraries.dtos.WalletDTO;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{


    @Override
    public TX newTransaction(TXRequest txRequest) {
        WalletDTO originWallet = walletServiceClient.get(txRequest.originAddress());
        WalletDTO destinyWallet = walletServiceClient.get(txRequest.destinyAddress());

    }
}
