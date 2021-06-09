package com.xtyu.toolapi.controller;

import com.alibaba.fastjson.JSON;
import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.model.support.BaseResponse;
import com.xtyu.toolapi.utils.ShortVideo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: smile
 * @date: 2021/6/9
 * @description:iphone 17521111022
 */
@ResponseBody
@Controller
public class VideoController {
    @RequestMapping("video")
    public BaseResponse<Map> getVideoInfo(@RequestParam String url) {
        Map<String, String> map_dy = null;
        if (url.indexOf("douyin") > 0) {
            map_dy = ShortVideo.getDY(url);
        } else if (url.indexOf("pipix") > 0) {
            map_dy = ShortVideo.getPPX(url, false);
        } else {
            throw new UrlParsingException("url输入有误");
        }
        return BaseResponse.ok(map_dy);
    }
}
