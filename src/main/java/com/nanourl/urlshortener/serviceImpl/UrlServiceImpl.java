package com.nanourl.urlshortener.serviceImpl;

import com.nanourl.urlshortener.cache.UrlCacheService;
import com.nanourl.urlshortener.config.AppProperties;
import com.nanourl.urlshortener.dto.CreateUrlRequest;
import com.nanourl.urlshortener.dto.CreateUrlResponse;
import com.nanourl.urlshortener.dto.UrlStatsResponse;
import com.nanourl.urlshortener.exception.UrlExpiredException;
import com.nanourl.urlshortener.exception.UrlNotFoundException;
import com.nanourl.urlshortener.model.Url;
import com.nanourl.urlshortener.repository.UrlRepository;
import com.nanourl.urlshortener.service.ShortCodeGenerator;
import com.nanourl.urlshortener.service.UrlService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private static final Logger LOGGER = LogManager.getLogger(UrlServiceImpl.class);

    private final UrlRepository urlRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final UrlCacheService cacheService;
    private final AppProperties appProperties;

    @Override
    public CreateUrlResponse createShortUrl(CreateUrlRequest request) {

        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setCreatedAt(LocalDateTime.now());
        url.setClickCount(0L);
        url.setExpiryDate(request.getExpiryDate());

        Url saved = urlRepository.save(url);

        String shortCode = shortCodeGenerator.generate(saved.getId());
        saved.setShortCode(shortCode);

        urlRepository.save(saved);

        try {
            cacheService.save(shortCode, saved.getLongUrl());
        } catch (Exception e) {
            LOGGER.warn("Redis unavailable, skipping cache");
        }

        return new CreateUrlResponse(
                appProperties.getDomain() + shortCode,
                saved.getExpiryDate());
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        String cached = cacheService.get(shortCode);
        if (cached != null){
            LOGGER.info("Returning original url for {} from cache ",shortCode);
            return cached;
        }

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(UrlNotFoundException::new);

        if (url.getExpiryDate() != null &&
                url.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new UrlExpiredException();
        }

        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);

        try {
            cacheService.save(shortCode, url.getLongUrl());
        } catch (Exception e) {
            LOGGER.warn("Redis unavailable, skipping cache");
        }

        return url.getLongUrl();
    }

    @Override
    public UrlStatsResponse getStats(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(UrlNotFoundException::new);

        return new UrlStatsResponse(
                url.getLongUrl(),
                url.getClickCount(),
                url.getCreatedAt(),
                url.getExpiryDate()
        );
    }
}
