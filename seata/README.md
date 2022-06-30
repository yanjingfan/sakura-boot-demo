## seata安装配置

[seata1.5.1安装](https://blog.csdn.net/yanzhenjingfan/article/details/125472153)



## 创建业务数据库

- seat-order：存储订单的数据库；
- seat-storage：存储库存的数据库；
- seat-account：存储账户信息的数据库



## 初始化业务表

+ `seat-order`库新建`order`表和`undo_log`表
  
  ```sql
  CREATE TABLE `order` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
    `product_id` bigint(11) DEFAULT NULL COMMENT '产品id',
    `count` int(11) DEFAULT NULL COMMENT '数量',
    `money` decimal(11,0) DEFAULT NULL COMMENT '金额',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
  
  ALTER TABLE `order` ADD COLUMN `status` int(1) DEFAULT NULL COMMENT '订单状态：0：创建中；1：已完结' AFTER `money` ;
  
  CREATE TABLE `undo_log` (
      `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
      `branch_id`     BIGINT(20)   NOT NULL,
      `xid`           VARCHAR(100) NOT NULL,
      `context`       VARCHAR(128) NOT NULL,
      `rollback_info` LONGBLOB     NOT NULL,
      `log_status`    INT(11)      NOT NULL,
      `log_created`   DATETIME     NOT NULL,
      `log_modified`  DATETIME     NOT NULL,
      `ext`           VARCHAR(100) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;
  ```

+ `seat-storage`库中新建`storage`和`undo_log`表
  
  ```sql
  CREATE TABLE `storage` (
                           `id` bigint(11) NOT NULL AUTO_INCREMENT,
                           `product_id` bigint(11) DEFAULT NULL COMMENT '产品id',
                           `total` int(11) DEFAULT NULL COMMENT '总库存',
                           `used` int(11) DEFAULT NULL COMMENT '已用库存',
                           `residue` int(11) DEFAULT NULL COMMENT '剩余库存',
                           PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
  
  INSERT INTO `seat-storage`.`storage` (`id`, `product_id`, `total`, `used`, `residue`) VALUES ('1', '1', '100', '0', '100');
  
  CREATE TABLE `undo_log` (
      `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
      `branch_id`     BIGINT(20)   NOT NULL,
      `xid`           VARCHAR(100) NOT NULL,
      `context`       VARCHAR(128) NOT NULL,
      `rollback_info` LONGBLOB     NOT NULL,
      `log_status`    INT(11)      NOT NULL,
      `log_created`   DATETIME     NOT NULL,
      `log_modified`  DATETIME     NOT NULL,
      `ext`           VARCHAR(100) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;
  ```

+ `seat-account`库中新建`account`表和`undo_log`表
  
  ```sql
  CREATE TABLE `account` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
    `total` decimal(10,0) DEFAULT NULL COMMENT '总额度',
    `used` decimal(10,0) DEFAULT NULL COMMENT '已用余额',
    `residue` decimal(10,0) DEFAULT '0' COMMENT '剩余可用额度',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
  
  INSERT INTO `seat-account`.`account` (`id`, `user_id`, `total`, `used`, `residue`) VALUES ('1', '1', '1000', '0', '1000');
  
  CREATE TABLE `undo_log` (
      `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
      `branch_id`     BIGINT(20)   NOT NULL,
      `xid`           VARCHAR(100) NOT NULL,
      `context`       VARCHAR(128) NOT NULL,
      `rollback_info` LONGBLOB     NOT NULL,
      `log_status`    INT(11)      NOT NULL,
      `log_created`   DATETIME     NOT NULL,
      `log_modified`  DATETIME     NOT NULL,
      `ext`           VARCHAR(100) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4;
  ```



## 测试

前期准备工作做好后，分别启动三个测试服务，然后调用`seata-order-service`服务创建订单的接口，测试能否创建成功。

在`seata-storage-service`或者`seata-account-service`服务中抛个异常模拟下，再调用`seata-order-service`服务创建订单的接口，测试即可。






