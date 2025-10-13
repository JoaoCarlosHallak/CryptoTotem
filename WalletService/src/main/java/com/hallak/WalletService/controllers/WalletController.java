package com.hallak.WalletService.controllers;

import com.hallak.WalletService.services.WalletService;
import com.hallak.shared_libraries.dtos.WalletDTO;
import com.hallak.shared_libraries.dtos.WalletSingleWayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @PostMapping
    public ResponseEntity<WalletSingleWayDTO> newWallet(){
        return new ResponseEntity<>(walletService.newWallet(), HttpStatus.CREATED);
    }

    /*@GetMapping(value = "/address")
    public ResponseEntity<WalletDTO> findByAddress(@RequestParam String address){
        return new ResponseEntity<>(walletService.findByAddress(address), HttpStatus.OK);

    }*/








}
