package com.wxy97.controller;

import com.wxy97.entity.ShortUrl;
import com.wxy97.exception.ServiceException;
import com.wxy97.param.ShortUrlParam;
import com.wxy97.service.ShortUrlService;
import com.wxy97.util.URLUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wxy
 */
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
    public ResponseEntity shortUrl(@Validated @RequestBody ShortUrlParam param, HttpServletRequest request) {
        return new ResponseEntity(shortUrlServiceImpl.genShortUrl(param.getLongUrl(), URLUtil.getUrlStart(request)), HttpStatus.OK);
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
    public ResponseEntity redirectUrl(HttpServletResponse response, HttpServletRequest request, @PathVariable String shorts) {
        ShortUrl shortUrl = shortUrlServiceImpl.genLongUrl(shorts);
        if (shortUrl != null) {
            try {
                response.sendRedirect(shortUrl.getLongUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new ServiceException(404, URLUtil.getUrlStart(request), "不存在的短链接");
    }


}
