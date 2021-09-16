package com.xtyu.toolapi;

import com.xtyu.toolapi.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToolApiApplicationTests {

    @Autowired
    VideoService videoService;
    @Test
    void contextLoads() {
        videoService.phpParsingVideoInfo("https://isee.weishi.qq.com/ws/app-pages/share/index.html?wxplay=1&id=779BnNuIf1MitFmg4&spid=1557234260090671&qua=v1_iph_weishi_8.26.3_104_app_a&chid=100081014&pkg=3670&attach=cp_reserves3_1000370011");
    }

}
