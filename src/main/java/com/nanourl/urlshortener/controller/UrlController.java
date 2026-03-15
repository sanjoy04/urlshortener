package com.nanourl.urlshortener.controller;

import com.nanourl.urlshortener.dto.CreateUrlRequest;
import com.nanourl.urlshortener.dto.CreateUrlResponse;
import com.nanourl.urlshortener.service.UrlService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.net.URI;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/sayHi")
    public String hiUrl(){
        return "hi";
    }

    @PostMapping
    public ResponseEntity<CreateUrlResponse> create(
            @RequestBody CreateUrlRequest request) {

        return ResponseEntity.ok(
                urlService.createShortUrl(request));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode) {

        String longUrl = urlService.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl.trim()))
                .build();
    }
}
