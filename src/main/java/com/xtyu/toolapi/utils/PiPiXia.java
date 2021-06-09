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

class PiPiXia {
    private String videoUrl;
    private String videoId;
    private String videoJson;
    private String videoOriginUrl;
    private String videoOriginTitle;


    public PiPiXia(String video_url) {
        this.videoUrl = video_url;
        this.videoId = getID();
        this.videoJson = getJson();
        this.videoOriginUrl = getOriginUrl();
        this.videoOriginTitle = getOriginTitle();
    }


    private String getJson() {
        String result = "";
        try {
            URL url = new URL("https://is.snssdk.com/bds/cell/detail/?cell_type=1&aid=1319&app_name=super&cell_id=" + this.videoId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            throw new UrlParsingException("URL解密无水印视频异常");
        }
        return result;
    }


    private String getID() {
        String id = "";
        try {
            URL url = new URL(this.videoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            String s = conn.getHeaderField("Location");
            id =s.substring(s.indexOf("item/") + 5, s.indexOf("?"));
        } catch (IOException e) {
            throw new UrlParsingException("URL解密ID异常");
        }
        return id;
    }


    // 无水印视频Url
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



    // 无水印视频标题
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
