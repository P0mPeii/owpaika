package com.owpai.server.controller;

import com.owpai.common.result.Result;
import com.owpai.pojo.dto.GoodsDTO;
import com.owpai.pojo.entity.Goods;
import com.owpai.server.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
