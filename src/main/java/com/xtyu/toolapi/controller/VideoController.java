package com.xtyu.toolapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xtyu.toolapi.exception.WxInfoException;
import com.xtyu.toolapi.mapper.ParsingInfoMapper;
import com.xtyu.toolapi.model.entity.ParsingInfo;
import com.xtyu.toolapi.model.entity.WxUser;
import com.xtyu.toolapi.model.support.BaseResponse;
import com.xtyu.toolapi.service.WxUserService;
import com.xtyu.toolapi.utils.video.ShortVideo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: 小熊
 * @date: 2021/6/9
 * @description:phone 17521111022
 */
@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Resource
    WxUserService wxUserService;
    @Resource
    ParsingInfoMapper parsingInfoMapper;

    /***
     * 视频无水印链接解析
     * @param url 分享地址
     * @param openId 用户openId
     * @return
     */
    @PostMapping(value = "getVideoInfo")
    public BaseResponse<Map> getVideoInfo(@RequestParam(value = "url") String url, @RequestParam(value = "openId") String openId) {
        WxUser wxUser = wxUserService.getUserInfoByOpenId(openId);
        if (wxUser == null) {
            throw new WxInfoException("未查到用户信息");
        } else if (wxUser.getVideoNumber() < 1) {
            throw new WxInfoException("解析次数已用完");
        }
        Map<String, String> urlInfoMap;
        //todo 先都用php
//        if (url.contains("douyin")) {
//            urlInfoMap = ShortVideo.getDY(url);
//        } else if (url.contains("pipix")) {
//            urlInfoMap = ShortVideo.getPPX(url);
//        } else {
//            urlInfoMap = ShortVideo.getOther(url);
//        }
        urlInfoMap = ShortVideo.getOther(url);
        wxUser.setVideoNumber(wxUser.getVideoNumber() - 1);
        wxUserService.updateById(wxUser);
        ParsingInfo parsingInfo=new ParsingInfo();
        parsingInfo.setTitle(urlInfoMap.get("OriginTitle"));
        parsingInfo.setDownloadUrl(urlInfoMap.get("OriginUrl"));
        parsingInfo.setUserOpenId(wxUser.getOpenId());
        parsingInfo.setCreateTime(new Date());
        parsingInfoMapper.insert(parsingInfo);
        return BaseResponse.ok(urlInfoMap);
    }

    /***
     * 获取解析记录
     * @param openId
     * @return
     */
    @PostMapping(value = "getParsingInfo")
    public BaseResponse<List> getVideoInfo(@RequestParam(value = "openId") String openId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_open_id",openId);
        List<ParsingInfo> parsingInfoList=parsingInfoMapper.selectList(queryWrapper);
        return BaseResponse.ok(parsingInfoList);
    }
}
