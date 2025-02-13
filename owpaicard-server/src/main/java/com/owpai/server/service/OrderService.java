package com.owpai.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.pojo.dto.OrderDTO;
import com.owpai.pojo.entity.Orders;
import com.owpai.pojo.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    void add(OrderDTO orderDTO);

    void delete(Long id);

    Orders select(Long id);

    void update(OrderStatus status, Long id);

    Page<Orders> pageQuery(Integer page, Integer pageSize);

    List<Orders> selectEmail(String email);

    Orders selectNumber(String orderNum);
}
