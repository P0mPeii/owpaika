package com.owpai.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderDTO implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    //商品id
    private Integer gd_id;

    //支付方式
    private Integer payMethod;

    //邮箱
    private String email;

    //购买数量
    private  BigDecimal number;

    //商品单价
    private BigDecimal price;

}
