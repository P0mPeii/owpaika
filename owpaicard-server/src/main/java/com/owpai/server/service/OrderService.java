package com.owpai.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.pojo.dto.OrderDTO;
import com.owpai.pojo.entity.Order;

import java.util.List;

public interface OrderService {
    void add(OrderDTO orderDTO);

    void delete(Long id);


    Order select(Long id);

    void update(Integer status, Long id);

    Page<Order> pageQuery(Integer page, Integer pageSize);

    List<Order> selectEmail(String email);

    Order selectNumber(String orderNum);
}
