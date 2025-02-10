package com.owpai.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.owpai.pojo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}