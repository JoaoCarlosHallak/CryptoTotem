package com.hallak.MinerService.controllers;

import com.hallak.MinerService.services.MinerServiceImpl;
import com.hallak.shared_libraries.dtos.TX;
import com.hallak.shared_libraries.dtos.TXtoMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class MinerController {

    private final MinerServiceImpl minerService;

    @Autowired
    public MinerController(MinerServiceImpl minerService) {
        this.minerService = minerService;
    }


    @GetMapping
    public ResponseEntity<List<TXtoMinerService>> findByTopFees(@RequestParam int limit){
        return new ResponseEntity<>(minerService.findByTopFees(limit), HttpStatus.OK);
    }


}
