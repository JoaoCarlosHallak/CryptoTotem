package com.hallak.WalletService.services;

import com.hallak.shared_libraries.dtos.WalletSingleWayDTO;

public interface WalletService {
    WalletSingleWayDTO newWallet();
    String getPublicKeyByAddress(String address);
}
