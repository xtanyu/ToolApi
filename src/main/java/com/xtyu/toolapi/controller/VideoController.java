package com.xtyu.toolapi.controller;

import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.model.support.BaseResponse;
import com.xtyu.toolapi.utils.video.ShortVideo;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: smile
 * @date: 2021/6/9
 * @description:iphone 17521111022
 */
@RestController
@RequestMapping("/api/video")
public class VideoController {

    @PostMapping(value = "getInfo")
    public BaseResponse<Map> getVideoInfo(@RequestParam(value = "url") String url) {
        Map<String, String> urlInfoMap = null;
        if (url.contains("douyin")) {
            urlInfoMap = ShortVideo.getDY(url);
        } else if (url.contains("pipix")) {
            urlInfoMap = ShortVideo.getPPX(url);
        }else {
            urlInfoMap = ShortVideo.getOther(url);
        }
        return BaseResponse.ok(urlInfoMap);
    }
}
