/*
Navicat MySQL Data Transfer

Source Server         : Local DB
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : app-dev

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-04-10 20:08:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `description` longtext,
  `is_fixed` int(11) DEFAULT '0',
  `name` varchar(100) DEFAULT NULL,
  `sequence` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ah1isfy5alow4rtkty9vri85r` (`code`),
  KEY `FKnd95at2xgu5eb9http5x3jdk` (`parent_id`),
  CONSTRAINT `FKnd95at2xgu5eb9http5x3jdk` FOREIGN KEY (`parent_id`) REFERENCES `sys_dictionary` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dictionary_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary_type`;
CREATE TABLE `sys_dictionary_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `description` longtext,
  `is_fixed` int(11) DEFAULT '0',
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hrpfljty1s5rqoii84gwvtscw` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dictionary_type
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `content` longtext,
  `description` longtext,
  `ip` varchar(100) DEFAULT NULL,
  `module` varchar(200) DEFAULT NULL,
  `response_time` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=277 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('269', '2017-04-10 20:05:43', '0', '2017-04-10 20:05:43', '[类名]:io.zhijian.system.service.impl.RoleService,[方法]:update,[参数]:name=超级管理员&code=admin&description=超级管理员&,[IP]:169.254.12.85', '修改角色信息', '169.254.12.85', '系统角色', null, 'admin');
INSERT INTO `sys_log` VALUES ('270', '2017-04-10 20:05:43', '0', '2017-04-10 20:05:43', '[类名]:io.zhijian.system.service.impl.RoleService,[方法]:update,[参数]:name=超级管理员&code=admin&description=超级管理员&,[IP]:169.254.12.85', '修改角色信息', '169.254.12.85', '系统角色', null, 'admin');
INSERT INTO `sys_log` VALUES ('271', '2017-04-10 20:05:54', '0', '2017-04-10 20:05:54', '[类名]:io.zhijian.system.service.impl.RoleService,[方法]:deleteUserRole,[参数]:null,[IP]:169.254.12.85', '删除角色下指定的用户', '169.254.12.85', '系统角色', null, 'admin');
INSERT INTO `sys_log` VALUES ('272', '2017-04-10 20:05:54', '0', '2017-04-10 20:05:54', '[类名]:io.zhijian.system.service.impl.RoleService,[方法]:deleteUserRole,[参数]:null,[IP]:169.254.12.85', '删除角色下指定的用户', '169.254.12.85', '系统角色', null, 'admin');
INSERT INTO `sys_log` VALUES ('273', '2017-04-10 20:06:07', '0', '2017-04-10 20:06:07', '[类名]:io.zhijian.system.service.impl.RoleService,[方法]:deleteUserRole,[参数]:null,[IP]:169.254.12.85', '删除角色下指定的用户', '169.254.12.85', '系统角色', null, 'admin');
INSERT INTO `sys_log` VALUES ('274', '2017-04-10 20:06:07', '0', '2017-04-10 20:06:07', '[类名]:io.zhijian.system.service.impl.RoleService,[方法]:deleteUserRole,[参数]:null,[IP]:169.254.12.85', '删除角色下指定的用户', '169.254.12.85', '系统角色', null, 'admin');
INSERT INTO `sys_log` VALUES ('275', '2017-04-10 20:07:33', '0', '2017-04-10 20:07:33', '[类名]:io.zhijian.system.service.impl.UserService,[方法]:update,[参数]:username=admin&name=admin&mobile=&email=&gender=0&,[IP]:169.254.12.85', '更新用户信息', '169.254.12.85', '系统用户', null, 'admin');
INSERT INTO `sys_log` VALUES ('276', '2017-04-10 20:07:33', '0', '2017-04-10 20:07:33', '[类名]:io.zhijian.system.service.impl.UserService,[方法]:update,[参数]:username=admin&name=admin&mobile=&email=&gender=0&,[IP]:169.254.12.85', '更新用户信息', '169.254.12.85', '系统用户', null, 'admin');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `code` longtext,
  `description` longtext,
  `icon` varchar(100) DEFAULT NULL,
  `is_fixed` int(11) DEFAULT '0',
  `name` varchar(100) DEFAULT NULL,
  `sequence` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `type` varchar(100) DEFAULT NULL,
  `url` longtext,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3fekum3ead5klp7y4lckn5ohi` (`parent_id`),
  CONSTRAINT `FK3fekum3ead5klp7y4lckn5ohi` FOREIGN KEY (`parent_id`) REFERENCES `sys_resource` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', null, '0', '2017-04-02 16:31:00', null, '', null, '0', '系统设置', '1', '0', '1', '', null);
INSERT INTO `sys_resource` VALUES ('2', null, '0', '2017-04-02 16:31:08', null, '', null, '0', '系统监控', '2', '0', '1', '', null);
INSERT INTO `sys_resource` VALUES ('3', null, '0', '2017-04-07 12:10:33', null, '', null, '0', '用户管理', '1', '0', '1', '/admin/user/list', '1');
INSERT INTO `sys_resource` VALUES ('4', null, '0', '2017-04-02 16:29:32', null, 'aaa', null, '0', '角色管理', '2', '0', '1', '/admin/role/list', '1');
INSERT INTO `sys_resource` VALUES ('5', null, '0', '2017-04-07 12:10:57', null, '', null, '0', '菜单管理', '4', '0', '1', '/admin/menu/list', '1');
INSERT INTO `sys_resource` VALUES ('6', null, '0', null, null, null, null, '0', '日志记录', '0', '0', '1', '/admin/log/list', '2');
INSERT INTO `sys_resource` VALUES ('7', null, '0', null, null, null, null, '0', '数据库监控', '0', '0', '1', '/druid/index.html', '2');
INSERT INTO `sys_resource` VALUES ('9', '2017-04-01 21:33:22', '0', '2017-04-07 19:44:00', 'sys:menu:add', '添加菜单', null, '0', '添加', null, '0', '2', null, '5');
INSERT INTO `sys_resource` VALUES ('10', '2017-04-06 20:21:45', '0', '2017-04-06 20:21:45', null, '', null, '0', '权限管理', '3', '0', '1', '/admin/permission/list', '1');
INSERT INTO `sys_resource` VALUES ('11', '2017-04-07 12:05:08', '0', '2017-04-07 12:05:08', null, '', null, '0', '字典管理', '5', '0', '1', '/admin/dict/list', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `code` varchar(100) NOT NULL,
  `description` longtext,
  `name` varchar(100) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `is_fixed` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_plpigyqwsqfn7mn66npgf9ftp` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '2017-03-31 18:23:40', '0', '2017-04-10 20:05:43', 'admin', '超级管理员', '超级管理员', '0', '1');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `FKkj7e3cva1e2s3nsd0yghpbsnk` (`resource_id`),
  CONSTRAINT `FK7urjh5xeujvp29nihwbs5b9kr` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKkj7e3cva1e2s3nsd0yghpbsnk` FOREIGN KEY (`resource_id`) REFERENCES `sys_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '1');
INSERT INTO `sys_role_resource` VALUES ('1', '2');
INSERT INTO `sys_role_resource` VALUES ('1', '3');
INSERT INTO `sys_role_resource` VALUES ('1', '4');
INSERT INTO `sys_role_resource` VALUES ('1', '5');
INSERT INTO `sys_role_resource` VALUES ('1', '6');
INSERT INTO `sys_role_resource` VALUES ('1', '7');
INSERT INTO `sys_role_resource` VALUES ('1', '10');
INSERT INTO `sys_role_resource` VALUES ('1', '11');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` int(11) DEFAULT '0',
  `mobile` varchar(40) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `password` varchar(200) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `username` varchar(100) NOT NULL,
  `is_fixed` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi4` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '2017-03-26 19:57:50', '0', '2017-04-10 20:07:33', '', '0', '', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '0', 'admin', '1');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
