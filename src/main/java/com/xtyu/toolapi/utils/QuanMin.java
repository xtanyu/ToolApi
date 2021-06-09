package com.xtyu.toolapi.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class QuanMin {
    private String videoUrl;
    private String videoId;
    private String videoJson;
    private String videoOriginUrl;
    private String videoOriginTitle;
    private String videoAuthorId;
    private String videoAuthorName;
    private String videoAuthorImage;

    public QuanMin(String videoUrl) {
        this.videoUrl = videoUrl;
        this.videoId = getID();
        this.videoJson = getJson();
        this.videoOriginUrl = getOriginUrl();
        this.videoOriginTitle = getOriginTitle();
        this.videoAuthorId = getAuthorId();
        this.videoAuthorName = getAuthorName();
        this.videoAuthorImage = getAuthorImage();
    }

    private String getID(){
        String id = "";
        id = this.videoUrl.substring(this.videoUrl.indexOf("vid=")+4,this.videoUrl.indexOf("&share"));
        return id;
    }

    private String getJson(){
        String result = "";
        BufferedWriter bw;
        try {
            URL url = new URL("https://quanmin.hao222.com/wise/growth/api/sv/immerse?source=share-h5&pd=qm_share_mvideo&vid="+this.videoId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = "";
            while ((line=br.readLine())!=null){
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String  getOriginUrl(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data").getJSONObject("meta").getJSONObject("video_info");
        JSONArray jsonArray = jsonObject.getJSONArray("clarityUrl");
        jsonObject = jsonArray.getJSONObject(0);
        return jsonObject.getString("url");
    }

    private String  getOriginTitle(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data").getJSONObject("meta");
        return jsonObject.getString("title");
    }

    private String  getAuthorId(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data").getJSONObject("author");
        return jsonObject.getString("id");
    }

    private String  getAuthorName(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data").getJSONObject("author");
        return jsonObject.getString("name");
    }

    private String  getAuthorImage(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data").getJSONObject("author");
        return jsonObject.getString("icon");
    }

    public String getVideoId() {
        return videoId;
    }

    public String getVideoOriginUrl() {
        return videoOriginUrl;
    }

    public String getVideoOriginTitle() {
        if (this.videoOriginTitle.isEmpty()){
            return new Date().getTime()+"";
        }
        return videoOriginTitle;
    }

    public String getVideoAuthorId() {
        return videoAuthorId;
    }

    public String getVideoAuthorName() {
        return videoAuthorName;
    }

    public String getVideoAuthorImage() {
        return videoAuthorImage;
    }

}
