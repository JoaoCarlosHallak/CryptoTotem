package com.hallak.TransactionFlowService.services;

import com.hallak.TransactionFlowService.OPF.WalletServiceClient;
import com.hallak.shared_libraries.dtos.TX;
import com.hallak.TransactionFlowService.dtos.TXRequest;
import com.hallak.TransactionFlowService.dtos.TXResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final WalletServiceClient walletServiceClient;
    private final Queue queue;
    private final RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    public TransactionServiceImpl(WalletServiceClient walletServiceClient, Queue queue, RabbitTemplate rabbitTemplate) {
        this.walletServiceClient = walletServiceClient;
        this.queue = queue;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public TXResponse getHashFromTransaction(TXRequest txRequest) {
        if (txRequest.originAddress().isBlank() || txRequest.destinyAddress().isBlank() || txRequest.amount() == null){
            throw new RuntimeException("Please complete all fields ->" + txRequest);
        }

        UUID nonce = UUID.randomUUID();
        return new TXResponse(makeHash(txRequest.originAddress(), txRequest.destinyAddress(), txRequest.amount(), nonce), nonce);
        // Agora vamos pegar e assinar essa hash com a privateKey em um ‘software’ ‘offline’. Lembrando de guardar o nonce, garantindo o determinismo da hash.








    }

    @Override
    public TX newTransaction(TXRequest txRequest) {
        if (txRequest.originAddress().isBlank()
                || txRequest.destinyAddress().isBlank()
                || txRequest.amount() == null
                || txRequest.hash().isBlank()
                || txRequest.signature().isBlank()
                || txRequest.nonce() == null) {
            throw new RuntimeException("Please complete all fields -> " + txRequest);
        }

        //
        TX tx = new TX();
        tx.setOriginAddress(txRequest.originAddress());
        tx.setDestinyAddress(txRequest.destinyAddress());


        //Devo verificar aqui se a wallet origem tem esse saldo. Isso vai se dar por uma comunicação síncrona com o WalletManagerService, pois atraves do endereco da carteira, ele vai retornar dados calculados que tem como fonte a blockchain ou ledger service
        // Isso vai ser temporário Mentira, na verdade a verificacao toda se da pelo NetworkValidationService
        tx.setAmount(txRequest.amount());

        tx.setCreatedAt(LocalDateTime.now());

        String generatedHash = makeHash(txRequest.originAddress(), txRequest.destinyAddress(), txRequest.amount(), txRequest.nonce());
        System.out.println("generated -> " + generatedHash + " | txRequestHash -> " + txRequest.hash() );
        if (!txRequest.hash().equals(makeHash(txRequest.originAddress(), txRequest.destinyAddress(), txRequest.amount(), txRequest.nonce()))) {
            throw new RuntimeException("This hash doesn't compatible with the transaction");
        }
        tx.setHash(txRequest.hash());
        tx.setSignature(txRequest.signature());
        tx.setNonce(txRequest.nonce());


        log.info("Sending TX -> {}", tx);
        rabbitTemplate.convertAndSend(queue.getName(), tx);
        return tx;
    }




    public static String makeHash(String originAddress, String destinyAddress, BigDecimal amount, UUID nonce){
        return DigestUtils.sha256Hex(originAddress + destinyAddress + amount + nonce);
    }

}
