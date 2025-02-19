package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.pojo.dto.PayInfoDTO;
import com.owpai.pojo.entity.PayInfo;
import com.owpai.server.mapper.PayInfoMapper;
import com.owpai.server.mapper.PayMapper;
import com.owpai.server.service.PayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl extends ServiceImpl<PayInfoMapper,PayInfo> implements PayService {
    @Autowired
    private PayMapper payMapper;


    @Override
    public void update(PayInfoDTO payInfoDTO) {
        PayInfo payInfo=new PayInfo();
        BeanUtils.copyProperties(payInfoDTO,payInfo);
        payMapper.updateById(payInfo);
    }

    @Override
    public void delete(Long id) {
        payMapper.deleteById(id);
    }

    @Override
    public void insert(PayInfoDTO payInfoDTO) {
        PayInfo payInfo = new PayInfo();
        BeanUtils.copyProperties(payInfoDTO,payInfo);
        payMapper.insert(payInfo);
    }

    @Override
    public PayInfo select(Long id) {
        return payMapper.selectById(id);
    }

    @Override
    public Page<PayInfo> pageQuery(Integer page, Integer pageSize) {
        Page<PayInfo> pageInfo = new Page<>(page, pageSize);
        return lambdaQuery()
                .orderByDesc(PayInfo::getCreateTime)
                .page(pageInfo);
    }
}
