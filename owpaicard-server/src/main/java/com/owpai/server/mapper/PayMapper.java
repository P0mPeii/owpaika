package com.owpai.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.owpai.pojo.entity.PayInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayMapper extends BaseMapper<PayInfo> {
}
