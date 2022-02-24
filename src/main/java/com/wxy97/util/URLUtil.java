package com.wxy97.util;

import com.wxy97.filter.ShortUrlBloomFilter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Slf4j
public class URLUtil {
    //生成短链接的八个字符
    public static String getShortURL() {
        Random random = new Random();
        // 要使用生成 URL 的字符
        char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        int ln = chars.length;
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 8; ++i) {
            key.append(chars[random.nextInt(ln)]);
        }
        String s = key.toString();
        //从 BloomFilter 查看是否存在
        boolean mightContain = ShortUrlBloomFilter.mightContain(s);
        if (mightContain) {
            String newS = getShortURL();
            s = newS;
            log.debug("{}已存在shorts,重新生成{}", s, newS);
        }
        return s;
    }

    //获取请求的协议域名，端口号生成连接的前半部分
    public static String getUrlStart(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme());
        url.append("://").append(request.getServerName());
        url.append(":").append(request.getServerPort());
        return url.toString();
    }

    public static Boolean isUrl(String url) {
        String reg = "http(s*)://[^\\s]*";
        return url.matches(reg);
    }
}

