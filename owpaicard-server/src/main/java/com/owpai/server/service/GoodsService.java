package com.owpai.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.pojo.dto.GoodsDTO;
import com.owpai.pojo.entity.Goods;
import com.owpai.pojo.enums.OnOffStatus;

import java.util.List;

public interface GoodsService {
    void add(GoodsDTO goodsDTO);

    void delete(Long id);

    void update(GoodsDTO goodsDTO);

    Goods selectById(Long id);

    List<Goods> list(Long categoryId);

    Page<Goods> pageQuery(OnOffStatus onOffStatus, Integer page, Integer pageSize, Long categoryId);

    void updateStatus(Integer status, Long id);
}
