package com.hallak.shared_libraries.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TX {
    private UUID id;
    private String originAddress;
    private String destinyAddress;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private BigDecimal fee;
    private String signature;
    private String hash;
    private UUID nonce;


    public TX(String originAddress, String destinyAddress, BigDecimal amount, LocalDateTime createdAt, String signature, String hash, UUID nonce, BigDecimal fee) {
        this.id = UUID.randomUUID();
        this.originAddress = originAddress;
        this.destinyAddress = destinyAddress;
        this.amount = amount;
        this.createdAt = createdAt;
        this.signature = signature;
        this.hash = hash;
        this.nonce = nonce;
        this.fee = fee;
    }

    public TX() {
        this.id = UUID.randomUUID();
    }
}
