package com.nanourl.urlshortener.service;

import com.nanourl.urlshortener.dto.CreateUrlRequest;
import com.nanourl.urlshortener.dto.CreateUrlResponse;
import com.nanourl.urlshortener.dto.UrlStatsResponse;

public interface UrlService {

    CreateUrlResponse createShortUrl(CreateUrlRequest request);

    String getOriginalUrl(String shortCode);

    UrlStatsResponse getStats(String shortCode);
}