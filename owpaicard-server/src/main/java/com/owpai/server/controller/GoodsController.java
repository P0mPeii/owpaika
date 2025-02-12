package com.owpai.server.controller;

import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.GoodsDTO;
import com.owpai.pojo.entity.Goods;
import com.owpai.server.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 新加商品
     */
    @PostMapping("/add")
    public Result add(@RequestBody GoodsDTO goodsDTO) {
        goodsService.add(goodsDTO);
        return Result.success();
    }

    /**
     * id删除商品
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        goodsService.delete(id);
        return Result.success();
    }

    /**
     * 修改商品
     */
    @PutMapping("/update")
    public Result update(@RequestBody GoodsDTO goodsDTO) {
        goodsService.update(goodsDTO);
        return Result.success();
    }

    /**
     * 根据id查询商品
     */
    @GetMapping("/select/{id}")
    public Result<Goods> select(@PathVariable Long id) {
        Goods goods = goodsService.selectById(id);
        return Result.success(goods);
    }

    /**
     * 根据种类id查询商品
     */
    @GetMapping("/list")
    public Result<List<Goods>> selectById(Long categoryId) {
        List<Goods> list = goodsService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 商品分页查询
     * 
     * @param page       页码
     * @param pageSize   每页记录数
     * @return 分页查询结果
     */
    @GetMapping
    public Result<Page> page(Integer page, Integer pageSize) {
        Page<Goods> pageResult = goodsService.pageQuery(page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用商品
     */
    @PutMapping("/status/{status}")
    public Result status(@PathVariable Integer status,Long id){
        goodsService.updateStatus(status,id);
        return Result.success();
    }
}
