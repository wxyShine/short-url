package com.wxy97.service;

import com.wxy97.entity.ShortUrl;
import com.wxy97.filter.ShortUrlBloomFilter;
import com.wxy97.repository.ShortUrlRepository;
import com.wxy97.util.URLUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
                .shortUrl(baseUrl + "/s/" + shortURL).build();
        ShortUrl save = shortUrlRepository.save(build);
        ShortUrlBloomFilter.put(save.getShorts());
        return save;
    }

    /**
     * 转换为原链接
     *
     * @param shorts
     * @return
     */
    @Override
    public ShortUrl genLongUrl(String shorts) {
        if (ShortUrlBloomFilter.mightContain(shorts)) {
            ShortUrl build = ShortUrl.builder().shorts(shorts).build();
            Example<ShortUrl> example = Example.of(build);
            Optional<ShortUrl> one = shortUrlRepository.findOne(example);
            if (one.isPresent()) {
                build = one.get();
                return build;
            }
        }
        return null;
    }

    /**
     * 获取全部数据
     *
     * @return
     */
    @Override
    public List<String> getAllShort() {
        return shortUrlRepository.findAll().stream().map(ShortUrl::getShorts).collect(Collectors.toList());
    }
}
