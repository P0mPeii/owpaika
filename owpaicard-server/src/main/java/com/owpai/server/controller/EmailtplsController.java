package com.owpai.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.EmailtplsDTO;
import com.owpai.pojo.entity.Emailtpls;
import com.owpai.server.service.EmailtplsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/emailtpls")
public class EmailtplsController {
    @Autowired
    private EmailtplsService emailtplsService;

    /**
     * 新建邮件模版
     */
    @PostMapping("/add")
    public Result add(@RequestBody EmailtplsDTO emailtplsDTO) {
        emailtplsService.add(emailtplsDTO);
        return Result.success();
    }

    /**
     * 删除模版
     *
     * @param eps
     * @return
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam List<EmailtplsDTO> eps) {
        emailtplsService.delete(eps);
        return Result.success();
    }

    /**
     * 修改模版
     */
    @PutMapping("/update")
    public Result update(@RequestBody EmailtplsDTO updateDTO) {
        Emailtpls emailtpls = emailtplsService.update(updateDTO);
        return Result.success(emailtpls);
    }

    /**
     * 分页查询模版
     */
    @GetMapping
    public Result<Page> list(Integer page, Integer pageSize) {
        Page<Emailtpls> list = emailtplsService.pageQuery(page, pageSize);
        return Result.success(list);
    }

    /**
     * id查询模版
     */
    public Result<Emailtpls> select(@PathVariable Long id) {
        Emailtpls emailtpls = emailtplsService.select(id);
        return Result.success(emailtpls);
    }
}
