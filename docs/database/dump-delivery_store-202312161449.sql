-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: localhost    Database: delivery_store
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `contracts`
--

DROP TABLE IF EXISTS `contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contracts` (
  `contract_id` bigint NOT NULL AUTO_INCREMENT COMMENT '계약서 ID',
  `store_id` bigint NOT NULL COMMENT '상점 ID',
  `start_date` datetime NOT NULL COMMENT '계약 시작일',
  `end_date` datetime NOT NULL COMMENT '계약 종료일',
  `contract_type` varchar(45) NOT NULL COMMENT '계약서 유형 (''BASIC'', ''PROMOTION'')',
  `fee_rate` decimal(10,2) NOT NULL COMMENT '수수료율',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '소프트 삭제',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `created_by` varchar(45) NOT NULL COMMENT '생성자',
  `updated_by` varchar(45) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracts`
--

LOCK TABLES `contracts` WRITE;
/*!40000 ALTER TABLE `contracts` DISABLE KEYS */;
INSERT INTO `contracts` VALUES (1,1,'2023-01-01 00:00:00','2023-12-31 00:00:00','BASIC',3.00,_binary '\0','2023-11-14 22:57:45','2023-11-14 22:57:45','contract-system','contract-system'),(2,1,'2023-10-01 00:00:00','2023-11-01 00:00:00','PROMOTION',1.00,_binary '\0','2023-11-14 22:57:48','2023-11-14 22:57:48','contract-system','contract-system');
/*!40000 ALTER TABLE `contracts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_messages`
--

DROP TABLE IF EXISTS `payment_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_messages` (
  `payment_message_id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(1024) NOT NULL,
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '소프트 삭제',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(45) NOT NULL,
  `updated_by` varchar(45) NOT NULL,
  PRIMARY KEY (`payment_message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_messages`
--

LOCK TABLES `payment_messages` WRITE;
/*!40000 ALTER TABLE `payment_messages` DISABLE KEYS */;
INSERT INTO `payment_messages` VALUES (41,'{\n  \"settlementId\": 1,\n  \"storeId\": 1,\n  \"orderId\": 1,\n  \"paymentId\": 1,\n  \"orderAmount\": 10000,\n  \"deliveryFee\": 3000,\n  \"discountAmount\": 2000,\n  \"promotionFee\": 0,\n  \"payAmount\": 11000\n}',_binary '\0','2023-11-15 14:57:33','2023-11-15 14:57:33','delivery-settlement-admin','delivery-settlement-admin');
/*!40000 ALTER TABLE `payment_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_tokens`
--

DROP TABLE IF EXISTS `refresh_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_tokens` (
  `refresh_token_id` bigint NOT NULL AUTO_INCREMENT COMMENT '리프레시 토큰 ID',
  `email` varchar(255) NOT NULL COMMENT '이메일',
  `refresh_token` varchar(255) NOT NULL COMMENT '리프레시 토큰',
  PRIMARY KEY (`refresh_token_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_tokens`
--

LOCK TABLES `refresh_tokens` WRITE;
/*!40000 ALTER TABLE `refresh_tokens` DISABLE KEYS */;
INSERT INTO `refresh_tokens` VALUES (3,'test@test.com','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNzAyNzA1MDg2LCJleHAiOjE3MDI3OTE0ODZ9.Y20e-waCmEDcNlndkwQm3KYcB6YklRn9YfJw0wbNkvQ');
/*!40000 ALTER TABLE `refresh_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_order_requests`
--

DROP TABLE IF EXISTS `store_order_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_order_requests` (
  `store_order_request_id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `order_shorten_id` varchar(45) NOT NULL,
  `order_uuid` varchar(255) NOT NULL,
  `store_id` bigint NOT NULL,
  `customer_id` bigint NOT NULL,
  `store_order_status` varchar(45) NOT NULL COMMENT '상점의 주문 처리 상태',
  `cooking_minutes` int NOT NULL DEFAULT '-1' COMMENT '조리 시간',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(45) NOT NULL,
  `updated_by` varchar(45) NOT NULL,
  `menu_quantity` int NOT NULL DEFAULT '0' COMMENT '메뉴 개수',
  PRIMARY KEY (`store_order_request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_order_requests`
--

LOCK TABLES `store_order_requests` WRITE;
/*!40000 ALTER TABLE `store_order_requests` DISABLE KEYS */;
INSERT INTO `store_order_requests` VALUES (2,7,'XDS12D','ADFKADFASDF',1,3,'COOKING',5,_binary '\0','2023-11-19 13:07:56','2023-11-19 13:07:56','${server.role-name','${server.role-name',0);
/*!40000 ALTER TABLE `store_order_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_settlement_histories`
--

DROP TABLE IF EXISTS `store_settlement_histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_settlement_histories` (
  `store_settlement_history_id` bigint NOT NULL COMMENT '상점 정산 이력 ID',
  `store_id` bigint NOT NULL COMMENT '상점 ID',
  `order_id` varchar(45) NOT NULL COMMENT '주문 ID',
  `order_amount` varchar(45) NOT NULL COMMENT '주문 금액',
  `discount_amount` decimal(10,2) NOT NULL COMMENT '할인 금액',
  `delivery_fee` decimal(10,2) NOT NULL COMMENT '배달 수수료',
  `service_fee_policy_id` bigint NOT NULL COMMENT '배달 서비스 수수료 정책 ID',
  `service_fee` decimal(10,2) NOT NULL COMMENT '배달 서비스 수수료',
  `promotion_policy_id` bigint NOT NULL COMMENT '배달 서비스 프로모션 정책 ID',
  `promotion_fee` decimal(10,2) NOT NULL COMMENT '배달 서비스 프로모션 수수료',
  `payment_amount` decimal(10,2) NOT NULL COMMENT '배달수수료',
  `settlement_amount` decimal(10,2) NOT NULL COMMENT '정산 금액',
  `settlement_status` varchar(45) NOT NULL COMMENT '정산의 상태 (대기, 보류, 지급)\n',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '소프트 삭제',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `created_by` varchar(45) NOT NULL COMMENT '생성자',
  `updated_by` varchar(45) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`store_settlement_history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_settlement_histories`
--

LOCK TABLES `store_settlement_histories` WRITE;
/*!40000 ALTER TABLE `store_settlement_histories` DISABLE KEYS */;
/*!40000 ALTER TABLE `store_settlement_histories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_settlements`
--

DROP TABLE IF EXISTS `store_settlements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_settlements` (
  `store_settlement_id` bigint NOT NULL AUTO_INCREMENT COMMENT '상점 정산 ID',
  `store_id` bigint NOT NULL COMMENT '상점 ID',
  `order_id` bigint NOT NULL COMMENT '주문 ID',
  `payment_id` bigint NOT NULL,
  `order_amount` decimal(10,0) NOT NULL COMMENT '주문 금액',
  `discount_amount` decimal(10,0) NOT NULL COMMENT '할인 금액',
  `delivery_fee` decimal(10,0) NOT NULL COMMENT '배달료',
  `promotion_fee` decimal(10,0) NOT NULL COMMENT '프로모션 수수료',
  `fee_amount` decimal(10,2) NOT NULL COMMENT '서비스 수수료',
  `fee_rate` decimal(10,2) NOT NULL,
  `payment_amount` decimal(10,2) NOT NULL COMMENT '총 결제 금액',
  `settlement_amount` decimal(10,0) NOT NULL COMMENT '정산 금액',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '소프트 삭제',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `created_by` varchar(45) NOT NULL COMMENT '생성자',
  `updated_by` varchar(45) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`store_settlement_id`),
  KEY `idx_01_store_id` (`store_id`),
  KEY `idx_02_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_settlements`
--

LOCK TABLES `store_settlements` WRITE;
/*!40000 ALTER TABLE `store_settlements` DISABLE KEYS */;
INSERT INTO `store_settlements` VALUES (1,7,1,1,10000,2000,3000,0,150.00,3.00,5000.00,4850,_binary '\0','2023-11-15 14:57:34','2023-11-15 14:57:34','delivery-settlement-admin','delivery-settlement-admin');
/*!40000 ALTER TABLE `store_settlements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_wallet_histories`
--

DROP TABLE IF EXISTS `store_wallet_histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_wallet_histories` (
  `store_wallet_history_id` bigint NOT NULL AUTO_INCREMENT COMMENT '상점 지갑 이력 ID',
  `store_wallet_id` bigint unsigned NOT NULL COMMENT '상점 지갑 ID',
  `wallet_job_type` varchar(45) NOT NULL COMMENT '상점 지갑의 이력 유형 - 입금, 출금',
  `amount` decimal(10,2) NOT NULL COMMENT '입출금 금액',
  `balance` decimal(10,2) NOT NULL COMMENT '작업 후 금액',
  `is_deleted` bit(1) NOT NULL COMMENT '소프트 삭제',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `created_by` varchar(45) NOT NULL COMMENT '생성자',
  `updated_by` varchar(45) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`store_wallet_history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_wallet_histories`
--

LOCK TABLES `store_wallet_histories` WRITE;
/*!40000 ALTER TABLE `store_wallet_histories` DISABLE KEYS */;
/*!40000 ALTER TABLE `store_wallet_histories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_wallets`
--

DROP TABLE IF EXISTS `store_wallets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_wallets` (
  `store_wallet_id` bigint NOT NULL AUTO_INCREMENT COMMENT '상점 지갑 ID',
  `store_id` bigint NOT NULL COMMENT '상점 ID',
  `balance` decimal(10,0) NOT NULL COMMENT '예금, 예치금',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '소프트 삭제',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `created_by` varchar(45) NOT NULL COMMENT '생성자',
  `updated_by` varchar(45) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`store_wallet_id`),
  KEY `idx_01_store_id` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_wallets`
--

LOCK TABLES `store_wallets` WRITE;
/*!40000 ALTER TABLE `store_wallets` DISABLE KEYS */;
INSERT INTO `store_wallets` VALUES (2,7,4850,_binary '\0','2023-12-16 03:29:37','2023-12-16 05:39:47','delivery-store-admin','test@test.com');
/*!40000 ALTER TABLE `store_wallets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stores`
--

DROP TABLE IF EXISTS `stores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stores` (
  `store_id` bigint NOT NULL AUTO_INCREMENT COMMENT '상점 ID',
  `store_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `store_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상점 주소',
  `business_number` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '사업자 번호',
  `manager_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `manager_phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `store_phone` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(55) NOT NULL COMMENT '상점 계정 아이디',
  `name` varchar(100) NOT NULL,
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상점 대표 폰번호',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '비밀번호',
  `bank_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `bank_account` varchar(45) NOT NULL COMMENT '은행 계좌',
  `bank_account_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '은행명',
  `delivery_time` varchar(45) NOT NULL DEFAULT '30분 ~ 40분' COMMENT '배달 평균 시간',
  `main_image_url` varchar(255) NOT NULL COMMENT '상점 대표 이미지',
  `description` varchar(1024) NOT NULL COMMENT '상점 상세 설명',
  `delivery_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '기본 배달 수수료',
  `review_grade` tinyint(1) NOT NULL DEFAULT '5' COMMENT '평점',
  `minimum_order_price` decimal(10,2) NOT NULL DEFAULT '8000.00',
  `store_status` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상점상태',
  `is_deleted` varchar(45) NOT NULL DEFAULT '0' COMMENT '소프트 삭제',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
  `created_by` varchar(45) NOT NULL COMMENT '생성자',
  `updated_by` varchar(45) NOT NULL COMMENT '수정자',
  PRIMARY KEY (`store_id`),
  KEY `idx_01_store_account_id` (`email`),
  KEY `idx_02_business_number` (`business_number`),
  KEY `idx_03_phone` (`phone`),
  KEY `idx_04_bank_account` (`bank_account`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stores`
--

LOCK TABLES `stores` WRITE;
/*!40000 ALTER TABLE `stores` DISABLE KEYS */;
INSERT INTO `stores` VALUES (7,'닭치고 순살','경기도 남양주시 평내동','111111-22222','김순살','01022223333','01022223333','test@test.com','김사장','01022223333','$2a$10$G2NgyQPYn.hZ3sFLNkmqkOh3b9.eSg/nE1Jt16Xizb88U/4E87KJ2','SHINHAN','1111-22-3333','김은행','40분','http://','치킨 순살이 바사삭',3000.00,5,12000.00,'INIT','0','2023-12-12 14:01:06','2023-12-12 14:01:06','delivery-store-admin','delivery-store-admin');
/*!40000 ALTER TABLE `stores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'delivery_store'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-16 14:49:27
