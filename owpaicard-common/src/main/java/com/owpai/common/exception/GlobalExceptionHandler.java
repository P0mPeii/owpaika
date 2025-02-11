package com.owpai.common.exception;

import com.owpai.common.constant.MessageConstant;
import com.owpai.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result<String> handleException(BaseException e) {
        log.error("系统异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg=username+ MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

    }

//    @ExceptionHandler(BusinessException.class)
//    public Result<String> handleBusinessException(BusinessException e) {
//        log.error("业务异常：{}", e.getMessage());
//        return Result.error(e.getMessage());
//    }
//
//    @ExceptionHandler(LoginFailedException.class)
//    public Result<String> handleLoginFailedException(LoginFailedException e) {
//        log.error("登录失败：{}", e.getMessage());
//        return Result.error(e.getMessage());
//    }
}