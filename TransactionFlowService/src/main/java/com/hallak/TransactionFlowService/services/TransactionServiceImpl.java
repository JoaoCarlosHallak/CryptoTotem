package com.hallak.TransactionFlowService.services;

import com.hallak.TransactionFlowService.OPF.WalletServiceClient;
import com.hallak.TransactionFlowService.dtos.TX;
import com.hallak.TransactionFlowService.dtos.TXRequest;
import com.hallak.shared_libraries.dtos.WalletDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final WalletServiceClient walletServiceClient;

    @Autowired
    public TransactionServiceImpl(WalletServiceClient walletServiceClient) {
        this.walletServiceClient = walletServiceClient;
    }

    @Override
    public TX newTransaction(TXRequest txRequest) {
        WalletDTO originWallet = walletServiceClient.findByAddress(txRequest.originAddress());
        WalletDTO destinyWallet = walletServiceClient.findByAddress(txRequest.destinyAddress());
        TX tx = new TX();
        tx.setOriginAddress(originWallet.getAddress());
        tx.setDestinyAddress(destinyWallet.getAddress());

        //Devo verificar aqui se a wallet origem tem esse saldo. Isso vai se dar por uma comunicacao sincrona com o WalletManagerService, pois atraves do endereco da carteira, ele vai retornar dados calculados que tem como fonte a blockchain ou ledger service
        // Isso vai ser temporario
        tx.setAmount(txRequest.amount());
        tx.setCreatedAt(LocalDateTime.now());


    }
}
