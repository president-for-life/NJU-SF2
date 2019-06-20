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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (17,'夏季狂欢奖券','夏季狂欢奖券',100,50,'2019-04-30 16:00:00','2019-08-30 16:00:00'),(18,'X战警奖券','X战警奖券',50,10,'2019-06-05 16:00:00','2019-07-05 16:00:00'),(19,'哥斯拉奖券','哥斯拉奖券',60,20,'2019-05-30 16:00:00','2019-06-29 16:00:00');
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
INSERT INTO `coupon_user` VALUES (19,17),(17,17),(18,16),(17,17),(18,17),(19,17),(18,16),(18,18),(19,18),(18,16),(17,16),(17,16),(17,17),(17,18),(17,16),(18,16),(18,18),(17,18),(18,16),(17,16),(17,16),(17,16);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hall`
--

LOCK TABLES `hall` WRITE;
/*!40000 ALTER TABLE `hall` DISABLE KEYS */;
INSERT INTO `hall` VALUES (1,'1号厅',10,5),(2,'2号厅',12,8),(3,'3号厅',10,5);
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
INSERT INTO `movie` VALUES (24,'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=246524674,1953258386&fm=26&gp=0.jpg','乔什·库雷','约翰·拉塞特 / 安德鲁·斯坦顿 / 彼特·道格特 / 李·昂克里奇 / 威尔·麦克马克 / 史黛芬妮·福尔松 / C·S·安德森','汤姆·汉克斯 / 蒂姆·艾伦 / 安妮·波茨 / 托尼·海尔 / 帕特丽夏·阿奎特','喜剧 / 动画 / 奇幻','美国','英语',90,'2019-06-13 03:10:04','玩具总动员4','《玩具总动员4》将是皮克斯的动画系列电影的第四部，主角是两个玩具：牛仔警长胡迪和太空骑警巴斯光年。《玩具总动员4》的故事将延续上集，孩子们变成了青年，玩具的回归便显得更加尴尬。当然，不排除皮克斯开辟新的故事方向，将系列电影更好地发展下去。',0),(25,'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3389348672,627749059&fm=26&gp=0.jpg','乔·沃茨','克里斯·麦克纳 / 埃里克·萨默斯 / 史蒂夫·迪特寇 / 斯坦·李','汤姆·赫兰德 / 赞达亚 / 玛丽莎·托梅 / 杰克·吉伦哈尔 / 寇碧·史莫德斯','动作 / 科幻 / 冒险','美国','英语',135,'2019-06-13 03:11:20','蜘蛛侠：英雄远征','最受关注的漫威超级英雄大片《蜘蛛侠：英雄远征》依旧由导演乔·沃茨执导，汤姆·赫兰德继续饰演蜘蛛侠彼得·帕克。此次蜘蛛侠将前往欧洲展开新的征程，并将对抗由杰克·吉伦哈尔加盟饰演的大反派神秘客，赞达亚、雅各布·巴特朗、托尼·雷沃罗利等原班人马也将悉数回归。本片将于2019年7月5日北美上映，经历了第一部的成长经历后，蜘蛛侠又会面临怎样的危机？敬请期待。',0),(26,'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3869451761,3310626608&fm=26&gp=0.jpg','西蒙·金伯格','西蒙·金伯格 / 约翰·拜恩 / 克里斯·克雷蒙 / 戴夫·科克勒姆 / 杰克·科比 / 斯坦·李','苏菲·特纳 / 詹姆斯·麦卡沃伊 / 迈克尔·法斯宾德 / 詹妮弗·劳伦斯 / 尼古拉斯·霍尔特','动作 / 科幻 / 冒险','美国','英语',114,'2019-06-13 03:08:49','X战警：黑凤凰','影片剧情围绕X战警中最受欢迎成员之一的琴·葛蕾展开，讲述她逐渐转化为黑凤凰的故事。在一次危及生命的太空营救行动中，琴被神秘的宇宙力量击中，成为最强大的变种人。此后琴·葛蕾不仅要设法掌控日益增长、极不稳定的力量，更要与自己内心的恶魔抗争，她的失控让整个X战警大家庭分崩离析，也让整个星球陷入毁灭的威胁之中。《X战警：黑凤凰》是迄今为止气氛最紧张、情感最丰富的一部《X战警》电影，是《X战警》系列20年来的集大成之作，大家非常熟悉和热爱的变种人大家庭即将面对最为强大的敌人——而她恰恰还是他们中的一员。',0),(27,'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=472916058,3896068495&fm=11&gp=0.jpg','迈克尔·道赫蒂','迈克尔·道赫蒂 / 扎克·希尔兹 / 麦克思·鲍伦斯坦','维拉·法米加 / 米莉·波比·布朗 / 章子怡 / 莎莉·霍金斯 / 布莱德利·惠特福德','动作 / 科幻 / 冒险 / 灾难','美国','英语',132,'2019-06-13 03:08:23','哥斯拉2：怪兽之王','随着《哥斯拉》和《金刚：骷髅岛》在全球范围内取得成功，传奇影业和华纳兄弟影业联手开启了怪兽宇宙系列电影的新篇章—一部史诗级动作冒险巨制。在这部电影中，哥斯拉将和众多大家在流行文化中所熟知的怪兽展开较量。全新故事中，研究神秘动物学的机构“帝王组织”将勇敢直面巨型怪兽，其中强大的哥斯拉也将和魔斯拉、拉顿和它的死对头——三头王者基多拉展开激烈对抗。当这些只存在于传说里的超级生物再度崛起时，它们将展开王者争霸，人类的命运岌岌可危……',0),(28,'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560404705290&di=8fb07b89964cc8de9656cc91f7723520&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20190523%2F02997865ded44237a9e3b7c006a3c821.jpeg','盖·里奇','盖·里奇 / 约翰·奥古斯特','梅纳·玛索德 / 娜奥米·斯科特 / 威尔·史密斯 / 马尔万·肯扎里 / 纳维德·内加班','爱情 / 奇幻 / 冒险','美国','英语',128,'2019-05-23 16:00:00','阿拉丁','在充满异域风情的古代阿拉伯王国，善良的穷小子阿拉丁（莫纳·马苏德 饰）和勇敢的茉莉公主（娜奥米·斯科特 饰）浪漫邂逅，在可以满足主人三个愿望的神灯精灵（威尔·史密斯 饰）的帮助下，两人踏上了一次寻找真爱和自我的魔幻冒险。',0);
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
INSERT INTO `movie_like` VALUES (26,16,'2019-06-05 13:34:05');
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
  `user_id` int(11) NOT NULL,
  `schedule_id` int(11) NOT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_strategy`
--

LOCK TABLES `refund_strategy` WRITE;
/*!40000 ALTER TABLE `refund_strategy` DISABLE KEYS */;
INSERT INTO `refund_strategy` VALUES (1,1,0.6,40),(2,0,0,26),(3,0,0.777,76),(4,1,0.8,40),(5,0,0,0);
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
INSERT INTO `refund_strategy_movie` VALUES (2,25),(1,27),(1,28),(5,26),(1,24);
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
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (88,1,26,'2019-06-13 10:00:00','2019-06-13 13:00:00',50),(89,2,28,'2019-06-12 11:00:00','2019-06-12 14:00:00',30),(90,2,26,'2019-06-22 00:00:00','2019-06-22 03:00:00',50),(91,2,27,'2019-06-13 12:00:00','2019-06-13 15:00:00',35),(92,1,24,'2019-06-21 23:00:00','2019-06-22 02:00:00',20),(93,3,24,'2019-06-22 00:00:00','2019-06-22 03:00:00',20);
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
) ENGINE=InnoDB AUTO_INCREMENT=548 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (16,88,0,0,3,25,485,'2019-06-12 06:33:40',212316393),(16,88,1,0,4,25,486,'2019-06-12 06:33:40',212316393),(18,89,4,0,3,26.6667,487,'2019-06-12 07:25:10',208618459),(18,89,5,0,3,26.6667,488,'2019-06-12 07:25:10',208618459),(18,89,6,0,3,26.6667,489,'2019-06-12 07:25:10',208618459),(17,91,9,0,1,35,500,'2019-06-13 00:47:09',210117559),(17,91,10,0,1,35,501,'2019-06-13 00:47:09',210117559),(17,91,11,0,1,35,502,'2019-06-13 00:47:09',210117559),(17,91,9,1,1,35,503,'2019-06-13 00:47:09',210117559),(17,91,10,1,1,35,504,'2019-06-13 00:47:09',210117559),(17,91,11,1,1,35,505,'2019-06-13 00:47:09',210117559),(16,88,7,0,4,50,506,'2019-06-13 01:02:49',209716715),(16,88,8,0,4,50,507,'2019-06-13 01:02:49',209716715),(16,88,9,0,3,50,508,'2019-06-13 01:02:49',209716715),(16,88,5,1,2,NULL,509,'2019-06-13 01:06:08',206016911),(16,88,6,1,2,NULL,510,'2019-06-13 01:06:08',206016911),(16,88,7,1,2,NULL,511,'2019-06-13 01:06:08',206016911),(16,88,7,2,2,NULL,512,'2019-06-13 01:06:08',206016911),(16,88,7,2,1,25,523,'2019-06-13 01:23:30',209916665),(16,88,8,2,1,25,524,'2019-06-13 01:23:30',209916665),(18,88,9,2,4,33.3333,525,'2019-06-13 03:55:19',21221816),(18,88,9,3,1,33.3333,526,'2019-06-13 03:55:19',21221816),(18,88,9,4,1,33.3333,527,'2019-06-13 03:55:19',21221816),(16,90,7,1,4,25,528,'2019-06-17 01:20:19',208916173),(16,90,8,1,3,25,529,'2019-06-17 01:20:19',208916173),(16,92,8,1,4,20,530,'2019-06-18 02:05:02',205916810),(16,92,9,1,3,20,531,'2019-06-18 02:05:02',205916810),(16,93,6,1,3,13.3333,532,'2019-06-18 02:18:52',212216340),(16,93,6,2,4,13.3333,533,'2019-06-18 02:18:52',212216340),(16,93,7,2,1,13.3333,534,'2019-06-18 02:18:52',212216340),(16,90,11,1,2,NULL,535,'2019-06-18 02:29:42',212316304),(16,90,11,2,2,NULL,536,'2019-06-18 02:29:42',212316304),(16,90,11,3,2,NULL,537,'2019-06-18 02:29:42',212316304);
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'manager','manager','123'),(2,'admin','admin1','123'),(16,'user','user1','123'),(17,'user','user2','123'),(18,'user','user3','123'),(19,'user','user4','123'),(20,'admin','admin3','123'),(21,'user','user5','123456'),(25,'admin','admin6','123');
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
  `strategy_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vip_card_user_id_uindex` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card`
--

LOCK TABLES `vip_card` WRITE;
/*!40000 ALTER TABLE `vip_card` DISABLE KEYS */;
INSERT INTO `vip_card` VALUES (24,12,16,610,'2019-06-18 02:19:18'),(25,12,18,0,'2019-06-13 03:54:49'),(26,11,19,0,'2019-06-18 13:12:27');
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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card_charge`
--

LOCK TABLES `vip_card_charge` WRITE;
/*!40000 ALTER TABLE `vip_card_charge` DISABLE KEYS */;
INSERT INTO `vip_card_charge` VALUES (16,22,'2019-06-12 06:29:11',70,100),(21,22,'2019-06-13 00:43:24',80,110),(22,24,'2019-06-13 01:01:13',70,100),(23,24,'2019-06-13 01:01:50',40,55),(24,24,'2019-06-17 10:34:08',505,685);
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card_strategy`
--

LOCK TABLES `vip_card_strategy` WRITE;
/*!40000 ALTER TABLE `vip_card_strategy` DISABLE KEYS */;
INSERT INTO `vip_card_strategy` VALUES (11,50,'白金卡',30,5),(12,70,'钻石卡',40,15);
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

-- Dump completed on 2019-06-20 23:07:40
