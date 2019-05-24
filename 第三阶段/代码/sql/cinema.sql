-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: localhost    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) NOT NULL,
  `a_description` varchar(255) NOT NULL,
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `coupon_id` int(11) DEFAULT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (7,'初夏优惠','初夏优惠','2019-05-17 16:00:00',11,'2019-05-16 16:00:00'),(8,'复联优惠','复联优惠','2019-05-19 16:00:00',12,'2019-05-16 16:00:00'),(9,'皮卡丘优惠','皮卡丘优惠','2019-05-19 16:00:00',13,'2019-05-18 16:00:00'),(10,'ab','ab','2019-05-23 16:00:00',14,'2019-05-20 16:00:00'),(11,'kk','kk','2019-05-23 16:00:00',15,'2019-05-20 16:00:00'),(12,'eee','eee','2019-05-23 16:00:00',16,'2019-05-20 16:00:00');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_movie`
--

DROP TABLE IF EXISTS `activity_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_movie` (
  `activity_id` int(11) DEFAULT NULL,
  `movie_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_movie`
--

LOCK TABLES `activity_movie` WRITE;
/*!40000 ALTER TABLE `activity_movie` DISABLE KEYS */;
INSERT INTO `activity_movie` VALUES (6,21),(8,21),(9,22),(10,23),(11,23),(11,22),(12,23);
/*!40000 ALTER TABLE `activity_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `target_amount` float DEFAULT NULL,
  `discount_amount` float DEFAULT NULL,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (11,'初夏奖券','感谢观看！',20,5,'2019-05-16 16:00:00','2019-05-17 16:00:00'),(12,'复联奖券','感谢观看！',50,20,'2019-05-16 16:00:00','2019-05-19 16:00:00'),(13,'皮卡丘奖券','感谢观看！',100,90,'2019-05-18 16:00:00','2019-05-19 16:00:00'),(14,'hhh','ee',50,40,'2019-05-20 16:00:00','2019-05-23 16:00:00'),(15,'kkk','kk',100,50,'2019-05-20 16:00:00','2019-05-23 16:00:00'),(16,'4000','20540',200,40,'2019-05-20 16:00:00','2019-05-23 16:00:00');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon_user`
--

DROP TABLE IF EXISTS `coupon_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon_user` (
  `coupon_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon_user`
--

LOCK TABLES `coupon_user` WRITE;
/*!40000 ALTER TABLE `coupon_user` DISABLE KEYS */;
INSERT INTO `coupon_user` VALUES (11,2),(11,16),(11,2),(11,2),(13,16),(13,2),(13,2);
/*!40000 ALTER TABLE `coupon_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hall`
--

DROP TABLE IF EXISTS `hall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `column` int(11) DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hall`
--

LOCK TABLES `hall` WRITE;
/*!40000 ALTER TABLE `hall` DISABLE KEYS */;
INSERT INTO `hall` VALUES (1,'1号厅',10,5),(2,'2号厅',12,8);
/*!40000 ALTER TABLE `hall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poster_url` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `screen_writer` varchar(255) DEFAULT NULL,
  `starring` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `length` int(11) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL,
  `description` text,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (21,'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2552058346.webp','安东尼·罗素 / 乔·罗素','克里斯托弗·马库斯 / 斯蒂芬·麦克菲利 / 斯坦·李 / 杰克·科比 / 吉姆·斯特林','小罗伯特·唐尼 / 克里斯·埃文斯 / 马克·鲁弗洛 / 克里斯·海姆斯沃斯 / 斯嘉丽·约翰逊','动作 / 科幻 / 奇幻 / 冒险','美国','英语',181,'2019-05-21 16:00:00','复仇者联盟4：终局之战 Avengers: Endgame','一声响指，宇宙间半数生命灰飞烟灭。几近绝望的复仇者们在惊奇队长（布丽·拉尔森 Brie Larson 饰）的帮助下找到灭霸（乔什·布洛林 Josh Brolin 饰）归隐之处，却得知六颗无限宝石均被销毁，希望彻底破灭。如是过了五年，迷失在量子领域的蚁人（保罗·路德 Paul Rudd 饰）意外回到现实世界，他的出现为幸存的复仇者们点燃了希望。与美国队长（克里斯·埃文斯 Chris Evans 饰）冰释前嫌的托尼（小罗伯特·唐尼 Robert Downey Jr. 饰）找到了穿越时空的方法，星散各地的超级英雄再度集结，他们分别穿越不同的时代去搜集无限宝石。而在这一过程中，平行宇宙的灭霸察觉了他们的计划。注定要载入史册的最终决战，超级英雄们为了心中恪守的信念前仆后继……',1),(22,'https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2555538168.webp','罗伯·莱特曼','尼科尔·帕尔曼 / 亚历克斯·赫什 / 田尻智','瑞恩·雷诺兹 / 贾斯蒂斯·史密斯 / 凯瑟琳·纽顿 / 克里斯·吉尔 / 比尔·奈伊','喜剧 / 动画 / 奇幻 / 冒险','美国 / 日本','英语 / 日语',104,'2019-05-09 16:00:00','大侦探皮卡丘 Pokémon Detective Pikachu','好莱坞真人电影《大侦探皮卡丘》（暂译）讲述了蒂姆·古德曼（贾 斯 提·史密斯饰） 为寻找下落不明的父亲来到莱姆市，意外与父亲的前宝可梦搭档大侦探皮卡丘相遇，并惊讶地发现自己是唯一能听懂皮卡丘说话的人类，他们决定组队踏上揭开真相的刺激冒险之路。探案过程中他们邂逅了各式各样 的宝可梦，并意外发现了一个足以毁灭整个宝可梦宇宙的惊天阴谋。该电影改编自任天堂3DS同名游戏，由罗伯·莱特曼导演，瑞安·雷诺兹为大侦探皮卡丘配音，贾斯提斯·史密斯、凯瑟琳·纽顿等主演。',0),(23,'www.baidu.com','a','a','a','a','a','a',100,'2019-05-19 16:00:00','A','a',0);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_like`
--

DROP TABLE IF EXISTS `movie_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_like` (
  `movie_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `like_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`movie_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_like`
--

LOCK TABLES `movie_like` WRITE;
/*!40000 ALTER TABLE `movie_like` DISABLE KEYS */;
INSERT INTO `movie_like` VALUES (22,16,'2019-05-19 05:57:11');
/*!40000 ALTER TABLE `movie_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_strategy`
--

DROP TABLE IF EXISTS `refund_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refund_strategy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refundable` tinyint(1) NOT NULL,
  `ratio` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_strategy`
--

LOCK TABLES `refund_strategy` WRITE;
/*!40000 ALTER TABLE `refund_strategy` DISABLE KEYS */;
/*!40000 ALTER TABLE `refund_strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_strategy_movie`
--

DROP TABLE IF EXISTS `refund_strategy_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refund_strategy_movie` (
  `refund_strategy_id` int(11) DEFAULT NULL,
  `movie_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_strategy_movie`
--

LOCK TABLES `refund_strategy_movie` WRITE;
/*!40000 ALTER TABLE `refund_strategy_movie` DISABLE KEYS */;
/*!40000 ALTER TABLE `refund_strategy_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hall_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fare` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (70,2,21,'2019-05-17 17:00:00','2019-05-17 20:01:00',100),(71,1,22,'2019-05-18 01:00:00','2019-05-18 02:44:00',20),(72,1,22,'2019-05-21 07:00:00','2019-05-21 08:44:00',30),(73,1,22,'2019-05-19 10:00:00','2019-05-19 11:44:00',70),(74,2,22,'2019-05-19 13:00:00','2019-05-19 14:44:00',1000000),(75,1,23,'2019-05-22 02:00:00','2019-05-22 12:00:00',10000000),(76,1,22,'2019-05-19 13:00:00','2019-05-19 15:00:00',5000000000),(77,1,22,'2019-05-23 01:00:00','2019-05-23 03:00:00',5000);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `user_id` int(11) DEFAULT NULL,
  `schedule_id` int(11) DEFAULT NULL,
  `column_index` int(11) DEFAULT NULL,
  `row_index` int(11) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=327 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (2,70,4,7,1,96,'2019-05-17 02:02:45'),(2,70,5,7,1,97,'2019-05-17 02:02:45'),(2,70,6,7,1,98,'2019-05-17 02:02:45'),(2,70,7,7,1,99,'2019-05-17 02:02:45'),(2,71,3,2,1,100,'2019-05-17 02:06:16'),(2,71,4,2,1,101,'2019-05-17 02:06:16'),(2,71,5,2,1,102,'2019-05-17 02:06:16'),(2,70,0,0,1,103,'2019-05-17 02:20:03'),(2,70,1,0,1,104,'2019-05-17 02:20:03'),(2,70,2,0,1,105,'2019-05-17 02:20:03'),(2,70,3,0,1,106,'2019-05-17 02:20:03'),(2,70,4,0,1,107,'2019-05-17 02:20:03'),(2,70,5,0,1,108,'2019-05-17 02:20:03'),(2,70,6,0,1,109,'2019-05-17 02:20:03'),(2,70,7,0,1,110,'2019-05-17 02:20:03'),(2,70,8,0,1,111,'2019-05-17 02:20:03'),(2,70,9,0,1,112,'2019-05-17 02:20:03'),(2,70,10,0,1,113,'2019-05-17 02:20:03'),(2,70,11,0,1,114,'2019-05-17 02:20:03'),(2,70,0,1,1,115,'2019-05-17 02:21:04'),(2,70,1,1,1,116,'2019-05-17 02:21:04'),(2,70,2,1,1,117,'2019-05-17 02:21:04'),(2,70,3,1,1,118,'2019-05-17 02:21:04'),(2,70,4,1,1,119,'2019-05-17 02:21:04'),(2,70,5,1,1,120,'2019-05-17 02:21:04'),(2,70,6,1,1,121,'2019-05-17 02:21:04'),(2,70,7,1,1,122,'2019-05-17 02:21:04'),(2,70,8,1,1,123,'2019-05-17 02:21:04'),(2,70,9,1,1,124,'2019-05-17 02:21:04'),(2,70,10,1,1,125,'2019-05-17 02:21:04'),(2,70,11,1,1,126,'2019-05-17 02:21:04'),(2,70,0,2,1,127,'2019-05-17 02:21:04'),(2,70,1,2,1,128,'2019-05-17 02:21:04'),(2,70,2,2,1,129,'2019-05-17 02:21:04'),(2,70,3,2,1,130,'2019-05-17 02:21:04'),(2,70,4,2,1,131,'2019-05-17 02:21:04'),(2,70,5,2,1,132,'2019-05-17 02:21:04'),(2,70,6,2,1,133,'2019-05-17 02:21:04'),(2,70,7,2,1,134,'2019-05-17 02:21:04'),(2,70,8,2,1,135,'2019-05-17 02:21:04'),(2,70,9,2,1,136,'2019-05-17 02:21:04'),(2,70,10,2,1,137,'2019-05-17 02:21:04'),(2,70,11,2,1,138,'2019-05-17 02:21:04'),(2,70,0,3,1,139,'2019-05-17 02:21:04'),(2,70,1,3,1,140,'2019-05-17 02:21:04'),(2,70,2,3,1,141,'2019-05-17 02:21:04'),(2,70,3,3,1,142,'2019-05-17 02:21:04'),(2,70,4,3,1,143,'2019-05-17 02:21:04'),(2,70,5,3,1,144,'2019-05-17 02:21:04'),(2,70,6,3,1,145,'2019-05-17 02:21:04'),(2,70,7,3,1,146,'2019-05-17 02:21:04'),(2,70,8,3,1,147,'2019-05-17 02:21:04'),(2,70,9,3,1,148,'2019-05-17 02:21:04'),(2,70,10,3,1,149,'2019-05-17 02:21:04'),(2,70,11,3,1,150,'2019-05-17 02:21:04'),(2,70,0,4,1,151,'2019-05-17 02:21:04'),(2,70,1,4,1,152,'2019-05-17 02:21:04'),(2,70,2,4,1,153,'2019-05-17 02:21:04'),(2,70,3,4,1,154,'2019-05-17 02:21:04'),(2,70,4,4,1,155,'2019-05-17 02:21:04'),(2,70,5,4,1,156,'2019-05-17 02:21:04'),(2,70,6,4,1,157,'2019-05-17 02:21:04'),(2,70,7,4,1,158,'2019-05-17 02:21:04'),(2,70,8,4,1,159,'2019-05-17 02:21:04'),(2,70,9,4,1,160,'2019-05-17 02:21:04'),(2,70,10,4,1,161,'2019-05-17 02:21:04'),(2,70,11,4,1,162,'2019-05-17 02:21:04'),(2,70,0,5,1,163,'2019-05-17 02:21:04'),(2,70,1,5,1,164,'2019-05-17 02:21:04'),(2,70,2,5,1,165,'2019-05-17 02:21:04'),(2,70,3,5,1,166,'2019-05-17 02:21:04'),(2,70,4,5,1,167,'2019-05-17 02:21:04'),(2,70,5,5,1,168,'2019-05-17 02:21:04'),(2,70,6,5,1,169,'2019-05-17 02:21:04'),(2,70,7,5,1,170,'2019-05-17 02:21:04'),(2,70,8,5,1,171,'2019-05-17 02:21:04'),(2,70,9,5,1,172,'2019-05-17 02:21:04'),(2,70,10,5,1,173,'2019-05-17 02:21:04'),(2,70,11,5,1,174,'2019-05-17 02:21:04'),(2,70,11,7,2,175,'2019-05-17 02:27:15'),(2,70,10,6,1,176,'2019-05-17 03:34:01'),(16,71,8,4,1,177,'2019-05-17 03:48:09'),(2,71,3,0,1,178,'2019-05-17 04:03:31'),(2,70,9,7,2,179,'2019-05-17 07:28:45'),(2,70,11,7,1,180,'2019-05-17 12:20:36'),(2,72,0,0,1,181,'2019-05-18 07:49:11'),(2,72,1,0,1,182,'2019-05-18 07:49:11'),(2,72,2,0,1,183,'2019-05-18 07:49:11'),(2,72,3,0,1,184,'2019-05-18 07:49:11'),(2,72,4,0,1,185,'2019-05-18 07:49:11'),(2,72,5,0,1,186,'2019-05-18 07:49:11'),(2,72,6,0,1,187,'2019-05-18 07:49:11'),(2,72,7,3,2,188,'2019-05-18 07:50:07'),(2,72,8,4,2,189,'2019-05-18 07:50:07'),(2,72,3,4,1,190,'2019-05-18 07:50:34'),(2,72,8,1,1,191,'2019-05-18 07:51:15'),(2,72,9,1,1,192,'2019-05-18 07:51:15'),(2,72,7,0,1,193,'2019-05-18 07:53:12'),(2,72,8,0,1,194,'2019-05-18 07:53:12'),(2,72,9,0,1,195,'2019-05-18 07:53:12'),(2,72,2,1,1,196,'2019-05-18 07:53:12'),(2,72,3,1,1,197,'2019-05-18 07:53:12'),(2,72,1,2,1,198,'2019-05-18 07:53:12'),(2,72,2,2,1,199,'2019-05-18 07:53:12'),(2,72,3,2,1,200,'2019-05-18 07:53:12'),(2,72,4,2,1,201,'2019-05-18 07:53:12'),(2,72,5,2,1,202,'2019-05-18 07:53:12'),(2,72,7,2,1,203,'2019-05-18 07:53:12'),(16,72,9,3,1,204,'2019-05-18 07:55:27'),(16,72,9,4,1,205,'2019-05-18 07:55:27'),(2,72,6,3,2,206,'2019-05-18 08:14:59'),(2,72,7,4,2,207,'2019-05-18 08:14:59'),(2,72,1,4,2,208,'2019-05-18 08:16:12'),(2,72,8,3,1,209,'2019-05-18 08:17:57'),(2,72,0,3,1,210,'2019-05-18 08:18:33'),(2,72,1,3,1,211,'2019-05-18 08:18:33'),(16,72,5,4,1,212,'2019-05-18 08:52:55'),(16,72,6,4,2,213,'2019-05-18 08:53:22'),(16,72,7,4,2,214,'2019-05-18 08:53:22'),(16,72,6,3,2,215,'2019-05-18 08:53:59'),(16,72,7,3,2,216,'2019-05-18 08:53:59'),(16,72,9,2,2,217,'2019-05-18 08:56:04'),(16,72,7,4,2,218,'2019-05-18 09:22:05'),(2,73,4,2,1,219,'2019-05-19 01:04:52'),(2,73,5,2,1,220,'2019-05-19 01:04:52'),(2,73,2,2,1,221,'2019-05-19 01:05:34'),(2,73,3,2,1,222,'2019-05-19 01:05:34'),(2,73,7,2,1,223,'2019-05-19 01:06:20'),(2,73,7,4,1,224,'2019-05-19 01:06:47'),(2,73,8,4,1,225,'2019-05-19 01:06:47'),(2,73,0,4,1,226,'2019-05-19 01:07:37'),(2,73,0,0,1,227,'2019-05-19 01:09:04'),(2,73,1,0,1,228,'2019-05-19 01:09:04'),(2,73,2,0,1,229,'2019-05-19 01:09:04'),(2,73,3,0,1,230,'2019-05-19 01:09:04'),(2,73,4,0,1,231,'2019-05-19 01:09:04'),(2,73,5,0,1,232,'2019-05-19 01:09:04'),(2,73,6,0,1,233,'2019-05-19 01:09:04'),(2,73,7,0,1,234,'2019-05-19 01:09:04'),(2,73,8,0,1,235,'2019-05-19 01:09:04'),(2,73,9,0,1,236,'2019-05-19 01:09:04'),(2,73,0,1,1,237,'2019-05-19 01:09:04'),(2,73,1,1,1,238,'2019-05-19 01:09:04'),(2,73,2,1,1,239,'2019-05-19 01:09:04'),(2,73,3,1,1,240,'2019-05-19 01:09:04'),(2,73,4,1,1,241,'2019-05-19 01:09:04'),(2,73,5,1,1,242,'2019-05-19 01:09:04'),(2,73,6,1,1,243,'2019-05-19 01:09:04'),(2,73,7,1,1,244,'2019-05-19 01:09:04'),(2,73,8,1,1,245,'2019-05-19 01:09:04'),(2,73,9,1,1,246,'2019-05-19 01:09:04'),(2,73,0,2,1,247,'2019-05-19 01:09:04'),(2,73,1,2,1,248,'2019-05-19 01:09:04'),(2,73,6,2,1,249,'2019-05-19 01:09:04'),(2,73,8,2,1,250,'2019-05-19 01:09:04'),(2,73,9,2,1,251,'2019-05-19 01:09:04'),(2,73,0,3,1,252,'2019-05-19 01:09:04'),(2,73,1,3,1,253,'2019-05-19 01:09:04'),(2,73,2,3,1,254,'2019-05-19 01:09:04'),(2,73,3,3,1,255,'2019-05-19 01:09:04'),(2,73,4,3,1,256,'2019-05-19 01:09:04'),(2,73,5,3,1,257,'2019-05-19 01:09:04'),(2,73,6,3,1,258,'2019-05-19 01:09:04'),(2,73,7,3,1,259,'2019-05-19 01:09:04'),(2,73,8,3,1,260,'2019-05-19 01:09:04'),(2,73,9,3,1,261,'2019-05-19 01:09:04'),(2,73,1,4,1,262,'2019-05-19 01:09:04'),(2,73,2,4,1,263,'2019-05-19 01:09:04'),(2,73,3,4,1,264,'2019-05-19 01:09:04'),(2,73,4,4,1,265,'2019-05-19 01:09:04'),(2,73,5,4,1,266,'2019-05-19 01:09:04'),(2,73,6,4,1,267,'2019-05-19 01:09:04'),(2,73,9,4,1,268,'2019-05-19 01:09:04'),(16,74,8,4,1,269,'2019-05-19 01:10:40'),(16,74,3,3,2,270,'2019-05-19 05:56:13'),(16,74,4,3,2,271,'2019-05-19 05:56:13'),(16,74,5,3,2,272,'2019-05-19 05:56:13'),(16,74,10,4,1,273,'2019-05-19 05:56:43'),(16,74,10,5,1,274,'2019-05-19 05:56:43'),(16,74,0,5,2,275,'2019-05-19 05:57:54'),(16,74,1,5,2,276,'2019-05-19 05:57:54'),(16,74,2,5,2,277,'2019-05-19 05:57:54'),(16,74,3,5,2,278,'2019-05-19 05:57:54'),(16,74,0,6,2,279,'2019-05-19 05:57:54'),(16,74,1,6,2,280,'2019-05-19 05:57:54'),(16,74,2,6,2,281,'2019-05-19 05:57:54'),(16,74,3,6,2,282,'2019-05-19 05:57:54'),(16,74,4,6,2,283,'2019-05-19 05:57:54'),(16,74,5,6,2,284,'2019-05-19 05:57:54'),(2,75,7,2,1,285,'2019-05-19 06:10:32'),(2,76,8,3,2,286,'2019-05-19 06:13:58'),(2,74,1,2,1,287,'2019-05-19 06:14:15'),(2,74,2,2,1,288,'2019-05-19 06:14:15'),(2,74,3,2,1,289,'2019-05-19 06:14:15'),(2,74,6,2,1,290,'2019-05-19 06:14:15'),(2,74,9,2,1,291,'2019-05-19 06:14:15'),(2,76,6,1,2,292,'2019-05-19 06:15:03'),(2,76,7,1,2,293,'2019-05-19 06:15:03'),(2,76,8,1,2,294,'2019-05-19 06:15:03'),(2,76,0,2,2,295,'2019-05-19 06:15:03'),(2,76,1,2,2,296,'2019-05-19 06:15:03'),(2,76,2,2,2,297,'2019-05-19 06:15:03'),(2,76,3,2,2,298,'2019-05-19 06:15:03'),(2,76,4,2,2,299,'2019-05-19 06:15:03'),(2,76,5,2,2,300,'2019-05-19 06:15:03'),(2,76,6,2,2,301,'2019-05-19 06:15:03'),(2,76,7,4,2,302,'2019-05-19 06:15:21'),(2,76,9,3,1,303,'2019-05-19 06:15:34'),(2,72,6,3,1,304,'2019-05-21 06:47:43'),(2,72,6,4,1,305,'2019-05-21 06:47:43'),(2,72,7,4,1,306,'2019-05-21 06:47:43'),(2,72,8,4,1,307,'2019-05-21 06:48:17'),(16,72,7,1,1,308,'2019-05-21 06:48:44'),(2,72,8,2,1,309,'2019-05-21 06:50:14'),(2,77,8,2,1,310,'2019-05-21 06:54:31'),(2,72,9,2,1,311,'2019-05-21 06:56:06'),(2,77,7,3,1,312,'2019-05-21 06:58:23'),(16,77,8,4,1,313,'2019-05-21 07:00:25'),(16,77,3,1,1,314,'2019-05-21 07:01:17'),(2,77,4,4,1,315,'2019-05-21 07:07:15'),(2,77,5,4,1,316,'2019-05-21 07:07:15'),(2,77,9,4,1,317,'2019-05-21 07:07:58'),(2,77,7,1,1,318,'2019-05-21 07:08:35'),(2,77,8,1,1,319,'2019-05-21 07:08:35'),(2,77,9,1,1,320,'2019-05-21 07:08:35'),(2,77,1,3,1,321,'2019-05-21 07:08:56'),(2,77,2,3,1,322,'2019-05-21 07:08:56'),(2,77,1,4,1,323,'2019-05-21 07:08:56'),(2,77,2,4,1,324,'2019-05-21 07:08:56'),(16,77,9,2,1,325,'2019-05-21 07:12:09'),(15,69,1,1,2,326,'2019-05-21 11:50:17');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uindex` (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'manager','root','123456'),(2,'user','test','123456'),(3,'admin','admin','123456'),(16,'user','test2','123456');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view`
--

DROP TABLE IF EXISTS `view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view`
--

LOCK TABLES `view` WRITE;
/*!40000 ALTER TABLE `view` DISABLE KEYS */;
INSERT INTO `view` VALUES (1,7);
/*!40000 ALTER TABLE `view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_card`
--

DROP TABLE IF EXISTS `vip_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vip_card_user_id_uindex` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card`
--

LOCK TABLES `vip_card` WRITE;
/*!40000 ALTER TABLE `vip_card` DISABLE KEYS */;
INSERT INTO `vip_card` VALUES (9,2,1826130000,'2019-05-21 07:08:59'),(10,16,65940,'2019-05-21 07:12:11');
/*!40000 ALTER TABLE `vip_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_card_charge`
--

DROP TABLE IF EXISTS `vip_card_charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_card_charge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vip_card_id` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `amount` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card_charge`
--

LOCK TABLES `vip_card_charge` WRITE;
/*!40000 ALTER TABLE `vip_card_charge` DISABLE KEYS */;
/*!40000 ALTER TABLE `vip_card_charge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_card_strategy`
--

DROP TABLE IF EXISTS `vip_card_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_card_strategy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` float NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `target_amount` float DEFAULT NULL,
  `discount_amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card_strategy`
--

LOCK TABLES `vip_card_strategy` WRITE;
/*!40000 ALTER TABLE `vip_card_strategy` DISABLE KEYS */;
/*!40000 ALTER TABLE `vip_card_strategy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-24 21:03:09
