-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: applicationdb
-- ------------------------------------------------------
-- Server version	8.0.37

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
  `course_name` varchar(100) NOT NULL,
  `course_description` varchar(100) DEFAULT NULL,
  `course_difficulty` varchar(10) DEFAULT NULL,
  `course_hours` int DEFAULT NULL,
  PRIMARY KEY (`course_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('Advanced Statistics and Probability','This is CS2617','Hard',5),('Applications Development','This is ICS2609','Hard',5),('Computer Architecture and Organization','This is CS2619','Average',3),('Introduction to Intelligence Systems','This is CS2618','Hard',3);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `entry` int NOT NULL AUTO_INCREMENT,
  `STUDENT_USERS_email_schedule` varchar(30) NOT NULL,
  `TEACHER_USERS_email_schedule` varchar(30) NOT NULL,
  `COURSES_course_name_schedule` varchar(100) NOT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`entry`,`STUDENT_USERS_email_schedule`,`TEACHER_USERS_email_schedule`,`COURSES_course_name_schedule`),
  KEY `STUDENT_USERS_email_schedule_idx` (`STUDENT_USERS_email_schedule`),
  KEY `TEACHER_USERS_email_schedule_idx` (`TEACHER_USERS_email_schedule`),
  KEY `COURSES_course_name_schedule_idx` (`COURSES_course_name_schedule`),
  CONSTRAINT `COURSES_course_name_schedule` FOREIGN KEY (`COURSES_course_name_schedule`) REFERENCES `teacher_courses` (`COURSES_course_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `STUDENT_USERS_email_schedule` FOREIGN KEY (`STUDENT_USERS_email_schedule`) REFERENCES `student` (`USERS_student_email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `TEACHER_USERS_email_schedule` FOREIGN KEY (`TEACHER_USERS_email_schedule`) REFERENCES `teacher_courses` (`TEACHER_USERS_email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,'wilmar@gmail.com','cherry@gmail.com','Advanced Statistics and Probability','2024-05-20','verified'),(2,'meisy@gmail.com','cj@gmail.com','Introduction to Intelligence Systems','2024-05-21','verified'),(3,'kristina@gmail.com','cherry@gmail.com','Advanced Statistics and Probability','2024-05-22','verified'),(4,'angelo@gmail.com','lawrence@gmail.com','Applications Development','2024-05-23','pending'),(5,'carlo@gmail.com','rochel@gmail.com','Computer Architecture and Organization','2024-05-24','pending'),(6,'wilmar@gmail.com','cj@gmail.com','Introduction to Intelligence Systems','2024-05-20','pending');
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `USERS_student_email` varchar(30) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `birthdate` date DEFAULT NULL,
  PRIMARY KEY (`USERS_student_email`),
  KEY `fk_STUDENT_USERS_idx` (`USERS_student_email`),
  CONSTRAINT `USERS_student_email` FOREIGN KEY (`USERS_student_email`) REFERENCES `users` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('angelo@gmail.com','Raphael Angelo','Dacayo','2003-08-01'),('bea@gmail.com','Bea','Gumilong','2003-01-01'),('carlo@gmail.com','Justin Carlo','Borja','2003-01-01'),('charmaine@gmail.com','Charmaine','Villalobos','2003-05-07'),('cyrus@gmail.com','Cyrus','Canape','2003-01-01'),('erika@gmail.com','Erika','Villaroza','2003-01-01'),('jade@gmail.com','Jade','Alfonso','2003-01-01'),('jamie@gmail.com','Jamie','Jalandoni','2003-01-01'),('joshua@gmail.com','Joshua','Camit','2003-01-01'),('kristina@gmail.com','Kristina Alejandra','Juico','2003-05-01'),('kyla@gmail.com','Kyla','Hernandez','2003-01-01'),('meisy@gmail.com','Jam Meisy','Tan','2003-02-01'),('racell@gmail.com','Racell','Sincioco','2003-01-01'),('therese@gmail.com','Therese','Segui','2003-01-01'),('wilmar@gmail.com','Wilmargherix','Castaneda','2003-09-13');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `USERS_teacher_email` varchar(30) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `resume` varchar(200) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`USERS_teacher_email`),
  CONSTRAINT `USERS_teacher_email` FOREIGN KEY (`USERS_teacher_email`) REFERENCES `users` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('alexandermartin@gmail.com','Alexander','Martin','1992-07-08','resume.com','pending'),('amandajohnson@gmail.com','Amanda','Johnson','1995-07-17','resume.com','verified'),('ashleyharris@gmail.com','Ashley','Harris','1997-02-18','resume.com','pending'),('aubreybaker@gmail.com','Aubrey','Baker','1993-08-25','resume.com','pending'),('cherry@gmail.com','Cherry Rose','Estabillo','1999-01-01','resume.com','verified'),('christopherlee@gmail.com','Christopher','Lee','1995-05-23','resume.com','pending'),('cj@gmail.com','Cecil Jose','Delfinado','1999-01-02','resume.com','pending'),('davidwilliams@gmail.com','David','Williams','1983-12-30','resume.com','verified'),('emilyjones@gmail.com','Emily','Jones','1988-06-18','resume.com','verified'),('emmadavis@gmail.com','Emma','Davis','1993-11-12','resume.com','pending'),('ethanclark@gmail.com','Ethan','Clark','1986-08-05','resume.com','pending'),('hannahwhite@gmail.com','Hannah','White','1990-09-30','resume.com','pending'),('jacobthompson@gmail.com','Jacob','Thompson','1987-03-04','resume.com','pending'),('janesmith@gmail.com','Jane','Smith','1987-09-25','resume.com','verified'),('jenniferwilson@gmail.com','Jennifer','Wilson','1989-10-14','resume.com','verified'),('john@gmail.com','John','Doe','1999-01-01','resume.com','pending'),('lawrence@gmail.com','Lawrence','Decamora','1999-01-03','resume.com','verified'),('lukephillips@gmail.com','Luke','Phillips','1983-01-29','resume.com','pending'),('madisonwalker@gmail.com','Madison','Walker','1991-04-20','resume.com','pending'),('matthewtaylor@gmail.com','Matthew','Taylor','1981-02-28','resume.com','verified'),('michaelbrown@gmail.com','Michael','Brown','1990-11-07','resume.com','verified'),('nathanielcarter@gmail.com','Nathaniel','Carter','1985-12-10','resume.com','pending'),('nicholasrobinson@gmail.com','Nicholas','Robinson','1984-06-27','resume.com','pending'),('robertdavis@gmail.com','Robert','Davis','1986-08-21','resume.com','verified'),('rochel@gmail.com','Rochel Lyn','Lopez','1999-01-04','resume.com','pending'),('samanthajackson@gmail.com','Samantha','Jackson','1988-12-15','resume.com','verified'),('sarahmiller@gmail.com','Sarah','Miller','1992-04-05','resume.com','verified'),('williammartinez@gmail.com','William','Martinez','1980-05-09','resume.com','verified'),('willowwilkins@gmail.com','Willow','Wilkins','1989-10-11','resume.com','verified'),('zoeharrison@gmail.com','Zoe','Harrison','1997-06-03','resume.com','verified');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_courses`
--

DROP TABLE IF EXISTS `teacher_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_courses` (
  `COURSES_course_name` varchar(100) NOT NULL,
  `TEACHER_USERS_email` varchar(30) NOT NULL,
  KEY `fk_TEACHER_COURSES_TEACHER1_idx` (`TEACHER_USERS_email`),
  KEY `fk_TEACHER_COURSES_COURSES1_idx` (`COURSES_course_name`),
  CONSTRAINT `COURSES_course_name` FOREIGN KEY (`COURSES_course_name`) REFERENCES `courses` (`course_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `TEACHER_USERS_email` FOREIGN KEY (`TEACHER_USERS_email`) REFERENCES `teacher` (`USERS_teacher_email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_courses`
--

LOCK TABLES `teacher_courses` WRITE;
/*!40000 ALTER TABLE `teacher_courses` DISABLE KEYS */;
INSERT INTO `teacher_courses` VALUES ('Advanced Statistics and Probability','cherry@gmail.com'),('Applications Development','lawrence@gmail.com'),('Computer Architecture and Organization','rochel@gmail.com'),('Introduction to Intelligence Systems','cj@gmail.com'),('Introduction to Intelligence Systems','cherry@gmail.com'),('Applications Development','rochel@gmail.com'),('Advanced Statistics and Probability','cj@gmail.com'),('Computer Architecture and Organization','lawrence@gmail.com');
/*!40000 ALTER TABLE `teacher_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `email` varchar(30) NOT NULL,
  `password` varchar(200) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('alexandermartin@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('amandajohnson@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('angelo@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('ashleyharris@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('aubreybaker@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('bea@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('carlo@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('charmaine@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('cherry@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('christopherlee@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('cj@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('cyrus@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('davidwilliams@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('emilyjones@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('emmadavis@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('erika@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('ethanclark@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('hannahwhite@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('jacobthompson@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('jade@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('jam@gmail.com','O0vFy0IMaqE1inUyJ5cgTQ==','admin'),('jamie@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('janesmith@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('jenniferwilson@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('john@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('joshua@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('justin@gmail.com','O0vFy0IMaqE1inUyJ5cgTQ==','admin'),('kristina@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('kyla@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('lawrence@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('lejan@gmail.com','O0vFy0IMaqE1inUyJ5cgTQ==','admin'),('lukephillips@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('madisonwalker@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('matthewtaylor@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('meisy@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('michaelbrown@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('nathanielcarter@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('nicholasrobinson@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('racell@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('raphael@gmail.com','O0vFy0IMaqE1inUyJ5cgTQ==','admin'),('robertdavis@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('rochel@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('samanthajackson@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('sarahmiller@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('therese@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('williammartinez@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('willowwilkins@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher'),('wilmar@gmail.com','9IDomW+XSaK4pi7CwBDk5Q==','student'),('wilmargherix@gmail.com','O0vFy0IMaqE1inUyJ5cgTQ==','admin'),('zoeharrison@gmail.com','VGlIzhE4TOKkwpgM4WaTMA==','teacher');
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

-- Dump completed on 2024-05-09 20:30:30
