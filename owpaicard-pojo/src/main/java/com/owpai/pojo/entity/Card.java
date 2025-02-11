package com.owpai.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("card")
public class Card implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String cardNo;    // 卡号
    private String cardKey;   // 卡密
    private BigDecimal price; // 价格
    private Integer status;   // 状态：0-未售出 1-已售出
    private Date createTime;  // 创建时间
    private Date updateTime;  // 更新时间
}