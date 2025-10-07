package com.hallak.TransactionFlowService.dtos;

import java.math.BigDecimal;

public record TXRequest(String originAddress, String destinyAddress, BigDecimal amount) {
}
