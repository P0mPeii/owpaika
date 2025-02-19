package com.owpai.server.usercontroller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.OrderDTO;
import com.owpai.pojo.entity.Orders;
import com.owpai.server.service.impl.AlipayService;
import com.owpai.server.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理接口", description = "订单相关接口")
@RestController("userOrderController")
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AlipayService alipayService;

    @Operation(summary = "新增订单", description = "创建新的订单信息")
    @PostMapping("/add")
    public Result add(@RequestParam(required = true) @RequestBody OrderDTO orderDTO) {
        // 创建订单
        Orders order = orderService.add(orderDTO);

        try {
            // 调用支付宝预下单接口
            AlipayTradePrecreateResponse response = alipayService.createPrePayOrder(
                    order.getOrderNum(),
                    order.getTotalPrice().toString(),
                    order.getOrderNum(),
                    "5m");

            if (response.isSuccess()) {
                // 返回支付二维码URL
                return Result.success(response.getQrCode());
            } else {
                return Result.error("创建支付订单失败：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            log.error("调用支付宝接口异常", e);
            return Result.error("创建支付订单异常");
        }
    }

    @Operation(summary = "订单号查询订单", description = "根据订单号查询订单信息")
    @GetMapping("/order_num_select")
    public Result<Orders> selectNumber(@Parameter(description = "订单号") @RequestParam(required = true) String orderNum) {
        Orders orders = orderService.selectNumber(orderNum);
        return Result.success(orders);
    }

    @Operation(summary = "订单号取消订单", description = "根据订单号查询订单信息")
    @PutMapping("/cancel")
    public Result cancel(@Parameter(description = "订单号") @RequestParam(required = true) String orderNum) {
        orderService.cancelOrder(orderNum);
        return Result.success();
    }
}
