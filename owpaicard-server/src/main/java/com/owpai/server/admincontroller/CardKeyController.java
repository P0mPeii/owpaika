package com.owpai.server.admincontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.CardKeyDTO;
import com.owpai.pojo.entity.CardKey;
import com.owpai.server.service.CardKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cardKey")
public class CardKeyController {
    @Autowired
    private CardKeyService cardKeyService;

    /**
     * 添加单个卡密
     */
    @PostMapping("/add")
    public Result add(@RequestBody CardKeyDTO cardKeyDTO) {
        cardKeyService.add(cardKeyDTO);
        return Result.success();
    }

    /**
     * 批量添加卡密
     */
    @PostMapping("/adds")
    public Result batchAdd(@RequestBody String cardKeys) {
        String[] keys = cardKeys.split("\n");
        cardKeyService.batchAdd(keys);
        return Result.success();
    }

    /**
     * 删除卡密（批量）
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam List<CardKeyDTO> cks) {
        cardKeyService.delete(cks);
        return Result.success();
    }

    /**
     * 修改卡密
     */
    @PutMapping("/update")
    public Result update(@RequestBody CardKeyDTO cardKeyDTO) {
        cardKeyService.update(cardKeyDTO);
        return Result.success();
    }

    /**
     * 分页查询卡密
     */
    @GetMapping("/page")
    public Result<Page> page(Integer page, Integer pageSize) {
        Page<CardKey> list = cardKeyService.pageQuery(page, pageSize);
        return Result.success(list);
    }

    /**
     * id查询单个卡密
     */
    @GetMapping("/select")
    public Result select(@PathVariable Long id) {
        return Result.success(cardKeyService.select(id));
    }

    /**
     * 修改卡密状态
     */
    @PutMapping("/status/{status}")
    private Result status(@PathVariable Integer status, Long id) {
        cardKeyService.status(status, id);
        return Result.success();
    }

}
