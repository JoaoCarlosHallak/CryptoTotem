package com.hallak.MinerService.services;

import com.hallak.MinerService.OPF.MempoolServiceClient;
import com.hallak.shared_libraries.dtos.TX;
import com.hallak.shared_libraries.dtos.TXtoMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MinerServiceImpl implements MinerService {

    private final MempoolServiceClient mempoolServiceClient;

    @Autowired
    public MinerServiceImpl(MempoolServiceClient mempoolServiceClient) {
        this.mempoolServiceClient = mempoolServiceClient;
    }


    @Override
    public List<TXtoMinerService> findByTopFees(int limit) {
        // Mais pra frente e bacana limitar o acesso de tal endpoint apenas para o servico Miner.
        return mempoolServiceClient.findByTopFees(limit);
    }
}
