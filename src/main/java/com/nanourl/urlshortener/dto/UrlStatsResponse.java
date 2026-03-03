package com.nanourl.urlshortener.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UrlStatsResponse {
    private String longUrl;
    private Long clickCount;
    private LocalDateTime createdAt;
    private LocalDateTime expiryDate;
}