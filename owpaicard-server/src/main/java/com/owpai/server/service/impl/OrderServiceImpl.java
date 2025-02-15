package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.common.constant.MessageConstant;
import com.owpai.common.exception.BusinessException;
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

import java.math.BigDecimal;
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
                .totalPrice(orders.getPrice().multiply(BigDecimal.valueOf(orders.getNumber())))
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

        // 如果订单状态更新为已支付，则分配卡密并更新状态
        if (status == OrderStatus.PAID) {
            // 检查订单商品ID是否存在
            Long gdId = orders.getGdId();
            if (gdId == null) {
                throw new BusinessException("订单商品信息不完整");
            }
            
            // 查询一个未售出的且属于该订单商品的卡密
            CardKey cardKey = cardKeyMapper.selectOne(new LambdaQueryWrapper<CardKey>()
                    .eq(CardKey::getStatus, 0)
                    .eq(CardKey::getGdId, gdId)
                    .last("LIMIT 1"));
            
            if (cardKey == null) {
                throw new BusinessException("暂无可用卡密");
            }
            
            // 更新卡密状态为已售出
            cardKey.setStatus(1);
            cardKey.setUpdateTime(LocalDateTime.now());
            cardKeyMapper.updateById(cardKey);
            
            // 将卡密ID关联到订单
            orders.setCardKeyId(cardKey.getId());
        }
        // 如果订单状态更新为已发货，确认卡密状态
        else if (status == OrderStatus.SENT) {
            Long cardKeyId = orders.getCardKeyId();
            if (cardKeyId != null) {
                CardKey cardKey = cardKeyMapper.selectById(cardKeyId);
                if (cardKey != null) {
                    cardKey.setStatus(1); // 确保卡密状态为已售出
                    cardKey.setUpdateTime(LocalDateTime.now());
                    cardKeyMapper.updateById(cardKey);
                }
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
