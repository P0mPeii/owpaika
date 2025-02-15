package com.owpai.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.pojo.dto.CardKeyDTO;
import com.owpai.pojo.entity.CardKey;

import java.util.List;

public interface CardKeyService {
    void update(CardKeyDTO cardKeyDTO);

    Object select(Long id);

    void status(Integer status, Long id);

    void delete(List<CardKeyDTO> cks);

    void add(CardKeyDTO cardKeyDTO);

    void batchAdd(String[] keys,boolean removeDuplicates);

    Page<CardKey> pageQuery(Integer page, Integer pageSize);
}
