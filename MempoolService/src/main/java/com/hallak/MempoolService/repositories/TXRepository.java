package com.hallak.MempoolService.repositories;

import com.hallak.shared_libraries.dtos.TX;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TXRepository {

    private final RedisTemplate<String, TX> txRedis;     // armazena TX completas
    private final RedisTemplate<String, String> stringRedis; // armazena hashes no SET

    private static final String TX_KEY_PREFIX = "tx:";
    private static final String MEMPOOL_SET = "mempool:hashes";

    public TXRepository(
            RedisTemplate<String, TX> txRedisTemplate,
            RedisTemplate<String, String> redisStringTemplate
    ) {
        this.txRedis = txRedisTemplate;
        this.stringRedis = redisStringTemplate;
    }

    // Salva TX com TTL e adiciona o hash ao SET da mempool
    public void save(TX tx, long ttlSeconds) {
        String key = TX_KEY_PREFIX + tx.getHash();

        // 1 — salvar TX completa
        txRedis.opsForValue().set(key, tx, Duration.ofSeconds(ttlSeconds));

        // 2 — colocar hash no SET
        stringRedis.opsForSet().add(MEMPOOL_SET, tx.getHash());
    }

    // Busca TX pelo hash
    public TX findByHash(String hash) {
        return txRedis.opsForValue().get(TX_KEY_PREFIX + hash);
    }

    // Remove TX completamente
    public void remove(String hash) {
        txRedis.delete(TX_KEY_PREFIX + hash);
        stringRedis.opsForSet().remove(MEMPOOL_SET, hash);
    }

    // Pega todas as transações do mempool
    public List<TX> findAll() {
        Set<String> hashes = stringRedis.opsForSet().members(MEMPOOL_SET);

        if (hashes == null) return List.of();

        return hashes.stream()
                .map(this::findByHash)
                .collect(Collectors.toList());
    }
}
