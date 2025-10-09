package com.example.OfflineSigner.Runner;

import com.example.OfflineSigner.Signer.OfflineSigner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        OfflineSigner.getSignature();

    }
}
