package com.owpai.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailtplsDTO implements Serializable {
    private Long id;
    //邮件标题
    private String tplName;
    //邮件内容
    private String tplContent;
    //邮件标识
    private String tplToken;
}
