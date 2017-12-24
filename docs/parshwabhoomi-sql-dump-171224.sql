-- MySQL dump 10.13  Distrib 5.6.36, for macos10.12 (x86_64)
--
-- Host: localhost    Database: parshwabhoomi
-- ------------------------------------------------------
-- Server version	5.6.36

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
-- Table structure for table `business_vendors`
--

DROP TABLE IF EXISTS `business_vendors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `business_vendors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL COMMENT 'foreign key from categories table that identifies to which category this business belongs',
  `name` mediumtext NOT NULL COMMENT 'name of the business',
  `route_or_lane` mediumtext NOT NULL,
  `sublocality` mediumtext NOT NULL,
  `locality` mediumtext NOT NULL,
  `offerings` longtext,
  `pincode` varchar(256) DEFAULT NULL,
  `state` varchar(256) DEFAULT NULL,
  `latitude` varchar(256) DEFAULT NULL,
  `longitude` varchar(256) DEFAULT NULL,
  `primary_mobile` varchar(256) DEFAULT NULL,
  `secondary_mobile` varchar(256) DEFAULT NULL,
  `landline` varchar(256) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `tagline` text,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_bvuser_id` (`user_id`),
  KEY `fk_category_id` (`category_id`),
  CONSTRAINT `fk_bvuser_id` FOREIGN KEY (`user_id`) REFERENCES `user_creds` (`id`),
  CONSTRAINT `fk_category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_vendors`
--

LOCK TABLES `business_vendors` WRITE;
/*!40000 ALTER TABLE `business_vendors` DISABLE KEYS */;
INSERT INTO `business_vendors` VALUES (2,1,'House Of laptops','Shivaji Chowk','Station Road','Pandharpur','All the laptop brands-\r\nApple computers,Maccbooks, iPad, macbook pro/air\r\nDell\r\nHP\r\nLenovo\r\nSony\r\nAlso we keep all the computer peripherals,accessories and provide best in class after sales service.','413304','Maharashtra','17.676907','75.33269','9890123456','','02186-243344','hol@gmail.com','Largest multi-brand laptop showroom in Pandharpur!',1),(3,2,'Foodmall','Near Railway Station','Ganesh Nagar','Pandharpur','- All the consumable food items.\r\n- Fresh fruits,vegetables,frozen food etc.\r\n- Cold drinks,ice cream,chocolates.\r\n- Cosmetics, personal cares\r\n- Home delivery for monthly grocery items.','413304','Maharashtra','17.669436','75.31962','9800123980','','02186-908987','foodmall@gmail.com','All your daily needs under single roof!',2),(4,3,'Chavan Tata Motors','Pune Road','Isbavi','Pandharpur','- All tata vehicles including: Nano, Nexon, Aria, Tiago\r\n- Fiat distribution\r\n- 0% finance\r\n- Excellent after sales service\r\n- State of the art workshop and paint shop','413304','Maharashtra','17.680222','75.32312','98014878909','','02186223098','tatasales@chavanmotors.com','Trusted Tata dealer on Solapur city and district!',3),(5,1,'Baba Computers','Tilak Road','Opp Maharashtra Mandal,Sadashiv Peth','Pune','All the branded laptops-\r\nDell Apple Lenovo HP \r\nAll the gadgets and peripherals-\r\niPod Nano,iPod Touch,Android phones.\r\nUSB cables,network accessories.',NULL,NULL,NULL,NULL,'9800123456',NULL,NULL,NULL,NULL,4),(6,2,'Reliance Fresh','Jule Solapur','Vijapur Road','Solapur','- Get the farm fresh vegetables and fruits.\r\n- Attractive prices,discounts to be availed.\r\n- 24 hrs open.\r\n- Home devilery services. \r\n- Special discounts on seasonal fruits for Mangoes,Strawberries,Pineapples. \r\n- Grab the farm fresh Hurda & Corns this all through this winter.','413009','Maharashtra','17.63544','75.90947','9921908912','','0217-2345123','solapur@reliancefresh.com','Solapur\'s biggest retail store now at Vijapur Road.',5),(7,1,'Chroma','Parihar Chowk','Aundh','Pune','-Offers on all the consumer electronics goods\r\n-Special gift vouchers on purchase of TV & Home theaters for Dasra & Diwali...\r\nHurry!!!!!!!!!!!! ',NULL,NULL,NULL,NULL,'9800123456',NULL,NULL,NULL,NULL,6),(11,4,'DVP Square','KBP College Road','Near Market Yard','Pandharpur','- 6 screens equipped with Dolby Digital sound\r\n- Centralized air conditioning\r\n- Comfortable reclined sitting\r\n- Large car parking area\r\n- Online ticket booking with BookMyShow.com','413304','Maharashtra','17.67965','75.31699','9880700700','','02186-290990','dvp2@gmail.com','First ever multiplex in Pandharpur!',20),(12,4,'Badamikar & Sons','Saraswati Chowk','Near Prabhat Talkies','Solapur','- Largest furniture showroom in the city\r\n- All types of furniture on display including: Indian woodwork, Spanish timber work, French aesthetics\r\n- Ranges include Wardrobes, Beds, Dining tables, Living room units, modular kitchen\r\n- Hurry - grab new year discounts till Sankranti','413005','Maharashtra','17.675583','75.90427','7899434165','7899126534','0217-2345124','sales@badamikar.com','The furniture brand trusted by generations!',31);
/*!40000 ALTER TABLE `business_vendors` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `categories` VALUES (1,'COMPUTERS','Computers/Gadgets/Electronics'),(2,'FOOD','Favorite Food/Cuisines'),(3,'AUTOMOBILES','Cars/Vehicles/Automobiles'),(4,'TRAVEL','Travel/Leisure'),(5,'LIFESTYLE','Lifestyle/Shopping'),(6,'TRAINING','Education/Academics/Training/Courses');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `end_users`
--

DROP TABLE IF EXISTS `end_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `end_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `education` text NOT NULL COMMENT 'multiple degrees/schools seperated by comma',
  `work` text NOT NULL COMMENT 'multiple value seperated by comma',
  `pincode` varchar(256) DEFAULT NULL,
  `state` varchar(256) DEFAULT NULL,
  `latitude` varchar(256) DEFAULT NULL,
  `longitude` varchar(256) DEFAULT NULL,
  `primary_mobile` varchar(256) DEFAULT NULL,
  `secondary_mobile` varchar(256) DEFAULT NULL,
  `landline` varchar(256) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `route_or_lane` mediumtext,
  `sublocality` mediumtext,
  `locality` mediumtext,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`user_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_creds` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `end_users`
--

LOCK TABLES `end_users` WRITE;
/*!40000 ALTER TABLE `end_users` DISABLE KEYS */;
INSERT INTO `end_users` VALUES (1,'BE (CSE)','Tech Arch','413304','Maharashtra',NULL,NULL,'9823034018','',NULL,'smsaurabh1@yahoo.com','Saurabh Sirdeshmukh','1983-07-02',7,'Dattaprasad, Opp English School,','Rukmini Nagar','Pandharpur'),(7,'Economics','CEO, Advait Inc','411041','Maharashtra',NULL,NULL,'8380810002','',NULL,'avadhut@cs.io','Avadhut','2017-01-10',8,'A3/05, Aditya Sanskruti Co-op. Hsg. Society','Manaji Nagar, Narhe','Pune'),(9,'MA Indology','Professor','413313','Maharashtra',NULL,NULL,'9850810828','',NULL,'ira@gmail.com','Iravati','2016-12-30',10,'Yogiraj Niwas','Ganagapur Road','Akkalkot'),(15,'ME CSE','Entrepreneur','413304','Maharashtra',NULL,NULL,'9823034018','',NULL,'dange.gayatri@gmail.com','Gayatri Sirdeshmukh','1989-02-22',30,'Dange Maruti,','Govindpura','Pandharpur');
/*!40000 ALTER TABLE `end_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `search_history`
--

DROP TABLE IF EXISTS `search_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `search_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `latitude` varchar(256) DEFAULT NULL,
  `longitude` varchar(256) DEFAULT NULL,
  `original_query` varchar(256) NOT NULL,
  `modified_query` varchar(256) DEFAULT NULL,
  `result` longtext,
  `time` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `search_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_creds` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search_history`
--

LOCK TABLES `search_history` WRITE;
/*!40000 ALTER TABLE `search_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `search_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_creds`
--

DROP TABLE IF EXISTS `user_creds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_creds` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(256) NOT NULL,
  `password` varchar(256) DEFAULT NULL,
  `role` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_creds`
--

LOCK TABLES `user_creds` WRITE;
/*!40000 ALTER TABLE `user_creds` DISABLE KEYS */;
INSERT INTO `user_creds` VALUES (1,'houseoflaptops','123','BUSINESS_ENTITY'),(2,'foodmall','123','BUSINESS_ENTITY'),(3,'chavantata','123','BUSINESS_ENTITY'),(4,'babacomputers','123','BUSINESS_ENTITY'),(5,'reliancefresh','123','BUSINESS_ENTITY'),(6,'chroma','123','BUSINESS_ENTITY'),(7,'saurabh','123','END_USER'),(8,'avadhut','123','END_USER'),(10,'ira','123','END_USER'),(20,'dvp2','123','BUSINESS_ENTITY'),(30,'gayatri','123','END_USER'),(31,'badamikar','123','BUSINESS_ENTITY');
/*!40000 ALTER TABLE `user_creds` ENABLE KEYS */;
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
) ENGINE=MyISAM AUTO_INCREMENT=221 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_preferences`
--

LOCK TABLES `user_preferences` WRITE;
/*!40000 ALTER TABLE `user_preferences` DISABLE KEYS */;
INSERT INTO `user_preferences` VALUES (204,10,3,'Toyota Innova, Honda Jazz'),(184,7,6,'Obj C, Java'),(180,7,3,'Tata nano'),(181,7,2,'Fruits- apples, pomegranate'),(203,10,1,'GPS watch, iWatch, Solar powered devices'),(202,8,6,'Thesis on micro-economics and rural entrepreneurship'),(201,8,4,'Wildlife, Yoga'),(200,8,5,'Amazon, Ali Express'),(199,8,2,'Pinapples, Cherries, South Indian'),(183,7,4,'Himalayas, Road trip'),(182,7,5,'Yoga'),(179,7,1,'iphone,apple devices and computers'),(220,30,6,'Hindustani classical music'),(219,30,4,'Konkan, tourist places, social networking, theatre'),(216,30,3,'Maruti Ertiga'),(217,30,2,'Chaat, Kolhapuri, South indian, Chinese, Misal'),(218,30,5,'Warali paintings, Clothes, books'),(198,8,3,'Tata Nano'),(197,8,1,'Java powered devices, Phablets'),(205,10,2,'Veg, Maharashtrian, South India'),(206,10,5,'Hangout, Library, Seminars, Musical concerts'),(207,10,4,'Historic monuments, archaeological surveys'),(208,10,6,'Pursuing PhD in Vedic culture and Vedas'),(215,30,1,'mobile phones');
/*!40000 ALTER TABLE `user_preferences` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-24 20:30:19
