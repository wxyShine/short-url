package com.wxy97.controller;

import com.wxy97.entity.ShortUrl;
import com.wxy97.param.ShortUrlParam;
import com.wxy97.service.ShortUrlService;
import com.wxy97.util.URLUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlServiceImpl;

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.addObject("baseUrl", URLUtil.getUrlStart(request));
        modelAndView.setViewName("index");
        return modelAndView;
    }

    /**
     * 生成短链接
     *
     * @param param   需要转换的链接
     * @param request
     * @return
     */
    @PostMapping("/")
    @ResponseBody
    public Map shortUrl(@RequestBody ShortUrlParam param, HttpServletRequest request) {
        HashMap<String, Object> res = new HashMap<>();
        String urlStart = URLUtil.getUrlStart(request);
        String longUrl = param.getLongUrl();
        if (longUrl == null || longUrl.equals("")) {
            res.put("msg", "生成短链接失败,缺少参数longUrl");
            res.put("data", null);
            res.put("result", false);
            res.put("createBy", urlStart);
            return res;
        }
        if (!URLUtil.isUrl(longUrl)) {
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

    /**
     * 访问短链接
     *
     * @param response
     * @param request
     * @param shorts
     * @return
     */
    @GetMapping("/s/{shorts}")
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
        res.put("msg", "不存在的短链接" + URLUtil.getUrlStart(request) + "/" + shorts);
        res.put("data", null);
        res.put("result", false);
        res.put("createBy", URLUtil.getUrlStart(request));
        return res;
    }


}
