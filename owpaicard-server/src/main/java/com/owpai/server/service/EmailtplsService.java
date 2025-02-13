package com.owpai.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.pojo.dto.EmailtplsDTO;
import com.owpai.pojo.entity.Emailtpls;

import java.util.List;

public interface EmailtplsService {
    void add(EmailtplsDTO emailtplsDTO);

    void delete(List<EmailtplsDTO> eps);

    Emailtpls update(EmailtplsDTO updateDTO);

    Page<Emailtpls> pageQuery(Integer page, Integer pageSize);

    Emailtpls select(Long id);
}
