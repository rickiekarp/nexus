-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: 192.168.2.200    Database: assistant_data
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
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `userid` int(10) unsigned NOT NULL,
  `dateAdded` date NOT NULL,
  `isDeleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
INSERT INTO `note` VALUES (1,NULL,'updatedNote',3,'2017-12-04',''),(2,NULL,'test',1,'2017-12-04',''),(3,NULL,'test',1,'2017-12-04',''),(4,NULL,'test',1,'2017-12-04',''),(5,NULL,'test',1,'2017-12-04',''),(6,NULL,'test',1,'2017-12-04',''),(7,'test','test',1,'2017-12-04',''),(8,'testTitle','testnote',3,'2017-12-05','\0'),(9,'testTitle','testnote',1,'2017-12-05',''),(10,'testTitle','testnote',1,'2017-12-05',''),(11,'testTitle','testnote',1,'2017-12-05',''),(12,'testTitle','testnote',1,'2017-12-05',''),(13,'testTitle','testnote',1,'2017-12-05',''),(14,'testTitle','testnote',1,'2017-12-05',''),(15,'testTitle','testnote',1,'2017-12-05',''),(16,'testTitle','testnote',1,'2017-12-05',''),(17,'testTitle','testnote',1,'2017-12-05',''),(18,'testTitle','testnote',1,'2017-12-05',''),(19,'testTitle','testnote',1,'2017-12-05',''),(20,'testTitle','testnote',1,'2017-12-05',''),(21,'testTitle','testnote',1,'2017-12-05',''),(22,'testTitle','testnote',1,'2017-12-05',''),(23,'testTitle','testnote',1,'2017-12-05',''),(24,'testTitle','testnote',1,'2017-12-05',''),(25,'testTitle','testnote',1,'2017-12-05',''),(26,'testTitle','testnote',1,'2017-12-05',''),(27,'testTitle','testnote',1,'2017-12-05',''),(28,'testTitle','testnote',1,'2017-12-05',''),(29,'testTitle','testnote',1,'2017-12-05',''),(30,'testTitle','testnote',1,'2017-12-05',''),(31,'testTitle','testnote',1,'2017-12-05',''),(32,'testTitle','testnote',1,'2017-12-05',''),(33,'testTitle','testnote',1,'2017-12-05',''),(34,'testTitle','testnote',1,'2017-12-05',''),(35,'testTitle','testnote',1,'2017-12-05',''),(36,'testTitle','testnote',1,'2017-12-05',''),(37,'testTitle','testnote',1,'2017-12-05',''),(38,'testTitle','testnote',1,'2017-12-05',''),(39,'testTitle','testnote',1,'2017-12-05',''),(40,'testTitle','testnote',1,'2017-12-05',''),(41,'yxcyxc','yxcyc',1,'2017-12-06',''),(42,'wdfjhsjdkfhjksdf','jokfjskdfjkdsj',1,'2017-12-06',''),(43,'sljfskldfjksdfs','sjfjsdjflsjfl',1,'2017-12-06',''),(44,'sdfkjhshdkfjhsdjkf','sjdhfjksdfjksdfjksdhfjklhsjkdfjksdh',1,'2017-12-06',''),(45,'sdfhjkshdfjke2rwsdfüspojsgkhkjhgjk2','fmnsd,nmbnm',1,'2017-12-06',''),(46,'sdfsdfsdf','m,nx,mnv,wer',1,'2017-12-06',''),(47,'body','title',1,'2017-12-06',''),(48,'jsdf','sdfsdf',1,'2017-12-06',''),(49,'test','test',1,'2017-12-06',''),(50,'jdhdhdbd','jdndnd',59,'2017-12-06',''),(51,'jdjdjfnnf','ndncnfn',59,'2017-12-06',''),(52,'idjdjnd','jdncncn',59,'2017-12-06',''),(53,'hdbcbb','bdbdbbd',59,'2017-12-06',''),(54,'jxbbdnxn','hdndnnxn',59,'2017-12-06',''),(55,' ',' ',1,'2017-12-06',''),(56,'testTi','testn',1,'2017-12-06',''),(57,'hdbhdd','hdhdjsj',59,'2017-12-06',''),(58,'jrjdjdn','nfndndn',59,'2017-12-06',''),(59,'crccf','vgff',59,'2017-12-06',''),(60,'ccdjsjdnd','jdndncj',59,'2017-12-06',''),(61,'jfhdhjd','jxjdnd',59,'2017-12-06',''),(62,'\n','',1,'2017-12-07',''),(63,'','',1,'2017-12-07',''),(64,'\n','\n\n\n\n\n\n\n',1,'2017-12-07',''),(65,'schwanz','',1,'2017-12-07',''),(66,'dhjdjejfjskdj jejtjsjjffnejcksmdcnfjfkdjckdk','',1,'2017-12-07',''),(67,'','',1,'2017-12-07',''),(68,'','',1,'2017-12-07',''),(69,'','',1,'2017-12-07','\0'),(70,'Test','test',126,'2017-12-09','\0'),(71,'Notiz 2','Text',59,'2017-12-17','\0'),(72,'Notiz 3','Text',59,'2017-12-17','\0'),(73,'Notiz 4','Text',59,'2017-12-17',''),(74,'Notiz 5','Text',59,'2017-12-17',''),(75,'Notiz 6','Text',59,'2017-12-17',''),(76,'Notiz 7','Text',59,'2017-12-17',''),(77,'Notiz 8','Text',59,'2017-12-17',''),(78,'Notiz 9','Text',59,'2017-12-17',''),(79,'Notiz 10','Text',59,'2017-12-17',''),(80,'Notiz 4','Text',59,'2017-12-17',''),(81,'Notiz 4','Text',59,'2017-12-17','\0'),(82,'Notiz 5','Text',59,'2017-12-17','\0'),(83,'Notiz 6','Text',59,'2017-12-17','\0'),(84,'Notiz 7','Text',59,'2017-12-17','\0'),(85,'Notiz 8','Text',59,'2017-12-17','\0'),(86,'Notiz 9','Text',59,'2017-12-17','\0'),(87,'Notiz 10','Text',59,'2017-12-17','\0'),(88,'Notiz 11','Text',59,'2017-12-17','\0'),(89,'Notiz 12','Text',59,'2017-12-17','\0'),(90,'Notiz 13','Text',59,'2017-12-17','\0'),(91,'Notiz 14','Text',59,'2017-12-17','\0'),(92,'Notiz 15','Text',59,'2017-12-17','\0'),(93,'Notiz 16','Text',59,'2017-12-17','\0');
/*!40000 ALTER TABLE `note` ENABLE KEYS */;
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
INSERT INTO `schema_version` VALUES (1,'1','Create note table','SQL','V1__Create_note_table.sql',-2093350445,'user','2017-11-15 13:38:20',18036,0);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-02 13:38:41
