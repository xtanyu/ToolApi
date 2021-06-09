package com.xtyu.toolapi.core;

import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.model.support.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author: smile
 * @date: 2021/6/9 10:48
 * @description:iphone 17521111022
 */
@RestControllerAdvice(value = {"com.xtyu.toolapi.controller"})
@Slf4j
public class ControllerException {

    @ExceptionHandler(UrlParsingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> UrlParsingException(UrlParsingException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        baseResponse.setStatus(status.value());
        baseResponse.setMessage(e.getMessage());
        return baseResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleGlobalException(Exception e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        baseResponse.setStatus(status.value());
        baseResponse.setMessage(status.getReasonPhrase());
        return baseResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponse.setMessage("缺失请求主体");
        return baseResponse;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponse.setMessage(
                String.format("请求字段缺失, 类型为 %s，名称为 %s", e.getParameterType(), e.getParameterName()));
        return baseResponse;
    }

    private <T> BaseResponse<T> handleBaseException(Throwable t) {
        Assert.notNull(t, "Throwable must not be null");
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(t.getMessage());
        log.error("Captured an exception:", t);
        if (log.isDebugEnabled()) {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw, true);
            t.printStackTrace(pw);
            String message=sw.getBuffer().toString();
            baseResponse.setDevMessage(message);
        }
        return baseResponse;
    }
}

