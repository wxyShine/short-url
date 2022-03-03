package com.wxy97.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: wxy
 * @create: 2022-02-23 17:29
 * @email: admin@mail.wxy97.com
 * @description:
 **/
@Data
public class ShortUrlParam {
    @NotNull(message = "参数错误")
    private String longUrl;
}
