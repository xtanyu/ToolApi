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

    }
}
