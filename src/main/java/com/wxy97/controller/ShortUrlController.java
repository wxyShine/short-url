package com.wxy97.controller;

import com.wxy97.entity.ShortUrl;
import com.wxy97.service.ShortUrlService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlServiceImpl;

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.addObject("baseUrl", getUrlStart(request));
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/short")
    @ResponseBody
    public Map shortUrl(String longUrl, HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<>();
        String urlStart = getUrlStart(request);

        if (longUrl == null || longUrl.equals("")) {
            res.put("msg", "生成短链接失败,缺少参数longUrl");
            res.put("data", null);
            res.put("result", false);
            res.put("createBy", urlStart);
            return res;
        }
        if (!isUrl(longUrl)) {
            res.put("msg", "生成短链接失败,参数longUrl不合法");
            res.put("data", null);
            res.put("result", false);
            res.put("createBy", urlStart);
            return res;
        }
        ShortUrl shortUrl = shortUrlServiceImpl.genShortUrl(longUrl, urlStart);
        res.put("msg", "生成短链接成功");
        res.put("data", shortUrl.getShortUrl());
        res.put("createBy", urlStart);
        res.put("result", true);
        return res;
    }

    @GetMapping("/{shorts}")
    @ResponseBody
    public Map redirectUrl(HttpServletResponse response, HttpServletRequest request, @PathVariable String shorts) {
        ShortUrl shortUrl = shortUrlServiceImpl.genLongUrl(shorts);
        if (shortUrl != null) {
            try {
                response.sendRedirect(shortUrl.getLongUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("msg", "不存在的短链接" + getUrlStart(request) +"/"+ shorts);
        res.put("data", null);
        res.put("result", false);
        res.put("createBy", getUrlStart(request));
        return res;
    }

    //获取请求的协议域名，端口号生成连接的前半部分
    public String getUrlStart(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme());
        url.append("://").append(request.getServerName());
        url.append(":").append(request.getServerPort());
/*        System.out.println(url);
        url.append(request.getServletPath());
        System.out.println(url);*/
        return url.toString();
    }

    public Boolean isUrl(String url) {
        String reg = "http(s*)://[^\\s]*";
        return url.matches(reg);
    }
}
