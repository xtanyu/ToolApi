package com.xtyu.toolapi.utils;

import com.xtyu.toolapi.exception.UrlParsingException;

import java.util.HashMap;
import java.util.Map;


public class UrlUtil {
    /**
     * 获取请求地址中的某个参数
     * @param url
     * @param name
     * @return
     */
    public static String getParam(String url, String name) {
        url = urlSplit(url).get(name);
        if (url==null){
            throw new UrlParsingException("url参数解析异常");
        }
        return url;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
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
     * @param url  url地址
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
}
