/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : frame

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 05/18/2017 11:46:19 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin_organization`
-- ----------------------------
DROP TABLE IF EXISTS `admin_organization`;
CREATE TABLE `admin_organization` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `parent` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `built_in` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5f1fbuxt9eafrqhasx2d5htnd` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `admin_organization`
-- ----------------------------
BEGIN;
INSERT INTO `admin_organization` VALUES ('1', '2017-05-11 17:30:57', '南京商数信息', '0', b'1');
COMMIT;

-- ----------------------------
--  Table structure for `admin_resource`
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource`;
CREATE TABLE `admin_resource` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `built_in` bit(1) NOT NULL DEFAULT b'0',
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `parent` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `permission` varchar(255) COLLATE utf8_bin NOT NULL,
  `resource_type` varchar(255) COLLATE utf8_bin NOT NULL,
  `sort_num` int(11) NOT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `menu_type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `dir` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `admin_resource`
-- ----------------------------
BEGIN;
INSERT INTO `admin_resource` VALUES ('1', b'1', null, '系统管理', '0', 'system', 'MENU', '0', '/system', 'LEFT', b'1'), ('11', b'1', null, '用户管理', '1', 'system:user', 'MENU', '1', '/user', 'LEFT', b'0'), ('111', b'1', null, '查询', '11', 'system:user:list', 'ACTION', '1', '/admin/user/list', null, b'0'), ('112', b'1', null, '新增', '11', 'system:user:save', 'ACTION', '2', '/admin/user/save', null, b'0'), ('113', b'1', null, '修改', '11', 'system:user:update', 'ACTION', '3', '/admin/user/update', null, b'0'), ('114', b'1', null, '删除', '11', 'system:user:delete', 'ACTION', '4', '/admin/user/delete', null, b'0'), ('115', b'1', null, '重置密码', '11', 'system:user:resetPwd', 'ACTION', '5', '/admin/user/resetPassword', null, b'0'), ('12', b'1', null, '角色管理', '1', 'system:role', 'MENU', '2', '/role', 'LEFT', b'0'), ('121', b'1', null, '查询', '12', 'system:role:list', 'ACTION', '1', '/admin/role/list', null, b'0'), ('122', b'1', null, '新增', '12', 'system:role:save', 'ACTION', '2', '/admin/role/save', null, b'0'), ('123', b'1', null, '修改', '12', 'system:role:update', 'ACTION', '3', '/admin/role/update', null, b'0'), ('124', b'1', null, '删除', '12', 'system:role:delete', 'ACTION', '4', '/admin/role/delete', null, b'0'), ('13', b'1', null, '资源管理', '1', 'system:resource', 'MENU', '3', '/resource', 'LEFT', b'0'), ('131', b'1', null, '查询', '13', 'system:resource:list', 'ACTION', '1', '/admin/resource/listByResourceTypeAndDir', null, b'0'), ('132', b'1', null, '新增', '13', 'system:resource:save', 'ACTION', '2', '/admin/resource/save', null, b'0'), ('133', b'1', null, '修改', '13', 'system:resource:update', 'ACTION', '3', '/admin/resource/update', null, b'0'), ('134', b'1', null, '删除', '13', 'system:resource:delete', 'ACTION', '4', '/admin/resource/delete', null, b'0'), ('14', b'1', null, '权限管理', '1', 'system:permission', 'MENU', '4', '/permission', 'LEFT', b'0'), ('141', b'1', null, '分配用户', '14', 'system:permission:save', 'ACTION', '1', '/admin/userRoleRelation/save', null, b'0'), ('142', b'1', null, '移除用户', '14', 'system:permission:delete', 'ACTION', '2', '/admin/userRoleRelation/delete', null, b'0'), ('15', b'1', null, '机构管理', '1', 'system:organization', 'MENU', '5', '/organization', 'LEFT', b'0'), ('151', b'1', null, '查询', '15', 'system:organization:list', 'ACTION', '1', '/admin/organization/list', null, b'0'), ('152', b'1', null, '新增', '15', 'system:organization:save', 'ACTION', '2', '/admin/organization/save', null, b'0'), ('153', b'1', null, '修改', '15', 'system:organization:update', 'ACTION', '3', '/admin/organization/update', null, b'0'), ('154', b'1', null, '删除', '15', 'system:organization:delete', 'ACTION', '4', '/admin/organization/delete', null, b'0');
COMMIT;

-- ----------------------------
--  Table structure for `admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `rolename` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `built_in` bit(1) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `admin_role`
-- ----------------------------
BEGIN;
INSERT INTO `admin_role` VALUES ('1', '系统管理员', 'admin', b'1', '2017-05-01 17:06:34');
COMMIT;

-- ----------------------------
--  Table structure for `admin_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_resource`;
CREATE TABLE `admin_role_resource` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `roleid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `resourceid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `admin_role_resource`
-- ----------------------------
BEGIN;
INSERT INTO `admin_role_resource` VALUES ('111', '1', '1'), ('1111', '1', '11'), ('11111', '1', '111'), ('11112', '1', '112'), ('11113', '1', '113'), ('11114', '1', '114'), ('11115', '1', '115'), ('1112', '1', '12'), ('11121', '1', '121'), ('11122', '1', '122'), ('11123', '1', '123'), ('11124', '1', '124'), ('1113', '1', '13'), ('11131', '1', '131'), ('11132', '1', '132'), ('11133', '1', '133'), ('11134', '1', '134'), ('1114', '1', '14'), ('11141', '1', '141'), ('11142', '1', '142'), ('1115', '1', '15'), ('11151', '1', '151'), ('11152', '1', '152'), ('11153', '1', '153'), ('11154', '1', '154');
COMMIT;

-- ----------------------------
--  Table structure for `admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `available` bit(1) DEFAULT NULL,
  `built_in` bit(1) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `real_name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lvod9bfm438ex1071ku1glb70` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `admin_user`
-- ----------------------------
BEGIN;
INSERT INTO `admin_user` VALUES ('1', b'1', b'1', '2017-04-01 17:38:10', '1', 'sdevil507@163.com', '2017-05-18 11:24:54', '2017-04-01 17:38:48', 'be05977add575832dc52655d4ad5c42e', 'admin', '管理员');
COMMIT;

-- ----------------------------
--  Table structure for `admin_user_organization`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_organization`;
CREATE TABLE `admin_user_organization` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `organizationid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `userid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `admin_user_organization`
-- ----------------------------
BEGIN;
INSERT INTO `admin_user_organization` VALUES ('1', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `admin_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `roleid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `userid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `admin_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `admin_user_role` VALUES ('1', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `age` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `test`
-- ----------------------------
BEGIN;
INSERT INTO `test` VALUES ('1', '31', 'gjf');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
