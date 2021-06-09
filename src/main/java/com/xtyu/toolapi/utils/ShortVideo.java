package com.xtyu.toolapi.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class ShortVideo {

    public static Map<String,String> getPPX(String url,boolean cell) {
        Map<String,String> map = new HashMap<>();
        PiPiXia piPiXia = new PiPiXia(url,cell);
        map.put("VideoID", piPiXia.getVideoId());
        map.put("OriginTitle", piPiXia.getVideoOriginTitle());
        map.put("OriginUrl", piPiXia.getVideoOriginUrl());
        map.put("AuthorID", piPiXia.getVideoAuthorId());
        map.put("AuthorName", piPiXia.getVideoAuthorName());
        map.put("AuthorImage", piPiXia.getVideoAuthorImage());
        return map;
    }

    public static Map<String,String> getDY(String url){
        Map<String,String> map = new HashMap<>();
        DouYin douYin = new DouYin(url);
        map.put("VideoID", douYin.getVideoId());
        map.put("OriginTitle", douYin.getVideoOriginTitle());
        map.put("OriginUrl", douYin.getVideoOriginUrl());;
        return map;
    }

    public static Map<String,String> getWS(String url){
        Map<String,String> map = new HashMap<>();
        WeiShi ws = new WeiShi(url);
        map.put("VideoID",ws.getVideoId());
        map.put("OriginTitle",ws.getVideoOriginTitle());
        map.put("OriginUrl",ws.getVideoOriginUrl());
        map.put("AuthorID",ws.getVideoAuthorId());
        map.put("AuthorName",ws.getVideoAuthorName());
        map.put("AuthorImage",ws.getVideoAuthorImage());
        return map;
    }

    public static Map<String,String> getQM(String url){
        Map<String,String> map = new HashMap<>();
        QuanMin quanMin = new QuanMin(url);
        map.put("VideoID", quanMin.getVideoId());
        map.put("OriginTitle", quanMin.getVideoOriginTitle());
        map.put("OriginUrl", quanMin.getVideoOriginUrl());
        map.put("AuthorID", quanMin.getVideoAuthorId());
        map.put("AuthorName", quanMin.getVideoAuthorName());
        map.put("AuthorImage", quanMin.getVideoAuthorImage());
        return map;
    }
}
