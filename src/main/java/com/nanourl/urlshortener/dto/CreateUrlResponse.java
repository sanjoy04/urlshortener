package com.nanourl.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateUrlResponse {
    private String shortUrl;
    private LocalDateTime expiryDate;
}