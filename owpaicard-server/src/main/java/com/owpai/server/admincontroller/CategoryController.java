package com.owpai.server.admincontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.CategoryDTO;
import com.owpai.pojo.entity.Category;
import com.owpai.server.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "种类相关接口", description = "种类相关接口")
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
    @Operation(summary = "新增种类", description = "新增种类")
    @PostMapping("/add")
    public Result add(@RequestBody CategoryDTO categoryDTO) {
        categoryService.add(categoryDTO);
        return Result.success();
    }

    /**
     * 删除种类
     */
    @Operation(summary = "删除种类", description = "根据id删除种类")
    @DeleteMapping("/delete")
    public Result delete(@Parameter(description = "种类id") @RequestParam Long id) {
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改种类
     */
    @Operation(summary = "修改种类", description = "修改种类信息")
    @PutMapping("/update")
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 根据id查询种类
     */
    @Operation(summary = "查询种类", description = "根据id查询种类")
    @GetMapping("/select")
    public Result<Category> SelectById(@Parameter(description = "种类id") @RequestParam Long id) {
        Category category = categoryService.selectById(id);
        return Result.success(category);
    }

    /**
     * 种类分页查询
     */
    @Operation(summary = "种类分页查询", description = "种类分页查询")
    @GetMapping("/page")
    public Result<Page> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Category> pageResult = categoryService.pageQuery(page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用分类
     */
    @Operation(summary = "修改种类状态", description = "修改种类启用禁用状态")
    @PutMapping("/status")
    public Result status(@Parameter(description = "状态") @RequestParam Integer status,
                         @Parameter(description = "种类id") @RequestParam Long id) {
        categoryService.status(status, id);
        return Result.success();
    }
}
