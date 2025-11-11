package com.example.NetworkValidationService.OPF;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wallet-service")
public interface WalletServiceClient {

    @GetMapping(value = "/address")
    String getPublicKeyByAddress(@RequestParam String address);



}
