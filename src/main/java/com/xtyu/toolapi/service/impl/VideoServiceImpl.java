package com.xtyu.toolapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xtyu.toolapi.exception.Asserts;
import com.xtyu.toolapi.model.dto.VideoInfoDto;
import com.xtyu.toolapi.model.entity.WxUser;
import com.xtyu.toolapi.service.VideoService;
import com.xtyu.toolapi.service.WxUserService;
import com.xtyu.toolapi.utils.RestTemplateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 小熊
 * @date: 2021/8/27 14:33
 * @description:phone 17521111022
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Resource(name = "wxUserService")
    private WxUserService wxUserService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RestTemplateUtil restTemplateUtil;

    @Override
    public VideoInfoDto getVideoInfo(String openid, String url) {
        isContainsStrings(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Mobile Safari/537.36");
        httpHeaders.set("Referer", url);
//        WxUser wxUser = wxUserService.getUserInfoByOpenId(openid);
//        if (wxUser == null) {
//            Asserts.wxInfoFail("未查到用户信息");
//        } else if (wxUser.getVideoNumber() < 1) {
//            Asserts.wxInfoFail("解析次数已用完");
//        }
//        if (url.contains("douyin")) {
//
//        } else if (url.contains("pipix")) {
//            urlInfoMap = ShortVideo.getPPX(url);
//        } else {
//            urlInfoMap = ShortVideo.getOther(url);
//        }
        return parsingDyVideoInfo("https://v.douyin.com/dR9Ew7U/", httpHeaders);
    }

    @Override
    public VideoInfoDto parsingDyVideoInfo(String url, HttpHeaders httpHeaders) {
        //获取重定向后的地址
        url = restTemplate.headForHeaders(url).getLocation().toString();
        Matcher matcher = Pattern.compile("/share/video/([\\d]*)[/|?]").matcher(url);
        if (!matcher.find())
            Asserts.urlParsingFail("解析链接ID异常");
        //获取链接ID
        String id = matcher.group(1);
        String dyWebApi = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=" + id;
        System.out.println(dyWebApi);
        String content = restTemplateUtil.getForObject(dyWebApi, httpHeaders, String.class);
        Asserts.urlInfoNotNull(content, "API请求异常");
        JSONObject videoInfo = JSON.parseObject(content).getJSONArray("item_list").getJSONObject(0);
        VideoInfoDto videoInfoDto = new VideoInfoDto();
        videoInfoDto.setUrl(videoInfo.getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").getString(0).replace("playwm", "play"));
        videoInfoDto.setTitle(videoInfo.getString("desc"));
        videoInfoDto.setAuthor(videoInfo.getJSONObject("author").getString("nickname"));
        return videoInfoDto;
    }

    @Override
    public VideoInfoDto parsingKsuVideoInfo(String url, HttpHeaders httpHeaders) {
        Matcher matcher = Pattern.compile("(https?://v.kuaishou.com/[\\S]*)").matcher(url);
        VideoInfoDto videoInfoDto = null;
        if (matcher.find()) {
            url = matcher.group(1);
            url = restTemplate.headForHeaders(url).getLocation().toString();
            String htmlContent = restTemplateUtil.getForObject(url, httpHeaders, String.class);
            Document document = Jsoup.parse(htmlContent);
            Elements elements = document.getElementsByTag("script");
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).childNodeSize() > 0) {
                    Matcher matcherForPageData = Pattern.compile("window.pageData[\\s]*=[\\s]*(.*)[\\s]*").matcher(elements.get(i).childNode(0).toString());
                    if (matcherForPageData.find()) {
                        String pageData = matcherForPageData.group(1);
                        JSONObject pageDataOb = JSONObject.parseObject(pageData);
                        videoInfoDto = new VideoInfoDto();
                        videoInfoDto.setAuthor(pageDataOb.getJSONObject("user").getString("name"));
                        JSONObject mediaJob = pageDataOb.getJSONObject("video");
                        String photoType = mediaJob.getString("type");
                        videoInfoDto.setTitle(mediaJob.getString("caption"));
                        if ("video".equals(photoType)) {
                            videoInfoDto.setUrl(mediaJob.getString("srcNoMark"));
                        }
                    }
                }

            }
        }
        Asserts.urlInfoNotNull(videoInfoDto, "视频解析异常");
        return videoInfoDto;
    }


    void isContainsStrings(String url) {
        String[] strings = new String[]{"douyin", "kuaishou"};
        for (String s : strings) {
            if (url.contains(s)) {
                return;
            }
        }
        Asserts.urlParsingFail("链接格式错误");
    }
}
