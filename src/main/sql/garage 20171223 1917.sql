﻿--
-- Script was generated by Devart dbForge Studio for MySQL, Version 7.2.53.0
-- Product home page: http://www.devart.com/dbforge/mysql/studio
-- Script date 23.12.2017 г. 19:17:15
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
AUTO_INCREMENT = 7
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
AUTO_INCREMENT = 3
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
AUTO_INCREMENT = 101
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
AUTO_INCREMENT = 3
AVG_ROW_LENGTH = 22
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
AUTO_INCREMENT = 3
AVG_ROW_LENGTH = 20
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Definition for view v_garage_status
--
DROP VIEW IF EXISTS v_garage_status CASCADE;
CREATE OR REPLACE 
	DEFINER = 'root'@'localhost'
VIEW v_garage_status
AS
	select `t_parking_lot`.`PARKING_LEVEL_ID` AS `PARKING_LEVEL_ID`,sum(`t_parking_lot`.`IS_FREE`) AS `free`,(select count(0) from `t_parking_lot` where (`t_parking_lot`.`IS_FREE` = 0)) AS `used` from `t_parking_lot` where (`t_parking_lot`.`IS_FREE` = 1) group by `t_parking_lot`.`PARKING_LEVEL_ID`,`used`;

-- 
-- Dumping data for table t_operation
--
INSERT INTO t_operation VALUES
(1, '2017-12-23 15:28:29', '2017-12-23 15:31:24', 1, 1),
(2, '2017-12-23 16:28:37', NULL, 1, 2),
(3, '2017-12-23 16:49:51', NULL, 2, 2),
(4, '2017-12-23 17:47:43', NULL, 3, 2),
(5, '2017-12-23 17:47:51', NULL, 4, 2),
(6, '2017-12-23 17:48:08', NULL, 5, 2);

-- 
-- Dumping data for table t_parking_level
--
INSERT INTO t_parking_level VALUES
(1, 100, 'Level1');

-- 
-- Dumping data for table t_parking_lot
--
INSERT INTO t_parking_lot VALUES
(1, 0, '1001', 1),
(2, 0, '1002', 1),
(3, 0, '1003', 1),
(4, 0, '1004', 1),
(5, 0, '1005', 1),
(6, 1, '1006', 1),
(7, 1, '1007', 1),
(8, 1, '1008', 1),
(9, 1, '1009', 1),
(10, 1, '1010', 1),
(11, 1, '1011', 1),
(12, 1, '1012', 1),
(13, 1, '1013', 1),
(14, 1, '1014', 1),
(15, 1, '1015', 1),
(16, 1, '1016', 1),
(17, 1, '1017', 1),
(18, 1, '1018', 1),
(19, 1, '1019', 1),
(20, 1, '1020', 1),
(21, 1, '1021', 1),
(22, 1, '1022', 1),
(23, 1, '1023', 1),
(24, 1, '1024', 1),
(25, 1, '1025', 1),
(26, 1, '1026', 1),
(27, 1, '1027', 1),
(28, 1, '1028', 1),
(29, 1, '1029', 1),
(30, 1, '1030', 1),
(31, 1, '1031', 1),
(32, 1, '1032', 1),
(33, 1, '1033', 1),
(34, 1, '1034', 1),
(35, 1, '1035', 1),
(36, 1, '1036', 1),
(37, 1, '1037', 1),
(38, 1, '1038', 1),
(39, 1, '1039', 1),
(40, 1, '1040', 1),
(41, 1, '1041', 1),
(42, 1, '1042', 1),
(43, 1, '1043', 1),
(44, 1, '1044', 1),
(45, 1, '1045', 1),
(46, 1, '1046', 1),
(47, 1, '1047', 1),
(48, 1, '1048', 1),
(49, 1, '1049', 1),
(50, 1, '1050', 1),
(51, 1, '1051', 1),
(52, 1, '1052', 1),
(53, 1, '1053', 1),
(54, 1, '1054', 1),
(55, 1, '1055', 1),
(56, 1, '1056', 1),
(57, 1, '1057', 1),
(58, 1, '1058', 1),
(59, 1, '1059', 1),
(60, 1, '1060', 1),
(61, 1, '1061', 1),
(62, 1, '1062', 1),
(63, 1, '1063', 1),
(64, 1, '1064', 1),
(65, 1, '1065', 1),
(66, 1, '1066', 1),
(67, 1, '1067', 1),
(68, 1, '1068', 1),
(69, 1, '1069', 1),
(70, 1, '1070', 1),
(71, 1, '1071', 1),
(72, 1, '1072', 1),
(73, 1, '1073', 1),
(74, 1, '1074', 1),
(75, 1, '1075', 1),
(76, 1, '1076', 1),
(77, 1, '1077', 1),
(78, 1, '1078', 1),
(79, 1, '1079', 1),
(80, 1, '1080', 1),
(81, 1, '1081', 1),
(82, 1, '1082', 1),
(83, 1, '1083', 1),
(84, 1, '1084', 1),
(85, 1, '1085', 1),
(86, 1, '1086', 1),
(87, 1, '1087', 1),
(88, 1, '1088', 1),
(89, 1, '1089', 1),
(90, 1, '1090', 1),
(91, 1, '1091', 1),
(92, 1, '1092', 1),
(93, 1, '1093', 1),
(94, 1, '1094', 1),
(95, 1, '1095', 1),
(96, 1, '1096', 1),
(97, 1, '1097', 1),
(98, 1, '1098', 1),
(99, 1, '1099', 1),
(100, 1, '1100', 1);

-- 
-- Dumping data for table t_vehicle
--
INSERT INTO t_vehicle VALUES
(1, 'PB1234MP', 1),
(2, '1', 1);

-- 
-- Dumping data for table t_vehicle_type
--
INSERT INTO t_vehicle_type VALUES
(1, 'Car'),
(2, 'MotorBike');

-- 
-- Restore previous SQL mode
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Enable foreign keys
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;