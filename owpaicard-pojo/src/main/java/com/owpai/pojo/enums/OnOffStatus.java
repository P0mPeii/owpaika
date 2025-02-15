package com.owpai.pojo.enums;

import lombok.Getter;

@Getter
public enum OnOffStatus {

    OFF(0, "已下架"),

    ON(1, "已下架"),

    ALL(99, "全部上下架状态");
    private final int code;
    private final String description;

    OnOffStatus(final int code, final String description) {
        this.code = code;
        this.description = description;
    }
}