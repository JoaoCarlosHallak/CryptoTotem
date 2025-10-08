package com.hallak.shared_libraries.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletSingleWayDTO {


    private String address;
    private String publicKey;
    private String privateKey;
    private LocalDateTime createdAt;
}
