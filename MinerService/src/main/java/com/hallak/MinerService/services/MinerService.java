package com.hallak.MinerService.services;

import com.hallak.shared_libraries.dtos.TX;
import com.hallak.shared_libraries.dtos.TXtoMinerService;

import java.util.List;

public interface MinerService {
    List<TXtoMinerService> findByTopFees(int limit);
}
