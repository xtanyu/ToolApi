package com.xtyu.toolapi.utils.video;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xtyu.toolapi.exception.UrlParsingException;
import com.xtyu.toolapi.model.dto.PhpParsingDto;
import com.xtyu.toolapi.model.enums.VideoType;
import com.xtyu.toolapi.utils.UrlUtil;
import lombok.Data;

/**
 * @author: 小熊
 * @date: 2021/6/11 11:51
 * @description:phone 17521111022
 */
@Data
class Other {
    private PhpParsingDto parsingDto;

    public Other(String videoUrl){
        String info = UrlUtil.getUrlInfo(VideoType.PHP_PARSING_SERVICE.getParsingUrl(), videoUrl);
        parsingDto = JSONArray.parseObject(info).getObject("data",PhpParsingDto.class);
        if (parsingDto==null)
            throw new UrlParsingException("视频服务解析异常");
    }
}
