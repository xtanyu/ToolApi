package com.xtyu.toolapi.utils.video;

import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.model.dto.PhpParsingDto;

import java.util.HashMap;
import java.util.Map;

public class ShortVideo {

    public static Map<String, String> getPPX(String url) {
        Map<String, String> map = new HashMap<>();
        PiPiXia piPiXia = new PiPiXia(url);
        map.put("VideoID", piPiXia.getVideoId());
        map.put("OriginTitle", piPiXia.getVideoOriginTitle());
        map.put("OriginUrl", piPiXia.getVideoOriginUrl());
        return map;
    }

    public static Map<String, String> getDY(String url) {
        Map<String, String> map = new HashMap<>();
        DouYin douYin = new DouYin(url);
        map.put("VideoID", douYin.getVideoId());
        map.put("OriginTitle", douYin.getVideoOriginTitle());
        map.put("OriginUrl", douYin.getVideoOriginUrl());
        return map;
    }

    public static PhpParsingDto getOther(String url) {
        if (!isContainsStrings(url))
            throw new UrlParsingException("链接格式错误");
        return new Other(url).getParsingDto();
    }

    static boolean isContainsStrings(String url) {
        String[] strings = new String[]
                {"pipix", "douyin", "huoshan", "h5.weishi", "isee.weishi", "weibo.com", "oasis.weibo", "zuiyou",
                        "bbq.bilibili", "kuaishou", "quanmin", "moviebase", "hanyuhl", "eyepetizer", "immomo", "vuevideo",
                        "xiaokaxiu", "ippzone", "qq.com", "ixigua.com"
                };
        for (String s : strings) {
            if (url.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
