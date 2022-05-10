-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: notesmuscle
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `CoursesID` int NOT NULL,
  `MondayID` varchar(12) DEFAULT NULL,
  `TuesdayID` varchar(12) DEFAULT NULL,
  `WednesdayID` varchar(12) DEFAULT NULL,
  `ThursdayID` varchar(12) DEFAULT NULL,
  `FridayID` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`CoursesID`),
  KEY `FridayID` (`FridayID`),
  KEY `ThursdayID` (`ThursdayID`),
  KEY `WednesdayID` (`WednesdayID`),
  KEY `TuesdayID` (`TuesdayID`),
  KEY `MondayID` (`MondayID`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`MondayID`) REFERENCES `monday` (`MondayID`),
  CONSTRAINT `courses_ibfk_10` FOREIGN KEY (`MondayID`) REFERENCES `monday` (`MondayID`) ON DELETE CASCADE,
  CONSTRAINT `courses_ibfk_2` FOREIGN KEY (`TuesdayID`) REFERENCES `tuesday` (`TuesdayID`),
  CONSTRAINT `courses_ibfk_3` FOREIGN KEY (`WednesdayID`) REFERENCES `wednesday` (`WednesdayID`),
  CONSTRAINT `courses_ibfk_4` FOREIGN KEY (`ThursdayID`) REFERENCES `thursday` (`ThursdayID`),
  CONSTRAINT `courses_ibfk_5` FOREIGN KEY (`FridayID`) REFERENCES `friday` (`FridayID`),
  CONSTRAINT `courses_ibfk_6` FOREIGN KEY (`FridayID`) REFERENCES `friday` (`FridayID`) ON DELETE CASCADE,
  CONSTRAINT `courses_ibfk_7` FOREIGN KEY (`ThursdayID`) REFERENCES `thursday` (`ThursdayID`) ON DELETE CASCADE,
  CONSTRAINT `courses_ibfk_8` FOREIGN KEY (`WednesdayID`) REFERENCES `wednesday` (`WednesdayID`) ON DELETE CASCADE,
  CONSTRAINT `courses_ibfk_9` FOREIGN KEY (`TuesdayID`) REFERENCES `tuesday` (`TuesdayID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (22101002,'22101002M','22101002T','22101002W','22101002Th','22101002F'),(22101023,'22101023M','22101023T','22101023W','22101023Th','22101023F'),(22101058,'22101058M','22101058T','22101058W','22101058Th','22101058F'),(22101170,'22101170M','22101170T','22101170W','22101170Th','22101170F');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-10 15:00:37
