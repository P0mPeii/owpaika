package com.owpai.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.pojo.dto.CategoryDTO;
import com.owpai.pojo.entity.Category;

public interface CategoryService {
    void add(CategoryDTO categoryDTO);

    void deleteById(Long id);

    void update(CategoryDTO categoryDTO);

    Category selectById(Long id);

    Page<Category> pageQuery(Integer page, Integer pageSize);

    void status(Integer status, Long id);
}
