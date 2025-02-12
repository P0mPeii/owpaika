package com.owpai.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
    @TableId(type = IdType.AUTO)

    //主键
    private Long id;

    //分类名称
    private String name;

    //排序
    private Integer sort;
}
