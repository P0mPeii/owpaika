package com.owpai.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends BaseException {
    public BusinessException(String msg) {
        super(msg);
    }
}