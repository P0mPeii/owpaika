package com.owpai.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CardKeyDTO implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long gd_id;
    private Integer status;
    private Integer loopKey;
    private String key;
}
