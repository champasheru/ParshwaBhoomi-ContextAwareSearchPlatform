-- MySQL dump 10.13  Distrib 5.5.15, for osx10.6 (i386)
--
-- Host: localhost    Database: smartsearch
-- ------------------------------------------------------
-- Server version	5.5.15

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'This is the auto increment id,will act like PK',
  `category_name` text NOT NULL,
  `category_description` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Computers','Computers/Gadgets/Electronics'),(2,'Food','Favorite food/Cuisines/Dishes'),(3,'Automobiles','Cars/Vehicles/Automobiles'),(4,'Travel','Traveling/Places/Journeys/Outdoors'),(5,'Lifestyle','Lifestyle/Shopping'),(6,'Training','Education/Training/Courses');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_preferences`
--

DROP TABLE IF EXISTS `user_preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_preferences` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT 'this is the foreign key from users table that identifies for which user this preference is.',
  `category_id` int(11) NOT NULL COMMENT 'this is the foreign key from categories table that identifies to which business category this user preference falls.',
  `preference` longtext NOT NULL COMMENT 'this is the user preference in which the keyword will be searched.if the keyword is contained in this preference text,then this text will be taken as modified keyword for search & will be searched against vendor''s category+advert/offerings.',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=159 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_preferences`
--

LOCK TABLES `user_preferences` WRITE;
/*!40000 ALTER TABLE `user_preferences` DISABLE KEYS */;
INSERT INTO `user_preferences` VALUES (77,1,1,' macbook air'),(76,1,1,' iphone'),(19,7,5,'Yoga'),(18,7,3,'Tata nano'),(17,7,1,'iphone,ios,all apple devices'),(16,7,2,'Fruits and apples'),(15,7,6,'Obj C'),(14,7,4,'Himalayas'),(154,8,2,'Icecream Parlour'),(153,8,2,'Pinapples'),(152,8,2,'Indian Cuisines'),(151,8,2,'Fruits'),(150,8,2,'Fish'),(149,8,6,'Solaris'),(148,8,6,'Robotics'),(147,8,6,'Java'),(146,8,6,'AI'),(145,8,4,'Wildlife'),(144,8,4,'Sahyadris'),(143,8,4,'Maldives'),(75,1,1,'apple computers'),(74,1,2,'Konkani '),(73,1,2,'Italian'),(72,1,6,'Comp Science,'),(70,1,4,' UK'),(71,1,4,' Vindhyas'),(69,1,4,' Kanha'),(68,1,4,'Java islands'),(142,9,5,'spa'),(79,1,3,'Fiat Punto'),(141,9,5,'beauty parlour'),(140,9,3,'tata nano'),(139,9,1,'android phones'),(138,9,2,'seafood'),(137,9,2,'apples'),(136,9,6,'theatre'),(135,9,6,'poetry'),(134,9,6,'literature'),(133,9,4,'Konkan'),(131,9,4,'MAldives'),(132,9,4,'UK'),(155,8,1,'Java powered devices'),(156,8,3,'Audi'),(157,8,3,'Jaguar Land Rover'),(158,8,3,'Tata Nano');
/*!40000 ALTER TABLE `user_preferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` mediumtext NOT NULL COMMENT 'name od the user',
  `password` varchar(20) NOT NULL COMMENT 'the apssword of an user.',
  `address` mediumtext NOT NULL COMMENT 'uesr''s addres',
  `contact_no` bigint(20) NOT NULL COMMENT 'phone/mobile number/s,multiple numbers seperated by commas',
  `education` text NOT NULL COMMENT 'multiple degrees/schools seperated by comma',
  `work` text NOT NULL COMMENT 'multiple value seperated by comma',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'saurabh','123','Pandharpur,Dist-Solapur',1100,'BE (CSE)','Tech Arch'),(7,'Steve','123','1 Infinite Loop,Cupertino,CA',1234567890,'Calligraphy','CEO,Apple'),(8,'James_Gosling','123','Calgery,Canada',34341,'Phd, Computer Science','Former VP Engg,Sun'),(9,'test_user','123','pune',1200,'BA English','professor');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendors`
--

DROP TABLE IF EXISTS `vendors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vendors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL COMMENT 'foreign key from categories table that identifies to which category this business belongs',
  `name` mediumtext NOT NULL COMMENT 'name of the business',
  `route_or_lane` mediumtext NOT NULL,
  `sublocality` mediumtext NOT NULL,
  `locality` mediumtext NOT NULL,
  `contact` int(11) NOT NULL COMMENT 'contact number of vendor',
  `advertisement_and_offerings` longtext NOT NULL COMMENT 'the details of the offerings made by the business or their advertisement. Multiple values need to be seperated by comma.This the text in which the  keyword  would be searched.',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendors`
--

LOCK TABLES `vendors` WRITE;
/*!40000 ALTER TABLE `vendors` DISABLE KEYS */;
INSERT INTO `vendors` VALUES (2,1,'House Of laptops','Tilak Road','Sadashiv Peth','Pune',12345,'All the laptop brands-\r\nApple computers,Maccbooks, iPad, macbook pro/air\r\nDell\r\nHP\r\nLenovo\r\nSony\r\nAlso we keep all the computer peripherals,accessories and provide best in class after sales service.'),(3,2,'Foodmall','Baner Road, Baner Telephone Exchange','Baner','Pune',3242,'We keep all the consumable food items.\r\nFresh fruits,vegetables,frozen food etc.\r\nCold drinks,ice cream,chocolates.'),(4,3,'Pandit Automotives','Tilak Road','Sadashiv Peth','Pune',13343,'All tata vehicles\r\n- Tata nano\r\n- Fiat distribution\r\n- Indica,Indigo,Aria,Safari'),(5,1,'Baba Computers','Tilak Road','Opp Maharashtra Mandal,Sadashiv Peth','Pune',2331,'All the branded laptops-\r\nDell Apple Lenovo HP \r\nAll the gadgets and peripherals-\r\niPod Nano,iPod Touch,Android phones.\r\nUSB cables,network accessories.'),(6,2,'Reliance Fresh','Paud Road,Bhusari Colony','Kothrud,Bhusari Colony','Pune',3422,'Get the farm fresh vegetables and fruits in Kothrud.\r\nAttractive prices,discounts to be availed.\r\n\r\n24 hrs open.\r\nHome devilery services. Special seasonal offers for Mangoes,Strawberries,Pineapples. '),(7,1,'Chroma','Parihar Chowk','Aundh','Pune',1234567890,'-Offers on all the consumer electronics goods\r\n-Special gift vouchers on purchase of TV & Home theaters for Dasra & Diwali...\r\nHurry!!!!!!!!!!!! ');
/*!40000 ALTER TABLE `vendors` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-10-05 11:40:06
