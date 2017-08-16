/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : subscriptiondb

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2017-08-16 17:17:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `subscriptions`
-- ----------------------------
DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE `subscriptions` (
  `subscription_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `modified_at` datetime DEFAULT NULL,
  `expired_at` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`subscription_id`),
  UNIQUE KEY `UK_19bnl9weancplokifq0i5mapv` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subscriptions
-- ----------------------------
INSERT INTO `subscriptions` VALUES ('1', '2018-12-12 00:00:00', null, '2018-12-12 00:00:00', 'subscription1', 'PREMIUM');
INSERT INTO `subscriptions` VALUES ('2', '2018-11-12 00:00:00', null, '2018-11-12 00:00:00', 'subscription2', 'ADVANCED');
INSERT INTO `subscriptions` VALUES ('3', '2018-10-12 00:00:00', null, '2018-10-12 00:00:00', 'subscription3', 'STANDARD');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `modified_at` datetime DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `login` varchar(255) NOT NULL,
  `rating` double NOT NULL,
  `registered_at` datetime DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '2017-04-18 00:00:00', null, '25', 'user1@gmail.com', '0', '2017-04-18 00:00:00', 'UNVERIFIED');
INSERT INTO `users` VALUES ('2', '2017-04-30 00:00:00', null, '26', 'user2@gmail.com', '0', '2017-04-30 00:00:00', 'ACTIVE');
INSERT INTO `users` VALUES ('3', '2017-01-30 00:00:00', null, '27', 'user3@gmail.com', '0', '2017-01-30 00:00:00', 'BLOCKED');

-- ----------------------------
-- Table structure for `user_sub_orders`
-- ----------------------------
DROP TABLE IF EXISTS `user_sub_orders`;
CREATE TABLE `user_sub_orders` (
  `created_at` datetime NOT NULL,
  `modified_at` datetime DEFAULT NULL,
  `expired_at` datetime DEFAULT NULL,
  `subscribed_at` datetime NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `subscription_id` bigint(20) NOT NULL,
  PRIMARY KEY (`subscription_id`,`user_id`),
  KEY `FK41afcgcba9qcv0lgrgdtb0tgi` (`user_id`),
  CONSTRAINT `FKr7kj4vhdqn3dw6rtrs5ihdtsx` FOREIGN KEY (`subscription_id`) REFERENCES `subscriptions` (`subscription_id`),
  CONSTRAINT `FK41afcgcba9qcv0lgrgdtb0tgi` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_sub_orders
-- ----------------------------
INSERT INTO `user_sub_orders` VALUES ('2017-12-12 00:00:00', null, '2018-05-12 00:00:00', '2017-12-12 00:00:00', '1', '1');
INSERT INTO `user_sub_orders` VALUES ('2017-09-12 00:00:00', null, '2018-09-12 00:00:00', '2017-09-12 00:00:00', '1', '2');
INSERT INTO `user_sub_orders` VALUES ('2017-10-25 00:00:00', null, '2018-04-25 00:00:00', '2017-10-25 00:00:00', '3', '2');
INSERT INTO `user_sub_orders` VALUES ('2017-08-30 00:00:00', null, '2017-08-30 00:00:00', '2017-08-30 00:00:00', '2', '3');
