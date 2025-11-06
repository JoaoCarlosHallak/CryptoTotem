package com.hallak.WalletService.services;

import com.hallak.WalletService.entities.Wallet;
import com.hallak.WalletService.repositories.WalletRepository;
import com.hallak.shared_libraries.dtos.WalletDTO;
import com.hallak.shared_libraries.dtos.WalletSingleWayDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService{

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
}

/*
    @Override
    public WalletSingleWayDTO newWallet() {
        String privateKey;
        String publicKey;
        String address;

        do {
            privateKey = String.valueOf(UUID.randomUUID());
            publicKey = "public" + DigestUtils.sha256Hex(privateKey);
            address = "address" + DigestUtils.sha256Hex(publicKey).substring(0, 20);
        } while (walletRepository.existsByAddress(address) || walletRepository.existsByPublicKey(publicKey));

        LocalDateTime now = LocalDateTime.now();

        walletRepository.save(new Wallet(address, publicKey, now));

        return new WalletSingleWayDTO(address, publicKey, privateKey, now);

    }
*/
    /*@Override
    public WalletDTO findByAddress(String address) {
        return modelMapper.map
                (walletRepository.findByAddress(address).orElseThrow(() -> new RuntimeException("Wallet not found for the given address")),
                        WalletDTO.class);
    }*/

