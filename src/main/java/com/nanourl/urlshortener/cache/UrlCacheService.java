package com.nanourl.urlshortener.cache;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlCacheService {

    private final StringRedisTemplate redisTemplate;

    public void save(String shortCode, String longUrl) {
        redisTemplate.opsForValue().set(shortCode, longUrl);
    }

    public String get(String shortCode) {
        return redisTemplate.opsForValue().get(shortCode);
    }
}
