package com.hallak.MempoolService.services;

import com.hallak.shared_libraries.dtos.TX;

public interface MempoolService {
    void listen(TX tx);
}
