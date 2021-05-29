/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50515
 Source Host           : localhost:3306
 Source Schema         : nebula

 Target Server Type    : MySQL
 Target Server Version : 50515
 File Encoding         : 65001

 Date: 29/05/2021 17:03:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for a
-- ----------------------------
DROP TABLE IF EXISTS `a`;
CREATE TABLE `a`  (
  `aid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` int(10) UNSIGNED NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `qid` int(10) UNSIGNED NOT NULL,
  `author` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of a
-- ----------------------------
INSERT INTO `a` VALUES (1, 2, 'dfasdfa', '2021-05-23 23:26:14', 1, 'vvvvv', 'vvvvv');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `art_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` int(10) UNSIGNED NOT NULL,
  `author` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`art_id`) USING BTREE,
  INDEX `uid`(`uid`, `title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, 1, 'root', 'test', 'test', '2021-05-19 17:35:03');
INSERT INTO `article` VALUES (2, 1, 'root', 'testdsaf', 'tets', '2021-05-20 18:42:27');
INSERT INTO `article` VALUES (4, 2, 'vvvvv', 'testdasfasdfasdf', 'sdat', '2021-05-23 23:16:42');
INSERT INTO `article` VALUES (5, 2, 'vvvvv', 'dsf', '![](/user/vvvvv/img/test24.jpg)', '2021-05-24 10:44:10');

-- ----------------------------
-- Table structure for q
-- ----------------------------
DROP TABLE IF EXISTS `q`;
CREATE TABLE `q`  (
  `q_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` int(11) UNSIGNED NOT NULL,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `author` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) UNSIGNED NULL DEFAULT 0,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `answer` int(10) UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`q_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of q
-- ----------------------------
INSERT INTO `q` VALUES (1, 1, 'test', 'test', 'root', 0, '2021-05-19 17:35:23', 1);
INSERT INTO `q` VALUES (2, 2, 'asdf', 'dsaf', 'vvvvv', 0, '2021-05-23 23:26:04', 0);

-- ----------------------------
-- Table structure for user_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_detail`;
CREATE TABLE `user_detail`  (
  `uid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `blogname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `motto` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hobby` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QQ` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `location` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_detail
-- ----------------------------
INSERT INTO `user_detail` VALUES (1, 'root', 'root的小天地', '', '男', '', '', '');
INSERT INTO `user_detail` VALUES (2, 'vvvvv', 'vvvvv的小天地', '', '男', '', NULL, '');
INSERT INTO `user_detail` VALUES (3, 'vvvvvv', 'vvvvvv的小天地', NULL, '男', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `uid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `tel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mail` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'root', '5b7ebefabae7bf4e4056148cba52bdb8', '15875870016', '982486598@qq.com');
INSERT INTO `user_info` VALUES (2, 'vvvvv', '5b7ebefabae7bf4e4056148cba52bdb8', '15875870014', 'adsf@jkdfl.com');
INSERT INTO `user_info` VALUES (3, 'vvvvvv', '5b7ebefabae7bf4e4056148cba52bdb8', '15875870013', 'addsf@jkdfl.com');

SET FOREIGN_KEY_CHECKS = 1;
