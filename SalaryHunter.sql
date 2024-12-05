CREATE DATABASE  IF NOT EXISTS `SalaryHunter` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `SalaryHunter`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: SalaryHunter
-- ------------------------------------------------------
-- Server version	8.0.40-0ubuntu0.22.04.1

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
-- Table structure for table `Background`
--

DROP TABLE IF EXISTS `Background`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Background` (
  `background_id` int NOT NULL AUTO_INCREMENT,
  `degree_level` enum('BS','MS','PhD') DEFAULT NULL,
  `university_name` varchar(64) NOT NULL,
  `year_of_work` int NOT NULL,
  `username` varchar(64) DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  PRIMARY KEY (`background_id`),
  KEY `username` (`username`),
  KEY `job_id` (`job_id`),
  CONSTRAINT `Background_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Registered_User` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Background_ibfk_2` FOREIGN KEY (`job_id`) REFERENCES `Job_Position` (`job_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Background`
--

LOCK TABLES `Background` WRITE;
/*!40000 ALTER TABLE `Background` DISABLE KEYS */;
INSERT INTO `Background` VALUES (1,'BS','Northeastern University',0,'a',1),(2,'MS','Northeastern University',2,'a',2),(3,'BS','Northeastern University',0,'b',3);
/*!40000 ALTER TABLE `Background` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Benefit`
--

DROP TABLE IF EXISTS `Benefit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Benefit` (
  `benefit_id` int NOT NULL AUTO_INCREMENT,
  `benefit_name` varchar(64) NOT NULL,
  `benefit_type` enum('Insurance','Holiday','Stock','Retirement','Family') DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  PRIMARY KEY (`benefit_id`),
  KEY `job_id` (`job_id`),
  CONSTRAINT `Benefit_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `Job_Position` (`job_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Benefit`
--

LOCK TABLES `Benefit` WRITE;
/*!40000 ALTER TABLE `Benefit` DISABLE KEYS */;
INSERT INTO `Benefit` VALUES (1,'401K','Retirement',1),(2,'401K','Retirement',2),(3,'401K','Retirement',3),(4,'Share Incentive Plan','Stock',2);
/*!40000 ALTER TABLE `Benefit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Company_Branch`
--

DROP TABLE IF EXISTS `Company_Branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Company_Branch` (
  `company_id` int NOT NULL AUTO_INCREMENT,
  `company_name` varchar(64) NOT NULL,
  `state_abbr` char(2) NOT NULL,
  `industry_name` varchar(64) NOT NULL,
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `company_name` (`company_name`),
  KEY `state_abbr` (`state_abbr`),
  CONSTRAINT `Company_Branch_ibfk_1` FOREIGN KEY (`state_abbr`) REFERENCES `State_Area` (`state_abbr`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Company_Branch`
--

LOCK TABLES `Company_Branch` WRITE;
/*!40000 ALTER TABLE `Company_Branch` DISABLE KEYS */;
INSERT INTO `Company_Branch` VALUES (1,'Z','MA','Technology'),(2,'Y','NY','AI');
/*!40000 ALTER TABLE `Company_Branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Interview`
--

DROP TABLE IF EXISTS `Interview`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Interview` (
  `interview_id` int NOT NULL AUTO_INCREMENT,
  `interview_type` enum('Online Assessment','Pre-Recorded Interview','Behavioral Interview','Technical Interview','Supervisor Interview') DEFAULT NULL,
  `description` varchar(500) NOT NULL,
  `job_id` int DEFAULT NULL,
  PRIMARY KEY (`interview_id`),
  KEY `job_id` (`job_id`),
  CONSTRAINT `Interview_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `Job_Position` (`job_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Interview`
--

LOCK TABLES `Interview` WRITE;
/*!40000 ALTER TABLE `Interview` DISABLE KEYS */;
INSERT INTO `Interview` VALUES (1,'Technical Interview','Coding and algorithm problems',1),(2,'Technical Interview','Complex machine learning problems',2),(3,'Behavioral Interview','Assess behavioral skills and cultural fit',2),(4,'Technical Interview','Coding and algorithm problems',3);
/*!40000 ALTER TABLE `Interview` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Job_Position`
--

DROP TABLE IF EXISTS `Job_Position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Job_Position` (
  `job_id` int NOT NULL AUTO_INCREMENT,
  `position_name` varchar(64) NOT NULL,
  `description` varchar(500) NOT NULL,
  `year` int NOT NULL,
  `salary_amount` decimal(10,2) NOT NULL,
  `company_id` int DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`job_id`),
  KEY `company_id` (`company_id`),
  KEY `username` (`username`),
  CONSTRAINT `Job_Position_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `Company_Branch` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Job_Position_ibfk_2` FOREIGN KEY (`username`) REFERENCES `Registered_User` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Job_Position_chk_1` CHECK ((`salary_amount` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Job_Position`
--

LOCK TABLES `Job_Position` WRITE;
/*!40000 ALTER TABLE `Job_Position` DISABLE KEYS */;
INSERT INTO `Job_Position` VALUES (1,'Software Engineer I','Full stack developer',2022,120000.00,1,'a'),(2,'Software Engineer I','Develop applications',2024,150000.00,2,'a'),(3,'Software Engineer I','Full stack developer',2022,120000.00,1,'b');
/*!40000 ALTER TABLE `Job_Position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Registered_User`
--

DROP TABLE IF EXISTS `Registered_User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Registered_User` (
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Registered_User`
--

LOCK TABLES `Registered_User` WRITE;
/*!40000 ALTER TABLE `Registered_User` DISABLE KEYS */;
INSERT INTO `Registered_User` VALUES ('a','123'),('b','456');
/*!40000 ALTER TABLE `Registered_User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Skill`
--

DROP TABLE IF EXISTS `Skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Skill` (
  `skill_id` int NOT NULL AUTO_INCREMENT,
  `skill_name` varchar(64) NOT NULL,
  `job_id` int DEFAULT NULL,
  PRIMARY KEY (`skill_id`),
  KEY `job_id` (`job_id`),
  CONSTRAINT `Skill_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `Job_Position` (`job_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Skill`
--

LOCK TABLES `Skill` WRITE;
/*!40000 ALTER TABLE `Skill` DISABLE KEYS */;
INSERT INTO `Skill` VALUES (1,'Python',1),(2,'Python',2),(3,'Machine Learning',2),(4,'Python',3);
/*!40000 ALTER TABLE `Skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `State_Area`
--

DROP TABLE IF EXISTS `State_Area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `State_Area` (
  `state_abbr` char(2) NOT NULL,
  `state_name` varchar(64) NOT NULL,
  `in_area` enum('Northeast','Midwest','South','West') NOT NULL,
  PRIMARY KEY (`state_abbr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `State_Area`
--

LOCK TABLES `State_Area` WRITE;
/*!40000 ALTER TABLE `State_Area` DISABLE KEYS */;
INSERT INTO `State_Area` VALUES ('AK','Alaska','West'),('AL','Alabama','South'),('AR','Arkansas','South'),('AZ','Arizona','West'),('CA','California','West'),('CO','Colorado','West'),('CT','Connecticut','Northeast'),('DE','Delaware','South'),('FL','Florida','South'),('GA','Georgia','South'),('HI','Hawaii','West'),('IA','Iowa','Midwest'),('ID','Idaho','West'),('IL','Illinois','Midwest'),('IN','Indiana','Midwest'),('KS','Kansas','Midwest'),('KY','Kentucky','South'),('LA','Louisiana','South'),('MA','Massachusetts','Northeast'),('MD','Maryland','South'),('ME','Maine','Northeast'),('MI','Michigan','Midwest'),('MN','Minnesota','Midwest'),('MO','Missouri','Midwest'),('MS','Mississippi','South'),('MT','Montana','West'),('NC','North Carolina','South'),('ND','North Dakota','Midwest'),('NE','Nebraska','Midwest'),('NH','New Hampshire','Northeast'),('NJ','New Jersey','Northeast'),('NM','New Mexico','West'),('NV','Nevada','West'),('NY','New York','Northeast'),('OH','Ohio','Midwest'),('OK','Oklahoma','South'),('OR','Oregon','West'),('PA','Pennsylvania','Northeast'),('RI','Rhode Island','Northeast'),('SC','South Carolina','South'),('SD','South Dakota','Midwest'),('TN','Tennessee','South'),('TX','Texas','South'),('UT','Utah','West'),('VA','Virginia','South'),('VT','Vermont','Northeast'),('WA','Washington','West'),('WI','Wisconsin','Midwest'),('WV','West Virginia','South'),('WY','Wyoming','West');
/*!40000 ALTER TABLE `State_Area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User_Rate_Company`
--

DROP TABLE IF EXISTS `User_Rate_Company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User_Rate_Company` (
  `username` varchar(64) DEFAULT NULL,
  `company_id` int DEFAULT NULL,
  `rate` int DEFAULT NULL,
  KEY `username` (`username`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `User_Rate_Company_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Registered_User` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `User_Rate_Company_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `Company_Branch` (`company_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `User_Rate_Company_chk_1` CHECK (((`rate` >= 0) and (`rate` <= 5)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User_Rate_Company`
--

LOCK TABLES `User_Rate_Company` WRITE;
/*!40000 ALTER TABLE `User_Rate_Company` DISABLE KEYS */;
INSERT INTO `User_Rate_Company` VALUES ('a',1,4),('a',2,5),('b',1,5),('b',2,4);
/*!40000 ALTER TABLE `User_Rate_Company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'SalaryHunter'
--

--
-- Dumping routines for database 'SalaryHunter'
--
/*!50003 DROP FUNCTION IF EXISTS `check_user_exist` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` FUNCTION `check_user_exist`(
	user VARCHAR(64) 
) RETURNS tinyint(1)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE user_exists BOOLEAN;
	SELECT EXISTS(SELECT 1 FROM Registered_User WHERE username = user) INTO user_exists;
    RETURN user_exists;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `DisplayRate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` FUNCTION `DisplayRate`(
	 p_company VARCHAR(64)
) RETURNS decimal(10,2)
    READS SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE v_company_id INT;
    DECLARE v_avg_rate DECIMAL(10, 2);
    SELECT company_id INTO v_company_id FROM Company_Branch WHERE company_name = p_company;
    SELECT AVG(rate) INTO v_avg_rate FROM User_Rate_Company WHERE company_id = v_company_id;
    RETURN v_avg_rate;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `GetCompanyID` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` FUNCTION `GetCompanyID`(
	p_company_name VARCHAR(64),
    p_state_abbr CHAR(2),
    p_industry_name VARCHAR(64)
) RETURNS int
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	DECLARE v_company_id INT;
    SELECT company_id INTO v_company_id FROM Company_Branch 
		WHERE company_name = p_company_name AND state_abbr = p_state_abbr;
	IF v_company_id IS NOT NULL THEN
		RETURN v_company_id;
	ELSE 
		INSERT INTO Company_Branch (company_name, state_abbr, industry_name)
        VALUES (p_company_name, p_state_abbr, p_industry_name);
        RETURN LAST_INSERT_ID();
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `GetJobID` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` FUNCTION `GetJobID`(
	p_position_name VARCHAR(64),
	p_description VARCHAR(500),
    p_year INT,
    p_salary_amount DECIMAL(10, 2),
	p_company_id INT,
    p_username VARCHAR(64)
) RETURNS int
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN
	INSERT INTO Job_Position (position_name, description, year, salary_amount, company_id, username)
	VALUES (p_position_name, p_description, p_year, p_salary_amount, p_company_id, p_username);
    RETURN LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `password_match` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` FUNCTION `password_match`(
    user VARCHAR(64) 
) RETURNS varchar(64) CHARSET utf8mb4
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE user_password VARCHAR(64);
    SELECT password INTO user_password FROM Registered_User WHERE username = user;
    RETURN user_password;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `benefit_delete` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `benefit_delete`(
	IN r_job_id INT,
    IN r_benefit_id INT
)
BEGIN
DELETE  FROM Benefit WHERE job_id = r_job_id AND benefit_id = r_benefit_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `benefit_update` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `benefit_update`(
	IN r_job_id INT,
    IN r_benefit_id INT,
    IN r_benefit_type ENUM('Insurance', 'Holiday', 'Stock', 'Retirement', 'Family'),
    IN r_benefit_name VARCHAR(64)
)
BEGIN
	IF EXISTS (SELECT 1 FROM Benefit WHERE job_id = r_job_id AND benefit_id = r_benefit_id) THEN
	UPDATE Benefit
    SET benefit_name = r_benefit_name, benefit_type = r_benefit_type
		WHERE job_id = r_job_id AND benefit_id = r_benefit_id;
	ELSE
    INSERT INTO Benefit (job_id, benefit_name, benefit_type)
        VALUES (r_job_id, r_benefit_name, r_benefit_type);
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetFilteredRecords` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `GetFilteredRecords`(
	IN p_in_area VARCHAR(256),
    IN p_stateAbbr VARCHAR(256),
    IN p_industryName VARCHAR(64),
    IN p_companyName VARCHAR(64),
    IN p_positionName VARCHAR(64),
    IN p_year INT,
    IN p_degree_level VARCHAR(20),
	IN p_university_name VARCHAR(64),
    IN p_year_of_work INT
)
BEGIN
    SET @sql_query = 'SELECT sa.in_area, cb.state_abbr, cb.industry_name,  cb.company_name, 
									  jp.job_id, jp.position_name,  jp.year, jp.salary_amount, jp.description, ba.degree_level, 
                                      ba.year_of_work, ba.university_name FROM Job_Position AS jp
                                      INNER JOIN Company_Branch AS cb USING (company_id)
                                      INNER JOIN State_Area AS sa USING (state_abbr) 
                                      INNER JOIN Background AS ba USING (job_id)
                                      WHERE 1=1';
	IF p_in_area IS NOT NULL AND p_in_area != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND (sa.in_area = "', REPLACE(p_in_area, ',', '" OR sa.in_area = "'), '" )');
	ELSE
		SET @sql_query = CONCAT(@sql_query, ' AND sa.in_area != ""');
    END IF;

    IF p_stateAbbr IS NOT NULL AND p_stateAbbr != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND (cb.state_abbr = "', REPLACE(p_stateAbbr, ',', '" OR cb.state_abbr = "'), '" )');
    ELSE
		SET @sql_query = CONCAT(@sql_query, ' AND cb.state_abbr != ""');
    END IF;

    IF p_industryName IS NOT NULL AND p_industryName != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND cb.industry_name = "',  p_industryName, '"');
    END IF;

    IF p_companyName IS NOT NULL AND p_companyName != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND cb.company_name = "', p_companyName, '"');
    END IF;

    IF p_positionName IS NOT NULL AND p_positionName != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND jp.position_name = "', p_positionName, '"');
    END IF;

    IF p_year IS NOT NULL THEN
        SET @sql_query = CONCAT(@sql_query, ' AND jp.year = ', p_year);
    END IF;

    IF p_degree_level IS NOT NULL AND p_degree_level != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND (ba.degree_level = "', REPLACE(p_degree_level, ',', '" OR ba.degree_level = "'), '" )');
	ELSE
    SET @sql_query = CONCAT(@sql_query, ' AND ba.degree_level != ""');
    END IF;

    IF p_university_name IS NOT NULL AND p_university_name != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND ba.university_name = "', p_university_name, '"');
    END IF;

    IF p_year_of_work IS NOT NULL THEN
        SET @sql_query = CONCAT(@sql_query, ' AND ba.year_of_work = ', p_year_of_work);
    END IF;
    
    PREPARE stmt FROM @sql_query;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InsertRate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `InsertRate`(
	IN p_username VARCHAR(64),
    IN p_company VARCHAR(64),
    IN p_rate INT
)
BEGIN
	DECLARE v_company_id INT;
    SELECT company_id INTO v_company_id FROM Company_Branch WHERE company_name = p_company;
    INSERT INTO User_Rate_Company(username, company_id, rate)
    VALUES (p_username, v_company_id, p_rate);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `InsertUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `InsertUser`(
    IN p_username VARCHAR(64),
    IN p_password VARCHAR(64)
)
BEGIN
    INSERT INTO Registered_User (username, password)
    VALUES (p_username, p_password);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_background` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `insert_background`(
	IN p_job_id INT,
    IN p_degree_level ENUM('BS', 'MS', 'PhD'),
    IN p_university_name VARCHAR(64),
    IN p_year_of_work INT,
    IN p_username VARCHAR(64)
)
BEGIN
	INSERT INTO Background (degree_level, university_name, year_of_work, username, job_id)
    VALUES(p_degree_level, p_university_name, p_year_of_work, p_username, p_job_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_benefit` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `insert_benefit`(
	IN p_job_id INT,
    IN p_benefit_type ENUM('Insurance', 'Holiday', 'Stock', 'Retirement', 'Family'),
    IN p_benefit_name VARCHAR(64)
)
BEGIN
	INSERT INTO Benefit (benefit_type, benefit_name, job_id)
    VALUES(p_benefit_type, p_benefit_name, p_job_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_interview` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `insert_interview`(
	IN r_job_id INT,
    IN r_interview_type ENUM (
		'Online Assessment', 
        'Pre-Recorded Interview', 
        'Behavioral Interview', 
        'Technical Interview', 
		'Supervisor Interview'),
	IN r_description VARCHAR(500)
)
BEGIN
	INSERT INTO Interview (interview_type, description, job_id)
    VALUES(r_interview_type, r_description, r_job_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_skill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `insert_skill`(
	IN p_job_id INT,
    IN p_skill_name VARCHAR(64)
)
BEGIN
	INSERT INTO Skill (skill_name, job_id)
    VALUES(p_skill_name, p_job_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `interview_delete` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `interview_delete`(
	IN r_job_id INT,
     IN r_interview_id INT
)
BEGIN
DELETE  FROM Interview WHERE job_id = r_job_id AND interview_id = r_interview_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `interview_update` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `interview_update`(
	IN r_job_id INT,
    IN r_interview_id INT,
    IN r_interview_type ENUM (
		'Online Assessment', 
        'Pre-Recorded Interview', 
        'Behavioral Interview', 
        'Technical Interview', 
		'Supervisor Interview'),
	IN r_description VARCHAR(500)
)
BEGIN
IF EXISTS (SELECT 1 FROM Interview WHERE job_id = r_job_id AND interview_id = r_interview_id) THEN
	UPDATE Interview
    SET interview_type = r_interview_type, description = r_description
    WHERE job_id = r_job_id AND interview_id = r_interview_id;
ELSE
    INSERT INTO Interview (job_id, interview_type, description)
    VALUES (r_job_id, r_interview_type, r_description);
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `record_benefit` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `record_benefit`(
	IN r_job_id INT 
)
BEGIN
	SELECT be.benefit_id, be.benefit_name, be.benefit_type FROM Benefit AS be
		WHERE job_id = r_job_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `record_delete` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `record_delete`(
	IN r_job_id INT
)
BEGIN
DELETE  FROM Job_Position WHERE job_id = r_job_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `record_interview` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `record_interview`(
	IN r_job_id INT 
)
BEGIN
	SELECT iv.interview_id, iv.interview_type, iv.description FROM Interview AS iv
		WHERE job_id = r_job_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `record_skill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `record_skill`(
	IN r_job_id INT 
)
BEGIN
	SELECT sk.skill_id, sk.skill_name FROM Skill AS sk
		WHERE job_id = r_job_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `record_update` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `record_update`(
	IN r_job_id INT,
	IN r_state_abbr CHAR(2),
    IN r_company_name VARCHAR(64),
    IN r_industry_name VARCHAR(64),
    IN r_position_name VARCHAR(64),
    IN r_year INT,
    IN r_salary_amount DECIMAL(10, 2),
    IN r_description VARCHAR(500),
    IN r_degree_level ENUM('BS', 'MS', 'PhD'),
    IN r_year_of_work INT, 
    IN r_university_name VARCHAR(64)
)
BEGIN
	START TRANSACTION;
	UPDATE Company_Branch 
    SET state_abbr = r_state_abbr, company_name = r_company_name, industry_name = r_industry_name
    WHERE company_id = (SELECT company_id FROM Job_Position WHERE job_id = r_job_id);
    
    UPDATE Job_Position 
    SET position_name = r_position_name, year = r_year, salary_amount = r_salary_amount, description = r_description
    WHERE job_id = r_job_id;
    
    UPDATE Background 
    SET degree_level = r_degree_level, year_of_work = r_year_of_work, university_name = r_university_name
    WHERE  job_id = r_job_id;
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SearchCompany` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `SearchCompany`()
BEGIN
	SELECT company_name FROM Company_Branch;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `skill_delete` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `skill_delete`(
	IN r_job_id INT,
    IN r_skill_id INT
)
BEGIN
DELETE  FROM Skill WHERE job_id = r_job_id AND skill_id =  r_skill_id ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `skill_update` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `skill_update`(
	IN r_job_id INT,
    IN r_skill_id INT,
    IN r_skill_name VARCHAR(64)
)
BEGIN
	IF EXISTS (SELECT 1 FROM Skill WHERE job_id = r_job_id AND skill_id =  r_skill_id ) THEN
	UPDATE Skill
    SET skill_name = r_skill_name
		WHERE job_id = r_job_id AND skill_id =  r_skill_id;
	ELSE
    INSERT INTO Skill (job_id, skill_name)
    VALUES (r_job_id, r_skill_name);
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `user_record` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`romine24`@`localhost` PROCEDURE `user_record`(
    IN r_username VARCHAR(64)
)
BEGIN
	SELECT cb.state_abbr, cb.company_name, cb.industry_name,  jp.job_id, jp.position_name,  jp.year, jp.salary_amount, 
                     jp.description, ba.degree_level, ba.year_of_work, ba.university_name  FROM Registered_User AS ru
			INNER JOIN Job_Position AS jp USING (username)
			INNER JOIN Company_Branch AS cb USING (company_id)
			INNER JOIN Background AS ba USING (job_id)
		WHERE ru.username = r_username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-05 16:15:00
