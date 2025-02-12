package com.owpai.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.owpai.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
