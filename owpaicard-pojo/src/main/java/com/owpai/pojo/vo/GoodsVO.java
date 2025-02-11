package com.owpai.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVO implements Serializable {
    private Long id;
    private String name;
    private Long categoryId;
    private String image;
    private BigDecimal price;
    private String description;
    private Integer status;
    private LocalDate updateTime;
    private String categoryName;
}
