package com.xtyu.toolapi.utils.video;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.model.dto.PhpParsingDto;
import com.xtyu.toolapi.model.enums.VideoType;
import com.xtyu.toolapi.utils.UrlUtil;
import lombok.Data;

/**
 * @author: smile
 * @date: 2021/6/11 11:51
 * @description:iphone 17521111022
 */
@Data
class Other {
    private PhpParsingDto parsingDto;
    private String videoOriginUrl;
    private String videoOriginTitle;

    public Other(String videoUrl){
        String info = UrlUtil.getUrlInfo(VideoType.PHP_PARSING_SERVICE.getParsingUrl(), videoUrl);
        PhpParsingDto phpParsingDto = JSONArray.parseObject(info).getObject("data",PhpParsingDto.class);
        if (phpParsingDto==null)
            throw new UrlParsingException("视频服务解析异常");
        this.videoOriginUrl=phpParsingDto.getUrl();
        this.videoOriginTitle=phpParsingDto.getTitle();
    }
}
