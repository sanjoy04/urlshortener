package com.nanourl.urlshortener.service;

import com.nanourl.urlshortener.util.Base62Encoder;
import org.springframework.stereotype.Component;

@Component
public class ShortCodeGenerator {

    public String generate(Long id) {
        return Base62Encoder.encode(id);
    }
}
