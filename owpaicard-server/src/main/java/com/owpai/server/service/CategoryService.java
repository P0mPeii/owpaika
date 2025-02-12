package com.owpai.server.service;

import com.owpai.pojo.dto.CategoryDTO;
import com.owpai.pojo.entity.Category;

public interface CategoryService {
    void add(CategoryDTO categoryDTO);

    void deleteById(Long id);

    void update(CategoryDTO categoryDTO);

    Category selectById(Long id);
}
