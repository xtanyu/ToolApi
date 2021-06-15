package com.xtyu.toolapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xtyu.toolapi.mapper.WxUserMapper;
import com.xtyu.toolapi.model.entity.WxUser;
import com.xtyu.toolapi.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: smile
 * @date: 2021/6/15 13:38
 * @description:iphone 17521111022
 */
@Service
public class WxUserServiceImpl implements WxUserService {

    @Resource
    private WxUserMapper wxUserMapper;

    @Override
    public WxUser getUserInfoByOpenId(String openId) {
        QueryWrapper<WxUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        List<WxUser> userList = wxUserMapper.selectList(queryWrapper);
        if (!userList.isEmpty())
            return userList.get(0);
        return null;
    }

    @Override
    public int insert(WxUser wxUser) {
        return wxUserMapper.insert(wxUser);
    }

    @Override
    public int updateById(WxUser wxUser) {
        return wxUserMapper.updateById(wxUser);
    }
}
