package com.hallak.WalletService.dtos;

import java.time.LocalDateTime;

public record WalletDTO(String address,
                        String publicKey,
                        String privateKey,
                        LocalDateTime createdAt){
}
