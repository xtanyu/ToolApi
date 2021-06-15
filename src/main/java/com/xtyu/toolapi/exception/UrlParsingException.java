package com.xtyu.toolapi.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author: smile
 * @date: 2021/6/9 13:47
 * @description:iphone 17521111022
 * 视频地址解析时异常
 */
public class UrlParsingException extends RuntimeException {

    public UrlParsingException(String message) {
        super(message);
    }

    public UrlParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getStatus() {
        return 1000;
    }
}
