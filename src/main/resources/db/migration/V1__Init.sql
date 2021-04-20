CREATE TABLE `event_record`
(
    id                  BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    create_time         DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    edit_time           DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    creator             VARCHAR(20)     NOT NULL DEFAULT 'admin',
    editor              VARCHAR(20)     NOT NULL DEFAULT 'admin',
    deleted             TINYINT         NOT NULL DEFAULT 0,
    current_retry_times TINYINT         NOT NULL DEFAULT 0 COMMENT '当前重试次数',
    max_retry_times     TINYINT         NOT NULL DEFAULT 0 COMMENT '最大重试次数',
    queue_name          VARCHAR(255)    NOT NULL COMMENT '队列名',
    exchange_name       VARCHAR(255)    NOT NULL COMMENT '交换器名',
    exchange_type       VARCHAR(8)      NOT NULL COMMENT '交换类型',
    routing_key         VARCHAR(255) COMMENT '路由键',
    source     VARCHAR(32)     NOT NULL COMMENT '事件源（业务模块）',
    business_key        VARCHAR(255)    NOT NULL COMMENT '业务键',
    next_schedule_time  DATETIME        NOT NULL COMMENT '下一次调度时间',
    event_status      TINYINT         NOT NULL DEFAULT 0 COMMENT '事件消息状态',
    init_backoff        BIGINT UNSIGNED NOT NULL DEFAULT 5 COMMENT '退避初始化值',
    backoff_factor      TINYINT         NOT NULL DEFAULT 2 COMMENT '退避因子(也就是指数)',
    INDEX idx_queue_name (queue_name),
    INDEX idx_create_time (create_time),
    INDEX idx_next_schedule_time (next_schedule_time),
    INDEX idx_business_key (business_key)
) COMMENT '事务消息表';

CREATE TABLE `event_data`
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    record_id BIGINT UNSIGNED NOT NULL COMMENT '事务消息记录ID',
    source_data    TEXT COMMENT '消息内容'
) COMMENT '事务消息内容表';

#下面的是例子中的表
CREATE TABLE `t_order`
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    create_time DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    edit_time   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id     BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    order_id    VARCHAR(64)     NOT NULL COMMENT '订单ID',
    amount      DECIMAL(12, 2)  NOT NULL DEFAULT 0 COMMENT '订单金额',
    INDEX idx_user_id (user_id),
    UNIQUE uniq_order_id (order_id)
) COMMENT '订单表';

CREATE TABLE `t_user` (
  `user_id` varchar(255) NOT NULL DEFAULT '' COMMENT '用户id',
  `username` varchar(255) DEFAULT '' COMMENT '用户名',
  `tenant_id` varchar(255) DEFAULT '' COMMENT '租户id',
  `sex` varchar(255) DEFAULT '' COMMENT '性别（1：男，2：女）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;