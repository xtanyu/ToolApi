package com.xtyu.toolapi.exception;

/**
 * @author: 小熊
 * @date: 2021/8/20 16:57
 * @description:phone 17521111022
 */
public class Asserts {
    public static void wxInfoFail(String message) {
        throw new WxInfoException(message);
    }

    public static void urlParsingFail(String message) {
        throw new UrlParsingException(message);
    }

    public static void urlInfoNotNull(Object o, String message) {
        if (o == null)
            throw new UrlParsingException(message);
    }
}
