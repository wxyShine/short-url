package com.wxy97.exception.handler;

import com.wxy97.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleException(Throwable e) {
        e.printStackTrace();
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }


    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<ApiError> badRequestException(ServiceException e) {
        e.printStackTrace();
        return buildResponseEntity(ApiError.error(e.getStatus(), e.getDetailMsg()));
    }


    /**
     * 统一返回
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}