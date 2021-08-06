package com.xtyu.toolapi;

import com.xtyu.toolapi.mapper.WxUserMapper;
import com.xtyu.toolapi.model.entity.WxUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author: 小熊
 * @date: 2021/6/15 09:55
 * @description:phone 17521111022
 */
@SpringBootTest
public class WxUserTest {
    @Autowired
    private WxUserMapper wxUserMapper;
    @Test
    public void testH2(){
        WxUser wxUser=new WxUser();
        wxUser.setName("测试小黑");
        wxUser.setCreateTime(new Date());
        wxUser.setOpenId("123123132132132132");
        wxUser.setVideoNumber(12);
        wxUserMapper.insert(wxUser);
        System.out.println(wxUserMapper.selectList(null));
    }
}
