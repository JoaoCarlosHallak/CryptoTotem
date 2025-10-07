package com.hallak.TransactionFlowService.OPF;

import com.hallak.shared_libraries.dtos.WalletDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wallet-service")
public interface WalletServiceClient {

    @GetMapping(value = "/findByAddress")
    WalletDTO findByAddress(@RequestParam String address);
}
