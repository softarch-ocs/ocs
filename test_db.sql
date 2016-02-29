-- MySQL dump 10.13  Distrib 5.7.11, for Win64 (x86_64)
--
-- Host: localhost    Database: ocs
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  `salary` int(11) NOT NULL,
  `jobArea` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pnxbh7bmjptpstqyg7f3uo9yv` (`jobArea`),
  CONSTRAINT `FK_pnxbh7bmjptpstqyg7f3uo9yv` FOREIGN KEY (`jobArea`) REFERENCES `jobarea` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,'JavaEE developer','Aprende algo JAVA EEE',2147483647,1),(2,'Grails Developer','Solo grails ',4564,3),(3,'Actor','Actor heue',7878,4),(4,'Algorithms analyst','Best job ever',789798,3),(5,'Teacher','Hmmm ',7897,2),(6,'Rector','KHAAAAAAA',1234,1),(7,'ola k ase','q213¿asdasd',123213,5),(8,'lelol','sadasd',21323,5);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_jobfeatures`
--

DROP TABLE IF EXISTS `job_jobfeatures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_jobfeatures` (
  `jobId` int(11) NOT NULL,
  `elt` int(11) NOT NULL,
  `idx` int(11) NOT NULL,
  PRIMARY KEY (`jobId`,`idx`),
  KEY `FK_eph1n5mkplibgoamnj8w1dx4c` (`elt`),
  CONSTRAINT `FK_57wulax9h3n5746ep88n6wtmo` FOREIGN KEY (`jobId`) REFERENCES `job` (`id`),
  CONSTRAINT `FK_eph1n5mkplibgoamnj8w1dx4c` FOREIGN KEY (`elt`) REFERENCES `jobfeature` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_jobfeatures`
--

LOCK TABLES `job_jobfeatures` WRITE;
/*!40000 ALTER TABLE `job_jobfeatures` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_jobfeatures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobarea`
--

DROP TABLE IF EXISTS `jobarea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobarea` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobarea`
--

LOCK TABLES `jobarea` WRITE;
/*!40000 ALTER TABLE `jobarea` DISABLE KEYS */;
INSERT INTO `jobarea` VALUES (1,'Area1'),(2,'Area2'),(3,'Area3'),(4,'Area4'),(5,'Area5');
/*!40000 ALTER TABLE `jobarea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobfeature`
--

DROP TABLE IF EXISTS `jobfeature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobfeature` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(140) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_at8dfksgdi8gxsohi3qtglue6` (`userId`),
  CONSTRAINT `FK_at8dfksgdi8gxsohi3qtglue6` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobfeature`
--

LOCK TABLES `jobfeature` WRITE;
/*!40000 ALTER TABLE `jobfeature` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobfeature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobrequest`
--

DROP TABLE IF EXISTS `jobrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobrequest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL,
  `job` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rpx29gnc3lku3i73bs590w9rk` (`job`),
  KEY `FK_5m76uxvj568i4uhpk9s58s5x9` (`user`),
  CONSTRAINT `FK_5m76uxvj568i4uhpk9s58s5x9` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_rpx29gnc3lku3i73bs590w9rk` FOREIGN KEY (`job`) REFERENCES `job` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobrequest`
--

LOCK TABLES `jobrequest` WRITE;
/*!40000 ALTER TABLE `jobrequest` DISABLE KEYS */;
INSERT INTO `jobrequest` VALUES (1,1,4,7);
/*!40000 ALTER TABLE `jobrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `phoneNumber` varchar(15) NOT NULL,
  `address` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `personalID` varchar(45) DEFAULT NULL,
  `role` int(11) NOT NULL,
  `gender` int(11) NOT NULL,
  `birthday` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'a@unal.edu.co','a@unal.edu.co','a@unal.edu.co','a@unal.edu.co','a@unal.edu.co','a@unal.edu.co','a@unal.edu.co',1,1,'1987-12-31'),(2,'b@u.com','b@u.com','b@u.com','b@u.com','b@u.com','b@u.com','b@u.com',0,1,'1992-12-31'),(3,'x@unal.edu.co','x@unal.edu.co','123','x@unal.edu.co','x@unal.edu.co','x@unal.edu.co','x@unal.edu.co',0,1,'2016-02-11'),(4,'jjo@xd.com','jjo@xd.com','jjo@xd.com','jjo@xd.com','jjo@xd.com','jjo@xd.com','jjo@xd.com',0,1,'2016-02-04'),(5,'a@unal.edu.co','a@unal.edu.co','a@unal.edu.co','a@unal.edu.co','a@unal.edu.co','a@unal.edu.co','a@unal.edu.co',0,1,'0023-02-10'),(6,'b@u.com','b@u.com','b@u.com','b@u.com','b@u.com','b@u.com','b@u.com',0,0,'1998-02-27');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_jobfeatures`
--

DROP TABLE IF EXISTS `user_jobfeatures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_jobfeatures` (
  `userId` int(11) NOT NULL,
  `elt` int(11) NOT NULL,
  `idx` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`idx`),
  KEY `FK_c541iifj3ri8ivnx5napt2i9u` (`elt`),
  CONSTRAINT `FK_8ey2b6nupnpqqjkkalj6l08hv` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_c541iifj3ri8ivnx5napt2i9u` FOREIGN KEY (`elt`) REFERENCES `jobfeature` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_jobfeatures`
--

LOCK TABLES `user_jobfeatures` WRITE;
/*!40000 ALTER TABLE `user_jobfeatures` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_jobfeatures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersjobs`
--

DROP TABLE IF EXISTS `usersjobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usersjobs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `job` int(11) NOT NULL,
  `startTime` date NOT NULL,
  `endTime` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mr4n46e0rjtdahqni46e2wmrl` (`user`),
  KEY `FK_sxoqkj753vi9lcxhhu28hxbml` (`job`),
  CONSTRAINT `FK_mr4n46e0rjtdahqni46e2wmrl` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_sxoqkj753vi9lcxhhu28hxbml` FOREIGN KEY (`job`) REFERENCES `job` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersjobs`
--

LOCK TABLES `usersjobs` WRITE;
/*!40000 ALTER TABLE `usersjobs` DISABLE KEYS */;
/*!40000 ALTER TABLE `usersjobs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-29  8:01:05
