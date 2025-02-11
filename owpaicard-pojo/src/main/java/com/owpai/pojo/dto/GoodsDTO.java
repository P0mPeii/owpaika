package com.owpai.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsDTO implements Serializable {
    private Long id;
    private String name;
    private Long categoryId;
    private String image;
    private BigDecimal price;
    private String description;
    private Integer status;
}
