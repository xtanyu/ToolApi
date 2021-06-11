package com.xtyu.toolapi.utils.video;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.model.dto.RedirectUrlDto;
import com.xtyu.toolapi.model.enums.VideoType;
import com.xtyu.toolapi.utils.UrlUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

class DouYin {
    private String videoJson;
    private String videoId;
    private String videoOriginUrl;
    private String videoOriginTitle;

    public DouYin(String video_url) {
        RedirectUrlDto redirectUrlDto = UrlUtil.getRedirectUrl(video_url, VideoType.DOU_YIN);
        this.videoId = redirectUrlDto.getId();
        this.videoJson = UrlUtil.getUrlInfo(VideoType.DOU_YIN.getParsingUrl(), videoId);
        this.videoOriginUrl = getOriginUrl();
        this.videoOriginTitle = getOriginTitle();
    }

    private String getOriginUrl() {
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        JSONArray jsonArray = jsonObject.getJSONArray("item_list");
        jsonObject = jsonArray.getJSONObject(0).getJSONObject("video").getJSONObject("play_addr");
        jsonArray = jsonObject.getJSONArray("url_list");
        HttpURLConnection conn = null;
        String s = "";
        try {
            URL url = new URL(jsonArray.getString(0).replace("playwm", "play"));
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            s = conn.getHeaderField("Location");
        } catch (IOException e) {
            throw new UrlParsingException("URL解密无水印视频异常");
        }
        return s;
    }

    private String getOriginTitle() {
        try {
            JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
            JSONArray jsonArray = jsonObject.getJSONArray("item_list");
            jsonObject = jsonArray.getJSONObject(0);
            return jsonObject.getString("desc");
        } catch (Exception e) {
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
