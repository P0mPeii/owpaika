package com.owpai.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.CategoryDTO;
import com.owpai.pojo.dto.GoodsDTO;
import com.owpai.pojo.entity.Category;
import com.owpai.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增 种类
     *
     * @param categoryDTO
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CategoryDTO categoryDTO) {
        categoryService.add(categoryDTO);
        return Result.success();
    }

    /**
     * 删除种类
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改种类
     */
    @PutMapping("/update")
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 根据id查询种类
     */
    @GetMapping("/select/{id}")
    public Result<Category> SelectById(@PathVariable Long id) {
        Category category = categoryService.selectById(id);
        return Result.success(category);
    }

    /**
     * 种类分页查询
     */
    @GetMapping
    public Result<Page> page(Integer page, Integer pageSize) {
        Page<Category> pageResult = categoryService.pageQuery(page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用分类
     */
    @PutMapping("/status/{status}")
    public Result status(@PathVariable Integer status, Long id) {
        categoryService.status(status, id);
        return Result.success();
    }
}
