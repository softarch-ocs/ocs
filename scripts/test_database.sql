-- MySQL dump 10.15  Distrib 10.0.23-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: ocs
-- ------------------------------------------------------
-- Server version	5.6.27

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
  UNIQUE KEY `UK_kbxm4s8j62fg5khtwj3ljmiwb` (`name`),
  KEY `FK_pnxbh7bmjptpstqyg7f3uo9yv` (`jobArea`),
  CONSTRAINT `FK_pnxbh7bmjptpstqyg7f3uo9yv` FOREIGN KEY (`jobArea`) REFERENCES `jobarea` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,'Arquitecto','Desarrolla la arquitectura del proyecto',15000000,3),(2,'Ingenierio','Desarrolla el software.',955555555,1),(3,'Publicista','Crea la publicidad',45678979,5),(4,'Administrador de empresas','Manejar las finanzas.',112111,2),(5,'Ingeniero industrial','optimizar jajaja',0,4),(6,'Ingenierio de testing','Realiza pruebas de la aplicacion.',99999999,6);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_jobfeatures`
--

LOCK TABLES `job_jobfeatures` WRITE;
/*!40000 ALTER TABLE `job_jobfeatures` DISABLE KEYS */;
INSERT INTO `job_jobfeatures` VALUES (2,1,0),(1,2,2),(2,2,1),(6,2,1),(2,3,2),(6,4,0),(3,5,0),(4,6,0),(5,6,0),(1,7,0),(4,7,2),(6,7,3),(1,8,1),(3,8,2),(2,9,3),(3,9,1),(5,9,2),(6,9,2),(4,10,1),(5,10,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobarea`
--

LOCK TABLES `jobarea` WRITE;
/*!40000 ALTER TABLE `jobarea` DISABLE KEYS */;
INSERT INTO `jobarea` VALUES (1,'Ingenieria'),(2,'Finanzas'),(3,'Administracion'),(4,'Recursos humanos'),(5,'Mercadeo'),(6,'Testing');
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
  `skillTest` varchar(140),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobfeature`
--

LOCK TABLES `jobfeature` WRITE;
/*!40000 ALTER TABLE `jobfeature` DISABLE KEYS */;
INSERT INTO `jobfeature` VALUES (1,'SQL','Lenguaje para base de datos', NULL),(2,'Java','Lenguaje de programacion.', NULL),(3,'C++','Lenguaje de programacion.', NULL),(4,'Selenium.','Suite de testing', NULL),(5,'Photoshop','Software de edicion', NULL),(6,'Excel','Hojas de calculo', NULL),(7,'Resolucion de problemas','Experiencia y habilidad para resolver problemas.', NULL),(8,'Manejo de personas','Experiencia y habilidad en manejo de personas', NULL),(9,'Trabajo en equipo','Tiene experiencia en trabajar en equipo', NULL),(10,'Manejo de recursos','Experiencia y habilidad en manejo de recursos.', NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobrequest`
--

LOCK TABLES `jobrequest` WRITE;
/*!40000 ALTER TABLE `jobrequest` DISABLE KEYS */;
INSERT INTO `jobrequest` VALUES (1,1,3,3),(2,1,4,4),(3,1,5,4),(4,1,2,5),(5,1,6,6),(6,2,4,7),(7,2,5,7),(8,1,2,8),(9,1,6,8),(10,0,4,7),(11,0,5,7),(12,1,1,1),(13,0,6,1),(14,0,1,9);
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
  `phoneNumber` varchar(15) NOT NULL,
  `address` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `personalID` varchar(45) DEFAULT NULL,
  `role` int(11) NOT NULL,
  `gender` int(11) NOT NULL,
  `birthday` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES 
(1,'ah@ah.com','7531592','carrera 565 ds','Andres','Hernandez','123465',0,1,'1994-01-31'),
(2,'admin@ocs.com','7531592','carrera 565 ds','Admin','1','123465',2,0,'1993-05-05'),
(3,'carlos@ocs.com','1451587','calle 484  d84','Carlos','Diaz','1564877',0,1,'1985-01-02'),
(4,'cl@gmail.com','7531592','calle 484  d84','Claudia','Mendoza','123465',0,0,'1961-10-20'),
(5,'juan@velez.c','7531592','calle 484  d84','Juan','Velez','154687',0,1,'1995-06-30'),
(6,'mau@za.c','7531592','calle 484  d84','Mauricio','Za','465487987',0,1,'1976-04-15'),
(7,'hernan@o.com','7531592','calle 484  d84','Hernan','Wess','123465',0,1,'1976-04-14'),
(8,'xim@xi.xi','7531592','calle 484  d84','Ximena','Xi','154687',0,0,'1985-01-03'),
(9,'pau@p.c','7531592','calle 484  d84','Paula','Q','154687',0,0,'1976-04-17'),
(10,'webservice@ocs.com','7531592','carrera 565 ds','Web Service','1','123465',1,0,'1993-05-05');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_jobfeatures`
--

LOCK TABLES `user_jobfeatures` WRITE;
/*!40000 ALTER TABLE `user_jobfeatures` DISABLE KEYS */;
INSERT INTO `user_jobfeatures` VALUES (1,1,0),(2,1,0),(5,1,0),(8,1,0),(9,1,5),(1,2,1),(2,2,1),(5,2,1),(6,2,0),(8,2,1),(9,2,4),(1,3,2),(2,3,2),(5,3,2),(8,3,2),(1,4,3),(5,4,3),(6,4,1),(8,4,3),(1,5,4),(3,5,0),(5,5,4),(8,5,4),(1,6,9),(4,6,0),(7,6,0),(1,7,6),(3,7,4),(4,7,1),(6,7,2),(7,7,2),(8,7,5),(9,7,3),(1,8,8),(3,8,3),(4,8,2),(7,8,1),(9,8,2),(1,9,5),(3,9,2),(4,9,3),(5,9,5),(6,9,3),(7,9,3),(8,9,6),(9,9,1),(1,10,7),(3,10,1),(4,10,4),(7,10,4),(9,10,0);
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
  `endTime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mr4n46e0rjtdahqni46e2wmrl` (`user`),
  KEY `FK_sxoqkj753vi9lcxhhu28hxbml` (`job`),
  CONSTRAINT `FK_mr4n46e0rjtdahqni46e2wmrl` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_sxoqkj753vi9lcxhhu28hxbml` FOREIGN KEY (`job`) REFERENCES `job` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersjobs`
--

LOCK TABLES `usersjobs` WRITE;
/*!40000 ALTER TABLE `usersjobs` DISABLE KEYS */;
INSERT INTO `usersjobs` VALUES (1,5,2,'2016-02-29',NULL),(2,8,2,'2016-02-29',NULL),(3,3,3,'2016-02-29',NULL),(4,4,4,'2016-02-29',NULL),(5,4,5,'2016-02-29',NULL),(6,6,6,'2016-02-29',NULL),(7,8,6,'2016-02-29',NULL),(8,8,6,'2016-02-28','2016-03-18'),(9,1,1,'2016-02-29',NULL);
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
