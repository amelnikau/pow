package edu.bsu.pow.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class NonceService
{
    private final AtomicLong nonceGenerator = new AtomicLong(0);
    private final Cache<Long, Boolean> activeNonces = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    public Long getNewNonce()
    {
        long nonce = nonceGenerator.addAndGet(1);
        activeNonces.put(nonce, true);
        return nonce;
    }

    public void validateNonce(long nonce)
    {
        if (activeNonces.getIfPresent(nonce) == null)
        {
            throw new IllegalArgumentException(String.format("%s has expired or was never generated", nonce));
        }
        activeNonces.invalidate(nonce);
    }
}
