package com.hallak.WalletService.services;

import com.hallak.WalletService.entities.Wallet;
import com.hallak.WalletService.repositories.WalletRepository;
import com.hallak.shared_libraries.dtos.WalletDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService{

    private final ModelMapper modelMapper;
    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(ModelMapper modelMapper, WalletRepository walletRepository) {
        this.modelMapper = modelMapper;
        this.walletRepository = walletRepository;
    }


    @Override
    public WalletDTO newWallet() {
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

        return new WalletDTO(address, publicKey, privateKey, now);

    }
}
