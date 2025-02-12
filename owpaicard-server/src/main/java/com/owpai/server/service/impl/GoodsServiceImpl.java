package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.constant.MessageConstant;
import com.owpai.common.exception.AddNotAllowedException;
import com.owpai.common.exception.DeletionNotAllowedException;
import com.owpai.common.exception.SelectFailedException;
import com.owpai.common.exception.UpdateNotAllowedException;
import com.owpai.pojo.dto.GoodsDTO;
import com.owpai.pojo.entity.Goods;
import com.owpai.server.mapper.GoodsMapper;
import com.owpai.server.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void add(GoodsDTO goodsDTO) {
        String name = goodsDTO.getGdName();
        Goods good = lambdaQuery()
                .eq(Goods::getGdName, name)
                .one();
        if (good != null) {
            throw new AddNotAllowedException(MessageConstant.ALREADY_EXISTS);
        }
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDTO, goods);
        goods.setCreateTime(LocalDateTime.now());
        goodsMapper.insert(goods);
    }

    @Override
    public void delete(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new DeletionNotAllowedException(MessageConstant.NOT_EXISTS);
        }
        goodsMapper.deleteById(id);
    }

    @Override
    public void update(GoodsDTO goodsDTO) {
        Goods gd = goodsMapper.selectById(goodsDTO.getId());
        if (gd == null) {
            throw new UpdateNotAllowedException(MessageConstant.NOT_EXISTS);
        }
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDTO, goods);
        goods.setUpdateTime(LocalDateTime.now());
        goodsMapper.updateById(goods);
    }

    @Override
    public Goods selectById(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new SelectFailedException(MessageConstant.NOT_EXISTS);
        }
        return goods;
    }

    @Override
    public List<Goods> list(Long categoryId) {
        List<Goods> list = lambdaQuery()
                .eq(categoryId != null, Goods::getCategoryId, categoryId)
                .list();
        return list;
    }

    @Override
    public Page<Goods> pageQuery(Integer page, Integer pageSize) {
        Page<Goods> pageInfo = new Page<>(page, pageSize);
        return lambdaQuery()
                .orderByDesc(Goods::getCreateTime)
                .page(pageInfo);
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        Goods gd = goodsMapper.selectById(id);
        if (gd == null) {
            throw new UpdateNotAllowedException(MessageConstant.NOT_EXISTS);
        }
        if (status.equals(gd.getStatus())){
            throw new UpdateNotAllowedException(MessageConstant.ALREADY_IS);
        }
                    gd.setUpdateTime(LocalDateTime.now());
        goodsMapper.updateById(gd);
    }
}
