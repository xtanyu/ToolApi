package com.xtyu.toolapi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.model.enums.VideoType;

class PiPiXia {
    private String videoUrl;
    private String videoId;
    private String videoJson;
    private String videoOriginUrl;
    private String videoOriginTitle;

    public PiPiXia(String video_url) {
        this.videoUrl = video_url;
        this.videoId = UrlUtil.getUrlId(video_url, VideoType.PI_PI_XIA);
        this.videoJson = UrlUtil.getUrlInfo(VideoType.PI_PI_XIA.getParsingUrl(),videoId);
        this.videoOriginUrl = getOriginUrl();
        this.videoOriginTitle = getOriginTitle();
    }

    private String getOriginUrl(){
        try {
            JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
            jsonObject = jsonObject.getJSONObject("data").getJSONObject("data").getJSONObject("item").getJSONObject("origin_video_download");
            JSONArray jsonArray = jsonObject.getJSONArray("url_list");
            return jsonArray.getJSONObject(0).get("url").toString();
        }catch (NullPointerException e){
            throw new UrlParsingException("DATA解析URL异常");
        }
    }


    private String getOriginTitle() {
        try {
            JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
            jsonObject = jsonObject.getJSONObject("data").getJSONObject("data").getJSONObject("item").getJSONObject("video");
            return jsonObject.getString("text");
        }catch (NullPointerException e){
            throw new UrlParsingException("DATA解析标题异常");
        }
    }


    public String getVideoId() {
        return videoId;
    }

    public String getVideoOriginUrl() {
        return videoOriginUrl;
    }

    public String getVideoOriginTitle() {
        if (this.videoOriginTitle.isEmpty()) {
            return new Date().getTime() + "";
        }
        return videoOriginTitle;
    }
}
