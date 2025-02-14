package com.owpai.server.usercontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.OrderDTO;
import com.owpai.pojo.entity.Orders;
import com.owpai.pojo.enums.OrderStatus;
import com.owpai.server.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "订单管理接口", description = "订单相关接口")
@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @Operation(summary = "新增订单", description = "创建新的订单信息")
    @PostMapping("/add")
    public Result add(@RequestBody OrderDTO orderDTO) {
        orderService.add(orderDTO);
        return Result.success();
    }

    //todo 用户取消订单


    @Operation(summary = "订单号查询订单", description = "根据订单号查询订单信息")
    @GetMapping("/selectNumber")
    public Result<Orders> selectNumber(@Parameter(description = "订单号") String orderNum) {
        Orders orders = orderService.selectNumber(orderNum);
        return Result.success(orders);
    }

}
