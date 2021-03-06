﻿--
-- Script was generated by Devart dbForge Studio for MySQL, Version 7.2.53.0
-- Product home page: http://www.devart.com/dbforge/mysql/studio
-- Script date 28.12.2017 г. 11:59:52
-- Server version: 5.7.17-log
-- Client version: 4.1
--


-- 
-- Disable foreign keys
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Set SQL mode
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- Set character set the client will use to send SQL statements to the server
--
SET NAMES 'utf8';

-- 
-- Set default database
--
USE garage;

--
-- Definition for table garagestatus
--
DROP TABLE IF EXISTS garagestatus;
CREATE TABLE IF NOT EXISTS garagestatus (
  LEVEL_NAME VARCHAR(255) NOT NULL,
  CAPACITY INT(11) DEFAULT NULL,
  FREE INT(11) DEFAULT NULL,
  USED INT(11) DEFAULT NULL,
  PRIMARY KEY (LEVEL_NAME)
)
ENGINE = MYISAM
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Definition for table t_operation
--
DROP TABLE IF EXISTS t_operation;
CREATE TABLE IF NOT EXISTS t_operation (
  ID BIGINT(20) NOT NULL AUTO_INCREMENT,
  TIME_START DATETIME DEFAULT NULL,
  TIME_EXIT DATETIME DEFAULT NULL,
  PARKING_LOT_ID INT(11) DEFAULT NULL,
  VEHICLE_ID INT(11) DEFAULT NULL,
  PRIMARY KEY (ID),
  INDEX FKdj5la17ljo82yh7oaouhr2tqe (PARKING_LOT_ID),
  INDEX FKi2egrhqdk4yax2lc607c6k7oh (VEHICLE_ID)
)
ENGINE = MYISAM
AUTO_INCREMENT = 12
AVG_ROW_LENGTH = 27
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Definition for table t_parking_level
--
DROP TABLE IF EXISTS t_parking_level;
CREATE TABLE IF NOT EXISTS t_parking_level (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  CAPACITY INT(11) NOT NULL,
  LEVEL_NAME VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID),
  UNIQUE INDEX UK_egnmbgwpnclcj7x7rhp7vfmb1 (LEVEL_NAME)
)
ENGINE = MYISAM
AUTO_INCREMENT = 5
AVG_ROW_LENGTH = 20
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Definition for table t_parking_lot
--
DROP TABLE IF EXISTS t_parking_lot;
CREATE TABLE IF NOT EXISTS t_parking_lot (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  IS_FREE TINYINT(1) NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  PARKING_LEVEL_ID INT(11) DEFAULT NULL,
  PRIMARY KEY (ID),
  INDEX FKmjt5hep9tq579lqx4673iomdw (PARKING_LEVEL_ID),
  UNIQUE INDEX UK_a7jwm5iul0js1kosunoqnwhc6 (NAME)
)
ENGINE = MYISAM
AUTO_INCREMENT = 571
AVG_ROW_LENGTH = 20
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Definition for table t_vehicle
--
DROP TABLE IF EXISTS t_vehicle;
CREATE TABLE IF NOT EXISTS t_vehicle (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  PLATE_NUMBER VARCHAR(255) NOT NULL,
  VEHICLE_TYPE_ID INT(11) DEFAULT NULL,
  PRIMARY KEY (ID),
  INDEX FKcieg3ukfgxpd807ii1nw0lt49 (VEHICLE_TYPE_ID),
  UNIQUE INDEX UK_ov10hyplvkpr9jj2o0fjdjbcg (PLATE_NUMBER)
)
ENGINE = MYISAM
AUTO_INCREMENT = 10
AVG_ROW_LENGTH = 24
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Definition for table t_vehicle_type
--
DROP TABLE IF EXISTS t_vehicle_type;
CREATE TABLE IF NOT EXISTS t_vehicle_type (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID),
  UNIQUE INDEX UK_8l18wy237yghuohoie2a765o (NAME)
)
ENGINE = MYISAM
AUTO_INCREMENT = 5
AVG_ROW_LENGTH = 20
CHARACTER SET utf8
COLLATE utf8_general_ci;

-- 
-- Restore previous SQL mode
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Enable foreign keys
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;