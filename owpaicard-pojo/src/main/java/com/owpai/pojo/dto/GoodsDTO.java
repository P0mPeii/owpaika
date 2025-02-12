package com.owpai.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsDTO implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String gdName;
    private Long categoryId;
    private BigDecimal price;
    private String description;
    private String image;
    private Integer status;
}
