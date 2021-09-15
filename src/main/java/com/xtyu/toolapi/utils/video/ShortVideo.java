package com.xtyu.toolapi.utils.video;

import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.model.dto.PhpParsingDto;

public class ShortVideo {

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
