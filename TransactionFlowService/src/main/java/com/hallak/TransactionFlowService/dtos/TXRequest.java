package com.hallak.TransactionFlowService.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TXRequest(String originAddress, String destinyAddress, BigDecimal amount, String hash, String signature, UUID nonce) {
}
