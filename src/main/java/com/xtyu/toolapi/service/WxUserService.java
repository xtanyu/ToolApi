package com.xtyu.toolapi.service;

import com.xtyu.toolapi.model.entity.WxUser;

/**
 * @author: smile
 * @date: 2021/6/15 13:38
 * @description:iphone 17521111022
 */
public interface WxUserService {
    WxUser getUserInfoByOpenId(String openId);

    int insert(WxUser wxUser);

    int updateById(WxUser wxUser);
}
