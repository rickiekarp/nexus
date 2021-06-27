-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: 192.168.2.200    Database: login
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.23-MariaDB-9+deb9u1

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
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `id` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permissionname` (`permissionname`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (17,'CTRL_PERM_ADD_POST'),(20,'CTRL_PERM_DELETE_GET'),(18,'CTRL_PERM_EDIT_GET'),(19,'CTRL_PERM_EDIT_POST'),(16,'CTRL_PERM_LIST_GET'),(12,'CTRL_ROLE_ADD_POST'),(15,'CTRL_ROLE_DELETE_GET'),(13,'CTRL_ROLE_EDIT_GET'),(14,'CTRL_ROLE_EDIT_POST'),(11,'CTRL_ROLE_LIST_GET'),(2,'CTRL_STRATEGY_ADD_POST'),(5,'CTRL_STRATEGY_DELETE_GET'),(3,'CTRL_STRATEGY_EDIT_GET'),(4,'CTRL_STRATEGY_EDIT_POST'),(1,'CTRL_STRATEGY_LIST_GET'),(7,'CTRL_USER_ADD_POST'),(10,'CTRL_USER_DELETE_GET'),(8,'CTRL_USER_EDIT_GET'),(9,'CTRL_USER_EDIT_POST'),(6,'CTRL_USER_LIST_GET');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permissions` (
  `role_id` int(6) unsigned NOT NULL,
  `permission_id` int(6) unsigned NOT NULL,
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permissions_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
INSERT INTO `role_permissions` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20);
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rolename` (`rolename`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_version` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_version`
--

LOCK TABLES `schema_version` WRITE;
/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
INSERT INTO `schema_version` VALUES (1,'1','Create login tables','SQL','V1__Create_login_tables.sql',1545770830,'user','2017-11-15 13:39:54',69440,1),(2,'2','Add login data','SQL','V2__Add_login_data.sql',-553917527,'user','2017-11-15 13:40:00',4687,1);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin',1,'MTpEVXpkUHJwQXNvSVhIcWFl'),(2,'tokentest','test',1,'MjpHdk9LRVZQeWxHb3FoVmFl'),(3,'logintest','test',1,'Mzp5dnBGaUV6SVRVak1EY2NU'),(4,'registertest','test',1,'NDp3a1pQUkZ2UUF0UVRYdk5o'),(5,'jjjjrrr','eegghh',1,'NTprQXZia2xLZktTTkxMd1Rs'),(6,'qqqqqqqqwdjfsdfhkjs','sjdfkls',1,'NjpJTXdxQ09yUEhncUxWVER3'),(8,'drop Database','sjdfkls',1,'ODpNR2tNYVBscHZwR3VCUk1y'),(10,'\'; drop table users; --','sjdfkls',1,'MTA6VVpXZ0hmcVdEVkRmdUVLTw=='),(11,'\'; drop table user; --','sjdfkls',1,'MTE6clpyWGZnbFhhT3VZdmVHQw=='),(12,'\'; drop database; --','sjdfkls',1,'MTI6dnhSZEdDTFVnVnhxaUN1dA=='),(13,'shsjxjbsjdjd','dhwudu',1,'MTM6ZEV2SFRaY0xValpreGdXdQ=='),(14,'@_€_#(#\"?€(!','dhwudu',1,'MTQ6WGZoVVRqb2VxQVViQ0VkTg=='),(15,'Bitches','becrazy',1,'MTU6TllaeFdkWEVwTld2SXlDTg=='),(16,'\'; drop table user;  --','becrazy',1,'MTY6dlhBVXFObldzRmRIa2x1Yg=='),(19,'marlon\'; drop table user;  --','becrazy',1,'MTk6VEFTam1oYnljTWpEbVVRTA=='),(20,'marlon\'\"; drop table user;  --','becrazy',1,'MjA6amNOT1ZiSE9Uc0tVQktaaQ=='),(21,'marlon\', \'pw\'); drop table user;  --','becrazy',1,'MjE6a2Zlb05JSU1OSHpuclVKdQ=='),(23,'marlon\', \'pw\'); drop table user; ','becrazy',1,'MjM6Tkh6cXNuT29iVE9LRlRPZw=='),(24,'','becrazy',1,'MjQ6SFdTQVl1WGFKQ1BnTG9wYg=='),(27,'NULL','becrazyk',1,'Mjc6WUhYSkJtd3BhbkdWR1lDYw=='),(28,'\'+NULL+\'','becrazyk',1,'Mjg6dHVpY3FzenpCb2pveWtHTQ=='),(29,'; drop table user; --j','becrazyk',1,'Mjk6d3VkUEt6ekd4TGpjRFpKdg=='),(30,'hihuihui','sdfsdf',1,'MzA6ckhwdmF2dVVzZlN4aElJbQ=='),(31,'dfdsgfdfgs','sgsdgs',1,'MzE6WlZjZktJWmtqa2RLbVBxdQ=='),(32,'fhdhdhd','djdndj',1,'MzI6ZU5NYldpR1NyT2NZdWxBYQ=='),(33,'aaaaa','aaaa',1,'MzM6UGhaeEhzZ0xUcEhGcEpyQQ=='),(38,'aaaaadhhdjf','aaaa',1,'Mzg6VGtuR3lleEZidHlYS1RPdQ=='),(39,'aajdjfjjf','aaaa',1,'Mzk6ZE1QTXVKZ1JyYVVRZnJacw=='),(40,'aajdjfjjfjdhdh','aaaa',1,'NDA6cVpJTW5Od21lT1ZPS1F4bQ=='),(41,'asdfsdjkfsdkf','sdfjnsdf',1,'NDE6a1R1dWpzS1hWa2pqdEdudQ=='),(42,'hjgjhkg','ghjghj',1,'NDI6dGloVFBEaUltQWZoTkpRbw=='),(43,'test100','test',1,'NDM6UlpNUGZ4VXZPdUFIR3NHTw=='),(44,'test101','test',1,'NDQ6eXd1S0pwekJ6UmtneWd5Sw=='),(45,'test102','test',1,'NDU6TEdMRUpYT0VwS1ZFZVBYRQ=='),(46,'pfghfv','vedg',1,'NDY6akpHRHBEdWpidmtwZUx3WA=='),(47,'fhfgv','fudjdh',1,'NDc6VVZJem9LWmtxRmpvZmV2VQ=='),(48,'dhdhxhh','sshfh',1,'NDg6TkZVak1MWWJ5UklHT2lJdA=='),(49,'dvdg','fgh',1,'NDk6a2xHemVyZmVKWEx5aHdBWA=='),(50,'hdhdjsjc','ndjdjdn',1,'NTA6YkhDWmhJc2ZOVEtNSkl4Uw=='),(51,'ituiuki','jizjk',1,'NTE6UGdCQ0xhV2hteHdJdEZ0Uw=='),(52,'hdjdjdnfng','kjxjdjdj',1,'NTI6eFJubVhSaGhwbkZLQmdQaA=='),(53,'test02','test',1,'NTM6d1Bad3dtbWZNaE9DeVRrbw=='),(55,'test03','test',1,'NTU6T1FuZW9KcmVSTnhmcnF6VQ=='),(57,'jdndjejf','jdjxjdjjd',1,'NTc6WVV0TXpTb05GVGFxZ2hMbg=='),(58,'jdiejjdjd','jdjdjdjd',1,'NTg6aVZYTWVkZmlwcVFMVW9USA=='),(59,'a','a',1,'NTk6V0ptWmFFcFBuRnVzYlBTTA=='),(62,'jejdjfjd','jdjdjjd',1,'NjI6akppeW9WTnZIS05mdFVUbA=='),(63,'jdjdjdkd','jdjdncjcj',1,'NjM6TkdzeUloRWFNdENSd0NaaA=='),(64,'hdjdjxndncnndj','jxnxnxnxn',1,'NjQ6R2V5Y0xIaklhdXhKUmRBVQ=='),(65,'jdjdhdjdndn','ndnxndndn',1,'NjU6d1ZCenJnU2Z4VE5WYXpmVw=='),(66,'ndjsksnd','jdjxjsndj',1,'NjY6V0JnZnNuc2R0VFNjbld3eA=='),(67,'jwjdjsjdjdjnd','jdjejdj',1,'Njc6aWJBbmpIRlJmeWhDSXFLUg=='),(68,'hdhdjjfj','jdnfjdjd',1,'Njg6TGxnb3B5d2FUcURReWdHdA=='),(69,'jdjfnfj','jcndjdn',1,'Njk6ZGRMR2ZJdmhxclB4aXRVeA=='),(70,'ffff','fff',1,'NzA6QmZ2U0tPYWVmZmlvQ0JJcA=='),(71,'test05','test',1,'NzE6b3BIY3hBU09OVVhOWnVVeg=='),(72,'test06','test',1,'NzI6TWhnckxkWUFoZ1dFSExKRg=='),(73,'dhhgh','gfzug',1,'NzM6clZ1ZnFmS2ZoclNoZGxzcA=='),(74,'vivj','cjicjcj',1,'NzQ6VnpNWVJOVGFST2hMeUVZVw=='),(77,'hehfjdjf','bxncjdnxjd',1,'Nzc6YWRGaWN1bm9mc0JzYVVzbw=='),(79,'test07','test',1,'Nzk6dkJWVnNIc0dyYnpURmR4Sg=='),(81,'ndbxnx','ndncnc',1,'ODE6T3lUd2NJeGxzTlRFSGRmbQ=='),(82,'jkdshfkjsdhfk','sdhfjkhsdf',1,'ODI6ZkFNa3NyZ1BuSklkbFFyRQ=='),(83,'hdbdjd','hxbhdbd',1,'ODM6VXhKWVdhZEdOZ3lQRWdkZQ=='),(84,'sjkdfhksjdfh','sdfhjksdfhkjsd',1,'ODQ6bnZtV1FPUVNQdVZSS1hMRw=='),(85,'hdbdbdn','admin',1,'ODU6Um5TS1FaSG13UWFER1J0eQ=='),(86,'ndbfbdb','nxnxbxb',1,'ODY6SUhCamh2cWhUb2R3ZHZUQQ=='),(87,'r7weuds','sdfhkj23',1,'ODc6UUdVVXJma1FQaUxJbnBpYw=='),(89,'jdjfhf','bcjxbc',1,'ODk6V2RjWm5XSUV6VlFlZXdXWg=='),(91,'bfhdnxhch','bxjxbxnx',1,'OTE6aFhjUElJWUxCTmlZVnVUeA=='),(92,'bdbdnfn','jdndnxn',1,'OTI6RkdvRHV0b2VXd0NzcmVtVA=='),(94,'ajfncnxn','Ahdhdhd',1,'OTQ6cWR4YWhTdWpyd3pjZ0xDZg=='),(95,'jfnxjd','nxnxncc',1,'OTU6WW1pdXdtQVJ3ekFCVGVFQw=='),(96,'jdhdc','fncbncc',1,'OTY6b2NpRlFVRElsUUFIRnRWSg=='),(97,'hdbd','bcbcnx',1,'OTc6YVNnYlFyQk9Md2hNcFJmbw=='),(98,'bdhdbd','nxnxnd',1,'OTg6YlVHb1FBU3J6RmpDSUlnUg=='),(99,'jdbdd','gvv',1,'OTk6YXNXTkJxb0ROZFVSRmFidQ=='),(100,'ncncnf','nxncnc',1,'MTAwOmxJS0J3SlNDYUt3Qk1aQWk='),(101,'hxbdbd','hxnxnxnnx',1,'MTAxOlhzUVpXTVlwRVFZc0RQRXA='),(102,'hdjdbdjc','nynxnxnx',1,'MTAyOnVRWUVGRUVObVB1bXFMTE0='),(104,'bdbdbxb','jdhdjf',1,'MTA0Onp1enpGTVh2aWpuRE9CSHY='),(106,'bdbdbxbcgg','jdhdjf',1,'MTA2OmxnTURsTXlzVXpUUG9XcXM='),(107,'hdbdbdb',' x xnx',1,'MTA3OllVcm5hd3dyQWdRYUpZWE0='),(108,'hdbdhd','hxbxbx',1,'MTA4OlBoaFJkYXVWZXNKcUVXS3c='),(109,'djbdhxh','jdnxjc',1,'MTA5OlZPSWVNSFVSVnVkemtoalk='),(110,'bdbdbdnjx','hxbdbxb',1,'MTEwOnhaWkZma3FiZWxPb3hNS2M='),(111,'hdjdj','jxnxndn',1,'MTExOnhrQnJJUkVtQ0pPbWhRSXM='),(112,'hxbsndn','hxjxjnd',1,'MTEyOlJzTG5jT1Rkb1ppemNmZlo='),(113,'test','teat',1,'MTEzOkhwZkFEVHF3VkR5ZHZnQUc='),(114,'ffjdndn','hfbxbc',1,'MTE0OnBFb2dXd2xjSHNxeWZrT3Q='),(116,'jdjsjdnd','hxnsjdj',1,'MTE2OnduRkFwRnFyWm9ieHdpeHg='),(117,'hdbyns','jsnsnsnny',1,'MTE3OnNNd1pkUFNrRFFPc3lpb0M='),(118,'jxjejucu','jcjdjjd',1,'MTE4OmxaR2JCYkpWZ2tzUnNhSkE='),(119,'@@@@','ndjdjxjnx',1,'MTE5OmRmRmVHRXJMYllMeU13VUI='),(123,'jdjdnnd',' cnxnxn',1,'MTIzOnV0aUFTV0tUcHp3d1dHT1Y='),(125,'testtest','test',1,'MTI1OnlwSWh1cHVsWHZab1FmdW4='),(126,'hsjdncjcjdjf','jdnfnjcjf',1,'MTI2OlNYZlBqeW1HRHJaVXZhSFo='),(127,'user','user',1,'MTI3Onp2RVNZckNUVGVzWm1rQWI=');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_id` int(6) unsigned NOT NULL,
  `role_id` int(6) unsigned NOT NULL,
  KEY `USER` (`user_id`),
  KEY `role` (`role_id`),
  CONSTRAINT `USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(2,2),(3,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-02 13:37:55
