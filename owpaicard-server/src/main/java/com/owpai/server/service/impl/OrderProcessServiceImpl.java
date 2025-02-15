package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.owpai.common.exception.BusinessException;
import com.owpai.pojo.entity.CardKey;
import com.owpai.pojo.entity.Goods;
import com.owpai.pojo.entity.Orders;
import com.owpai.pojo.enums.OrderStatus;
import com.owpai.server.mapper.CardKeyMapper;
import com.owpai.server.mapper.GoodsMapper;
import com.owpai.server.mapper.OrderMapper;
import com.owpai.server.service.EmailService;
import com.owpai.server.service.OrderProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderProcessServiceImpl implements OrderProcessService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CardKeyMapper cardKeyMapper;
    @Autowired
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    @Transactional
    public boolean handlePaymentNotify(String orderNum, Double payAmount, String paymentId) {
        // 查询订单
        Orders order = orderMapper.selectOne(
                new LambdaQueryWrapper<Orders>()
                        .eq(Orders::getOrderNum, orderNum));

        if (order == null) {
            log.error("订单不存在：{}", orderNum);
            return false;
        }

        // 验证订单状态
        if (order.getStatus() != OrderStatus.PENDING) {
            log.error("订单状态异常：{}, 当前状态：{}", orderNum, order.getStatus());
            return false;
        }

        // 验证支付金额
        BigDecimal notifyAmount = BigDecimal.valueOf(payAmount);
        if (notifyAmount.compareTo(order.getTotalPrice()) != 0) {
            log.error("支付金额不匹配：订单金额 {}, 支付金额 {}", order.getTotalPrice(), notifyAmount);
            return false;
        }

        // 更新订单信息
        order.setPaymentId(paymentId);
        order.setPayAmount(notifyAmount);
        order.setPayTime(LocalDateTime.now());

        // 完成订单处理
        completedOrder(order);
        return true;
    }

    @Override
    @Transactional
    public void completedOrder(Orders order) {
        // 更新订单状态为已支付
        order.setStatus(OrderStatus.PAID);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        // 增加商品销量
        Goods goods = goodsMapper.selectById(order.getGdId());
        if (goods != null) {
            goods.setSales(goods.getSales() + order.getNumber());
            goodsMapper.updateById(goods);
        }

        // 根据发货方式处理订单
        if (goods.getType() == 1) { // 自动发货
            processAutoDeliveryOrder(order);
        } else { // 手动处理
            processManualOrder(order);
        }
    }

    @Override
    @Transactional
    public void processAutoDeliveryOrder(Orders order) {
        // 获取对应数量的卡密
        Long count = cardKeyMapper.selectCount(
                new LambdaQueryWrapper<CardKey>()
                        .eq(CardKey::getGdId, order.getGdId())
                        .eq(CardKey::getStatus, 0));

        if (count < order.getNumber()) {
            throw new BusinessException("库存不足");
        }

        // 分配卡密
        CardKey cardKey = cardKeyMapper.selectOne(
                new LambdaQueryWrapper<CardKey>()
                        .eq(CardKey::getGdId, order.getGdId())
                        .eq(CardKey::getStatus, 0)
                        .last("LIMIT 1"));

        if (cardKey != null) {
            // 更新卡密状态
            cardKey.setStatus(1);
            cardKey.setUpdateTime(LocalDateTime.now());
            cardKeyMapper.updateById(cardKey);

            // 更新订单状态和卡密信息
            order.setStatus(OrderStatus.SENT);
            order.setCardKeyId(cardKey.getId());
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);

            // 发送邮件通知
            String emailContent = String.format("您的订单 %s 已发货，卡密信息：%s",
                    order.getOrderNum(), cardKey.getKey());
            emailService.sendEmail(order.getEmail(), "订单发货通知", emailContent);
        }
    }

    @Override
    @Transactional
    public void processManualOrder(Orders order) {
        // 更新订单状态为处理中
        order.setStatus(OrderStatus.PROCESSING);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);

        // 减少商品库存
        Goods goods = goodsMapper.selectById(order.getGdId());
        if (goods != null) {
            goods.setStock(goods.getStock() - order.getNumber());
            goodsMapper.updateById(goods);
        }

        // 发送邮件通知管理员
        String emailContent = String.format("新订单待处理，订单号：%s，商品：%s，数量：%d",
                order.getOrderNum(), goods.getGdName(), order.getNumber());
        emailService.sendEmail(adminEmail, "新订单待处理通知", emailContent);
    }
}