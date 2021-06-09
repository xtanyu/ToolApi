package com.xtyu.toolapi.model.enums;

public enum VideoType {

    DOU_YIN("video/", "/?", "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids="),
    PI_PI_XIA("item/", "?", "https://is.snssdk.com/bds/cell/detail/?cell_type=1&aid=1319&app_name=super&cell_id=");
    private final String cutStart;
    private final String cutStop;
    private final String parsingUrl;

    VideoType(String cutStart, String cutStop, String parsingUrl) {
        this.cutStart = cutStart;
        this.cutStop = cutStop;
        this.parsingUrl = parsingUrl;
    }

    public String getCutStart() {
        return this.cutStart;
    }

    public String getCutStop() {
        return this.cutStop;
    }

    public String getParsingUrl() {
        return this.parsingUrl;
    }
}
