package com.owpai.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("order")
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)

    private Long id;

    //商品id
    private Long gd_id;

    //购买数量
    private BigDecimal number;

    //订单号
    private  String orderNum;

    //订单状态 1待付款 2待发货 3已完成 4已取消 5待退款 6已退款
    private Integer status;

    //支付方式
    private Integer payMethod;

    //邮箱
    private String email;

    //单价
    private BigDecimal price;

    //总价
    private BigDecimal totalPrice;

    //实收金额
    private BigDecimal amount;

    //创建时间，下单时间
    private LocalDateTime createTime;

    //更新时间
    private LocalDateTime updateTime;


}
