/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : dks

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-11-01 11:05:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `init_info`
-- ----------------------------
DROP TABLE IF EXISTS `init_info`;
CREATE TABLE `init_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `current_value` int(20) NOT NULL DEFAULT '100000' COMMENT '当前主键值',
  `sys_id` int(20) NOT NULL COMMENT '系统id',
  `sys_name` varchar(20) NOT NULL DEFAULT '' COMMENT '系统名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='主键初始化表';

-- ----------------------------
-- Records of init_info
-- ----------------------------
INSERT INTO `init_info` VALUES ('1', '100000', '1', 'PCS', '2016-11-01 11:03:51', '0');
INSERT INTO `init_info` VALUES ('2', '100000', '2', 'BOH', '2016-11-01 11:04:21', '0');
INSERT INTO `init_info` VALUES ('3', '100000', '3', 'GMS', '2016-11-01 11:04:31', '0');

-- ----------------------------
-- Table structure for `sys_info`
-- ----------------------------
DROP TABLE IF EXISTS `sys_info`;
CREATE TABLE `sys_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sys_name` varchar(20) NOT NULL DEFAULT '' COMMENT '系统名称',
  `authority_code` varchar(40) NOT NULL DEFAULT '' COMMENT '系统权限码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_info
-- ----------------------------
INSERT INTO `sys_info` VALUES ('1', 'PCS', 'ffa9c6553bd093c43628899a2b5008b0', '2016-11-01 11:03:51', '0');
INSERT INTO `sys_info` VALUES ('2', 'BOH', 'c6acf1c2c5f96af518cb98a7ca68dbd6', '2016-11-01 11:04:21', '0');
INSERT INTO `sys_info` VALUES ('3', 'GMS', '34770578c6602aa852110c0abc5efd07', '2016-11-01 11:04:31', '0');
