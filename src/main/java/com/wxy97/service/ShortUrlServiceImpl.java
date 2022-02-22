package com.wxy97.service;

import com.wxy97.entity.ShortUrl;
import com.wxy97.filter.ShortUrlBloomFilter;
import com.wxy97.repository.ShortUrlRepository;
import com.wxy97.util.URLUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        ShortUrl save = null;
        try {
            String shortURL = URLUtil.getShortURL();
            ShortUrl build = ShortUrl.builder()
                    .shorts(shortURL)
                    .longUrl(longUrl)
                    .shortUrl(baseUrl + "/" + shortURL).build();
            save = shortUrlRepository.save(build);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ShortUrlBloomFilter.put(save.getShorts());
        }
        return save;
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

    @Override
    public List<String> getAllShort() {
        return shortUrlRepository.findAll().stream().map(ShortUrl::getShorts).collect(Collectors.toList());
    }
}
