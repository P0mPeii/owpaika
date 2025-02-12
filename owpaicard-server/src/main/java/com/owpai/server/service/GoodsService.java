package com.owpai.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.pojo.dto.GoodsDTO;
import com.owpai.pojo.entity.Goods;

import java.util.List;

public interface GoodsService {
    void add(GoodsDTO goodsDTO);

    void delete(Long id);

    void update(GoodsDTO goodsDTO);

    Goods selectById(Long id);

    List<Goods> list(Long categoryId);

    Page<Goods> pageQuery(Integer page, Integer pageSize);

    void updateStatus(Integer status, Long id);
}
