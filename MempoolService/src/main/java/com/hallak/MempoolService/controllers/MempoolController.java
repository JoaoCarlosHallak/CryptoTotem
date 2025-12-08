package com.hallak.MempoolService.controllers;


import com.hallak.MempoolService.services.MempoolService;
import com.hallak.shared_libraries.dtos.TXtoMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/mempool/")
public class MempoolController {

    @Autowired
    private MempoolService mempoolService;

    @GetMapping("top-fees")
    public ResponseEntity<List<TXtoMinerService>> findByTopFees(@RequestParam int limit) {
        return new ResponseEntity<>(mempoolService.findByTopFees(limit), HttpStatus.OK);
    }
}
