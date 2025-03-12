/*
 Navicat Premium Data Transfer

 Source Server         : tibetan_medicine
 Source Server Type    : SQLite
 Source Server Version : 3035005 (3.35.5)
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3035005 (3.35.5)
 File Encoding         : 65001

 Date: 25/02/2025 03:00:58
*/

-- ----------------------------
-- Table structure for MedicinalPlants
-- ----------------------------
USE Medicine;

CREATE TABLE `MedicinalPlants` (
                                   `id` INT PRIMARY KEY AUTO_INCREMENT,
                                   `name` TEXT NOT NULL,
                                   `category` TEXT,
                                   `efficacyScore` INT,
                                   `distribution` TEXT,
                                   `description` TEXT,
                                   `usage` TEXT
);

-- ----------------------------
-- Records of MedicinalPlants
-- ----------------------------
INSERT INTO `MedicinalPlants` (`name`, `category`, `efficacyScore`, `distribution`, `description`, `usage`) VALUES
                                                                                                                ('丹参', '根茎类', 120, '四川、云南', '活血化瘀', '用于心脑血管疾病'),
                                                                                                                ('黄芪', '根茎类', 132, '内蒙古、甘肃', '补气固表', '用于体虚乏力'),
                                                                                                                ('冬虫夏草', '补药', 9, '青藏高原', '一种珍贵的药材，具有极高的药用价值。', '增强免疫力，抗疲劳'),
                                                                                                                ('人参', '补药', 8, '中国东北', '多年生草本植物，常被称为“百草之王”。', '大补元气，复脉固脱，益气健脾'),
                                                                                                                ('黄芪', '补药', 7, '中国北部', '豆科植物，具有增强机体免疫功能的作用。', '治疗自汗，盗汗，慢性肾炎'),
                                                                                                                ('当归', '补血药', 8, '甘肃岷县', '伞形科植物，被誉为“女性之药”。', '调经止痛，润肠通便'),
                                                                                                                ('枸杞', '滋补肝肾药', 6, '宁夏', '茄科植物，常用于泡茶或煮粥。', '滋补肝肾，明目');