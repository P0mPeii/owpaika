package com.owpai.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.owpai.pojo.enums.OrderStatus;
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
@TableName("orders")
public class Orders implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 商品id
    private Long gdId;

    // 购买数量
    private Integer number;

    // 订单号
    private String orderNum;

    // 订单状态 0待付款 1已付款/待处理 2处理中 3已完成 4用户/admin已取消 5超时 6已退款 7拒绝退款
    private OrderStatus status;

    // 支付方式
    private Integer payMethod;

    // 邮箱
    private String email;

    // 单价
    private BigDecimal price;

    // 发货方式 1自动发货 2人工处理
    private Integer type;

    // 卡密id
    private Long cardKeyId;

    // 总价
    private BigDecimal totalPrice;

    // 实收金额
    private BigDecimal payAmount;

    // 支付流水号
    private String paymentId;

    // 支付时间
    private LocalDateTime payTime;

    // 创建时间，下单时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;


}
