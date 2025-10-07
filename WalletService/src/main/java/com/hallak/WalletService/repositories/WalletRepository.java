package com.hallak.WalletService.repositories;

import com.hallak.WalletService.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    boolean existsByAddress(String address);

    boolean existsByPublicKey(String publicKey);
}
