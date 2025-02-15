package com.owpai.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("goods")
public class Goods implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String gdName;

    private Long categoryId;

    private BigDecimal price;

    private String description;

    //发货方式
    private Integer type;

    private String image;

    private Integer status;

    //销量
    private Integer sales;

    //库存
    private Integer stock;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
