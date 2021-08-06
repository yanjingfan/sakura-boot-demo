#下面的是例子中的表
CREATE TABLE IF NOT EXISTS `t_order`
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

CREATE TABLE IF NOT EXISTS `t_user` (
  `user_id` varchar(255) NOT NULL DEFAULT '' COMMENT '用户id',
  `username` varchar(255) DEFAULT '' COMMENT '用户名',
  `passwd` VARCHAR(100) DEFAULT '' COMMENT '密码',
  `tenant_id` varchar(255) DEFAULT '' COMMENT '租户id',
  `sex` varchar(255) DEFAULT '' COMMENT '性别（1：男，2：女）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;