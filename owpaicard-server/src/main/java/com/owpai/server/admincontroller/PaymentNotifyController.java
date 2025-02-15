package com.owpai.server.admincontroller;

import com.owpai.common.result.Result;
import com.owpai.server.service.OrderProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "支付通知接口", description = "处理支付平台异步通知")
@RestController
@RequestMapping("/payment/notify")
@Slf4j
public class PaymentNotifyController {

    @Autowired
    private OrderProcessService orderProcessService;

    @Operation(summary = "支付结果通知", description = "接收支付平台的异步通知")
    @PostMapping
    public Result handlePaymentNotify(
            @Parameter(description = "订单号") @RequestParam String orderNum,
            @Parameter(description = "支付金额") @RequestParam Double payAmount,
            @Parameter(description = "支付平台交易号") @RequestParam String paymentId) {
        
        log.info("收到支付通知：订单号 {}, 支付金额 {}, 交易号 {}", orderNum, payAmount, paymentId);
        
        boolean success = orderProcessService.handlePaymentNotify(orderNum, payAmount, paymentId);
        
        if (success) {
            return Result.success("支付通知处理成功");
        } else {
            return Result.error("支付通知处理失败");
        }
    }
}