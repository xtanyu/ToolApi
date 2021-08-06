package com.xtyu.toolapi.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: 小熊
 * @date: 2021/6/15 15:33
 * @description:phone 17521111022
 */
@Data
public class ParsingInfo {
    private Long id;
    private String userOpenId;
    private String downloadUrl;
    private String title;
    private Date createTime;
}
