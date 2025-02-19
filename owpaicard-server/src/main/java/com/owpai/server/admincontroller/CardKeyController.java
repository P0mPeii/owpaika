package com.owpai.server.admincontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.CardKeyDTO;
import com.owpai.pojo.entity.CardKey;
import com.owpai.server.service.CardKeyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "卡密相关接口", description = "卡密相关接口")
@RestController
@RequestMapping("/admin/card_key")
public class CardKeyController {
    @Autowired
    private CardKeyService cardKeyService;

    /**
     * 添加单个卡密
     */
    @Operation(summary = "添加单个卡密", description = "添加单个卡密")
    @PostMapping("/add")
    public Result add(@RequestBody CardKeyDTO cardKeyDTO) {
        cardKeyService.add(cardKeyDTO);
        return Result.success();
    }

    /**
     * 批量添加卡密
     */
    @Operation(summary = "批量添加卡密", description = "批量添加卡密")
    @PostMapping("/adds")
    public Result batchAdd(@Parameter(description = "多个卡密") @RequestParam String cardKeys,
                           @Parameter(description = "是否去重") @RequestParam(required = false) boolean removeDuplicates) {
        String[] keys = cardKeys.split("\n");
        cardKeyService.batchAdd(keys, removeDuplicates);
        return Result.success();
    }

    /**
     * 删除卡密（批量）
     */
    @Operation(summary = "删除卡密", description = "删除卡密")
    @DeleteMapping("/delete")
    public Result delete(@Parameter(description = "卡密id,可单个可多个") @RequestParam List<CardKeyDTO> cks) {
        cardKeyService.delete(cks);
        return Result.success();
    }

    /**
     * 修改卡密
     */
    @Operation(summary = "修改卡密", description = "修改卡密信息")
    @PutMapping("/update")
    public Result update(@RequestBody CardKeyDTO cardKeyDTO) {
        cardKeyService.update(cardKeyDTO);
        return Result.success();
    }

    /**
     * 分页查询卡密
     */
    @Operation(summary = "分页查询卡密", description = "分页查询卡密")
    @GetMapping("/page")
    public Result<Page> page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<CardKey> list = cardKeyService.pageQuery(page, pageSize);
        return Result.success(list);
    }

    /**
     * id查询单个卡密
     */
    @Operation(summary = "查询单个卡密", description = "id查询单个卡密")
    @GetMapping("/select")
    public Result select(@Parameter(description = "卡密id") @RequestParam Long id) {
        return Result.success(cardKeyService.select(id));
    }

    /**
     * 修改卡密状态
     */
    @Operation(summary = "修改卡密状态", description = "修改卡密状态")
    @PutMapping("/status")
    private Result status(@Parameter(description = "卡密更新的状态") @RequestParam Integer status,
                          @Parameter(description = "卡密id") @RequestParam Long id) {
        cardKeyService.status(status, id);
        return Result.success();
    }

}
