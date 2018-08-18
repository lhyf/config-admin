/*
Navicat MySQL Data Transfer

Source Server         : 本机MySQL
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : config-admin

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-08-18 11:34:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_status` smallint(2) DEFAULT NULL COMMENT '状态（1:正常；0:已失效；）',
  `nickname` varchar(32) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES ('1', 'tom', 'ea401ce5147134d86332381e2cdb808c', '2018-08-10 22:41:17', '2018-08-10 22:41:19', '1', 'Tom', '2018-08-18 11:28:14');

-- ----------------------------
-- Table structure for t_app
-- ----------------------------
DROP TABLE IF EXISTS `t_app`;
CREATE TABLE `t_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_code` varchar(16) DEFAULT NULL,
  `app_name` varchar(64) DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app
-- ----------------------------
INSERT INTO `t_app` VALUES ('1', 'config-admin', '配置中心', 'tom', '2018-08-18 10:44:38', '2018-08-18 10:44:38', '配置中心测试数据', 'tom');

-- ----------------------------
-- Table structure for t_env
-- ----------------------------
DROP TABLE IF EXISTS `t_env`;
CREATE TABLE `t_env` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `app_code` varchar(32) DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_env
-- ----------------------------
INSERT INTO `t_env` VALUES ('1', 'dev', '2018-08-18 10:45:10', '2018-08-18 10:45:10', 'config-admin', 'tom', 'tom', '本机开发环境');
INSERT INTO `t_env` VALUES ('2', 'prod', '2018-08-18 11:23:42', '2018-08-18 11:23:42', 'config-admin', 'tom', 'tom', '生产环境');

-- ----------------------------
-- Table structure for t_item
-- ----------------------------
DROP TABLE IF EXISTS `t_item`;
CREATE TABLE `t_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `namespace_id` int(11) DEFAULT NULL,
  `property` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_item
-- ----------------------------
INSERT INTO `t_item` VALUES ('1', '2', 'spring.redis.host', '127.0.0.1', 'Redis ip', '2018-08-18 11:11:03', 'tom', '2018-08-18 11:11:03', 'tom');
INSERT INTO `t_item` VALUES ('2', '2', 'spring.redis.port', '6379', 'Redis port', '2018-08-18 11:11:03', 'tom', '2018-08-18 11:11:03', 'tom');
INSERT INTO `t_item` VALUES ('3', '2', 'spring.redis.jedis.pool.max-active', '20', '最大连接数', '2018-08-18 11:11:03', 'tom', '2018-08-18 11:11:03', 'tom');
INSERT INTO `t_item` VALUES ('4', '2', 'spring.redis.jedis.pool.min-idle', '2', '最小连接数', '2018-08-18 11:11:03', 'tom', '2018-08-18 11:22:12', 'tom');
INSERT INTO `t_item` VALUES ('5', '3', 'spring.datasource.username', 'root', '用户名', '2018-08-18 11:11:13', 'tom', '2018-08-18 11:11:13', 'tom');
INSERT INTO `t_item` VALUES ('6', '3', 'spring.datasource.password', '123456', '密码', '2018-08-18 11:11:13', 'tom', '2018-08-18 11:11:13', 'tom');
INSERT INTO `t_item` VALUES ('7', '3', 'spring.datasource.url', 'jdbc:mysql://127.0.0.1:3306/config-admin?useUnicode=true&characterEncoding=UTF-8&useSSL=true', 'URL', '2018-08-18 11:11:13', 'tom', '2018-08-18 11:11:13', 'tom');
INSERT INTO `t_item` VALUES ('8', '3', 'spring.datasource.driver-class-name', 'com.mysql.jdbc.Driver', '驱动', '2018-08-18 11:11:13', 'tom', '2018-08-18 11:11:13', 'tom');
INSERT INTO `t_item` VALUES ('9', '3', 'spring.datasource.type', 'com.alibaba.druid.pool.DruidDataSource', '连接池', '2018-08-18 11:11:13', 'tom', '2018-08-18 11:11:13', 'tom');
INSERT INTO `t_item` VALUES ('10', '3', 'spring.datasource.druid.initial-size', '5', '初始化大小', '2018-08-18 11:11:13', 'tom', '2018-08-18 11:11:13', 'tom');
INSERT INTO `t_item` VALUES ('12', '5', 'spring.redis.host', '192.168.16.36', 'Redis ip', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:25:54', 'tom');
INSERT INTO `t_item` VALUES ('13', '5', 'spring.redis.port', '6379', 'Redis port', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:24:58', 'tom');
INSERT INTO `t_item` VALUES ('14', '5', 'spring.redis.jedis.pool.max-active', '20', '最大连接数', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:24:58', 'tom');
INSERT INTO `t_item` VALUES ('15', '5', 'spring.redis.jedis.pool.min-idle', '2', '最小连接数', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:24:58', 'tom');
INSERT INTO `t_item` VALUES ('16', '6', 'spring.datasource.username', 'root', '用户名', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:24:58', 'tom');
INSERT INTO `t_item` VALUES ('17', '6', 'spring.datasource.password', 'root', '密码', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:25:28', 'tom');
INSERT INTO `t_item` VALUES ('18', '6', 'spring.datasource.url', 'jdbc:mysql://192.168.16.36:3306/config-admin?useUnicode=true&characterEncoding=UTF-8&useSSL=true', 'URL', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:25:41', 'tom');
INSERT INTO `t_item` VALUES ('19', '6', 'spring.datasource.driver-class-name', 'com.mysql.jdbc.Driver', '驱动', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:24:58', 'tom');
INSERT INTO `t_item` VALUES ('20', '6', 'spring.datasource.type', 'com.alibaba.druid.pool.DruidDataSource', '连接池', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:24:58', 'tom');
INSERT INTO `t_item` VALUES ('21', '6', 'spring.datasource.druid.initial-size', '5', '初始化大小', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:24:58', 'tom');

-- ----------------------------
-- Table structure for t_namespace
-- ----------------------------
DROP TABLE IF EXISTS `t_namespace`;
CREATE TABLE `t_namespace` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `env_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `space_code` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_namespace
-- ----------------------------
INSERT INTO `t_namespace` VALUES ('2', 'redis通用配置', '1', '2018-08-18 11:11:03', 'tom', '2018-08-18 11:11:03', 'tom', '', 'redis');
INSERT INTO `t_namespace` VALUES ('3', '数据源配置', '1', '2018-08-18 11:11:13', 'tom', '2018-08-18 11:11:13', 'tom', '本机数据库环境', 'mysql');
INSERT INTO `t_namespace` VALUES ('5', 'redis通用配置', '2', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:24:58', 'tom', '', 'redis');
INSERT INTO `t_namespace` VALUES ('6', '数据源配置', '2', '2018-08-18 11:24:58', 'tom', '2018-08-18 11:24:58', 'tom', '本机数据库环境', 'mysql');

-- ----------------------------
-- Table structure for t_pub_item
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_item`;
CREATE TABLE `t_pub_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pub_namespace_id` int(11) DEFAULT NULL,
  `property` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_item
-- ----------------------------
INSERT INTO `t_pub_item` VALUES ('1', '1', 'spring.datasource.username', 'root', '用户名', '2018-08-18 10:39:44', 'tom', '2018-08-18 10:39:44', 'tom');
INSERT INTO `t_pub_item` VALUES ('2', '1', 'spring.datasource.password', '123456', '密码', '2018-08-18 10:40:05', 'tom', '2018-08-18 10:40:05', 'tom');
INSERT INTO `t_pub_item` VALUES ('3', '1', 'spring.datasource.url', 'jdbc:mysql://127.0.0.1:3306/config-admin?useUnicode=true&characterEncoding=UTF-8&useSSL=true', 'URL', '2018-08-18 10:40:28', 'tom', '2018-08-18 10:40:28', 'tom');
INSERT INTO `t_pub_item` VALUES ('4', '1', 'spring.datasource.driver-class-name', 'com.mysql.jdbc.Driver', '驱动', '2018-08-18 10:40:58', 'tom', '2018-08-18 10:40:58', 'tom');
INSERT INTO `t_pub_item` VALUES ('5', '1', 'spring.datasource.type', 'com.alibaba.druid.pool.DruidDataSource', '连接池', '2018-08-18 10:41:24', 'tom', '2018-08-18 10:41:24', 'tom');
INSERT INTO `t_pub_item` VALUES ('6', '1', 'spring.datasource.druid.initial-size', '5', '初始化大小', '2018-08-18 10:41:48', 'tom', '2018-08-18 10:41:48', 'tom');
INSERT INTO `t_pub_item` VALUES ('7', '2', 'spring.redis.host', '127.0.0.1', 'Redis ip', '2018-08-18 10:42:49', 'tom', '2018-08-18 10:42:49', 'tom');
INSERT INTO `t_pub_item` VALUES ('8', '2', 'spring.redis.port', '6379', 'Redis port', '2018-08-18 10:43:11', 'tom', '2018-08-18 10:43:11', 'tom');
INSERT INTO `t_pub_item` VALUES ('9', '2', 'spring.redis.jedis.pool.max-active', '20', '最大连接数', '2018-08-18 10:43:37', 'tom', '2018-08-18 10:43:37', 'tom');
INSERT INTO `t_pub_item` VALUES ('10', '2', 'spring.redis.jedis.pool.min-idle', '1', '最小连接数', '2018-08-18 10:44:03', 'tom', '2018-08-18 10:44:03', 'tom');

-- ----------------------------
-- Table structure for t_pub_namespace
-- ----------------------------
DROP TABLE IF EXISTS `t_pub_namespace`;
CREATE TABLE `t_pub_namespace` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `space_code` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pub_namespace
-- ----------------------------
INSERT INTO `t_pub_namespace` VALUES ('1', '数据源配置', '2018-08-10 22:49:19', 'tom', null, null, '本机数据库环境', 'mysql');
INSERT INTO `t_pub_namespace` VALUES ('2', 'redis通用配置', '2018-08-18 10:42:20', 'tom', null, null, '', 'redis');
