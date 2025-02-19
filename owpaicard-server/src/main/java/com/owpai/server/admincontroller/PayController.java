package com.owpai.server.admincontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.PayInfoDTO;
import com.owpai.pojo.entity.PayInfo;
import com.owpai.server.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "支付配置信息")
@RestController
@RequestMapping("/admin/pay")
public class PayController {
    @Autowired
    private PayService payService;

    @Operation(summary = "修改支付设置",description = "修改支付设置")
    @PutMapping("/update")
    public Result update(@Parameter(description = "支付方式信息") @RequestBody PayInfoDTO payInfoDTO){
        payService.update(payInfoDTO);
        return Result.success();
    }

    @Operation(summary = "删除支付设置",description = "删除支付设置")
    @DeleteMapping("/delete")
    public Result delete(@Parameter(description = "支付设置id")@RequestParam Long id){
        payService.delete(id);
        return Result.success();
    }

    @Operation(summary = "新增支付设置",description = "新增支付设置")
    @PostMapping("/add")
    public Result add(@Parameter(description = "支付设置")@RequestBody PayInfoDTO payInfoDTO){
        payService.insert(payInfoDTO);
        return Result.success();
    }

    @Operation(summary = "查询支付设置详情",description = "查询支付设置详情")
    @GetMapping("/select")
    public Result select(@Parameter(description = "支付设置id")Long id){
        PayInfo payInfo=payService.select(id);
        return Result.success(payInfo);
    }

    @Operation(summary = "分页查询支付设置",description = "分页查询支付设置")
    @GetMapping("/page")
    public Result<Page> page(@Parameter Integer page,@RequestParam Integer pageSize){
        Page<PayInfo> list=payService.pageQuery(page,pageSize);
        return Result.success(list);
    }
}
