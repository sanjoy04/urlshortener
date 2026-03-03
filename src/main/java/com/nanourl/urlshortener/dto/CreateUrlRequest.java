package com.nanourl.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateUrlRequest {
    private String longUrl;
    private String customAlias;
    private LocalDateTime expiryDate;
}
