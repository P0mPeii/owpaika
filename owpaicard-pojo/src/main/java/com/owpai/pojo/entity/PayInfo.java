package com.owpai.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("pay_info")
public class PayInfo {
    @TableId(type = IdType.AUTO)

    private Long id;
    
    private String serverUrl;
    
    private String appId;
    
    private String privateKey;
    
    private String alipayPublicKey;
    
    private String charset;
    
    private String format;
    
    private String signType;
    
    private String returnUrl;
    
    private String notifyUrl;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}