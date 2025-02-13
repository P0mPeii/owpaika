-- 创建管理员表
CREATE TABLE IF NOT EXISTS admin
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(32) NOT NULL UNIQUE COMMENT '用户名',
    password    VARCHAR(64) NOT NULL COMMENT '密码',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '管理员表';

-- 创建商品分类表
CREATE TABLE IF NOT EXISTS category
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(32) NOT NULL UNIQUE COMMENT '分类名称',
    status      TINYINT  DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '商品分类表';

-- 创建商品表
CREATE TABLE IF NOT EXISTS goods
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    gd_name     VARCHAR(32)    NOT NULL COMMENT '商品名称',
    category_id BIGINT         NOT NULL COMMENT '分类id',
    price       DECIMAL(10, 2) NOT NULL COMMENT '价格',
    status      TINYINT  DEFAULT 1 COMMENT '状态 0:下架，1:上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (category_id) REFERENCES category (id)
) COMMENT '商品表';

-- 创建卡密表
CREATE TABLE IF NOT EXISTS card_key
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `key`       VARCHAR(255) NOT NULL COMMENT '卡密',
    status      TINYINT  DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '卡密表';

-- 创建订单表
CREATE TABLE orders
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_num   VARCHAR(32)    NOT NULL UNIQUE COMMENT '订单号',
    gd_id       BIGINT         NOT NULL COMMENT '商品ID',
    email       VARCHAR(32)    NOT NULL COMMENT '邮箱',
    number      INT            NOT NULL DEFAULT 1 COMMENT '数量',
    price       DECIMAL(10, 2) NOT NULL COMMENT '价格',
    total_price DECIMAL(10, 2) NOT NULL COMMENT '订单总金额',
    totalPrice  DECIMAL(10, 2) NOT NULL COMMENT '实收金额',
    type        TINYINT                 default 1 COMMENT '发货方式 1自动发货 2人工处理',
    pay_method  TINYINT COMMENT '支付方式 ',
    status      TINYINT                 DEFAULT 0 COMMENT '订单状态  0待付款 1待发货 2已完成 3已取消 4待退款 5已退款',
    create_time DATETIME                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '订单表';

CREATE TABLE email_tpls
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    tpl_name    varchar(150) NOT NULL COMMENT '邮件标题',
    tpl_content text         NOT NULL COMMENT '邮件内容',
    tpl_token   varchar(50)  NOT NULL COMMENT '邮件标识',
    create_time timestamp    NULL DEFAULT NULL COMMENT '创建时间',
    update_time timestamp    NULL DEFAULT NULL COMMENT '更新时间'
) COMMENT '邮件模板表';