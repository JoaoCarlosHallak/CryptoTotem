package com.hallak.WalletService.services;

import com.hallak.shared_libraries.dtos.WalletDTO;
import com.hallak.shared_libraries.dtos.WalletSingleWayDTO;

public interface WalletService {
    WalletSingleWayDTO newWallet();
    WalletDTO findByAddress(String address);
}
