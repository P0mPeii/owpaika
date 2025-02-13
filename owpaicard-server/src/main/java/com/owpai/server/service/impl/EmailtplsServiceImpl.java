package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.common.constant.MessageConstant;
import com.owpai.common.exception.AddNotAllowedException;
import com.owpai.pojo.dto.EmailtplsDTO;
import com.owpai.pojo.entity.Emailtpls;
import com.owpai.server.mapper.EmailtplsMapper;
import com.owpai.server.service.EmailtplsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailtplsServiceImpl extends ServiceImpl<EmailtplsMapper, Emailtpls> implements EmailtplsService {
    @Autowired
    private EmailtplsMapper emailtplsMapper;
    @Override
    public void add(EmailtplsDTO emailtplsDTO) {
        if(emailtplsMapper.selectById(emailtplsDTO.getId())!=null){
            throw new AddNotAllowedException(MessageConstant.ALREADY_EXISTS);
        }
        Emailtpls emailtpls = new Emailtpls();
        BeanUtils.copyProperties(emailtplsDTO,emailtpls);
        emailtpls.setCreateTime(LocalDateTime.now());
        emailtplsMapper.insert(emailtpls);
    }

    @Override
    public void delete(List<EmailtplsDTO> eps) {
        emailtplsMapper.deleteBatchIds(eps);
    }

    @Override
    public Emailtpls update(EmailtplsDTO updateDTO) {
        Emailtpls emails = new Emailtpls();
        BeanUtils.copyProperties(updateDTO,emails);
        emails.setUpdateTime(LocalDateTime.now());
        return emails;
    }

    @Override
    public Page<Emailtpls> pageQuery(Integer page, Integer pageSize) {
        Page<Emailtpls> pageInfo=new Page<>(page, pageSize);
        return lambdaQuery()
                .orderByDesc(Emailtpls::getUpdateTime)
                .page(pageInfo);



    }

    @Override
    public Emailtpls select(Long id) {
        return emailtplsMapper.selectById(id);
    }
}
