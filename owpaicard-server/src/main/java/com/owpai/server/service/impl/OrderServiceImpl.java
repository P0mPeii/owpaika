package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.common.constant.MessageConstant;
import com.owpai.common.exception.DeletionNotAllowedException;
import com.owpai.common.exception.UpdateNotAllowedException;
import com.owpai.pojo.dto.OrderDTO;
import com.owpai.pojo.entity.CardKey;
import com.owpai.pojo.entity.Orders;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CardKeyMapper cardKeyMapper;

    @Override
    public void add(OrderDTO orderDTO) {
        Orders orders = new Orders();
        BeanUtils.copyProperties(orderDTO, orders);
        Orders.builder()
                .totalPrice(orders.getPrice().multiply(orders.getNumber()))
                .createTime(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();

        orderMapper.insert(orders);
    }

    @Override
    public void delete(Long id) {
        if (orderMapper.selectById(id) == null) {
            throw new DeletionNotAllowedException(MessageConstant.NOT_EXISTS);
        }
        orderMapper.deleteById(id);
    }

    @Override
    public Orders select(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    @Transactional
    public void update(OrderStatus status, Long id) {
        Orders orders = orderMapper.selectById(id);
        if (orders == null) {
            throw new UpdateNotAllowedException(MessageConstant.NOT_EXISTS);
        }

        if (status.equals(orders.getStatus())) {
            throw new UpdateNotAllowedException(MessageConstant.ALREADY_IS);
        }

        // 如果订单状态更新为已完成(status=COMPLETED)，则更新对应卡密状态为已售出
        if (status == OrderStatus.SENT) {
            CardKey cardKey = cardKeyMapper.selectById(orders.getCardKeyId());
            if (cardKey != null) {
                cardKey.setStatus(1); // 设置卡密状态为已售出
                cardKey.setUpdateTime(LocalDateTime.now());
                cardKeyMapper.updateById(cardKey);
            }
        }

        orders.setStatus(status);
        orders.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(orders);
    }

    @Override
    public Page<Orders> pageQuery(Integer page, Integer pageSize) {
        Page<Orders> pageInfo = new Page<>();
        return lambdaQuery()
                .orderByDesc(Orders::getUpdateTime)
                .page(pageInfo);
    }

    @Override
    public List<Orders> selectEmail(String email) {
        List<Orders> list = lambdaQuery()
                .eq(email != null, Orders::getEmail, email)
                .list();
        return list;
    }

    // 订单号查订单
    @Override
    public Orders selectNumber(String orderNum) {
        Orders orders = lambdaQuery()
                .eq(orderNum != null, Orders::getOrderNum, orderNum)
                .one();
        return orders;
    }
}
