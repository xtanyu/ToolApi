package com.xtyu.toolapi.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xtyu.toolapi.config.WxConfig;
import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.exception.WxInfoException;
import com.xtyu.toolapi.model.dto.RedirectUrlDto;
import com.xtyu.toolapi.model.enums.VideoType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


public class UrlUtil {

    /**
     * 获取请求地址中的某个参数
     *
     * @param url
     * @param name
     * @return
     */
    public static String getParam(String url, String name) {
        url = urlSplit(url).get(name);
        if (url == null) {
            throw new UrlParsingException("url参数解析异常");
        }
        return url;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param url url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String url) {
        String strAllParam = null;
        String[] arrSplit = null;
        url = url.trim().toLowerCase();
        arrSplit = url.split("[?]");
        if (url.length() > 1) {
            if (arrSplit.length > 1) {
                for (int i = 1; i < arrSplit.length; i++) {
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }


    /**
     * 将参数存入map集合
     *
     * @param url url地址
     * @return url请求参数部分存入map集合
     */
    public static Map<String, String> urlSplit(String url) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = truncateUrlPage(url);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 解析出无水印视频相关信息
     *
     * @param url 解析url
     * @param id  视频对应id
     * @return
     */
    public static String getUrlInfo(String url, String id) {
        StringBuffer html = new StringBuffer();
        InputStreamReader isr = null;
        BufferedReader buf = null;
        String str = null;
        try {
            URL urlObj = new URL(url + id);
            URLConnection con = urlObj.openConnection();
            isr = new InputStreamReader(con.getInputStream());
            buf = new BufferedReader(isr);
            while ((str = buf.readLine()) != null) {
                html.append(str);
            }
        } catch (Exception e) {
            throw new UrlParsingException("URL解密无水印视频异常");
        } finally {
            if (isr != null) {
                try {
                    buf.close();
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return html.toString();
    }

    public static RedirectUrlDto getRedirectUrl(String videoUrl, VideoType videoType) {
        RedirectUrlDto redirectUrlDto;
        try {
            URL url = new URL(videoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            String s = conn.getHeaderField("Location");
            redirectUrlDto = new RedirectUrlDto();
            if (videoType != null)
                redirectUrlDto.setId(s.substring(s.indexOf(videoType.getCutStart()) + videoType.getCutStart().length(), s.indexOf(videoType.getCutStop())));
            redirectUrlDto.setRedirectUrl(s);
        } catch (IOException e) {
            throw new UrlParsingException("URL解密ID异常");
        }
        return redirectUrlDto;
    }

    /**
     * 获取微信用户openId
     *
     * @param code 微信用户js_code
     */
    public static String getOpenId(String code) {
        String openId = null;
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?");
        url.append("appid=" + WxConfig.appId);
        url.append("&secret=" + WxConfig.secret);
        url.append("&js_code=" + code);
        url.append("&grant_type=authorization_code");
        StringBuilder info = new StringBuilder();
        try {
            URL urlObj = new URL(url.toString());
            URLConnection con = urlObj.openConnection();
            InputStreamReader isr = new InputStreamReader(con.getInputStream());
            BufferedReader buf = new BufferedReader(isr);
            String str;
            while ((str = buf.readLine()) != null) {
                info.append(str);
            }
            JSONObject jsonObject = JSON.parseObject(info.toString());
            openId = jsonObject.get("openid").toString();
        } catch (Exception e) {
          throw new WxInfoException("微信openId解析失败"+JSONObject.toJSON(info));
        }
        return openId;
    }
}
