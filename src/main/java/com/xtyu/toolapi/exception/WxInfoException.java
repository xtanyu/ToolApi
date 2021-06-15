package com.xtyu.toolapi.exception;

import org.springframework.http.HttpStatus;

/**
 * @author: smile
 * @date: 2021/6/15 14:19
 * @description:iphone 17521111022
 * 微信相关异常
 */
public class WxInfoException extends RuntimeException{
    public WxInfoException(String message) {
        super(message);
    }

    public WxInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getStatus() {
        return 1001;
    }
}
