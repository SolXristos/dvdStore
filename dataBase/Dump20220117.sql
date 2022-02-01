-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dvdstore
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dvdcards`
--

DROP TABLE IF EXISTS `dvdcards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dvdcards` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dvd_id` int DEFAULT NULL,
  `card_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dvd_id_idx` (`dvd_id`),
  KEY `card_id_idx` (`card_id`),
  CONSTRAINT `card_id` FOREIGN KEY (`card_id`) REFERENCES `shopingcard` (`idshopingCard`),
  CONSTRAINT `dvd_id` FOREIGN KEY (`dvd_id`) REFERENCES `dvds` (`idDVDs`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dvdcards`
--

LOCK TABLES `dvdcards` WRITE;
/*!40000 ALTER TABLE `dvdcards` DISABLE KEYS */;
INSERT INTO `dvdcards` VALUES (1,1,1),(2,2,2),(3,1,2);
/*!40000 ALTER TABLE `dvdcards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dvdorders`
--

DROP TABLE IF EXISTS `dvdorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dvdorders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_dvd` int DEFAULT NULL,
  `id_order` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idDVDs_idx` (`id_dvd`),
  KEY `id_idx` (`id_order`),
  CONSTRAINT `id` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `idDVDs` FOREIGN KEY (`id_dvd`) REFERENCES `dvds` (`idDVDs`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dvdorders`
--

LOCK TABLES `dvdorders` WRITE;
/*!40000 ALTER TABLE `dvdorders` DISABLE KEYS */;
INSERT INTO `dvdorders` VALUES (1,1,1),(2,2,1),(3,2,2),(4,7,1),(5,7,1),(6,7,2),(7,7,3),(8,1,7),(9,2,7),(10,3,7);
/*!40000 ALTER TABLE `dvdorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dvds`
--

DROP TABLE IF EXISTS `dvds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dvds` (
  `idDVDs` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `actors` varchar(45) DEFAULT NULL,
  `director` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `lang` varchar(45) DEFAULT NULL,
  `subs` varchar(45) DEFAULT NULL,
  `kind` varchar(45) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `pieces` int DEFAULT NULL,
  PRIMARY KEY (`idDVDs`),
  UNIQUE KEY `idDVDs_UNIQUE` (`idDVDs`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dvds`
--

LOCK TABLES `dvds` WRITE;
/*!40000 ALTER TABLE `dvds` DISABLE KEYS */;
INSERT INTO `dvds` VALUES (1,'movie','actor1, actor2, actor3','director1','2022-01-12',90,'english','english,greek','opera',14,120),(2,'movie2','actor1, actor2, actor3','director2','2022-01-12',190,'english','english,greek','opera',5,150),(3,'movie','actor1, actor2, actor3','director1','2022-01-01',90,'english','english,greek','opera',120,14),(6,'movie','actor1, actor2, actor3','director1','2022-01-12',90,'english','english,greek','opera',14,120),(7,'movie','ACTOR','director1','2022-01-12',90,'english','english,greek','opera',14,120),(8,'movie','ACTOR','director1','1984-05-23',90,'english','english,greek','opera',14,120),(9,'fairy movie','brajolina','director1','2022-01-12',90,'english','english,greek','opera',14,120);
/*!40000 ALTER TABLE `dvds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `addr` varchar(45) DEFAULT NULL,
  `shopingCard_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `dateFin` varchar(45) DEFAULT NULL,
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_id_idx` (`user_id`),
  KEY `shopingCard_id_idx` (`shopingCard_id`),
  CONSTRAINT `shopingCard_id` FOREIGN KEY (`shopingCard_id`) REFERENCES `shopingcard` (`idshopingCard`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`idUsers`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'address1',NULL,1,'completed','2022-01-12','2022-01-12'),(2,'address2',NULL,1,'canceled','2022-01-12','2022-01-12'),(3,'address2',1,1,'active','2022-01-13','2022-01-13'),(4,'address1',1,1,'completed','2022-01-16','2022-01-16'),(5,'samou',1,8,'completed','2022-01-16','2022-01-16'),(6,'tripolis',1,8,'canceled','2022-01-16','2022-01-16'),(7,'My house',2,1,'active','2022-01-13','2022-01-13'),(8,'tripolis',1,8,'canceled','2022-01-16','2022-01-16');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopingcard`
--

DROP TABLE IF EXISTS `shopingcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopingcard` (
  `idshopingCard` int NOT NULL AUTO_INCREMENT,
  `amount` int DEFAULT NULL,
  `creDate` date DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `userid` int DEFAULT NULL,
  PRIMARY KEY (`idshopingCard`),
  KEY `userid_idx` (`userid`),
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `users` (`idUsers`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopingcard`
--

LOCK TABLES `shopingcard` WRITE;
/*!40000 ALTER TABLE `shopingcard` DISABLE KEYS */;
INSERT INTO `shopingcard` VALUES (1,45,'2022-01-12','stilactive',1),(2,90,'2022-01-12','stilactive',1);
/*!40000 ALTER TABLE `shopingcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `idUsers` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `fullName` varchar(45) DEFAULT NULL,
  `role` int DEFAULT NULL,
  `addr` varchar(45) DEFAULT NULL,
  `card` varchar(45) DEFAULT NULL,
  `cardNum` varchar(45) DEFAULT NULL,
  `cardExpdate` varchar(45) DEFAULT NULL,
  `cardPass` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUsers`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'username','123456','name',3,'address','debit','4200','2022-01-12','123456'),(8,'notMe','Pass','FullName',2,'addr','debit','4200','2022-01-13','1234');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-17 12:52:40
