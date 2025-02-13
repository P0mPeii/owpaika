package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.common.constant.MessageConstant;
import com.owpai.common.exception.DeletionNotAllowedException;
import com.owpai.common.exception.UpdateNotAllowedException;
import com.owpai.pojo.dto.OrderDTO;
import com.owpai.pojo.entity.CardKey;
import com.owpai.pojo.entity.Order;
import com.owpai.pojo.enums.OrderStatus;
import com.owpai.server.mapper.CardKeyMapper;
import com.owpai.server.mapper.OrderMapper;
import com.owpai.server.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CardKeyMapper cardKeyMapper;

    @Override
    public void add(OrderDTO orderDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        Order.builder()
                .totalPrice(order.getPrice().multiply(order.getNumber()))
                .createTime(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();

        orderMapper.insert(order);
    }

    @Override
    public void delete(Long id) {
        if (orderMapper.selectById(id) == null) {
            throw new DeletionNotAllowedException(MessageConstant.NOT_EXISTS);
        }
        orderMapper.deleteById(id);
    }

    @Override
    public Order select(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    @Transactional
    public void update(OrderStatus status, Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new UpdateNotAllowedException(MessageConstant.NOT_EXISTS);
        }

        if (status.equals(order.getStatus())) {
            throw new UpdateNotAllowedException(MessageConstant.ALREADY_IS);
        }

        // 如果订单状态更新为已完成(status=COMPLETED)，则更新对应卡密状态为已售出
        if (status == OrderStatus.SENT) {
            CardKey cardKey = cardKeyMapper.selectById(order.getCardKeyId());
            if (cardKey != null) {
                cardKey.setStatus(1); // 设置卡密状态为已售出
                cardKey.setUpdateTime(LocalDateTime.now());
                cardKeyMapper.updateById(cardKey);
            }
        }

        order.setStatus(status);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    @Override
    public Page<Order> pageQuery(Integer page, Integer pageSize) {
        Page<Order> pageInfo = new Page<>();
        return lambdaQuery()
                .orderByDesc(Order::getUpdateTime)
                .page(pageInfo);
    }

    @Override
    public List<Order> selectEmail(String email) {
        List<Order> list = lambdaQuery()
                .eq(email != null, Order::getEmail, email)
                .list();
        return list;
    }

    // 订单号查订单
    @Override
    public Order selectNumber(String orderNum) {
        Order order = lambdaQuery()
                .eq(orderNum != null, Order::getOrderNum, orderNum)
                .one();
        return order;
    }
}
