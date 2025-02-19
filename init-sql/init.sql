-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS owpaicard DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE owpaicard;


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

INSERT INTO admin (username, password)
VALUES ('admin', MD5('123456'));


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


CREATE TABLE IF NOT EXISTS `pay_info`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `server_url`        varchar(255) NOT NULL COMMENT '支付宝网关',
    `app_id`            varchar(255) NOT NULL COMMENT '应用ID',
    `private_key`       text         NOT NULL COMMENT '应用私钥',
    `alipay_public_key` text         NOT NULL COMMENT '支付宝公钥',
    `charset`           varchar(10)  NOT NULL DEFAULT 'UTF-8' COMMENT '字符编码',
    `format`            varchar(10)  NOT NULL DEFAULT 'json' COMMENT '格式',
    `sign_type`         varchar(10)  NOT NULL DEFAULT 'RSA2' COMMENT '签名方式',
    `return_url`        varchar(255) NOT NULL COMMENT '同步回调地址',
    `notify_url`        varchar(255) NOT NULL COMMENT '异步通知地址',
    `create_time`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='支付配置信息表';

-- 插入默认配置
INSERT INTO `pay_info` (`server_url`, `app_id`, `private_key`, `alipay_public_key`,
                        `charset`, `format`, `sign_type`, `return_url`, `notify_url`)
VALUES ('https://openapi.alipay.com/gateway.do',
        'your_app_id',
        'your_private_key',
        'your_alipay_public_key',
        'UTF-8',
        'json',
        'RSA2',
        'http://your-domain/return',
        'http://your-domain/notify');