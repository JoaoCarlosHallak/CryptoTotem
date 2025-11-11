package com.hallak.TransactionFlowService.OPF;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "wallet-service")
public interface WalletServiceClient {

    /*@GetMapping(value = "/address")
    WalletDTO findByAddress(@RequestParam String address);


     */
}
