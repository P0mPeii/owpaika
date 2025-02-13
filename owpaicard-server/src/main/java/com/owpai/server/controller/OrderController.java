package com.owpai.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.OrderDTO;
import com.owpai.pojo.entity.Order;
import com.owpai.pojo.enums.OrderStatus;
import com.owpai.server.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "订单管理接口", description = "订单相关接口")
@RestController
@RequestMapping("/admin/order")
public class OrderController {
    private OrderService orderService;

    @Operation(summary = "新增订单", description = "创建新的订单信息")
    @PostMapping("/order/add")
    public Result add(@RequestBody OrderDTO orderDTO) {
        orderService.add(orderDTO);
        return Result.success();
    }

    @Operation(summary = "删除订单", description = "根据ID删除订单信息")
    @DeleteMapping("/admin/order/delete")
    public Result delete(@Parameter(description = "订单ID") @RequestBody Long id) {
        orderService.delete(id);
        return Result.success();
    }

    @Operation(summary = "查询订单", description = "根据ID查询订单详细信息")
    @GetMapping("/select")
    public Result<Order> select(@Parameter(description = "订单ID") @RequestBody Long id) {
        Order order = orderService.select(id);
        return Result.success(order);
    }

    @Operation(summary = "邮箱查询订单", description = "根据邮箱查询订单列表")
    @GetMapping("/selectEmail")
    public Result<List<Order>> selectEmail(@Parameter(description = "邮箱地址") String email) {
        List<Order> list = orderService.selectEmail(email);
        return Result.success(list);
    }

    @Operation(summary = "订单号查询订单", description = "根据订单号查询订单信息")
    @GetMapping("/order/selectNumber")
    public Result<Order> selectNumber(@Parameter(description = "订单号") String orderNum) {
        Order order = orderService.selectNumber(orderNum);
        return Result.success(order);
    }

    @Operation(summary = "更改订单状态", description = "更新订单的状态信息")
    @PutMapping("/status")
    public Result status(@Parameter(description = "订单状态") @RequestBody OrderStatus status,
            @Parameter(description = "订单ID") @RequestBody Long id) {
        orderService.update(status, id);
        return Result.success();
    }

    @Operation(summary = "分页查询订单", description = "管理员分页查询订单列表")
    @GetMapping("/page")
    public Result<Page> page(@Parameter(description = "页码") Integer page,
            @Parameter(description = "每页记录数") Integer pageSize) {
        Page<Order> list = orderService.pageQuery(page, pageSize);
        return Result.success(list);
    }

}
