package com.owpai.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.OrderDTO;
import com.owpai.pojo.entity.Order;
import com.owpai.server.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
public class OrderController {
    private OrderService orderService;

    /**
     * 新增订单
     */
    @PostMapping("/order/add")
    public Result add(@RequestBody OrderDTO orderDTO) {
        orderService.add(orderDTO);
        return Result.success();
    }

    /**
     * 删除订单
     */
    @DeleteMapping("/admin/order/delete/{id}")
    public Result delete(@PathVariable Long id) {
        orderService.delete(id);
        return Result.success();
    }


    /**
     * id查询订单
     */
    @GetMapping("/select/{id}")
    public Result<Order> select(@PathVariable Long id) {
        Order order = orderService.select(id);
        return Result.success(order);
    }

    /**
     * 邮箱查询订单
     */
    @GetMapping("/selectEmail")
    public Result<List<Order>> selectEmail(String email) {
        List<Order> list = orderService.selectEmail(email);
        return Result.success(list);
    }

    /**
     * 订单号查询订单
     */
    @GetMapping("/order/selectNumber")
    public Result<Order> selectNumber(String orderNum) {
        Order order = orderService.selectNumber(orderNum);
        return Result.success(order);
    }

    /**
     * 更改订单状态
     */
    @PutMapping("/status/{status}")
    public Result status(@PathVariable Integer status, Long id) {
        orderService.update(status, id);
        return Result.success();
    }

    /**
     * admin分页查询订单
     */
    @GetMapping("/page")
    public Result<Page> page(Integer page, Integer pageSize) {
        Page<Order> list = orderService.pageQuery(page, pageSize);
        return Result.success(list);
    }

}
