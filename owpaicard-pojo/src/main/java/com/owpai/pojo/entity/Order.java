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
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    // 商品id
    private Long gdId;

    // 购买数量
    private BigDecimal number;

    // 订单号
    private String orderNum;

    // 订单状态 0待付款 1待发货 2已完成 3已取消 4待退款 5已退款
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
    private Long CardKeyId;

    // 总价
    private BigDecimal totalPrice;

    // 实收金额
    private BigDecimal amount;

    // 创建时间，下单时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;


}
