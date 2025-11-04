package com.example.NetworkValidationService.services;

import com.hallak.shared_libraries.dtos.TX;

public interface NetworkValidationService {
    void txValidator(TX tx);
}
