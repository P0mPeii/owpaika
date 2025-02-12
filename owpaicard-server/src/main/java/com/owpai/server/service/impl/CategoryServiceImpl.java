package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.common.constant.MessageConstant;
import com.owpai.common.exception.AddNotAllowedException;
import com.owpai.common.exception.DeletionNotAllowedException;
import com.owpai.common.exception.SelectFailedException;
import com.owpai.common.exception.UpdateNotAllowedException;
import com.owpai.pojo.dto.CategoryDTO;
import com.owpai.pojo.entity.Category;
import com.owpai.server.mapper.CategoryMapper;
import com.owpai.server.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(CategoryDTO categoryDTO) {
        String name =categoryDTO.getName();
        Category cg = lambdaQuery()
                .eq(Category::getName,name)
                .one();
        if(cg != null){
            throw new AddNotAllowedException(MessageConstant.ALREADY_EXISTS);
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setCreateTime(LocalDateTime.now());
        categoryMapper.insert(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryMapper.selectById(id);

        if(category == null){
            throw new DeletionNotAllowedException(MessageConstant.NOT_EXISTS);
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category cg=categoryMapper.selectById(categoryDTO.getId());
        if(cg == null){
            throw new UpdateNotAllowedException(MessageConstant.NOT_EXISTS);
        }
        Category category= new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.updateById(category);

    }

    @Override
    public Category selectById(Long id) {
        Category category = categoryMapper.selectById(id);
        if(category == null){
            throw new SelectFailedException(MessageConstant.NOT_EXISTS);
        }
        return category;
    }
}
