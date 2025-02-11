package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.common.constant.MessageConstant;
import com.owpai.common.exception.AddNotAllowedException;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.GoodsDTO;
import com.owpai.pojo.entity.Goods;
import com.owpai.server.mapper.GoodsMapper;
import com.owpai.server.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void add(GoodsDTO goodsDTO) {
        String name = goodsDTO.getName();
        Goods good = lambdaQuery()
                .eq(Goods::getGdName, name)
                .one();
        if (good != null) {
            throw new AddNotAllowedException(MessageConstant.ALREADY_EXISTS);
        }
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDTO, goods);
        goods.setCreateTime(LocalDateTime.now());
        goods.setVersion(1L); // 初始化版本号
        goodsMapper.insert(goods);
    }
}
