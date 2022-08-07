DROP DATABASE IF EXISTS db04;
CREATE DATABASE db04;
USE db04;
/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80027
Source Host           : localhost:3306
Source Database       : db04

Target Server Type    : MYSQL
Target Server Version : 80027
File Encoding         : 65001

Date: 2022-08-07 14:28:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图书名称',
  `author` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图书作者',
  `price` mediumtext COLLATE utf8mb4_general_ci COMMENT '图书价格(分为单位)',
  `release_date` date DEFAULT NULL COMMENT '发行日期',
  `pic_path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图书封面路径',
  `stock` int DEFAULT '0' COMMENT '库存',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `ftk_name` (`name`) /*!50100 WITH PARSER `ngram` */ ,
  FULLTEXT KEY `ftk_author` (`author`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='图书表';

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('6', '1', '1', '0', '2022-08-09', '/b091e543a98f4e33909f3a5210c95fe8.png', '1');
INSERT INTO `book` VALUES ('7', '2', '2', '2', '2022-08-16', 'ce4133bfdd664c18825ffc8143cb2227.JPG', '1');
INSERT INTO `book` VALUES ('8', 'test', 'test', '0', '2022-08-16', '54b4a44af45b4ef7a648f0578937e3f3.JPG', '1');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING BTREE,
  FULLTEXT KEY `fk_name` (`name`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', 'computer系', '2022-07-29');
INSERT INTO `department` VALUES ('2', '信息工程系', '2022-07-29');
INSERT INTO `department` VALUES ('3', '视觉传达系', '2021-07-29');
INSERT INTO `department` VALUES ('4', '通信系', '2021-07-29');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `descp` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限描述',
  `parent_id` int DEFAULT '0' COMMENT '父权限id  0表示根权限',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `descp` (`descp`) /*!50100 WITH PARSER `ngram` */ ,
  FULLTEXT KEY `tag` (`tag`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'sys:book:manager', '图书管理', '0');
INSERT INTO `permission` VALUES ('2', 'sys:book:add', '图书添加', '1');
INSERT INTO `permission` VALUES ('8', 'sys:book:delete', '删除图书', '1');
INSERT INTO `permission` VALUES ('9', 'sys:book:update', '修改图书', '1');
INSERT INTO `permission` VALUES ('11', 'sys:department:manager', '院系管理', '0');
INSERT INTO `permission` VALUES ('12', 'sys:department:add', '添加院系', '11');
INSERT INTO `permission` VALUES ('13', 'sys:department:list', '院系列表', '11');
INSERT INTO `permission` VALUES ('14', 'sys:student:manager', '学生管理', '0');
INSERT INTO `permission` VALUES ('15', 'sys:student:add', '添加学生', '14');
INSERT INTO `permission` VALUES ('16', 'sys:rbac:manager', 'RBAC管理', '0');
INSERT INTO `permission` VALUES ('17', 'sys:rbac:role:manager', '角色管理', '16');
INSERT INTO `permission` VALUES ('18', 'sys:rbac:permission:manager', '权限管理', '16');
INSERT INTO `permission` VALUES ('19', 'sys:rbac:user:manager', '用户管理', '16');
INSERT INTO `permission` VALUES ('20', 'sys:book:list', '图书列表', '1');
INSERT INTO `permission` VALUES ('22', 'sys:student:delete', '删除学生', '14');
INSERT INTO `permission` VALUES ('23', 'sys:student:update', '修改学生', '14');
INSERT INTO `permission` VALUES ('24', 'sys:student:list', '学生列表', '14');
INSERT INTO `permission` VALUES ('25', 'sys:rbac:user:add', '添加用户', '19');
INSERT INTO `permission` VALUES ('26', 'sys:rbac:role:add', '添加角色', '17');
INSERT INTO `permission` VALUES ('27', 'sys:rbac:permission:add', '添加权限', '18');
INSERT INTO `permission` VALUES ('28', 'sys:rbac:user:list', '查看用户列表', '19');
INSERT INTO `permission` VALUES ('29', 'sys:rbac:role:list', '查看角色列表', '17');
INSERT INTO `permission` VALUES ('30', 'sys:rbac:permission:list', '查看权限列表', '18');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `descp` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `name` (`name`) /*!50100 WITH PARSER `ngram` */ ,
  FULLTEXT KEY `descp` (`descp`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_super-admin', '超级管理员');
INSERT INTO `role` VALUES ('2', 'ROLE_boss', '老板');
INSERT INTO `role` VALUES ('3', 'ROLE_worker', '普通员工');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `rid` int NOT NULL,
  `pid` int NOT NULL,
  `descp` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`rid`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', null);
INSERT INTO `role_permission` VALUES ('1', '2', null);
INSERT INTO `role_permission` VALUES ('1', '8', null);
INSERT INTO `role_permission` VALUES ('1', '9', null);
INSERT INTO `role_permission` VALUES ('1', '11', null);
INSERT INTO `role_permission` VALUES ('1', '12', null);
INSERT INTO `role_permission` VALUES ('1', '13', null);
INSERT INTO `role_permission` VALUES ('1', '14', null);
INSERT INTO `role_permission` VALUES ('1', '15', null);
INSERT INTO `role_permission` VALUES ('1', '16', null);
INSERT INTO `role_permission` VALUES ('1', '17', null);
INSERT INTO `role_permission` VALUES ('1', '18', null);
INSERT INTO `role_permission` VALUES ('1', '19', null);
INSERT INTO `role_permission` VALUES ('1', '20', null);
INSERT INTO `role_permission` VALUES ('1', '22', null);
INSERT INTO `role_permission` VALUES ('1', '23', null);
INSERT INTO `role_permission` VALUES ('1', '24', null);
INSERT INTO `role_permission` VALUES ('1', '25', null);
INSERT INTO `role_permission` VALUES ('1', '26', null);
INSERT INTO `role_permission` VALUES ('1', '27', null);
INSERT INTO `role_permission` VALUES ('1', '28', null);
INSERT INTO `role_permission` VALUES ('1', '29', null);
INSERT INTO `role_permission` VALUES ('1', '30', null);
INSERT INTO `role_permission` VALUES ('2', '1', null);
INSERT INTO `role_permission` VALUES ('2', '2', null);
INSERT INTO `role_permission` VALUES ('2', '8', null);
INSERT INTO `role_permission` VALUES ('2', '9', null);
INSERT INTO `role_permission` VALUES ('2', '11', null);
INSERT INTO `role_permission` VALUES ('2', '12', null);
INSERT INTO `role_permission` VALUES ('2', '13', null);
INSERT INTO `role_permission` VALUES ('2', '14', null);
INSERT INTO `role_permission` VALUES ('2', '15', null);
INSERT INTO `role_permission` VALUES ('2', '20', null);
INSERT INTO `role_permission` VALUES ('3', '1', null);
INSERT INTO `role_permission` VALUES ('3', '2', null);
INSERT INTO `role_permission` VALUES ('3', '8', null);
INSERT INTO `role_permission` VALUES ('3', '9', null);
INSERT INTO `role_permission` VALUES ('3', '20', null);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '学生id(主键)',
  `name` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学生姓名',
  `age` tinyint unsigned DEFAULT NULL COMMENT '学生年龄',
  `sex` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '性别',
  `email` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `number` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学号',
  `join_date` date DEFAULT NULL COMMENT '入学日期',
  `dep_id` int DEFAULT NULL COMMENT '院系id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING BTREE,
  FULLTEXT KEY `ft_name` (`name`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生表';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '你好', '1', '女', 'a', '1', '2022-08-31', '2');
INSERT INTO `student` VALUES ('2', 'asd', '0', '男', 'ads', 'ads', '2022-08-22', '1');
INSERT INTO `student` VALUES ('3', 'dasda', '1', '男', 'dasdad', 'asdsda', '2022-08-24', '3');
INSERT INTO `student` VALUES ('4', 'ddsasdada', '1', '男', 'dasda', 'da', '2022-08-03', '1');
INSERT INTO `student` VALUES ('5', 'adsdaadd', '1', '男', 'sda', 'asdasd', '2022-08-31', '2');
INSERT INTO `student` VALUES ('6', 'asdadd', '0', '男', 'dadsd', 'asd', '2022-08-29', '1');
INSERT INTO `student` VALUES ('7', 'dddd', '1', '男', 'ddasd', 'ads', '2022-08-16', '1');
INSERT INTO `student` VALUES ('9', 'asdsad', '0', '男', 'asdas', 'a', '2022-08-15', '2');
INSERT INTO `student` VALUES ('10', 'addasd', '0', '男', 'dasd', 'asda', '2022-08-22', '2');
INSERT INTO `student` VALUES ('11', 'aa', '1', '女', 'asd', 'asd', '2022-08-09', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(24) COLLATE utf8mb4_general_ci NOT NULL,
  `descp` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户描述',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `username` (`username`) /*!50100 WITH PARSER `ngram` */ ,
  FULLTEXT KEY `descp` (`descp`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin123', null);
INSERT INTO `user` VALUES ('2', 'root', 'admin123', null);
INSERT INTO `user` VALUES ('5', 'xiaoming', '123', '普通员工');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `uid` int NOT NULL,
  `rid` int NOT NULL,
  `descp` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`uid`,`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', null);
INSERT INTO `user_role` VALUES ('2', '2', null);
INSERT INTO `user_role` VALUES ('5', '3', null);
