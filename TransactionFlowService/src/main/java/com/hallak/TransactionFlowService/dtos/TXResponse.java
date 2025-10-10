package com.hallak.TransactionFlowService.dtos;

import java.util.UUID;

public record TXResponse(String hash, UUID nonce) {
}
