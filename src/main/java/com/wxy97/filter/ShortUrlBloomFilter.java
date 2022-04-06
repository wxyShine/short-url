package com.wxy97.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.wxy97.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortUrlBloomFilter implements InitializingBean {

    private static BloomFilter<String> bloomFilter = BloomFilter.create(
            Funnels.stringFunnel(Charset.defaultCharset()), 1000000);


    private final ShortUrlService shortUrlService;

    public static boolean mightContain(String shorts) {
        return bloomFilter.mightContain(shorts);
    }

    public static void put(String shorts) {
        bloomFilter.put(shorts);
    }

    @Override
    public void afterPropertiesSet() {
        log.info("===============初始化BloomFilter......==============");
        long startTime = System.currentTimeMillis();
        long count = 0;
        //从数据加载数据到  BloomFilter
        List<String> allShorts = shortUrlService.getAllShort();
        for (String hash : allShorts) {
            bloomFilter.put(hash);
        }
        count = allShorts.size();
        long costTime = System.currentTimeMillis() - startTime;
        log.info("===============初始化BloomFilter完成,costTime:[{}],Count:[{}]==============", costTime, count);
    }
}