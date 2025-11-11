package com.example.NetworkValidationService.services;

import com.example.NetworkValidationService.OPF.WalletServiceClient;
import com.hallak.shared_libraries.dtos.TX;
import static com.hallak.shared_libraries.utils.Utils.makeHashSHA256TX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@Service
public class NetworkValidationServiceImpl implements NetworkValidationService {

    private static final Logger log = LoggerFactory.getLogger(NetworkValidationServiceImpl.class);
    private final WalletServiceClient walletServiceClient;
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    @Autowired
    public NetworkValidationServiceImpl(WalletServiceClient walletServiceClient, RabbitTemplate rabbitTemplate, Queue queue) {
        this.walletServiceClient = walletServiceClient;
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }


    @RabbitListener(queues = "${rabbitmq.queue.tx}")
    public void txValidator(@Payload TX tx) {
        //Vai ficar faltando verificacao de saldo. (Criacao de ledgeService) | Depois pensar em um atributo nonce (Evitar repeticao)
        //        //    LedgerService.checkBalance(tx.getOriginAddress(), tx.getAmount());
        //        //    NonceRegistry.verifyAndLockNonce(tx.getOriginAddress(), tx.getNonce());
        // +- Isso
        log.info("Received TX -> {}", tx);

        String generatedHash = makeHashSHA256TX(tx.getOriginAddress(), tx.getDestinyAddress(), tx.getAmount(), tx.getNonce());

        if (!generatedHash.equals(tx.getHash())) {
            log.info("The hash does not match | generated: {}| tx: {}", generatedHash, tx.getHash());
            return;
        }


        String publicKey = walletServiceClient.getPublicKeyByAddress(tx.getOriginAddress());
        if (publicKey == null || publicKey.isBlank()) {
            log.warn("❌ Could not find public key for address {}", tx.getOriginAddress());
            return;
        }


        boolean isSignatureValid = verifySignature(publicKey, tx.getHash(), tx.getSignature());
        if (!isSignatureValid) {
            log.warn("❌ Invalid signature for TX {}", tx.getHash());
            return;
        }

        rabbitTemplate.convertAndSend(queue.getName(), tx);





    }

        private boolean verifySignature(String publicKeyBase64, String hash, String signatureBase64){
            try {
                byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("EC");
                PublicKey pubKey = keyFactory.generatePublic(keySpec);

                Signature verifier = Signature.getInstance("SHA256withECDSA");
                verifier.initVerify(pubKey);
                verifier.update(hash.getBytes());

                byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
                return verifier.verify(signatureBytes);
            } catch (Exception e) {
                log.error("Error verifying signature: {}", e.getMessage());
                return false;
            }
        }
    }
















