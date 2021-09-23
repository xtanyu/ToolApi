DROP TABLE IF EXISTS wx_user;
CREATE TABLE wx_user
(
    id           BIGINT(20) NOT NULL COMMENT '主键ID',
    name         VARCHAR(30) NULL DEFAULT NULL COMMENT '用户名',
    video_number INT(11) NULL DEFAULT NULL COMMENT '解析次数',
    sign_in_sum INT(11) NULL DEFAULT NULL COMMENT '累计签到次数',
    end_sign_in_time  datetime NULL DEFAULT NULL COMMENT '最后签到时间',
    open_id      VARCHAR(50) NULL DEFAULT NULL COMMENT '用户openId',
    create_time  datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_parsing_time datetime NULL DEFAULT NULL COMMENT '最后解析时间',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS parsing_info;
CREATE TABLE parsing_info
(
    id           BIGINT(20) NOT NULL COMMENT '主键ID',
    user_open_id VARCHAR(100) NULL DEFAULT NULL COMMENT 'openId',
    download_url VARCHAR(1000) NULL DEFAULT NULL COMMENT '视频链接',
    title        VARCHAR(500) NULL DEFAULT NULL COMMENT '视频标题',
    author        VARCHAR(200) NULL DEFAULT NULL COMMENT '视频作者',
    cover        VARCHAR(1000) NULL DEFAULT NULL COMMENT '视频封面地址',
    create_time  datetime NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id)
);
