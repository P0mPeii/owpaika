package com.owpai.server.service;

import com.owpai.pojo.entity.Orders;

public interface OrderProcessService {
    /**
     * 处理支付平台异步通知
     * @param orderNum 订单号
     * @param payAmount 支付金额
     * @param paymentId 支付平台交易号
     * @return 处理结果
     */
    boolean handlePaymentNotify(String orderNum, Double payAmount, String paymentId);

    /**
     * 完成订单处理
     * @param order 订单信息
     */
    void completedOrder(Orders order);

    /**
     * 处理自动发货订单
     * @param order 订单信息
     */
    void processAutoDeliveryOrder(Orders order);

    /**
     * 处理手动处理订单
     * @param order 订单信息
     */
    void processManualOrder(Orders order);
}