package com.xtyu.toolapi.controller;

import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.exception.WxInfoException;
import com.xtyu.toolapi.model.entity.WxUser;
import com.xtyu.toolapi.model.support.BaseResponse;
import com.xtyu.toolapi.service.WxUserService;
import com.xtyu.toolapi.utils.video.ShortVideo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: smile
 * @date: 2021/6/9
 * @description:iphone 17521111022
 */
@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Resource
    WxUserService wxUserService;

    @PostMapping(value = "getInfo")
    public BaseResponse<Map> getVideoInfo(@RequestParam(value = "url") String url, @RequestParam(value = "openId") String openId) {
        WxUser wxUser = wxUserService.getUserInfoByOpenId(openId);
        if (wxUser == null) {
            throw new WxInfoException("未查到用户信息");
        } else {
            if (wxUser.getVideoNumber() < 1) {
                throw new WxInfoException("解析次数已用完");
            }
            wxUser.setVideoNumber(wxUser.getVideoNumber() - 1);
            wxUserService.updateById(wxUser);
        }
        Map<String, String> urlInfoMap;
        if (url.contains("douyin")) {
            urlInfoMap = ShortVideo.getDY(url);
        } else if (url.contains("pipix")) {
            urlInfoMap = ShortVideo.getPPX(url);
        } else {
            urlInfoMap = ShortVideo.getOther(url);
        }
        return BaseResponse.ok(urlInfoMap);
    }
}
