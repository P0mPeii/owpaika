package com.owpai.server.admincontroller;

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
@RestController("adminGoodsController")
@RequestMapping("/admin/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Operation(summary = "新增商品", description = "创建新的商品信息")
    @PostMapping("/add")
    public Result add(@RequestBody GoodsDTO goodsDTO) {
        goodsService.add(goodsDTO);
        return Result.success();
    }

    @Operation(summary = "删除商品", description = "根据ID删除商品信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@Parameter(description = "商品ID") @PathVariable Long id) {
        goodsService.delete(id);
        return Result.success();
    }

    @Operation(summary = "修改商品", description = "更新商品信息")
    @PutMapping("/update")
    public Result update(@RequestBody GoodsDTO goodsDTO) {
        goodsService.update(goodsDTO);
        return Result.success();
    }

    @Operation(summary = "查询商品", description = "根据ID查询商品详细信息")
    @GetMapping("/select/{id}")
    public Result<Goods> select(@Parameter(description = "商品ID") @PathVariable Long id) {
        Goods goods = goodsService.selectById(id);
        return Result.success(goods);
    }

    @Operation(summary = "分类商品列表", description = "根据分类ID查询商品列表")
    @GetMapping("/list")
    public Result<List<Goods>> selectById(@Parameter(description = "分类ID") Long categoryId) {
        List<Goods> list = goodsService.list(categoryId);
        return Result.success(list);
    }

    @Operation(summary = "分页查询商品", description = "分页查询商品列表")
    @GetMapping
    public Result<Page> page(
            @Parameter(description = "页码") Integer page,
            @Parameter(description = "每页记录数") Integer pageSize) {
        Page<Goods> pageResult = goodsService.pageQuery(OnOffStatus.ALL,page, pageSize);
        return Result.success(pageResult);
    }

    @Operation(summary = "更新商品状态", description = "启用或禁用商品")
    @PutMapping("/status/{status}")
    public Result status(
            @Parameter(description = "商品状态") @PathVariable Integer status,
            @Parameter(description = "商品ID") Long id) {
        goodsService.updateStatus(status, id);
        return Result.success();
    }
}
