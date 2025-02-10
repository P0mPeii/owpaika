package com.owpai.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.owpai.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}