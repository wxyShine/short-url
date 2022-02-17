package com.wxy97.service;

import com.wxy97.entity.ShortUrl;
import com.wxy97.repository.ShortUrlRepository;
import com.wxy97.util.URLUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    /**
     * 生成短链接
     *
     * @param longUrl
     * @param baseUrl
     * @return
     */
    @Override
    public ShortUrl genShortUrl(String longUrl, String baseUrl) {
        String shortURL = URLUtil.getShortURL();
        ShortUrl build = ShortUrl.builder()
                .shorts(shortURL)
                .longUrl(longUrl)
                .shortUrl(baseUrl + "/" + shortURL).build();
        return shortUrlRepository.save(build);
    }

    @Override
    public ShortUrl genLongUrl(String shorts) {
        ShortUrl build = ShortUrl.builder().shorts(shorts).build();
        Example<ShortUrl> example = Example.of(build);
        Optional<ShortUrl> one = shortUrlRepository.findOne(example);
        if (one.isPresent()) {
            build = one.get();
            return build;
        } else {
            return null;
        }
    }
}
