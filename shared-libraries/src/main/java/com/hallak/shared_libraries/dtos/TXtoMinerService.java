package com.hallak.shared_libraries.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TXtoMinerService{
    private String originAddress;
    private String destinyAddress;
    private BigDecimal amount;
    private BigDecimal fee;
    private String signature;
    private String hash;
    private UUID nonce;
}
