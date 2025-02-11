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
    private String categoryId;
    private BigDecimal price;
    private String image;
    private Integer status;
    @Version
    private Long version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
// DTO
// private Long id;
// private String name;
// private Long categoryId;
// private String image;
// private BigDecimal price;
// private String description;
// private Integer status;

// dont have
// ct
// ut
// description