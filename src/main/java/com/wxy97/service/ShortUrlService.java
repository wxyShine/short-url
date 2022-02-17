package com.wxy97.service;

import com.wxy97.entity.ShortUrl;

public interface ShortUrlService {

    ShortUrl genShortUrl(String longUrl,String baseUrl);
    ShortUrl genLongUrl(String shorts);
}
