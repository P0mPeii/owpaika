package com.owpai.pojo.enums;

import lombok.Getter;

@Getter
public enum OnOffStatus {

    ALL(1),

    ON(1 << 1),

    OFF(1 << 2);

    private final int mask;

    OnOffStatus(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }

}
