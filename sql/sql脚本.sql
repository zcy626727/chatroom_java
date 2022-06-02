/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.18 : Database - community
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`community` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `community`;

/*Table structure for table `apply_message` */

CREATE TABLE `apply_message` (
  `a_id` varchar(19) NOT NULL,
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `sender_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '申请者id',
  `target_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '申请目标id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '申请内容',
  `sender_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '申请者姓名',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '申请类型',
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `apply_message` */

/*Table structure for table `chat_message` */

CREATE TABLE `chat_message` (
  `id` varchar(19) NOT NULL COMMENT '消息id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '消息内容',
  `type` tinyint(4) NOT NULL COMMENT '0为私聊,1为聊天室',
  `sender_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发送者',
  `target_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接收者',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `sender_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发送者名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `chat_message` */

insert  into `chat_message`(`id`,`content`,`type`,`sender_id`,`target_id`,`send_time`,`sender_name`) values ('1389802355251339266','111',1,'1389801963952136193','1389802102154452993','2021-05-05 12:41:17','测试1');
insert  into `chat_message`(`id`,`content`,`type`,`sender_id`,`target_id`,`send_time`,`sender_name`) values ('1389802377070108673','222',1,'1389802003038855169','1389802102154452993','2021-05-05 12:41:23','测试2');
insert  into `chat_message`(`id`,`content`,`type`,`sender_id`,`target_id`,`send_time`,`sender_name`) values ('1389802576207273985','我是2',0,'1389801963952136193','1389802003038855169','2021-05-05 12:42:10','测试1');
insert  into `chat_message`(`id`,`content`,`type`,`sender_id`,`target_id`,`send_time`,`sender_name`) values ('1389802597925380098','我是1',0,'1389802003038855169','1389801963952136193','2021-05-05 12:42:15','测试2');
insert  into `chat_message`(`id`,`content`,`type`,`sender_id`,`target_id`,`send_time`,`sender_name`) values ('1389803708761628674','11',0,'1389802003038855169','1389801963952136193','2021-05-05 12:46:40','测试2');

/*Table structure for table `chatroom` */

CREATE TABLE `chatroom` (
  `r_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '聊天室id',
  `room_name` varchar(61) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '聊天室名称',
  `u_id` varchar(19) NOT NULL COMMENT '创建者id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `room_face` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '聊天室头像',
  `motto` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '聊天室签名',
  `last_msg_time` datetime DEFAULT NULL COMMENT '最后活跃时间',
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `chatroom` */

insert  into `chatroom`(`r_id`,`room_name`,`u_id`,`create_time`,`room_face`,`motto`,`last_msg_time`) values ('1389802102154452993','聊天室1','1389802003038855169','2021-05-05 12:40:17',NULL,'222',NULL);

/*Table structure for table `friends` */

CREATE TABLE `friends` (
  `id` varchar(19) NOT NULL,
  `u_id_from` varchar(19) NOT NULL,
  `u_id_to` varchar(19) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `friends` */

/*Table structure for table `permission` */

CREATE TABLE `permission` (
  `p_id` varchar(19) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `permission` */

/*Table structure for table `role` */

CREATE TABLE `role` (
  `r_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`r_id`,`name`) values ('1','普通用户');
insert  into `role`(`r_id`,`name`) values ('2','管理员');

/*Table structure for table `role_permission` */

CREATE TABLE `role_permission` (
  `id` varchar(19) NOT NULL,
  `r_id` varchar(19) NOT NULL,
  `p_id` varchar(19) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role_permission` */

/*Table structure for table `user` */

CREATE TABLE `user` (
  `u_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `state` tinyint(4) DEFAULT '0' COMMENT '是否禁用',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `reg_time` datetime DEFAULT NULL COMMENT '注册时间',
  `userFace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `motto` varchar(255) DEFAULT NULL COMMENT '个人签名',
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `U_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`u_id`,`username`,`password`,`nickname`,`state`,`email`,`reg_time`,`userFace`,`motto`) values ('1389801963952136193','321','202cb962ac59075b964b07152d234b70','测试1',0,'123@qq.com','2021-05-05 12:39:44',NULL,NULL);
insert  into `user`(`u_id`,`username`,`password`,`nickname`,`state`,`email`,`reg_time`,`userFace`,`motto`) values ('1389802003038855169','123','202cb962ac59075b964b07152d234b70','测试2',0,'321@qq.com','2021-05-05 12:39:53',NULL,NULL);

/*Table structure for table `user_role` */

CREATE TABLE `user_role` (
  `id` char(19) NOT NULL,
  `r_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `u_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_role` */

/*Table structure for table `user_room` */

CREATE TABLE `user_room` (
  `id` varchar(19) NOT NULL,
  `u_id` varchar(19) NOT NULL,
  `r_id` varchar(19) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_room` */

insert  into `user_room`(`id`,`u_id`,`r_id`) values ('1382927009054720001','1382926950477070337','1382927008882753537');
insert  into `user_room`(`id`,`u_id`,`r_id`) values ('1382927267344154626','1382907061347553281','1382927008882753537');
insert  into `user_room`(`id`,`u_id`,`r_id`) values ('1386619531002159106','1386619449813016578','1386619530968604673');
insert  into `user_room`(`id`,`u_id`,`r_id`) values ('1386620206951997441','1386620094225883138','1386619530968604673');
insert  into `user_room`(`id`,`u_id`,`r_id`) values ('1389520040713297922','1381585075526770690','1389520040428085250');
insert  into `user_room`(`id`,`u_id`,`r_id`) values ('1389520083004465153','1381585075526770690','1389520082958327810');
insert  into `user_room`(`id`,`u_id`,`r_id`) values ('1389522454409420801','1381585200764493825','1389520040428085250');
insert  into `user_room`(`id`,`u_id`,`r_id`) values ('1389802102204784642','1389802003038855169','1389802102154452993');
insert  into `user_room`(`id`,`u_id`,`r_id`) values ('1389802286863212545','1389801963952136193','1389802102154452993');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
