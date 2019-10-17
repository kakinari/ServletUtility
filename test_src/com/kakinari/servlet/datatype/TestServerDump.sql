-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: testserver    Database: testSchema
-- ------------------------------------------------------
-- Server version	5.7.22

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
-- Temporary view structure for view `initparams`
--

DROP TABLE IF EXISTS `initparams`;
/*!50001 DROP VIEW IF EXISTS `initparams`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `initparams` AS SELECT 
 1 AS `servlet_name`,
 1 AS `params`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_name` varchar(15) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `security_roles`
--

DROP TABLE IF EXISTS `security_roles`;
/*!50001 DROP VIEW IF EXISTS `security_roles`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `security_roles` AS SELECT 
 1 AS `security`,
 1 AS `security_role`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `servlet_contents`
--

DROP TABLE IF EXISTS `servlet_contents`;
/*!50001 DROP VIEW IF EXISTS `servlet_contents`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `servlet_contents` AS SELECT 
 1 AS `ServletName`,
 1 AS `ServletClass`,
 1 AS `ServletMap`,
 1 AS `ServletParams`,
 1 AS `ServletAuth`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `servlet_info`
--

DROP TABLE IF EXISTS `servlet_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servlet_info` (
  `servlet_name` varchar(25) COLLATE utf8mb4_bin NOT NULL,
  `class_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `security_group` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`servlet_name`),
  KEY `security_idx` (`security_group`),
  CONSTRAINT `security` FOREIGN KEY (`security_group`) REFERENCES `servlet_security` (`security_group`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `servlet_map`
--

DROP TABLE IF EXISTS `servlet_map`;
/*!50001 DROP VIEW IF EXISTS `servlet_map`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `servlet_map` AS SELECT 
 1 AS `servlet_name`,
 1 AS `map_list`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `servlet_mapping`
--

DROP TABLE IF EXISTS `servlet_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servlet_mapping` (
  `url` varchar(768) COLLATE utf8mb4_bin NOT NULL,
  `servlet_name` varchar(25) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`url`),
  KEY `name` (`servlet_name`),
  CONSTRAINT `servlet_name` FOREIGN KEY (`servlet_name`) REFERENCES `servlet_info` (`servlet_name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servlet_params`
--

DROP TABLE IF EXISTS `servlet_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servlet_params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `servlet_name` varchar(25) COLLATE utf8mb4_bin NOT NULL,
  `param_key` varchar(15) COLLATE utf8mb4_bin NOT NULL,
  `param_value` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `KEYSET` (`servlet_name`,`param_key`),
  CONSTRAINT `NAME` FOREIGN KEY (`servlet_name`) REFERENCES `servlet_info` (`servlet_name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servlet_security`
--

DROP TABLE IF EXISTS `servlet_security`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servlet_security` (
  `security_group` varchar(25) COLLATE utf8mb4_bin NOT NULL,
  `method` enum('ALL','GET','POST','HEAD','PUT','DELETE','OPTIONS','CONNECT') COLLATE utf8mb4_bin NOT NULL DEFAULT 'ALL',
  `role_allowed` varchar(15) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`security_group`,`method`,`role_allowed`),
  KEY `Role_idx` (`role_allowed`),
  CONSTRAINT `Role` FOREIGN KEY (`role_allowed`) REFERENCES `roles` (`role_name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Servlet` FOREIGN KEY (`security_group`) REFERENCES `servlet_info` (`servlet_name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_name` varchar(15) COLLATE utf8mb4_bin NOT NULL,
  `role_name` varchar(15) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`user_name`,`role_name`),
  KEY `ROLES_idx` (`role_name`),
  CONSTRAINT `ROLES` FOREIGN KEY (`role_name`) REFERENCES `roles` (`role_name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `USERS` FOREIGN KEY (`user_name`) REFERENCES `users` (`user_name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Tomcat用のログインセキュリティーテーブル';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'testSchema'
--

--
-- Final view structure for view `initparams`
--

/*!50001 DROP VIEW IF EXISTS `initparams`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `initparams` AS select `servlet_params`.`servlet_name` AS `servlet_name`,concat('{',group_concat(`servlet_params`.`param_key`,'=',`servlet_params`.`param_value` separator ','),'}') AS `params` from `servlet_params` group by `servlet_params`.`servlet_name` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `security_roles`
--

/*!50001 DROP VIEW IF EXISTS `security_roles`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `security_roles` AS select `rolelist`.`security_group` AS `security`,concat('{',group_concat(`rolelist`.`roles` order by `rolelist`.`roles` ASC separator ','),'}') AS `security_role` from (select `testSchema`.`servlet_security`.`security_group` AS `security_group`,concat(`testSchema`.`servlet_security`.`method`,'=[',group_concat(`testSchema`.`servlet_security`.`role_allowed` separator ','),']') AS `roles` from `testSchema`.`servlet_security` group by `testSchema`.`servlet_security`.`security_group`,`testSchema`.`servlet_security`.`method`) `rolelist` group by `rolelist`.`security_group` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `servlet_contents`
--

/*!50001 DROP VIEW IF EXISTS `servlet_contents`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `servlet_contents` AS select `testSchema`.`servlet_info`.`servlet_name` AS `ServletName`,`testSchema`.`servlet_info`.`class_name` AS `ServletClass`,`servlet_map`.`map_list` AS `ServletMap`,`initparams`.`params` AS `ServletParams`,`security_roles`.`security_role` AS `ServletAuth` from (((`testSchema`.`servlet_info` left join `testSchema`.`servlet_map` on((`testSchema`.`servlet_info`.`servlet_name` = `servlet_map`.`servlet_name`))) left join `testSchema`.`initparams` on((`testSchema`.`servlet_info`.`servlet_name` = `initparams`.`servlet_name`))) left join `testSchema`.`security_roles` on((`testSchema`.`servlet_info`.`security_group` = `security_roles`.`security`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `servlet_map`
--

/*!50001 DROP VIEW IF EXISTS `servlet_map`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `servlet_map` AS select `servlet_mapping`.`servlet_name` AS `servlet_name`,concat('[',group_concat(`servlet_mapping`.`url` separator ','),']') AS `map_list` from `servlet_mapping` group by `servlet_mapping`.`servlet_name` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-29 17:35:52
