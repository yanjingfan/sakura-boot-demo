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
  `user_id` bigint(20) NOT NULL DEFAULT '' AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户名',
  `passwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户id',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别（1：男，2：女）',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;