#下面的是例子中的表
CREATE TABLE IF NOT EXISTS `t_actor`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `actor_name` varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT '' COMMENT '演员名',
  `actor_age` int(0) NULL DEFAULT NULL COMMENT '演员的年龄',
  `actor_email` varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT '' COMMENT '演员的邮箱',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic COMMENT '演员信息表';

CREATE TABLE IF NOT EXISTS `t_work`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `work_name` varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT '' COMMENT '作品名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic COMMENT '演员作品表' ;