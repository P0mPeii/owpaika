package com.owpai.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.pojo.dto.PayInfoDTO;
import com.owpai.pojo.entity.PayInfo;

public interface PayService {
    void update(PayInfoDTO payInfoDTO);

    void delete(Long id);

    void insert(PayInfoDTO payInfoDTO);

    PayInfo select(Long id);

    Page<PayInfo> pageQuery(Integer page, Integer pageSize);
}
