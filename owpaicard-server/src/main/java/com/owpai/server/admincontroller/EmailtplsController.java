package com.owpai.server.admincontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.EmailtplsDTO;
import com.owpai.pojo.entity.Emailtpls;
import com.owpai.server.service.EmailtplsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "邮件模板管理接口", description = "邮件模板相关接口")
@RestController
@RequestMapping("/admin/emailtpls")
public class EmailtplsController {
    @Autowired
    private EmailtplsService emailtplsService;

    @Operation(summary = "新增邮件模板", description = "创建新的邮件模板")
    @PostMapping("/add")
    public Result add(@RequestBody EmailtplsDTO emailtplsDTO) {
        emailtplsService.add(emailtplsDTO);
        return Result.success();
    }

    @Operation(summary = "删除邮件模板", description = "批量删除邮件模板")
    @DeleteMapping("/delete")
    public Result delete(@Parameter(description = "模板列表") @RequestParam List<EmailtplsDTO> eps) {
        emailtplsService.delete(eps);
        return Result.success();
    }

    @Operation(summary = "修改邮件模板", description = "更新邮件模板信息")
    @PutMapping("/update")
    public Result update(@RequestBody EmailtplsDTO updateDTO) {
        Emailtpls emailtpls = emailtplsService.update(updateDTO);
        return Result.success(emailtpls);
    }

    @Operation(summary = "分页查询模板", description = "分页查询邮件模板列表")
    @GetMapping("/page")
    public Result<Page> list(
            @Parameter(description = "页码") Integer page,
            @Parameter(description = "每页记录数") Integer pageSize) {
        Page<Emailtpls> list = emailtplsService.pageQuery(page, pageSize);
        return Result.success(list);
    }

    @Operation(summary = "查询模板", description = "根据ID查询邮件模板详细信息")
    @GetMapping("/select")
    public Result<Emailtpls> select(@Parameter(description = "模板ID") @RequestParam Long id) {
        Emailtpls emailtpls = emailtplsService.select(id);
        return Result.success(emailtpls);
    }
}
