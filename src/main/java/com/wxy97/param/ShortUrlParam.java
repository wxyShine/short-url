package com.wxy97.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author: wxy
 * @create: 2022-02-23 17:29
 * @email: admin@mail.wxy97.com
 * @description:
 **/
@Data
public class ShortUrlParam {
    @NotNull(message = "参数错误")
    @Pattern(regexp = "http(s*)://[^\\s]*",message = "不是合法的url")
    private String longUrl;
}
