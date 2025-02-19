package com.owpai.server.admincontroller;

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

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;

@Tag(name = "订单管理接口", description = "订单相关接口")
@RestController("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController {
    private OrderService orderService;

    @Operation(summary = "新增订单", description = "创建新的订单信息")
    @PostMapping("/add")
    public Result add(@RequestBody OrderDTO orderDTO) {
        orderService.add(orderDTO);
        return Result.success();
    }

    @Operation(summary = "删除订单", description = "根据ID删除订单信息")
    @DeleteMapping("/delete")
    public Result delete(@Parameter(description = "订单ID") @RequestBody Long id) {
        orderService.delete(id);
        return Result.success();
    }

    @Operation(summary = "查询订单", description = "根据ID查询订单详细信息")
    @GetMapping("/select")
    public Result<Orders> select(@Parameter(description = "订单ID") @RequestBody Long id) {
        Orders orders = orderService.select(id);
        return Result.success(orders);
    }

    @Operation(summary = "邮箱查询订单", description = "根据邮箱查询订单列表")
    @GetMapping("/email_select")
    public Result<List<Orders>> selectEmail(@Parameter(description = "邮箱地址") String email) {
        List<Orders> list = orderService.selectEmail(email);
        return Result.success(list);
    }

    @Operation(summary = "订单号查询订单", description = "根据订单号查询订单信息")
    @GetMapping("/order_num_select")
    public Result<Orders> selectNumber(@Parameter(description = "订单号") String orderNum) {
        Orders orders = orderService.selectNumber(orderNum);
        return Result.success(orders);
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
        Page<Orders> list = orderService.pageQuery(page, pageSize);
        return Result.success(list);
    }

    @Operation(summary = "导出订单数据", description = "导出订单数据到Excel文件")
    @GetMapping("/export")
    public void exportOrders(
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime,
            @Parameter(description = "订单状态") @RequestParam(required = false) OrderStatus status,
            HttpServletResponse response) throws IOException {
        List<Orders> ordersList = orderService.getOrdersForExport(startTime, endTime, status);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder
                .encode("订单数据_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), Orders.class)
                .sheet("订单数据")
                .doWrite(ordersList);
    }

}
