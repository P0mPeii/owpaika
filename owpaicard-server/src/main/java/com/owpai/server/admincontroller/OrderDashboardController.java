package com.owpai.server.admincontroller;

import com.owpai.common.result.Result;
import com.owpai.pojo.vo.OrderDashboardTimeVO;
import com.owpai.pojo.vo.OrderDashboardVO;
import com.owpai.server.service.OrderDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class OrderDashboardController {

    private final OrderDashboardService orderDashboardService;

    /**
     * 获取订单仪表盘数据
     *
     * @return 包含今日、近一周、近一月、近一年的订单统计数据
     */
    @GetMapping("/get")
    public Result<OrderDashboardTimeVO> getOrderDashboardData() {
        OrderDashboardTimeVO orderDashboardData = orderDashboardService.getOrderDashboardData();
        return Result.success(orderDashboardData);
    }

    @GetMapping("/get_vo")
    public Result<OrderDashboardVO> getOrderDashboardVO(LocalDateTime startTime, LocalDateTime endTime) {
        OrderDashboardVO orderDashboardVO = orderDashboardService.getOrderDashboardVO(startTime,endTime);
        return Result.success(orderDashboardVO);
    }
}
