package com.owpai.server.usercontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.GoodsDTO;
import com.owpai.pojo.entity.Goods;
import com.owpai.pojo.enums.OnOffStatus;
import com.owpai.server.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品管理接口", description = "商品相关接口")
@RestController("userGoodsController")
@RequestMapping("/user")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Operation(summary = "查询商品", description = "根据ID查询商品详细信息")
    @GetMapping("/select")
    public Result<Goods> select(@Parameter(description = "商品ID") @RequestParam(required = true) Long id) {
        Goods goods = goodsService.selectById(id);
        return Result.success(goods);
    }

    @Operation(summary = "分类商品列表", description = "根据分类ID查询商品列表")
    @GetMapping("/list")
    public Result<List<Goods>> selectById(@Parameter(description = "分类ID") @RequestParam(required = true) Long categoryId) {
        List<Goods> list = goodsService.list(categoryId);
        return Result.success(list);
    }

    @Operation(summary = "分页查询商品", description = "分页查询商品列表，支持按分类筛选")
    @GetMapping("/page")
    public Result<Page> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页记录数") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "分类ID") Long categoryId) {
        Page<Goods> pageResult = goodsService.pageQuery(OnOffStatus.ON, page, pageSize, categoryId);
        return Result.success(pageResult);
    }

}
