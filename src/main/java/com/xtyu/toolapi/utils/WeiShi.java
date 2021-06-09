package com.xtyu.toolapi.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


class WeiShi {
    private String videoUrl;
    private String videoId;
    private String videoJson;
    private String videoOriginUrl;
    private String videoOriginTitle;
    private String videoAuthorId;
    private String videoAuthorName;
    private String videoAuthorImage;

    public WeiShi(String videoUrl) {
        this.videoUrl = videoUrl;
        this.videoId = getID();
        this.videoJson = getJson();
        this.videoOriginUrl = getOriginUrl();
        this.videoOriginTitle = getOriginTitle();
        this.videoAuthorId = getAuthorId();
        this.videoAuthorName = getAuthorName();
        this.videoAuthorImage = getAuthorImage();
    }

    private String getJson(){
        String result = "";
        BufferedWriter bw;
        try {
            URL url = new URL("https://h5.weishi.qq.com/webapp/json/weishi/WSH5GetPlayPage");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type","application/json");
            conn.setDoOutput(true);
            bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bw.write("{\"feedid\":\""+this.videoId+"\",\"recommendtype\":0,\"datalvl\":\"all\",\"_weishi_mapExt\":{}}");
            bw.flush();
            bw.close();
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

    private String getID(){
        String id = "";
        id = this.videoUrl.substring(this.videoUrl.indexOf("feed/")+5,this.videoUrl.indexOf("/wsfeed"));
        return id;
    }

    private String  getOriginUrl(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject.getJSONArray("feeds");
        jsonObject = jsonArray.getJSONObject(0).getJSONObject("video_spec_urls").getJSONObject("0");
        return jsonObject.getString("url");
    }

    private String  getOriginTitle(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject.getJSONArray("feeds");
        jsonObject = jsonArray.getJSONObject(0);
        return jsonObject.getString("feed_desc_withat");
    }

    private String  getAuthorId(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject.getJSONArray("feeds");
        jsonObject = jsonArray.getJSONObject(0).getJSONObject("poster");
        return jsonObject.getString("id");
    }

    private String  getAuthorName(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject.getJSONArray("feeds");
        jsonObject = jsonArray.getJSONObject(0).getJSONObject("poster");
        return jsonObject.getString("nick");
    }

    private String  getAuthorImage(){
        JSONObject jsonObject = JSONObject.parseObject(this.videoJson);
        jsonObject = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject.getJSONArray("feeds");
        jsonObject = jsonArray.getJSONObject(0).getJSONObject("poster");
        return jsonObject.getString("avatar");
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
