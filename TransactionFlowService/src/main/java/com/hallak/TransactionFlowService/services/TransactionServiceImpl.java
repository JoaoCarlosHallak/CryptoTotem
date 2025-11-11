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

import static com.hallak.shared_libraries.utils.Utils.makeHashSHA256TX;

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
        String hash = makeHashSHA256TX(txRequest.originAddress(), txRequest.destinyAddress(), txRequest.amount(), nonce);

        return new TXResponse(hash, nonce);
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


        String expectedHash = makeHashSHA256TX(txRequest.originAddress(), txRequest.destinyAddress(), txRequest.amount(), txRequest.nonce());
        if (!txRequest.hash().equals(expectedHash)) {
            throw new RuntimeException("This hash doesn't compatible with the transaction. Received: " + txRequest.hash() + "| Expected: " + expectedHash);
        }


        TX tx = new TX();
        tx.setOriginAddress(txRequest.originAddress());
        tx.setDestinyAddress(txRequest.destinyAddress());
        tx.setAmount(txRequest.amount());
        tx.setCreatedAt(LocalDateTime.now());
        tx.setHash(txRequest.hash());
        tx.setSignature(txRequest.signature());
        tx.setNonce(txRequest.nonce());

        log.info("Publishing TX to raw queue -> {}", tx.getHash());
        rabbitTemplate.convertAndSend(queue.getName(), tx);
        return tx;
    }




}
