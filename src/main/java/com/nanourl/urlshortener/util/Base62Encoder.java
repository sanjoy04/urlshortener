package com.nanourl.urlshortener.util;

public class Base62Encoder {

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(Long value) {

        StringBuilder sb = new StringBuilder();

        while (value > 0) {
            sb.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }

        return sb.reverse().toString();
    }
}
