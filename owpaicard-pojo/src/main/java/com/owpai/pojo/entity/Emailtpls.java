package com.owpai.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("emailtpls")
public class Emailtpls implements Serializable {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String tplName;
    private String tplContent;
    private String tplToken;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
