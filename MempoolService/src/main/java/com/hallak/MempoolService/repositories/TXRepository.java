package com.hallak.MempoolService.repositories;

import com.hallak.shared_libraries.dtos.TX;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TXRepository {

    private final RedisTemplate<String, TX> txRedis;
    private final RedisTemplate<String, String> stringRedis;

    private static final String TX_KEY_PREFIX = "tx:";
    private static final String MEMPOOL_SET = "mempool:hashes";
    private static final String MEMPOOL_ZSET = "mempool:fees";

    public TXRepository(
            RedisTemplate<String, TX> txRedisTemplate,
            RedisTemplate<String, String> redisStringTemplate
    ) {
        this.txRedis = txRedisTemplate;
        this.stringRedis = redisStringTemplate;
    }

    // Salva TX com TTL e cria índices
    public void save(TX tx, long ttlSeconds) {
        String key = TX_KEY_PREFIX + tx.getHash();

        // 1 — salvar TX com TTL
        txRedis.opsForValue().set(key, tx, Duration.ofSeconds(ttlSeconds));

        // 2 — indexar no SET principal
        stringRedis.opsForSet().add(MEMPOOL_SET, tx.getHash());

        // 3 — indexar no ZSET pelo fee
        stringRedis.opsForZSet().add(MEMPOOL_ZSET, tx.getHash(), tx.getFee().doubleValue());
    }

    // Busca TX pelo hash
    public TX findByHash(String hash) {
        TX tx = txRedis.opsForValue().get(TX_KEY_PREFIX + hash);

        if (tx == null) {
            stringRedis.opsForSet().remove(MEMPOOL_SET, hash);
            stringRedis.opsForZSet().remove(MEMPOOL_ZSET, hash);
        }

        return tx;
    }

    // Busca a hash com maior fee
    public String findHighestFeeHash() {
        Set<String> result = stringRedis.opsForZSet()
                .reverseRange(MEMPOOL_ZSET, 0, 0);

        if (result == null || result.isEmpty()) return null;

        return result.iterator().next();
    }

    // Remove TX completamente
    public void remove(String hash) {
        txRedis.delete(TX_KEY_PREFIX + hash);
        stringRedis.opsForSet().remove(MEMPOOL_SET, hash);
        stringRedis.opsForZSet().remove(MEMPOOL_ZSET, hash);
    }

    // Busca todas as TXs
    public List<TX> findAll() {
        Set<String> hashes = stringRedis.opsForSet().members(MEMPOOL_SET);

        if (hashes == null) return List.of();

        return hashes.stream()
                .map(this::findByHash) // auto-limpa expiradas
                .collect(Collectors.toList());
    }

    public List<TX> findTopNByFee(int limit) {
        Set<String> hashes = stringRedis.opsForZSet()
                .reverseRange(MEMPOOL_ZSET, 0, limit - 1);

        if (hashes == null) return List.of();

        return hashes.stream()
                .map(this::findByHash) // já remove expiradas automaticamente
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
