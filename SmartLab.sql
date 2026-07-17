-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: lab
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '管理员ID（主键）',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '管理员姓名',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('1001','小段','123456','19834954831','11'),('1002','管理员','123456','11111','1111');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signin`
--

DROP TABLE IF EXISTS `signin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `signin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '签到记录ID',
  `student_id` varchar(20) NOT NULL COMMENT '学生学号',
  `name` varchar(50) DEFAULT NULL COMMENT '学生姓名',
  `sign_in_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '签到时间',
  `status` varchar(10) DEFAULT '正常' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='签到记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signin`
--

LOCK TABLES `signin` WRITE;
/*!40000 ALTER TABLE `signin` DISABLE KEYS */;
/*!40000 ALTER TABLE `signin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `student_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学生学号（主键）',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学生姓名',
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '性别',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `grade` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '年级',
  `college_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学院名',
  `department_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '系名',
  `class_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '班级名',
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('2021004','赵六','女','admin888','2021级','数学与统计学院','应用数学','应数2101班'),('2022003','王五','男','qwerty','2022级','电子信息工程学院','通信工程','通信2201班'),('2023001','张三','男','123456','2023级','计算机科学学院','软件工程','软工2301班'),('2024002','李四','女','abcdef','2024级','计算机科学学院','计算机科学','计科2402班'),('2413040401','哈哈','男','123456','2024级','软件学院','软件工程','软工2404班'),('2413040403','段璎芮','女','123456','2024级','软件学院','软件工程','软工2404班');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-09 14:46:23
