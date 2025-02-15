package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.common.constant.MessageConstant;
import com.owpai.common.exception.SelectFailedException;
import com.owpai.common.exception.UpdateNotAllowedException;
import com.owpai.pojo.dto.CardKeyDTO;
import com.owpai.pojo.entity.CardKey;
import com.owpai.server.mapper.CardKeyMapper;
import com.owpai.server.service.CardKeyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CardKeyImpl extends ServiceImpl<CardKeyMapper, CardKey> implements CardKeyService {
    @Autowired
    private CardKeyMapper cardKeyMapper;

    @Override
    public void update(CardKeyDTO cardKeyDTO) {
        CardKey ck = cardKeyMapper.selectById(cardKeyDTO.getId());
        if (ck == null) {
            throw new UpdateNotAllowedException(MessageConstant.NOT_EXISTS);
        }
        ck.setUpdateTime(LocalDateTime.now());
        BeanUtils.copyProperties(cardKeyDTO, ck);
        cardKeyMapper.updateById(ck);
    }

    @Override
    public Object select(Long id) {
        CardKey cardKey = cardKeyMapper.selectById(id);
        if (cardKey == null) {
            throw new SelectFailedException(MessageConstant.NOT_EXISTS);
        }
        return cardKey;
    }

    @Override
    public void status(Integer status, Long id) {
        CardKey cardKey = cardKeyMapper.selectById(id);
        if (status.equals(cardKey.getStatus())) {
            throw new UpdateNotAllowedException(MessageConstant.ALREADY_IS);
        }
        cardKey.setStatus(status);
        cardKeyMapper.updateById(cardKey);
    }

    @Override
    public void delete(List<CardKeyDTO> cks) {
        cardKeyMapper.deleteBatchIds(cks);
    }

    @Override
    public void add(CardKeyDTO cardKeyDTO) {
        CardKey cardKey = new CardKey();
        BeanUtils.copyProperties(cardKeyDTO, cardKey);
        cardKey.setCreateTime(LocalDateTime.now());
        cardKeyMapper.insert(cardKey);
    }

    @Override
    public void batchAdd(String[] keys, boolean removeDuplicates) {
        // 如果需要去重，使用Set集合存储唯一的卡密
        if (removeDuplicates) {
            Set<String> uniqueKeys = new HashSet<>();
            for (String key : keys) {
                if (key != null && !key.trim().isEmpty()) {
                    uniqueKeys.add(key.trim());
                }
            }
            keys = uniqueKeys.toArray(new String[0]);
        }

        for (String key : keys) {
            if (key != null && !key.trim().isEmpty()) {
                CardKey cardKey = new CardKey();
                cardKey.setKey(key.trim());
                cardKey.setCreateTime(LocalDateTime.now());
                cardKey.setStatus(1); // 默认启用状态
                cardKeyMapper.insert(cardKey);
            }
        }
    }

    @Override
    public Page<CardKey> pageQuery(Integer page, Integer pageSize) {
        Page<CardKey> pageInfo = new Page<>(page, pageSize);
        return lambdaQuery()
                .orderByDesc(CardKey::getCreateTime)
                .page(pageInfo);
    }
}
