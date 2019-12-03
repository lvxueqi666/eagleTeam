/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : xianwan

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2019-12-03 14:28:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `commodity`
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `id` int(16) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `userId` int(16) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `attr` varchar(255) DEFAULT NULL,
  `showLike` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of commodity
-- ----------------------------
INSERT INTO `commodity` VALUES ('111111', 'http://49.233.142.163:8080/images/111.jpg', '111', '100', null, '11111111', null, '111', 'toy', '1222');
INSERT INTO `commodity` VALUES ('222222', 'http://49.233.142.163:8080/images/45465.jpg', '222', '200', null, '22222222', null, '222', 'toy', '25');
INSERT INTO `commodity` VALUES ('333333', 'http://49.233.142.163:8080/images/afa.jpg', '333', '300', null, '33333333', null, '333', 'toy', '1237');
INSERT INTO `commodity` VALUES ('444444', 'http://49.233.142.163:8080/images/daitujpg.jpg', '444', '400', null, '44444444', null, '444', 'toy', '2345');
INSERT INTO `commodity` VALUES ('555555', 'http://49.233.142.163:8080/images/dak2lkald.jpg', '555', '500', null, '55555555', null, '555', 'toy', '4537');
INSERT INTO `commodity` VALUES ('666666', 'http://49.233.142.163:8080/images/dasdasd.jpg', '666', '600', null, '66666666', null, '666', 'toy', '2143');
INSERT INTO `commodity` VALUES ('777777', 'http://49.233.142.163:8080/images/dsah.jpg', '777', '700', null, '77777777', null, '777', 'toy', '234');
INSERT INTO `commodity` VALUES ('888888', 'http://49.233.142.163:8080/images/dsfsfd.jpg', '888', '800', null, '88888888', null, '888', 'toy', '175');
INSERT INTO `commodity` VALUES ('999999', 'http://49.233.142.163:8080/images/dskahdh.jpg', '999', '90', null, '99999999', null, '999', 'toy', '2444');
INSERT INTO `commodity` VALUES ('753753', 'http://49.233.142.163:8080/images/gfjs.jpg', '753', '775', null, '78373555', null, '544', 'toy', '442');
INSERT INTO `commodity` VALUES ('735357', 'http://49.233.142.163:8080/images/liudao.jpg', '722', '787', null, '44344245', null, '425', 'toy', '543');
INSERT INTO `commodity` VALUES ('444425', 'http://49.233.142.163:8080/images/sdas.jpg', '544', '776', null, '54337537', null, '284', 'toy', '42');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` bigint(11) NOT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `userPassword` varchar(255) DEFAULT NULL,
  `userAccount` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('123', '李同', '123456', '1403062814');
INSERT INTO `user` VALUES ('124', '王天伟', '123456', '123456789');
INSERT INTO `user` VALUES ('651169644211601408', '1', '1', '1');
INSERT INTO `user` VALUES ('651366596689788928', '2', '2', '2');
