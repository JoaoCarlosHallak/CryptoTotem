package com.hallak.WalletService.controllers;

import com.hallak.WalletService.dtos.WalletDTO;
import com.hallak.WalletService.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @PostMapping
    public ResponseEntity<WalletDTO> newWallet(){
        return new ResponseEntity<>(walletService.newWallet(), HttpStatus.CREATED);


    }








}
