package com.owpai.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("card_key")
public class CardKey implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long gdId;
    private Integer status;
    private Integer loopKey;
    private String key;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
