SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `course_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `course_no` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程信息表';

INSERT INTO `courses` VALUES ('1', '1', '2025-03-23 10:59:59.497000', '线性代数', '1', '2025-03-23 10:59:59.498000', '线性代数', 'c001');
INSERT INTO `courses` VALUES ('2', '1', '2025-03-23 11:00:31.283000', '高等数学', '1', '2025-03-23 11:00:31.284000', '高等数学', 'c002');
INSERT INTO `courses` VALUES ('3', '1', '2025-03-23 11:00:50.909000', '大学英语', '1', '2025-03-23 11:00:50.910000', '大学英语', 'c003');
INSERT INTO `courses` VALUES ('4', '1', '2025-03-23 11:01:17.114000', 'C语言程序员设计', '1', '2025-03-23 11:01:17.115000', 'C语言程序员设计', 'c004');
INSERT INTO `courses` VALUES ('5', '1', '2025-03-23 11:01:37.747000', '计算机组成原理', '1', '2025-03-23 11:01:37.748000', '计算机组成原理', 'c005');
INSERT INTO `courses` VALUES ('6', '1', '2025-03-23 11:01:58.498000', '计算机网络原理', '1', '2025-03-23 11:01:58.498000', '计算机网络原理', 'c006');
INSERT INTO `courses` VALUES ('7', '1', '2025-03-23 11:02:11.274000', '编译原理', '1', '2025-03-23 11:02:11.275000', '编译原理', 'c007');
INSERT INTO `courses` VALUES ('8', '1', '2025-03-23 11:02:34.564000', '大学物理', '1', '2025-03-23 11:02:34.564000', '大学物理', 'c008');
INSERT INTO `courses` VALUES ('9', '1', '2025-03-23 11:03:01.895000', 'JAVA程序员设计', '1', '2025-03-23 11:03:01.895000', 'JAVA程序员设计', 'c009');
INSERT INTO `courses` VALUES ('10', '1', '2025-03-23 11:04:31.361000', '数字逻辑设计', '1', '2025-03-23 11:04:31.361000', '数字逻辑设计', 'c010');
INSERT INTO `courses` VALUES ('11', '1', '2025-03-23 11:04:53.763000', '数据结构与算法', '1', '2025-03-23 11:04:53.763000', '数据结构与算法', 'c011');
INSERT INTO `courses` VALUES ('12', '1', '2025-03-23 11:05:08.130000', '软件工程', '1', '2025-03-23 11:05:08.130000', '软件工程', 'c012');
INSERT INTO `courses` VALUES ('13', '1', '2025-03-23 11:05:23.760000', '数据库系统', '1', '2025-03-23 11:05:23.760000', '数据库系统', 'c013');
INSERT INTO `courses` VALUES ('14', '1', '2025-03-23 11:06:00.019000', '操作系统', '1', '2025-03-23 11:06:00.019000', '操作系统', 'c014');


-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `clazz` int DEFAULT NULL,
  `code` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `grade` int DEFAULT NULL,
  `name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='班级信息表';

INSERT INTO `classes` VALUES ('1', '1', '2025-3-23 17:44:50.000000', '21级1班', '1', '2025-3-23 17:44:53.000000', '1', '202101', '2021', '21级1班');
INSERT INTO `classes` VALUES ('2', '1', '2025-3-23 18:02:08.000000', '21级2班', '1', '2025-3-23 18:02:11.000000', '2', '202102', '2021', '21级2班');
INSERT INTO `classes` VALUES ('3', '1', '2025-3-23 11:32:26.550000', '21级3班', '1', '2025-3-23 11:32:26.552000', '3', '202103', '2021', '21级3班');
INSERT INTO `classes` VALUES ('4', '1', '2025-3-23 11:34:52.609000', '21级4班', '1', '2025-3-23 11:34:52.609000', '4', '202104', '2021', '21级4班');
INSERT INTO `classes` VALUES ('5', '1', '2025-3-23 11:35:22.735000', '22级1班', '1', '2025-3-23 11:35:22.735000', '1', '202201', '2022', '22级1班');
INSERT INTO `classes` VALUES ('6', '1', '2025-3-23 11:42:29.701000', '22级2班', '1', '2025-3-23 11:42:29.701000', '2', '202202', '2022', '22级2班');
INSERT INTO `classes` VALUES ('8', '1', '2025-3-23 11:50:06.672000', '22级3班', '1', '2025-3-23 11:50:06.672000', '3', '202203', '2022', '22级3班');
INSERT INTO `classes` VALUES ('9', '1', '2025-3-23 12:29:46.799000', '22级4班', '1', '2025-3-23 12:32:27.696000', '4', '202204', '2022', '22级4班');

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `qq` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sex` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `stuno` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `grade_class_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhqce64ggjxx3othwe3cu4hdsd` (`grade_class_id`) USING BTREE,
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`grade_class_id`) REFERENCES `classes` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生信息表';

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES ('1', '1', '2025-3-23 20:39:17.000000', '韩立', '1', '2025-3-23 20:39:23.000000', '韩立', '13800138001', '11184629', '男', '202101001', '1');
INSERT INTO `students` VALUES ('3', '1', '2025-3-23 12:27:33.138000', '南宫婉', '1', '2025-3-23 12:27:33.143000', '南宫婉', '13800138001', '1165498745', '女', '202201001', '5');
INSERT INTO `students` VALUES ('4', '1', '2025-3-23 12:33:12.369000', '韩立好友，与韩立在七玄门相识，两人之间的情谊难以言表，以至于厉家与韩家都结成了世交，延续多年，代代都交好如初', '1', '2025-3-23 12:33:12.370000', '厉飞雨', '13800138002', '11184629', '男', '202101002', '1');
INSERT INTO `students` VALUES ('5', '1', '2025-3-23 12:34:28.220000', '真名汪凝，原乱星海妙音门门主之女，韩立平生所遇第一美女、红颜知己。', '1', '2025-3-23 12:34:28.221000', '紫灵仙子', '13800138003', '123456987', '女', '202201002', '5');
INSERT INTO `students` VALUES ('6', '1', '2025-3-23 12:35:47.631000', '魁星岛附近的修仙小门派女修，对师姐妍丽重情重义，为已成鬼魂的师姐不死重生付出极大代价，与师姐修炼阴阳轮回诀被未知通道吸入灵界，后被姜老怪/青元子收为徒。', '1', '2025-3-23 12:35:47.631000', '元瑶', '13800138004', '236598541', '女', '202201003', '5');
INSERT INTO `students` VALUES ('7', '1', '2025-3-23 12:38:29.538000', '本是灵界银月妖狼玲珑仙子两魂之一', '1', '2025-3-23 12:38:29.538000', '银月', '13800138005', '8965212', '女', '202202001', '6');
INSERT INTO `students` VALUES ('8', '1', '2025-3-23 12:39:47.920000', '落云宗女修，负责管理药园，韩立入落云宗修炼，归其管辖，被家族逼婚，一直推脱不肯完婚。', '1', '2025-3-23 12:39:47.920000', '慕沛灵', '13800138006', '5969854', '女', '202202002', '6');
INSERT INTO `students` VALUES ('9', '1', '2025-3-23 12:40:42.839000', '韩立黄枫谷师姐', '1', '2025-3-23 12:40:42.839000', '陈巧倩', '13800138007', '85969585', '女', '202203001', '8');
INSERT INTO `students` VALUES ('10', '1', '2025-3-23 12:41:29.804000', '韩立旧识。为人高傲，为了驻颜修炼化春决，喜欢男人为她争风吃醋，其师傅撮合她和韩立双修，两人相互不对眼，红拂派其和韩立一起参加燕家夺宝大会', '1', '2025-3-23 12:41:29.804000', '董萱儿', '13800138008', '88888888', '女', '202203002', '8');
INSERT INTO `students` VALUES ('11', '1', '2025-3-23 12:42:13.932000', '御灵宗女修，亲切可人，早年曾在太南小会上卖给韩立金竺符笔，多年后和柳眉等奉命追查至木灵婴下落，被韩立发现，韩立追上她时，发现是多年前旧识只将其打晕。', '1', '2025-3-23 12:46:05.678000', '菡云芝', '13800138009', '99999999', '女', '202204001', '9');
INSERT INTO `students` VALUES ('12', '1', '2025-3-23 12:46:47.361000', '与韩立同时参加七玄门试炼，又同时拜入墨大夫门下。学习《长春功》不成，为入七玄门内门习《象甲功》。', '1', '2025-3-23 12:46:47.361000', '张铁', '13800138010', '77777777', '男', '202204002', '9');
INSERT INTO `students` VALUES ('13', '1', '2025-3-23 12:51:11.668000', '云狐族，没落的狐族分支', '1', '2025-3-23 12:51:11.669000', '柳乐儿', '13800138000', '11184629', '女', '202101003', '1');
INSERT INTO `students` VALUES ('14', '1', '2025-3-23 12:52:16.308000', '北寒石矶殿大长老弟子，真仙巅峰，于北寒仙宫任职', '1', '2025-3-23 12:52:16.309000', '高升', '13800138000', '111846219', '男', '202101004', '1');
INSERT INTO `students` VALUES ('15', '1', '2025-3-23 12:53:20.579000', '本体是噬金仙，爱吃仙材仙器，韩立的灵宠，与韩立一起飞升仙界。', '1', '2025-3-23 12:53:20.579000', '金童', '13800138000', '11184629', '男', '202101005', '1');
INSERT INTO `students` VALUES ('16', '1', '2025-3-23 12:54:00.011000', '原为丰国宰相府七小姐，被灵寰界三大宗门之一的冷焰宗看中并收为内门弟子。', '1', '2025-3-23 12:54:00.011000', '余梦寒', '13800138000', '11184629', '女', '202101006', '1');
INSERT INTO `students` VALUES ('17', '1', '2025-3-23 12:54:41.843000', '冷焰宗内门弟子，元婴中期修为，余梦寒的师尊。', '1', '2025-3-23 12:54:41.843000', '古韵月', '13800138000', '11184629', '女', '202101007', '1');
INSERT INTO `students` VALUES ('18', '1', '2025-3-23 12:55:31.434000', '天鬼宗大乘期太上长老。', '1', '2025-3-23 12:55:31.434000', '段人离', '13800138000', '11184629', '男', '202101008', '1');
INSERT INTO `students` VALUES ('19', '1', '2025-3-23 12:56:22.065000', '天鬼宗大乘期太上大长老。', '1', '2025-3-23 12:56:22.065000', '童人垩', '13800138000', '11184629', '男', '202101009', '1');
INSERT INTO `students` VALUES ('20', '1', '2025-3-23 12:57:13.643000', '伏凌宗大长老封天都之徒，真仙中期修士，于仙宫供职', '1', '2025-3-23 12:57:13.643000', '方磐', '13800138000', '11184629', '男', '202101010', '1');
INSERT INTO `students` VALUES ('21', '1', '2025-3-23 12:58:31.090000', '韩立烛龙道的真仙仆从。为韩立饲养念羽并且外出寻找灵药及种子。', '1', '2025-3-23 12:58:31.090000', '梦浅浅', '13800138000', '11184629', '女', '202101011', '5');
INSERT INTO `students` VALUES ('22', '1', '2025-3-23 13:00:11.864000', '月华仙体，先天七个仙窍觉醒。', '1', '2025-3-23 13:00:11.864000', '白素媛', '13800138000', '11184629', '女', '202201004', '5');
INSERT INTO `students` VALUES ('23', '1', '2025-3-23 13:00:56.730000', '烛龙道十三道主之一，金仙境', '1', '2025-3-23 13:00:56.731000', '云霓', '13800138000', '11184629', '女', '202201005', '5');
INSERT INTO `students` VALUES ('24', '1', '2025-3-23 13:03:38.410000', '妙音门左使', '1', '2025-3-23 13:03:38.410000', '范静梅', '13800138000', '11184629', '女', '202201006', '5');
INSERT INTO `students` VALUES ('25', '1', '2025-3-23 13:04:26.494000', '妙音门右使', '1', '2025-3-23 13:04:26.495000', '卓如婷', '13800138000', '11184629', '女', '202201007', '5');
INSERT INTO `students` VALUES ('26', '1', '2025-3-23 13:06:19.690000', '落云宗第一美女，人称白凤仙，身具天灵根。', '1', '2025-3-23 13:06:19.691000', '宋玉', '13800138000', '11184629', '女', '202201008', '5');
INSERT INTO `students` VALUES ('27', '1', '2025-3-23 13:07:06.107000', '落云宗大长老。', '1', '2025-3-23 13:07:06.107000', '程天坤', '13800138000', '11184629', '男', '202201009', '5');
INSERT INTO `students` VALUES ('28', '1', '2025-3-23 13:07:48.235000', '落云宗长老', '1', '2025-3-23 13:07:48.235000', '吕洛', '13800138000', '11184629', '男', '202201010', '5');
INSERT INTO `students` VALUES ('29', '1', '2025-3-23 13:08:36.588000', '元婴中期，苍坤上人后人', '1', '2025-3-23 13:08:36.588000', '南陇侯', '13800138000', '11184629', '男', '202201011', '5');

-- ----------------------------
-- Table structure for student_score
-- ----------------------------
DROP TABLE IF EXISTS `student_score`;
CREATE TABLE `student_score` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `type` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `course_id` bigint DEFAULT NULL,
  `student_id` bigint DEFAULT NULL,
  `gradeclass_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrm1fvg0dausplxq9nphxb47j9` (`course_id`) USING BTREE,
  KEY `FKfb1pcum4u0xlbod236kmngx4f` (`student_id`) USING BTREE,
  KEY `FK8mac4aaiivjtr0kd4kpyt4vm5` (`gradeclass_id`) USING BTREE,
  CONSTRAINT `student_score_ibfk_1` FOREIGN KEY (`gradeclass_id`) REFERENCES `classes` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `student_score_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `student_score_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学生成绩表';

-- ----------------------------
-- Records of student_score
-- ----------------------------
INSERT INTO `student_score` VALUES ('33', '1', '2025-3-23 13:10:32.817000', '初始成绩', '1', '2025-3-23 13:10:02.795000', '89', '已批改', '2', '1', '1');
INSERT INTO `student_score` VALUES ('34', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '82', '已批改', '2', '4', '1');
INSERT INTO `student_score` VALUES ('35', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '100', '已批改', '1', '3', '5');
INSERT INTO `student_score` VALUES ('36', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '98', '已批改', '1', '5', '5');
INSERT INTO `student_score` VALUES ('37', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '68', '已批改', '1', '6', '5');
INSERT INTO `student_score` VALUES ('38', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '98', '已批改', '1', '1', '1');
INSERT INTO `student_score` VALUES ('39', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '59', '已批改', '1', '4', '1');
INSERT INTO `student_score` VALUES ('40', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '52', '已批改', '1', '13', '1');
INSERT INTO `student_score` VALUES ('41', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '66', '已批改', '1', '14', '1');
INSERT INTO `student_score` VALUES ('42', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '77', '已批改', '1', '15', '1');
INSERT INTO `student_score` VALUES ('43', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '88', '已批改', '1', '16', '1');
INSERT INTO `student_score` VALUES ('44', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '55', '已批改', '1', '17', '1');
INSERT INTO `student_score` VALUES ('45', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '68', '已批改', '1', '18', '1');
INSERT INTO `student_score` VALUES ('46', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '84', '已批改', '1', '19', '1');
INSERT INTO `student_score` VALUES ('47', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '58', '已批改', '1', '20', '1');
INSERT INTO `student_score` VALUES ('48', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '100', '已批改', '1', '21', '1');
INSERT INTO `student_score` VALUES ('49', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '88', '已批改', '3', '1', '1');
INSERT INTO `student_score` VALUES ('50', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '94', '已批改', '3', '4', '1');
INSERT INTO `student_score` VALUES ('51', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '81', '已批改', '3', '13', '1');
INSERT INTO `student_score` VALUES ('52', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '43', '已批改', '3', '14', '1');
INSERT INTO `student_score` VALUES ('53', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '68', '已批改', '3', '15', '1');
INSERT INTO `student_score` VALUES ('54', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '90', '已批改', '3', '16', '1');
INSERT INTO `student_score` VALUES ('55', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '75', '已批改', '3', '17', '1');
INSERT INTO `student_score` VALUES ('56', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '85', '已批改', '3', '18', '1');
INSERT INTO `student_score` VALUES ('57', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '56', '已批改', '3', '19', '1');
INSERT INTO `student_score` VALUES ('58', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '77', '已批改', '3', '20', '1');
INSERT INTO `student_score` VALUES ('59', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '69', '已批改', '3', '21', '1');
INSERT INTO `student_score` VALUES ('60', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '99', '已批改', '3', '3', '5');
INSERT INTO `student_score` VALUES ('61', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '92', '已批改', '3', '5', '5');
INSERT INTO `student_score` VALUES ('62', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '88', '已批改', '3', '6', '5');
INSERT INTO `student_score` VALUES ('63', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '71', '已批改', '3', '22', '5');
INSERT INTO `student_score` VALUES ('64', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '64', '已批改', '3', '23', '5');
INSERT INTO `student_score` VALUES ('65', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '60', '已批改', '3', '24', '5');
INSERT INTO `student_score` VALUES ('66', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '59', '已批改', '3', '25', '5');
INSERT INTO `student_score` VALUES ('67', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '58', '已批改', '3', '26', '5');
INSERT INTO `student_score` VALUES ('68', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '67', '已批改', '3', '27', '5');
INSERT INTO `student_score` VALUES ('69', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '69', '已批改', '3', '28', '5');
INSERT INTO `student_score` VALUES ('70', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '77', '已批改', '3', '29', '5');
INSERT INTO `student_score` VALUES ('71', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '77', '已批改', '4', '1', '1');
INSERT INTO `student_score` VALUES ('72', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '94', '已批改', '4', '4', '1');
INSERT INTO `student_score` VALUES ('73', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '73', '已批改', '4', '13', '1');
INSERT INTO `student_score` VALUES ('74', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '86', '已批改', '4', '14', '1');
INSERT INTO `student_score` VALUES ('75', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '58', '已批改', '4', '15', '1');
INSERT INTO `student_score` VALUES ('76', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '66', '已批改', '4', '16', '1');
INSERT INTO `student_score` VALUES ('77', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '53', '已批改', '4', '17', '1');
INSERT INTO `student_score` VALUES ('78', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '87', '已批改', '4', '18', '1');
INSERT INTO `student_score` VALUES ('79', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '96', '已批改', '4', '19', '1');
INSERT INTO `student_score` VALUES ('80', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '75', '已批改', '4', '20', '1');
INSERT INTO `student_score` VALUES ('81', '1', '2025-3-23 13:10:32.833000', '初始成绩', '1', '2025-3-23 13:10:07.562000', '44', '已批改', '4', '21', '1');

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `qq` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sex` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `teach_no` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `course_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgpacv4uc6gmdaridy3afaf5co` (`course_id`) USING BTREE,
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='教师信息表';

-- ----------------------------
-- Records of teachers
-- ----------------------------
INSERT INTO `teachers` VALUES ('1', '1', '2025-3-23 13:10:32.833000', '岚州枭雄，精通医术，见识过仙人交战后被仙术所震撼，从此一心想踏入修仙长生之途而隐居于七玄门，化名为墨大夫。\n', '1', '2025-3-23 13:10:07.562000', '墨居仁', '13800138001', '11184629', '男', 't001', '1');
INSERT INTO `teachers` VALUES ('2', '1', '2025-3-23 13:10:32.833000', '出身人界的玄剑门，自创《青元剑诀》功法', '1', '2025-3-23 13:10:07.562000', '青元子', '13800138002', '11184629', '男', 't002', '2');
INSERT INTO `teachers` VALUES ('3', '1', '2025-3-23 13:10:32.833000', '黄枫谷结丹长老之一', '1', '2025-3-23 13:10:07.562000', '李化元', '13800138003', '11184629', '男', 't003', '3');
INSERT INTO `teachers` VALUES ('4', '1', '2025-3-23 13:10:32.833000', '乱星海极阴岛岛主，姓乌，玄骨上人的徒弟。', '1', '2025-3-23 13:10:07.562000', '极阴祖师', '13800138004', '11184629', '男', 't004', '4');
INSERT INTO `teachers` VALUES ('5', '1', '2025-3-23 13:10:32.833000', '弥罗老祖是仙界真言门覆灭前最后一任宗主，修炼时间法则至大罗后期，坐下有五大亲传弟子；', '1', '2025-3-23 13:10:07.562000', '弥罗老祖', '13800138005', '11184629', '男', 't005', '5');
INSERT INTO `teachers` VALUES ('6', '1', '2025-3-23 13:10:32.833000', '本体是只黄金巨蟹，前世为魔域魔君石空解。自以为前主人未知原因陨落、自己掉落魔界魔源海苦灵岛附近百万余年，隐秘收集仙灵力修复自身。', '1', '2025-3-23 13:10:07.562000', '蟹道人', '13800138006', '11184629', '男', 't006', '6');
INSERT INTO `teachers` VALUES ('7', '1', '2025-3-23 13:10:32.833000', '降世真仙，受上界监察仙使指派，和另一名巡查使者共同下界铲除螟虫之母。', '1', '2025-3-23 13:10:07.562000', '何康老鬼', '13800138007', '11184629', '男', 't007', '7');
INSERT INTO `teachers` VALUES ('8', '1', '2025-3-23 13:10:32.833000', '天才人物，千竹教教主，自创《大衍决》、《傀儡术》。万年前自知无望突破化神，把元神寄付傀儡之上存于世间。', '1', '2025-3-23 13:10:07.562000', '大衍神君', '13800138008', '11184629', '男', 't008', '8');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `code` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色信息表';

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', '1', '2025-3-23 13:10:32.833000', '系统管理员', '1', '2025-3-23 13:10:07.562000', 'ROLE_ADMIN', '系统管理员');
INSERT INTO `roles` VALUES ('2', '1', '2025-3-23 13:10:32.833000', '普通用户', '1', '2025-3-23 13:10:07.562000', 'ROLE_USER', '普通用户');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `password` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `realname` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sex` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` int DEFAULT NULL,
  `username` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_icon` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4dm5kxn3potpfgdigehw7pdyu` (`role_id`) USING BTREE,
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户信息表';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '1', '2025-2-02 12:55:05.000000', '我是一名管理员', '1', '2025-2-3 07:05:28.241000', 'E10ADC3949BA59ABBE56E057F20F883E', 'Y老师', '男', '1', 'admin', '1366768403@qq.com', '27a0cd9a-581c-44bf-acba-5de5cab72b0a.png', '1');
INSERT INTO `users` VALUES ('2', '1', '2025-2-06 13:06:48.040000', '韩立红颜知己，名义上的妾侍。落云宗女修，负责管理药园，韩立入落云宗修炼，归其管辖，被家族逼婚，一直推脱不肯完婚。后韩立结婴后，慕沛灵对外谎称已被韩立收为妾侍，韩立为突破瓶颈，假戏真唱收其为妾侍，改修颠凤培元功，后为突破元婴期未果身陨。', '1', '2022-12-06 03:36:51.824000', 'E10ADC3949BA59ABBE56E057F20F883E', '慕沛灵', '女', '1', 'mupeiling', 'mupeiling@qq.com', null, '2');
INSERT INTO `users` VALUES ('3', '1', '2025-2-06 15:09:44.441000', '韩立红颜知己。又名雪玲，本是灵界银月妖狼玲珑仙子两魂之一。', '1', '2022-12-06 03:30:05.735000', 'E10ADC3949BA59ABBE56E057F20F883E', '银月', '女', '1', 'yinyue', 'yinyue@qq.com', null, '2');
INSERT INTO `users` VALUES ('4', '1', '2025-2-06 18:10:42.589000', '这是一个凡人修仙者', '1', '2022-12-05 13:10:42.589000', 'E10ADC3949BA59ABBE56E057F20F883E', '韩立', '男', '1', 'hanli', '22@qq.com', null, '2');
INSERT INTO `users` VALUES ('5', '1', '2025-2-09 19:14:47.104000', '韩立红颜知己。真名汪凝，原乱星海妙音门门主之女，韩立平生所遇第一美女、红颜知己。', '1', '2022-12-06 03:37:28.681000', 'E10ADC3949BA59ABBE56E057F20F883E', '紫灵', '女', '1', 'zilin', '33@qq.com', null, '2');
INSERT INTO `users` VALUES ('6', '1', '2025-2-09 13:36:24.775000', '这是一个备注', '1', '2022-12-05 13:36:24.776000', 'E10ADC3949BA59ABBE56E057F20F883E', '厉飞雨', '男', '1', 'lifeiyu', '22@qq.com', null, '2');
INSERT INTO `users` VALUES ('7', '1', '2025-2-09 13:38:54.366000', '韩立红颜知己。魁星岛附近的修仙小门派女修，对师姐妍丽重情重义，为已成鬼魂的师姐不死重生付出极大代价，与师姐修炼阴阳轮回诀被未知通道吸入灵界，后被姜老怪/青元子收为徒。韩立于其帮衬良多，对韩立情素暗生。', '1', '2022-12-05 13:38:54.366000', 'E10ADC3949BA59ABBE56E057F20F883E', '元瑶', '女', '1', 'yuanyao', '33@qq.com', null, '2');
INSERT INTO `users` VALUES ('10', '1', '2025-2-08 01:13:56.953000', '韩立道侣。掩月宗女修，本命法宝朱雀环，修炼素女轮回功 。', '1', '2022-12-06 01:13:56.953000', 'E10ADC3949BA59ABBE56E057F20F883E', '南宫婉', '男', '1', 'nangongwan', '333@qq.com', null, '2');
INSERT INTO `users` VALUES ('11', '1', '2025-2-08 03:46:19.410000', '韩立黄枫谷师姐。经过一些事与非后，爱上韩立，曾经向韩立告白，被一心求大道的韩立拒绝，为等韩立未婚不嫁，道消身死。后韩立在灵界碰到与巧倩长相极其相似之人，讶然不已。', '1', '2022-12-06 03:46:19.410000', 'E10ADC3949BA59ABBE56E057F20F883E', '陈巧倩', '女', '1', 'chenqiaoqian', 'chenqiaoqian@qq.com', null, '2');
INSERT INTO `users` VALUES ('12', '1', '2025-2-08 03:47:02.119000', '韩立旧识。为人高傲，为了驻颜修炼化春决，喜欢男人为她争风吃醋，其师傅撮合她和韩立双修，两人相互不对眼，红拂派其和韩立一起参加燕家夺宝大会。', '1', '2022-12-06 03:47:02.119000', 'E10ADC3949BA59ABBE56E057F20F883E', '董萱儿', '女', '1', 'dongxuaner', 'dongxuaner@qq.com', null, '2');
INSERT INTO `users` VALUES ('13', '1', '2025-2-08 03:47:40.257000', '韩立旧识。御灵宗女修，亲切可人，早年曾在太南小会上卖给韩立金竺符笔，多年后和柳眉等奉命追查至木灵婴下落，被韩立发现，韩立追上她时，发现是多年前旧识只将其打晕。', '1', '2022-12-06 03:47:40.258000', 'E10ADC3949BA59ABBE56E057F20F883E', '菡云芝', '女', '1', 'hanyunzhi', 'hanyunzhi@qq.com', null, '2');
INSERT INTO `users` VALUES ('14', '1', '2025-2-03 03:48:40.648000', '韩立旧识文樯之女。妙音门女修，外出执行危险任务，父女被韩立所救。韩立助其脱离妙音门文思月打算以身相许，奈何韩立不解风情，将她安置在一个荒岛上自行离去。', '1', '2022-12-06 03:48:40.648000', 'E10ADC3949BA59ABBE56E057F20F883E', '文思月', '男', '1', 'wensiyue', 'wensiyue@qq.com', null, '2');

-- ----------------------------
-- Table structure for teaching_plan
-- ----------------------------
DROP TABLE IF EXISTS `teaching_plan`;
CREATE TABLE `teaching_plan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `title` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `teacher_id` bigint DEFAULT NULL,
  `course_id` bigint DEFAULT NULL,
  `class_id` bigint DEFAULT NULL,
  `total_lessons` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_teaching_plan_teacher` (`teacher_id`) USING BTREE,
  KEY `FK_teaching_plan_course` (`course_id`) USING BTREE,
  KEY `FK_teaching_plan_class` (`class_id`) USING BTREE,
  CONSTRAINT `teaching_plan_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teaching_plan_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teaching_plan_ibfk_3` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='教学计划表';

-- ----------------------------
-- Table structure for teaching_progress
-- ----------------------------
DROP TABLE IF EXISTS `teaching_progress`;
CREATE TABLE `teaching_progress` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `teaching_plan_id` bigint NOT NULL,
  `completed_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `progress_percentage` decimal(5,2) NOT NULL DEFAULT '0.00',
  `expected_progress` decimal(5,2) NOT NULL DEFAULT '0.00',
  `lessons_completed` int NOT NULL DEFAULT '0',
  `record_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_teaching_progress_plan` (`teaching_plan_id`) USING BTREE,
  CONSTRAINT `teaching_progress_ibfk_1` FOREIGN KEY (`teaching_plan_id`) REFERENCES `teaching_plan` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='教学进度表';

-- ----------------------------
-- Table structure for teaching_progress_difference
-- ----------------------------
DROP TABLE IF EXISTS `teaching_progress_difference`;
CREATE TABLE `teaching_progress_difference` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime(5) NOT NULL,
  `remarks` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime(5) DEFAULT NULL,
  `teaching_plan_id` bigint NOT NULL,
  `teaching_progress_id` bigint NOT NULL,
  `actual_progress` decimal(5,2) NOT NULL DEFAULT '0.00',
  `expected_progress` decimal(5,2) NOT NULL DEFAULT '0.00',
  `difference` int NOT NULL DEFAULT '0',
  `difference_percentage` decimal(5,2) NOT NULL DEFAULT '0.00',
  `warning` tinyint NOT NULL DEFAULT '0',
  `warning_level` tinyint NOT NULL DEFAULT '0',
  `warning_message` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_difference_plan` (`teaching_plan_id`) USING BTREE,
  KEY `FK_difference_progress` (`teaching_progress_id`) USING BTREE,
  CONSTRAINT `teaching_progress_difference_ibfk_1` FOREIGN KEY (`teaching_plan_id`) REFERENCES `teaching_plan` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teaching_progress_difference_ibfk_2` FOREIGN KEY (`teaching_progress_id`) REFERENCES `teaching_progress` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='教学进度差异记录表';
