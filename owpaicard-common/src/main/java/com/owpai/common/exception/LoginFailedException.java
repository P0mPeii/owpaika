package com.owpai.common.exception;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String msg) {
        super(msg);
    }

}