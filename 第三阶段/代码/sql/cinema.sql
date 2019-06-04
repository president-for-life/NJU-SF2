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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (13,'夏季狂欢','夏季狂欢','2019-08-30 16:00:00',17,'2019-05-31 16:00:00'),(14,'X战警活动','X战警活动','2019-07-05 16:00:00',18,'2019-06-05 16:00:00'),(15,'哥斯拉活动','哥斯拉活动','2019-06-29 16:00:00',19,'2019-05-30 16:00:00'),(16,'六四纪念','六四纪念','2019-06-05 16:00:00',20,'2019-06-02 16:00:00');
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
INSERT INTO `activity_movie` VALUES (14,26),(15,27);
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (17,'夏季狂欢奖券','夏季狂欢奖券',100,50,'2019-04-30 16:00:00','2019-08-30 16:00:00'),(18,'X战警奖券','X战警奖券',50,10,'2019-06-05 16:00:00','2019-07-05 16:00:00'),(19,'哥斯拉奖券','哥斯拉奖券',60,20,'2019-05-30 16:00:00','2019-06-29 16:00:00'),(20,'六四纪念券','六四纪念券',64,64,'2019-06-02 16:00:00','2019-06-05 16:00:00');
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
INSERT INTO `coupon_user` VALUES (20,17),(17,18),(20,18),(17,17),(20,17),(17,17),(20,17),(17,16),(20,16),(17,16),(20,16),(17,16),(20,16),(17,16),(20,16),(17,16),(20,16),(17,16),(20,16),(17,19),(20,19);
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hall`
--

LOCK TABLES `hall` WRITE;
/*!40000 ALTER TABLE `hall` DISABLE KEYS */;
INSERT INTO `hall` VALUES (1,'1号厅',10,5),(2,'2号厅',12,8),(3,'3号厅',10,5),(6,'4号厅',8,6),(7,'5号厅',15,10),(8,'6号厅',2,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (24,'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2557284230.webp','乔什·库雷','约翰·拉塞特 / 安德鲁·斯坦顿 / 彼特·道格特 / 李·昂克里奇 / 威尔·麦克马克 / 史黛芬妮·福尔松 / C·S·安德森','汤姆·汉克斯 / 蒂姆·艾伦 / 安妮·波茨 / 托尼·海尔 / 帕特丽夏·阿奎特','喜剧 / 动画 / 奇幻','美国','英语',90,'2019-06-20 16:00:00','玩具总动员4','《玩具总动员4》将是皮克斯的动画系列电影的第四部，主角是两个玩具：牛仔警长胡迪和太空骑警巴斯光年。《玩具总动员4》的故事将延续上集，孩子们变成了青年，玩具的回归便显得更加尴尬。当然，不排除皮克斯开辟新的故事方向，将系列电影更好地发展下去。',0),(25,'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2557590422.webp','乔·沃茨','克里斯·麦克纳 / 埃里克·萨默斯 / 史蒂夫·迪特寇 / 斯坦·李','汤姆·赫兰德 / 赞达亚 / 玛丽莎·托梅 / 杰克·吉伦哈尔 / 寇碧·史莫德斯','动作 / 科幻 / 冒险','美国','英语',135,'2019-06-27 16:00:00','蜘蛛侠：英雄远征','最受关注的漫威超级英雄大片《蜘蛛侠：英雄远征》依旧由导演乔·沃茨执导，汤姆·赫兰德继续饰演蜘蛛侠彼得·帕克。此次蜘蛛侠将前往欧洲展开新的征程，并将对抗由杰克·吉伦哈尔加盟饰演的大反派神秘客，赞达亚、雅各布·巴特朗、托尼·雷沃罗利等原班人马也将悉数回归。本片将于2019年7月5日北美上映，经历了第一部的成长经历后，蜘蛛侠又会面临怎样的危机？敬请期待。',0),(26,'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2555886490.webp','西蒙·金伯格','西蒙·金伯格 / 约翰·拜恩 / 克里斯·克雷蒙 / 戴夫·科克勒姆 / 杰克·科比 / 斯坦·李','苏菲·特纳 / 詹姆斯·麦卡沃伊 / 迈克尔·法斯宾德 / 詹妮弗·劳伦斯 / 尼古拉斯·霍尔特','动作 / 科幻 / 冒险','美国','英语',114,'2019-06-05 16:00:00','X战警：黑凤凰','影片剧情围绕X战警中最受欢迎成员之一的琴·葛蕾展开，讲述她逐渐转化为黑凤凰的故事。在一次危及生命的太空营救行动中，琴被神秘的宇宙力量击中，成为最强大的变种人。此后琴·葛蕾不仅要设法掌控日益增长、极不稳定的力量，更要与自己内心的恶魔抗争，她的失控让整个X战警大家庭分崩离析，也让整个星球陷入毁灭的威胁之中。《X战警：黑凤凰》是迄今为止气氛最紧张、情感最丰富的一部《X战警》电影，是《X战警》系列20年来的集大成之作，大家非常熟悉和热爱的变种人大家庭即将面对最为强大的敌人——而她恰恰还是他们中的一员。',0),(27,'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2554370800.webp','迈克尔·道赫蒂','迈克尔·道赫蒂 / 扎克·希尔兹 / 麦克思·鲍伦斯坦','维拉·法米加 / 米莉·波比·布朗 / 章子怡 / 莎莉·霍金斯 / 布莱德利·惠特福德','动作 / 科幻 / 冒险 / 灾难','美国','英语',132,'2019-05-30 16:00:00','哥斯拉2：怪兽之王','随着《哥斯拉》和《金刚：骷髅岛》在全球范围内取得成功，传奇影业和华纳兄弟影业联手开启了怪兽宇宙系列电影的新篇章—一部史诗级动作冒险巨制。在这部电影中，哥斯拉将和众多大家在流行文化中所熟知的怪兽展开较量。全新故事中，研究神秘动物学的机构“帝王组织”将勇敢直面巨型怪兽，其中强大的哥斯拉也将和魔斯拉、拉顿和它的死对头——三头王者基多拉展开激烈对抗。当这些只存在于传说里的超级生物再度崛起时，它们将展开王者争霸，人类的命运岌岌可危……',0),(28,'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2553992741.webp','盖·里奇','盖·里奇 / 约翰·奥古斯特','梅纳·玛索德 / 娜奥米·斯科特 / 威尔·史密斯 / 马尔万·肯扎里 / 纳维德·内加班','爱情 / 奇幻 / 冒险','美国','英语',128,'2019-05-23 16:00:00','阿拉丁','在充满异域风情的古代阿拉伯王国，善良的穷小子阿拉丁（莫纳·马苏德 饰）和勇敢的茉莉公主（娜奥米·斯科特 饰）浪漫邂逅，在可以满足主人三个愿望的神灯精灵（威尔·史密斯 饰）的帮助下，两人踏上了一次寻找真爱和自我的魔幻冒险。',0);
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
/*!40000 ALTER TABLE `movie_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actual_payment` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
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
  `time` int(11) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (78,2,27,'2019-05-30 23:00:00','2019-05-31 01:12:00',35),(79,1,27,'2019-05-31 06:00:00','2019-05-31 08:12:00',35),(80,2,27,'2019-06-02 06:00:00','2019-06-02 08:12:00',35),(81,2,26,'2019-06-06 00:00:00','2019-06-06 01:54:00',50),(82,1,26,'2019-06-06 00:00:00','2019-06-06 01:54:00',50),(83,1,26,'2019-06-07 00:00:00','2019-06-07 01:54:00',50),(84,1,28,'2019-05-28 01:00:00','2019-05-28 03:08:00',40),(85,2,28,'2019-05-28 01:00:00','2019-05-28 03:08:00',40),(86,2,28,'2019-06-01 10:00:00','2019-06-01 12:08:00',40);
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
  `actual_payment` float DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `order_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=372 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (17,86,1,1,1,40,327,'2019-05-29 14:19:34',1),(16,81,3,0,1,50,328,'2019-06-03 11:05:43',1),(16,81,5,1,1,25,329,'2019-06-03 11:06:23',1),(16,81,6,1,1,25,330,'2019-06-03 11:06:23',1),(16,83,9,3,2,NULL,331,'2019-06-03 11:08:00',1),(16,83,8,4,2,NULL,332,'2019-06-03 11:08:00',1),(16,83,9,4,2,NULL,333,'2019-06-03 11:08:00',1),(17,81,8,1,1,50,334,'2019-06-03 12:51:05',1),(17,81,8,2,1,50,335,'2019-06-03 12:51:05',1),(17,82,2,1,1,25,336,'2019-06-03 12:52:37',1),(17,82,3,1,1,25,337,'2019-06-03 12:52:37',1),(18,82,7,2,1,50,338,'2019-06-03 13:29:36',1),(18,82,7,3,1,50,339,'2019-06-03 13:29:36',1),(17,83,2,1,1,18,340,'2019-06-03 13:37:46',1),(17,83,3,1,1,18,341,'2019-06-03 13:37:46',1),(17,83,0,2,1,25,342,'2019-06-03 13:38:42',1),(17,83,9,2,1,25,343,'2019-06-03 13:38:42',1),(19,81,4,3,2,NULL,344,'2019-06-03 13:43:33',1),(16,83,7,3,2,NULL,345,'2019-06-04 03:04:22',1),(16,83,8,3,2,NULL,346,'2019-06-04 03:04:22',1),(16,83,5,3,1,50,347,'2019-06-04 03:04:39',1),(16,83,7,4,1,50,348,'2019-06-04 03:07:54',1),(16,81,5,4,1,33.3333,349,'2019-06-04 03:08:10',1),(16,81,6,4,1,33.3333,350,'2019-06-04 03:08:10',1),(16,81,7,4,1,33.3333,351,'2019-06-04 03:08:10',1),(16,81,11,4,2,NULL,352,'2019-06-04 03:10:43',1),(16,81,11,5,2,NULL,353,'2019-06-04 03:10:43',1),(16,81,11,6,2,NULL,354,'2019-06-04 03:10:43',1),(16,81,10,3,1,50,355,'2019-06-04 03:13:30',1),(16,83,7,1,1,50,356,'2019-06-04 03:14:07',1),(16,81,1,3,1,33.3333,357,'2019-06-04 03:14:20',1),(16,81,2,3,1,33.3333,358,'2019-06-04 03:14:20',1),(16,81,3,3,1,33.3333,359,'2019-06-04 03:14:20',1),(19,81,8,6,1,50,360,'2019-06-04 07:00:45',1),(19,81,9,6,1,50,361,'2019-06-04 07:00:45',1),(18,82,4,3,2,NULL,362,'2019-06-04 07:16:49',1),(18,82,3,4,2,NULL,363,'2019-06-04 07:16:49',1),(18,82,4,4,2,NULL,364,'2019-06-04 07:16:49',1),(17,83,8,4,2,NULL,365,'2019-06-04 07:44:30',1),(17,83,9,4,2,NULL,366,'2019-06-04 07:44:30',1),(17,81,11,0,0,NULL,367,'2019-06-04 08:20:49',0),(17,81,11,1,0,NULL,368,'2019-06-04 08:20:49',0),(17,81,11,2,0,NULL,369,'2019-06-04 08:20:49',0),(17,81,11,3,0,NULL,370,'2019-06-04 08:20:49',0),(17,81,11,4,0,NULL,371,'2019-06-04 08:20:49',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'manager','manager','123'),(2,'admin','admin1','123'),(3,'admin','admin2','123'),(16,'user','user1','123'),(17,'user','user2','123'),(18,'user','user3','123'),(19,'user','user4','123'),(20,'admin','admin3','123'),(21,'user','user5','123456');
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
INSERT INTO `view` VALUES (1,30);
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
  `strategy_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vip_card_user_id_uindex` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card`
--

LOCK TABLES `vip_card` WRITE;
/*!40000 ALTER TABLE `vip_card` DISABLE KEYS */;
INSERT INTO `vip_card` VALUES (9,3,2,500,'2019-05-29 13:40:14'),(10,5,16,2440,'2019-06-04 03:14:30'),(11,10,17,469,'2019-06-03 13:38:48'),(14,8,18,100,'2019-06-03 13:29:41'),(16,10,19,0,'2019-06-02 10:02:31'),(17,5,21,0,'2019-06-02 10:13:11');
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
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `payment` float NOT NULL,
  `amount` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card_charge`
--

LOCK TABLES `vip_card_charge` WRITE;
/*!40000 ALTER TABLE `vip_card_charge` DISABLE KEYS */;
INSERT INTO `vip_card_charge` VALUES (1,11,'2019-05-29 12:07:57',10,10),(2,11,'2019-05-29 12:08:52',40,40),(3,11,'2019-05-29 12:09:06',100,125),(4,11,'2019-05-29 12:10:04',60,85),(5,10,'2019-05-29 13:52:03',100,130),(6,10,'2019-05-29 14:06:10',50,60),(7,11,'2019-05-29 14:17:42',60,85),(8,10,'2019-05-30 11:02:52',500,660),(9,10,'2019-05-30 11:03:05',30,40),(11,14,'2019-06-02 09:23:02',200,200);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card_strategy`
--

LOCK TABLES `vip_card_strategy` WRITE;
/*!40000 ALTER TABLE `vip_card_strategy` DISABLE KEYS */;
INSERT INTO `vip_card_strategy` VALUES (3,50,'青铜卡',30,10),(5,100,'白银卡',60,25),(7,500,'白金卡',100,200),(8,1000,'钻石卡',500,500),(10,5000,'灭霸卡',3000,3000);
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

-- Dump completed on 2019-06-04 16:23:19
