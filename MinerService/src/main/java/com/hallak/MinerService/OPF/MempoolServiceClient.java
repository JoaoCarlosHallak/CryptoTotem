package com.hallak.MinerService.OPF;


import com.hallak.shared_libraries.dtos.TX;
import com.hallak.shared_libraries.dtos.TXtoMinerService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "mempool-service")
public interface MempoolServiceClient {

    @GetMapping(value = "/mempool/top-fees")
    List<TXtoMinerService> findByTopFees(@RequestParam int limit);



}
