package com.nanourl.urlshortener.cache;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UrlCacheService {

    private final StringRedisTemplate redisTemplate;

    public void save(String shortCode, String longUrl, long ttlSeconds) {
        redisTemplate.opsForValue().set("url:"+shortCode, longUrl, ttlSeconds, TimeUnit.SECONDS);
    }

    public String get(String shortCode) {
        return redisTemplate.opsForValue().get("url:"+shortCode);
    }

    public void remove(String shortCode) {
        redisTemplate.delete("url:"+shortCode);
    }
}
