package com.xtyu.toolapi.service;

import com.xtyu.toolapi.model.entity.WxUser;

/**
 * @author: 小熊
 * @date: 2021/6/15 13:38
 * @description:phone 17521111022
 */
public interface WxUserService {
    WxUser getUserInfoByOpenId(String openId);

    int insert(WxUser wxUser);

    int updateById(WxUser wxUser);

    /**
     * 签到
     *
     * @param openId
     * @return 累计签到次数
     */
    WxUser singIn(String openId);

    /***
     * 获取微信用户openId
     * @param code
     * @return
     */
    String getOpenId(String code);
}
