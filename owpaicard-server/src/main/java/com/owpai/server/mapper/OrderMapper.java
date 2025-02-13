package com.owpai.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.owpai.pojo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
