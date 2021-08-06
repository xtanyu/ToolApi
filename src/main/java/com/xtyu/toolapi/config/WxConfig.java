package com.xtyu.toolapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: 小熊
 * @date: 2021/6/15 11:05
 * @description:phone 17521111022
 */
@Component
public class WxConfig {
    public static String appId;
    public static String secret;

    @Value("${wx.appId}")
    private void setAppId(String appId) {
        WxConfig.appId = appId;
    }

    @Value("${wx.secret}")
    private void setSecret(String secret) {
        WxConfig.secret = secret;
    }
}
