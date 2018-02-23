/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-23 15:02:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for website
-- ----------------------------
DROP TABLE IF EXISTS `website`;
CREATE TABLE `website` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `alias` varchar(255) DEFAULT NULL COMMENT '别名',
  `home_page` varchar(255) DEFAULT NULL COMMENT '主页',
  `is_deleted` tinyint(4) DEFAULT '0',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网站';

-- ----------------------------
-- Table structure for web_crawl_record
-- ----------------------------
DROP TABLE IF EXISTS `web_crawl_record`;
CREATE TABLE `web_crawl_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` int(10) DEFAULT NULL COMMENT '网站id',
  `url` varchar(255) DEFAULT NULL COMMENT '网页路径',
  `is_deleted` tinyint(4) DEFAULT '0',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '第一次爬取时间',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网页爬虫记录';

-- ----------------------------
-- Table structure for web_page_info
-- ----------------------------
DROP TABLE IF EXISTS `web_page_info`;
CREATE TABLE `web_page_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url_id` int(10) unsigned DEFAULT NULL COMMENT '网页路径id',
  `url` varchar(255) DEFAULT NULL COMMENT '网页路径',
  `hash` varchar(255) DEFAULT NULL COMMENT '页面哈希',
  `content` text COMMENT '页面内容',
  `is_deleted` tinyint(4) DEFAULT '0',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '爬取时间',
  `deleted_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网页内容信息';
