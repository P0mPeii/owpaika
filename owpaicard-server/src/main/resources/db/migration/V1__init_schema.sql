CREATE TABLE `admin`
(
    `id`          bigint(20)                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`    varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
    `password`    varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `avatar`      varchar(255) COLLATE utf8mb4_unicode_ci         DEFAULT NULL COMMENT '头像',
    `create_time` datetime                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='管理员表';



CREATE TABLE card_key
(
    id          bigint                                  NOT NULL AUTO_INCREMENT,
    `key`       varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '卡密',
    status      tinyint  DEFAULT '1' COMMENT '状态 0:禁用，1:启用',
    create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    gd_id       bigint   DEFAULT NULL COMMENT '商品ID',
    type        int      DEFAULT '0' COMMENT '卡密类型，0一次性卡密 1循环卡密',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='卡密表';

## 


CREATE TABLE `category`
(
    `id`          bigint(20)                             NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '种类名称',
    `sort`        int(11)    DEFAULT '0' COMMENT '排序',
    `status`      tinyint(4) DEFAULT '1' COMMENT '状态 0:禁用 1:启用',
    `create_time` datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='种类表';



CREATE TABLE `email_tpls`
(
    `id`          bigint(20)                              NOT NULL AUTO_INCREMENT,
    `tpl_name`    varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件标题',
    `tpl_content` text COLLATE utf8mb4_unicode_ci         NOT NULL COMMENT '邮件内容',
    `tpl_token`   varchar(50) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '邮件标识',
    `create_time` timestamp                               NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` timestamp                               NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='邮件模板表';



CREATE TABLE `goods`
(
    `id`          bigint(20)                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `gd_name`     varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
    `category_id` bigint(20)                              DEFAULT NULL,
    `price`       decimal(10, 2)                          NOT NULL COMMENT '商品价格',
    `image`       varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品图片',
    `description` text COLLATE utf8mb4_unicode_ci COMMENT '商品描述',
    `status`      tinyint(4)                              DEFAULT '1' COMMENT '状态 0:禁用 1:启用',
    `create_time` datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `sales`       int(11)                                 DEFAULT '0' COMMENT '商品销量',
    `stock`       int(11)                                 DEFAULT '0' COMMENT '商品库存',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='商品表';



CREATE TABLE `goods_card_key`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `gd_id`       bigint(20) NOT NULL COMMENT '商品ID',
    `card_key_id` bigint(20) NOT NULL COMMENT '卡密ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='商品卡密关系表';



CREATE TABLE `orders`
(
    `id`          bigint(20)                             NOT NULL AUTO_INCREMENT,
    `order_num`   varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
    `gd_id`       bigint(20)                             NOT NULL COMMENT '商品ID',
    `email`       varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
    `number`      int(11)                                NOT NULL DEFAULT '1' COMMENT '数量',
    `price`       decimal(10, 2)                         NOT NULL COMMENT '价格',
    `total_price` decimal(10, 2)                         NOT NULL COMMENT '订单总金额',
    `type`        tinyint(4)                                      DEFAULT '1' COMMENT '发货方式 1自动发货 2人工处理',
    `pay_method`  tinyint(4)                                      DEFAULT NULL COMMENT '支付方式 ',
    `status`      tinyint(4)                                      DEFAULT '0' COMMENT '订单状态*  `0待付款 1已付款/待处理 2处理中 3已完成 4用户/admin已取消 5超时 6已退款 7拒绝退款`*',
    `create_time` datetime                                        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `payment_id`  varchar(64) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '支付流水号',
    `pay_time`    datetime                                        DEFAULT NULL COMMENT '支付时间',
    `pay_amount`  decimal(10, 2)                                  DEFAULT NULL COMMENT '实收金额',
    `card_key_id` bigint(20)                                      DEFAULT NULL COMMENT '卡密ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `order_num` (`order_num`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='订单表';


CREATE TABLE pay_info
(
    -- 主键ID,使用自增
    id            INT AUTO_INCREMENT PRIMARY KEY,

    -- 支付名称,最大长度200个字符
    pay_name      VARCHAR(200) NOT NULL,

    -- 支付标识,最大长度50个字符
    pay_check     VARCHAR(50)  NOT NULL,

    -- 支付方式(1:跳转 2:扫码)
    pay_method    TINYINT(1)   NOT NULL,

    -- 支付场景(1:电脑pc 2:手机 3:全部),默认值为1
    pay_client    TINYINT(1)   NOT NULL DEFAULT 1,

    -- 商户ID,可以为空
    merchant_id   VARCHAR(200) NULL,

    -- 商户KEY,可以为空
    merchant_key  TEXT         NULL,

    -- 商户密钥
    merchant_pem  TEXT         NOT NULL,

    -- 支付处理路径,最大长度200个字符
    callback_path VARCHAR(200) NOT NULL,

    -- 是否启用(1:是 0:否),默认值为1
    is_open       TINYINT(1)   NOT NULL DEFAULT 1,

    -- 创建时间,可以为空
    created_time  TIMESTAMP    NULL,

    -- 更新时间,可以为空
    updated_time  TIMESTAMP    NULL,

    -- 删除时间,可以为空
    deleted_time  TIMESTAMP    NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='支付方式表';


INSERT INTO pay_info (pay_name,
                      pay_check,
                      pay_method,
                      pay_client,
                      merchant_id,
                      merchant_key,
                      merchant_pem,
                      callback_path,
                      is_open,
                      created_time)
VALUES ('支付宝PC支付', -- 支付名称：清晰地表明这是支付宝的PC端支付
        'alipay_pc', -- 支付标识：使用简单的英文标识，方便系统识别
        1, -- 支付方式：1表示跳转支付，适合支付宝PC支付场景
        1, -- 支付场景：1表示PC端
        '2088101123456789', -- 商户ID：这是示例的支付宝商户号
        '私钥', -- 商户KEY：示例的支付宝应用私钥
        '私钥', -- 商户密钥：RSA格式的私钥（示例）
        '回调接口路径', -- 支付处理路由：处理支付宝回调的接口路径
        1, -- 是否启用：1表示启用
        NOW() -- 创建时间：使用当前时间
       );