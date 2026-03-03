package com.nanourl.urlshortener.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortCode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    private LocalDateTime createdAt;

    private LocalDateTime expiryDate;

    private Long clickCount;

}
