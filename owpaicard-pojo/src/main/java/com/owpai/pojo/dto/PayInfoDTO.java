package com.owpai.pojo.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PayInfoDTO {
    @TableId(type = IdType.AUTO)

    private String serverUrl;

    private String appId;

    private String privateKey;

    private String alipayPublicKey;

    private String charset;

    private String format;

    private String signType;

    private String returnUrl;

    private String notifyUrl;

}