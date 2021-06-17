package com.xtyu.toolapi.controller;

import com.xtyu.toolapi.model.entity.WxUser;
import com.xtyu.toolapi.model.support.BaseResponse;
import com.xtyu.toolapi.service.WxUserService;
import com.xtyu.toolapi.utils.UrlUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: smile
 * @date: 2021/6/11 16:58
 * @description:iphone 17521111022
 */
@RestController
@RequestMapping("/api/wx")
public class WxController {

    @Resource
    private WxUserService wxUserService;

    @PostMapping(value = "auth")
    public BaseResponse<String> wxAuth(@RequestParam(value = "js_code") String code) {
        String openId = UrlUtil.getOpenId(code);
        return BaseResponse.ok("openId获取成功",openId);
    }

    /**
     * 登录、注册、获取用户信息
     *
     * @param openId
     * @return
     */
    @PostMapping(value = "login")
    public BaseResponse<WxUser> login(@RequestParam(value = "openId") String openId, String name) {
        WxUser wxUser = wxUserService.getUserInfoByOpenId(openId);
        if (wxUser == null) {
            wxUser = new WxUser();
            wxUser.setName(name);
            wxUser.setOpenId(openId);
            wxUser.setVideoNumber(10);
            wxUser.setSignInSum(1);
            wxUser.setCreateTime(new Date());
            wxUserService.insert(wxUser);
        }
        return BaseResponse.ok(wxUser);
    }

    /***
     * 签到
     * @param openId
     * @return
     */
    @PostMapping(value = "signIn")
    public BaseResponse<WxUser> sign(@RequestParam(value = "openId") String openId) {
        WxUser wxUser = wxUserService.singIn(openId);
        return BaseResponse.ok(wxUser);
    }

}
