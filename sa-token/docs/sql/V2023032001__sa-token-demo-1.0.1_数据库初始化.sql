/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : sakura

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 20/03/2023 15:49:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lqb_department
-- ----------------------------
DROP TABLE IF EXISTS `lqb_department`;
CREATE TABLE `lqb_department`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
  `lqb_dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `lqb_dept_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门code',
  `lqb_parent_id` bigint(20) NULL DEFAULT 0 COMMENT '上级部门ID',
  `lqb_parent_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门路径',
  `lqb_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '说明',
  `lqb_create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `lqb_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lqb_update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '操作人ID',
  `lqb_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `lqb_deleted` tinyint(2) NULL DEFAULT 0 COMMENT '是否被删除：1->已删除；0->未删除',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人员部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_department_menu
-- ----------------------------
DROP TABLE IF EXISTS `lqb_department_menu`;
CREATE TABLE `lqb_department_menu`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `lqb_menu_id` bigint(20) NULL DEFAULT 0 COMMENT '菜单主键ID',
  `lqb_dept_id` bigint(20) NULL DEFAULT 0 COMMENT '部门主键ID',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_department_resource
-- ----------------------------
DROP TABLE IF EXISTS `lqb_department_resource`;
CREATE TABLE `lqb_department_resource`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `lqb_resource_id` bigint(20) NULL DEFAULT 0 COMMENT '菜单主键ID',
  `lqb_dept_id` bigint(20) NULL DEFAULT 0 COMMENT '部门主键ID',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门菜单资源关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_menu
-- ----------------------------
DROP TABLE IF EXISTS `lqb_menu`;
CREATE TABLE `lqb_menu`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单主键ID',
  `lqb_menu_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单名称',
  `lqb_menu_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单编号',
  `lqb_menu_level` int(4) NULL DEFAULT 0 COMMENT '菜单级数',
  `lqb_order_num` int(4) NULL DEFAULT 0 COMMENT '排序',
  `lqb_parent_id` bigint(20) NULL DEFAULT 0 COMMENT '上级菜单ID',
  `lqb_parent_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单路径',
  `lqb_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '说明',
  `lqb_web_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '前端名称',
  `lqb_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单访问路径',
  `lqb_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '图标路径',
  `lqb_hidden` tinyint(2) NULL DEFAULT 1 COMMENT '前端隐藏：0->不隐藏；1->隐藏',
  `lqb_create_user_id` bigint(20) NULL DEFAULT 0 COMMENT '创建人ID',
  `lqb_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lqb_update_user_id` bigint(20) NULL DEFAULT 0 COMMENT '操作人ID',
  `lqb_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `lqb_deleted` tinyint(2) NULL DEFAULT 0 COMMENT '是否被删除：1->已删除；0->未删除',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_resource
-- ----------------------------
DROP TABLE IF EXISTS `lqb_resource`;
CREATE TABLE `lqb_resource`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源主键ID',
  `lqb_menu_id` bigint(20) NULL DEFAULT 0 COMMENT '菜单主键ID',
  `lqb_resource_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '资源名称',
  `lqb_resource_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '资源编号',
  `lqb_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '资源URL',
  `lqb_description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '资源描述',
  `lqb_category_id` bigint(20) NULL DEFAULT 0 COMMENT '资源分类ID',
  `lqb_create_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '创建人ID',
  `lqb_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lqb_update_user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '操作人ID',
  `lqb_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `lqb_deleted` tinyint(2) NULL DEFAULT 0 COMMENT '是否被删除：1->已删除；0->未删除',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `lqb_resource_category`;
CREATE TABLE `lqb_resource_category`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lqb_category_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `lqb_order_num` int(4) NULL DEFAULT NULL COMMENT '排序',
  `lqb_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资源分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_role
-- ----------------------------
DROP TABLE IF EXISTS `lqb_role`;
CREATE TABLE `lqb_role`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lqb_role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `lqb_description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '描述',
  `lqb_user_count` bigint(20) NULL DEFAULT NULL COMMENT '后台用户数量',
  `lqb_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lqb_role_status` tinyint(2) NULL DEFAULT 1 COMMENT '启用状态：0->禁用；1->启用',
  `lqb_order_num` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_role_menu_middle
-- ----------------------------
DROP TABLE IF EXISTS `lqb_role_menu_middle`;
CREATE TABLE `lqb_role_menu_middle`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lqb_role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `lqb_menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台角色菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_role_resource_middle
-- ----------------------------
DROP TABLE IF EXISTS `lqb_role_resource_middle`;
CREATE TABLE `lqb_role_resource_middle`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lqb_role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `lqb_resource_id` bigint(20) NULL DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台角色资源关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_user
-- ----------------------------
DROP TABLE IF EXISTS `lqb_user`;
CREATE TABLE `lqb_user`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lqb_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户名',
  `lqb_passwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `lqb_salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '盐',
  `lqb_nick_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '昵称',
  `lqb_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '家庭地址',
  `lqb_icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像',
  `lqb_email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `lqb_login_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后登录时间',
  `lqb_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号',
  `lqb_mobile_two` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '第二个手机号',
  `lqb_telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '座机号码',
  `lqb_user_status` tinyint(2) NULL DEFAULT 1 COMMENT '帐号启用状态：1->启用；0->禁用',
  `lqb_source` tinyint(2) NULL DEFAULT 0 COMMENT '用户来源：0->自填；1->管理员添加；2->微信；3：第三方',
  `lqb_admin` tinyint(2) NULL DEFAULT 0 COMMENT '是否是管理员：1->是；0->否',
  `lqb_order_num` bigint(20) NULL DEFAULT 0 COMMENT '排序字段',
  `lqb_platform_id` int(11) NULL DEFAULT 0 COMMENT '平台id',
  `lqb_create_user_id` bigint(20) NULL DEFAULT 0 COMMENT '创建人id',
  `lqb_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `lqb_update_user_id` bigint(20) NULL DEFAULT 0 COMMENT '修改人id',
  `lqb_update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `lqb_deleted` tinyint(2) NULL DEFAULT 0 COMMENT '是否被删除：1->已删除；0->未删除',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_user_department_middle
-- ----------------------------
DROP TABLE IF EXISTS `lqb_user_department_middle`;
CREATE TABLE `lqb_user_department_middle`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `lqb_user_id` bigint(20) NULL DEFAULT 0 COMMENT '人员主键ID',
  `lqb_dept_id` bigint(20) NULL DEFAULT 0 COMMENT '部门主键ID',
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人员部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lqb_user_role_middle
-- ----------------------------
DROP TABLE IF EXISTS `lqb_user_role_middle`;
CREATE TABLE `lqb_user_role_middle`  (
  `lqb_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lqb_user_id` bigint(20) NOT NULL,
  `lqb_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`lqb_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台用户和角色关系表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
