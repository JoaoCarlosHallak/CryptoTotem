package com.hallak.TransactionFlowService.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TX {
    private UUID id;
    private String originAddress;
    private String destinyAddress;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private String signature;
    private String hash;

    public TX(String originAddress, String destinyAddress, BigDecimal amount, LocalDateTime createdAt, String signature, String hash) {
        this.id = UUID.randomUUID();
        this.originAddress = originAddress;
        this.destinyAddress = destinyAddress;
        this.amount = amount;
        this.createdAt = createdAt;
        this.signature = signature;
        this.hash = hash;
    }
}
