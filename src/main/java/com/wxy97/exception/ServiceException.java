package com.wxy97.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wxy
 * @create: 2022-02-24 10:18
 * @email: admin@mail.wxy97.com
 * @description: 自定义异常
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String msg;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMsg;

}