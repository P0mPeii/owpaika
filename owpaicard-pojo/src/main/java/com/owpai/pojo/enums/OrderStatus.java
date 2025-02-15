package com.owpai.pojo.enums;

public enum OrderStatus {
    /**
     * 待支付
     */
    PENDING,

    /**
     * 已支付
     */
    PAID,

    /**
     * 处理中
     */
    PROCESSING,

    /**
     * 已完成
     */
    SENT,

    /**
     * 用户/admin已取消
     */
    CANCEL,

    /**
     * 超时
     */
    TIMEOUT,

    /**
     * 已退款
     */
    REFUND,

    /**
     * 拒绝退款
     */
    REFUSE_REFUND
}