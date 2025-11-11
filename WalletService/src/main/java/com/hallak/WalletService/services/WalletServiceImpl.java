package com.hallak.WalletService.services;

import com.hallak.WalletService.entities.Wallet;
import com.hallak.WalletService.repositories.WalletRepository;
import com.hallak.shared_libraries.dtos.WalletSingleWayDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(ModelMapper modelMapper, WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletSingleWayDTO newWallet() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            keyGen.initialize(256);
            KeyPair keyPair = keyGen.generateKeyPair();

            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(publicKey.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) hex.append(String.format("%02x", b));
            String address = "address" + hex.substring(0, 20);
            //Obs: Ex (publicKey = joaocarlos) -> sha(joaocarlos) -> xxxxx..... -> Trunca(20 primeiros -> xxxxx... -> resultado = addressxxxxxxxxxxxxxxxx...


            if (walletRepository.existsByAddress(address) || walletRepository.existsByPublicKey(publicKey)) {
                return newWallet();
            }

            LocalDateTime now = LocalDateTime.now();
            walletRepository.save(new Wallet(address, publicKey, now));

            return new WalletSingleWayDTO(address, publicKey, privateKey, now);


        } catch (Exception e) {
            throw new RuntimeException("Failed to generate wallet", e);
        }
    }


    @Override
    public String getPublicKeyByAddress(String address) {
        return walletRepository.findByAddress(address).orElseThrow(() -> new RuntimeException("Invalid Address")).getPublicKey();
    }
}


