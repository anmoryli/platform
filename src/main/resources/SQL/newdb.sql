drop database if exists medicine;
create database medicine;
use medicine;
drop table if exists user_image_recognition;
drop table if exists user_ai_conversation;
drop table if exists user_knowledge_search;
drop table if exists user_upload;
drop table if exists user_info;
drop table if exists source;
drop table if exists herb;
drop table if exists picture;
drop table if exists plant;

create table user_info(
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) not null ,
    password VARCHAR(100) not null ,
    is_admin int,
    email VARCHAR(100),
    create_time datetime default now(),
    update_time datetime default now() on update now()
);

insert into user_info(username,password,is_admin,email) values ('anmory','lmjnb666',1,'anmory@qq.com'),
                                                                ('xyh','xyh',1,'user@163.com'),
                                                                ('zzw','zzw',1,'user1@163.com'),
                                                                ('user2','123456',0,'user2@163.com'),
                                                                ('user3','123456',0,'user3@163.com'),
                                                                ('user4','123456',0,'user4@163.com'),
                                                                ('user5','123456',0,'user4@163.com'),
                                                                ('user6','123456',0,'user4@163.com'),
                                                                ('user7','123456',0,'user4@163.com'),
                                                                ('user8','123456',0,'user4@163.com'),
                                                                ('user9','123456',0,'user4@163.com'),
                                                                ('user10','123456',0,'user4@163.com'),
                                                                ('user11','123456',0,'user4@163.com'),
                                                                ('user12','123456',0,'user4@163.com'),
                                                                ('user13','123456',0,'user4@163.com'),
                                                                ('user14','123456',0,'user4@163.com'),
                                                                ('user15','123456',0,'user4@163.com'),
                                                                ('user16','123456',0,'user4@163.com'),
                                                                ('user17','123456',0,'user4@163.com'),
                                                                ('user18','123456',0,'user4@163.com'),
                                                                ('user19','123456',0,'user4@163.com');


create table plant(
    plant_id int primary key auto_increment,
    plant_name varchar(100) not null, # 植物名
    plant_latin varchar(100), # 拉丁学名
    plant_alias varchar(100), # 别称
    plant_tibetan_name varchar(100) not null , # 藏文名
    plant_family_name varchar(100) not null ,# 科名
    plant_genus_name varchar(100) not null , # 属名
    img_path varchar(100),# 图像路径
    plant_features varchar(1024), # 特征
    plant_origin varchar(100), # 采集地点
    plant_protect_level varchar(1024), # 保护等级
    notes varchar(1024) # 附注
);

drop table if exists herb;
create table herb(
                     herb_id int auto_increment primary key ,
                     plant_id int,
                     herb_name varchar(100) unique,
                     herb_tibetan_name varchar(100) unique ,# 藏文名
                     herb_alias varchar(100),# 别名
                     herb_description varchar(1024), # 描述
                     part_used varchar(200), # 药用部位
                     herb_features varchar(1024),# 特征
                     flavor_tropism varchar(1024), # 性味与归经
                     function_indication varchar(1024), # 功能与主治
                     clinical_application varchar(1024), # 临床应用
                     pharmacological_effect varchar(1024), # 药理作用
                     notes varchar(1024), # 附注
                     foreign key (plant_id) references plant(plant_id)
);

drop table if exists prescription;
CREATE TABLE prescription (
                              prescription_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '药方ID',
                              prescription_name VARCHAR(100) NOT NULL COMMENT '处方药名',
                              composition TEXT COMMENT '成分',
                              dosage VARCHAR(100) COMMENT '成分的具体用量，JSON格式存储',
                              treatment VARCHAR(100) COMMENT '用途',
                              source VARCHAR(255) COMMENT '药方来源，如《本草纲目》、医生推荐等',
                              suitable_population VARCHAR(255) COMMENT '适用人群，如成人、儿童、孕妇等',
                              precautions TEXT COMMENT '注意事项，如禁忌症、副作用等',
                              category VARCHAR(100) COMMENT '药方分类，如补益、清热、解毒等',
                              preparation_method TEXT COMMENT '药方的制备方法或使用说明',
                              doctor_or_expert VARCHAR(255) COMMENT '推荐医生或专家名称',
                              status ENUM('启用', '禁用', '审核中') DEFAULT '启用' COMMENT '药方状态',
                              image_url VARCHAR(1024) COMMENT '药方相关图片路径',
                              file_path VARCHAR(1024) COMMENT '药方相关文件存储路径',
                              rating FLOAT DEFAULT 0 COMMENT '用户评分',
                              reviews_count INT DEFAULT 0 COMMENT '用户评价总数',
                              version VARCHAR(50) DEFAULT '1.0' COMMENT '药方版本号',
                              medicine_ids text COMMENT '药方所含药材的ID列表',
                              is_public BOOLEAN DEFAULT TRUE COMMENT '是否公开，1表示公开，0表示私有',
                              create_time DATETIME DEFAULT NOW() COMMENT '创建时间',
                              update_time DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间'
) COMMENT '药方表';

drop table if exists literature;
CREATE TABLE literature (
                            literature_id BIGINT(20) primary key NOT NULL AUTO_INCREMENT COMMENT '主键，唯一标识每篇文献',
                            title VARCHAR(512) NOT NULL COMMENT '文献标题',
                            tibetan_title VARCHAR(512) NOT NULL COMMENT '藏文标题',
                            authors VARCHAR(512) NOT NULL COMMENT '作者列表，多个作者用逗号分隔',
                            publication_year YEAR COMMENT '出版年份',
                            journal_or_publisher VARCHAR(255) COMMENT '期刊名称或出版社',
                            volume_issue VARCHAR(100) COMMENT '卷号和期号（如果是期刊文章）',
                            pages VARCHAR(50) COMMENT '页码范围',
                            abstract TEXT COMMENT '文献摘要',
                            download_link VARCHAR(1024) COMMENT '文献下载链接（如果可以在线获取）',
                            file_path VARCHAR(1024) COMMENT '本地存储路径（如果文献已下载到服务器）',
                            main_plant VARCHAR(255) COMMENT '主要涉及的植物名称',
                            research_field VARCHAR(255) COMMENT '研究领域，例如化学成分、药理作用等',
                            keywords VARCHAR(512) COMMENT '关键词，用于快速检索',
                            create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                            update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间'
)COMMENT='藏药植物药文献库表';

drop table if exists user_image_recognition;
CREATE TABLE user_image_recognition (
                                        record_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
                                        user_id INT COMMENT '用户ID',
                                        image_name VARCHAR(100) COMMENT '图片名称',
                                        image_path VARCHAR(100) COMMENT '图片存储路径',
                                        image_result TEXT COMMENT '识别结果',
                                        accuracy FLOAT COMMENT '识别准确率',
                                        response_time FLOAT COMMENT '响应时间',
                                        model_used VARCHAR(100) COMMENT '使用的识别模型',
                                        status ENUM('成功', '失败', '处理中') DEFAULT '处理中' COMMENT '识别任务状态',
                                        error_message TEXT COMMENT '错误信息',
                                        device_info VARCHAR(255) COMMENT '用户设备信息',
                                        is_processed BOOLEAN DEFAULT FALSE COMMENT '是否已处理',
                                        create_time DATETIME DEFAULT NOW() COMMENT '时间戳',
                                        FOREIGN KEY (user_id) REFERENCES user_info(id)
) COMMENT '用户图像识别记录表';

INSERT INTO user_image_recognition (
    user_id,
    image_name,
    image_path,
    image_result,
    accuracy,
    response_time,
    model_used,
    status,
    error_message,
    device_info,
    is_processed,
    create_time
) VALUES
-- 2024年1月数据
(1, '1.jpg', '/usr/local/nginx/images/tmp/1.jpg', 'test', 0.9, 0.1, 'yolo', '成功', NULL, NULL, TRUE, '2024-01-05 10:30:00'),
(1, '2.jpg', '/usr/local/nginx/images/tmp/2.jpg', 'test', 0.85, 0.12, 'yolo', '成功', NULL, NULL, TRUE, '2024-01-12 14:45:00'),
(1, '3.jpg', '/usr/local/nginx/images/tmp/3.jpg', 'test', 0.92, 0.09, 'yolo', '成功', NULL, NULL, TRUE, '2024-01-20 09:15:00'),

-- 2024年2月数据
(1, '4.jpg', '/usr/local/nginx/images/tmp/4.jpg', 'test', 0.88, 0.11, 'yolo', '成功', NULL, NULL, TRUE, '2024-02-03 16:20:00'),
(1, '5.jpg', '/usr/local/nginx/images/tmp/5.jpg', 'test', 0.91, 0.13, 'yolo', '成功', NULL, NULL, TRUE, '2024-02-15 11:50:00'),
(1, '6.jpg', '/usr/local/nginx/images/tmp/6.jpg', 'test', 0.87, 0.14, 'yolo', '成功', NULL, NULL, TRUE, '2024-02-22 08:10:00'),

-- 2024年3月数据
(1, '7.jpg', '/usr/local/nginx/images/tmp/7.jpg', 'test', 0.93, 0.08, 'yolo', '成功', NULL, NULL, TRUE, '2024-03-01 13:00:00'),
(1, '8.jpg', '/usr/local/nginx/images/tmp/8.jpg', 'test', 0.89, 0.1, 'yolo', '成功', NULL, NULL, TRUE, '2024-03-10 17:30:00'),
(1, '9.jpg', '/usr/local/nginx/images/tmp/9.jpg', 'test', 0.9, 0.11, 'yolo', '成功', NULL, NULL, TRUE, '2024-03-25 09:45:00'),

-- 2024年4月数据
(1, '10.jpg', '/usr/local/nginx/images/tmp/10.jpg', 'test', 0.95, 0.07, 'yolo', '成功', NULL, NULL, TRUE, '2024-04-02 15:20:00'),
(1, '11.jpg', '/usr/local/nginx/images/tmp/11.jpg', 'test', 0.86, 0.12, 'yolo', '成功', NULL, NULL, TRUE, '2024-04-14 10:50:00'),
(1, '12.jpg', '/usr/local/nginx/images/tmp/12.jpg', 'test', 0.94, 0.09, 'yolo', '成功', NULL, NULL, TRUE, '2024-04-28 12:30:00'),

-- 2024年5月数据
(1, '13.jpg', '/usr/local/nginx/images/tmp/13.jpg', 'test', 0.88, 0.1, 'yolo', '成功', NULL, NULL, TRUE, '2024-05-05 08:45:00'),
(1, '14.jpg', '/usr/local/nginx/images/tmp/14.jpg', 'test', 0.91, 0.11, 'yolo', '成功', NULL, NULL, TRUE, '2024-05-18 14:10:00'),
(1, '15.jpg', '/usr/local/nginx/images/tmp/15.jpg', 'test', 0.89, 0.12, 'yolo', '成功', NULL, NULL, TRUE, '2024-05-25 16:50:00'),

-- 2024年6月数据
(1, '16.jpg', '/usr/local/nginx/images/tmp/16.jpg', 'test', 0.92, 0.08, 'yolo', '成功', NULL, NULL, TRUE, '2024-06-03 11:20:00'),
(1, '17.jpg', '/usr/local/nginx/images/tmp/17.jpg', 'test', 0.87, 0.13, 'yolo', '成功', NULL, NULL, TRUE, '2024-06-12 09:50:00'),
(1, '18.jpg', '/usr/local/nginx/images/tmp/18.jpg', 'test', 0.93, 0.09, 'yolo', '成功', NULL, NULL, TRUE, '2024-06-20 13:30:00');

CREATE TABLE user_ai_conversation (
                                      conversation_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '对话ID',
                                      user_id INT COMMENT '用户ID',
                                      question TEXT COMMENT '用户问题',
                                      answer TEXT COMMENT 'AI回答',
                                      conversation_type VARCHAR(50) COMMENT '对话类型，如文本问答、语音问答',
                                      session_id VARCHAR(100) COMMENT '会话ID',
                                      response_time FLOAT COMMENT 'AI响应时间',
                                      language VARCHAR(50) DEFAULT '中文' COMMENT '对话语言',
                                      is_satisfied BOOLEAN DEFAULT NULL COMMENT '用户是否满意',
                                      create_time DATETIME DEFAULT NOW() COMMENT '时间戳',
                                      FOREIGN KEY (user_id) REFERENCES user_info(id)
) COMMENT '用户与AI的对话记录表';

-- 插入模拟数据（藏药相关）
INSERT INTO user_ai_conversation (
    user_id,
    question,
    answer,
    conversation_type,
    session_id,
    response_time,
    language,
    is_satisfied,
    create_time
) VALUES
-- 2024年1月数据
(1, '请问藏药“雪莲花”有什么功效？', '雪莲花具有清热解毒、消肿止痛的功效，常用于治疗风湿性关节炎等疾病。', '文本问答', 'session_001', 1.0, '中文', TRUE, '2024-01-03 10:15:00'),
(2, '藏药中“冬虫夏草”的主要产地在哪里？', '冬虫夏草主要产于中国西藏、青海等地的高原地区。', '文本问答', 'session_002', 0.8, '中文', TRUE, '2024-01-05 14:30:00'),
(3, '藏药“红景天”可以用来缓解疲劳吗？', '是的，红景天有助于提高身体的抗疲劳能力，并且对心血管系统也有益处。', '文本问答', 'session_003', 1.2, '中文', FALSE, '2024-01-10 09:45:00'),

-- 2024年2月数据
(1, '藏药里有没有能改善睡眠的药材？', '藏药中的“夜交藤”有安神助眠的作用，适合用于失眠多梦的情况。', '文本问答', 'session_004', 1.0, '中文', TRUE, '2024-02-01 16:20:00'),
(2, '藏药“藏红花”有哪些使用方法？', '藏红花可以泡茶饮用，也可以作为调料加入食物中，或者制成膏剂外用。', '语音问答', 'session_005', 0.9, '中文', TRUE, '2024-02-14 11:50:00'),
(3, '藏药“青稞”对健康有什么好处？', '青稞富含膳食纤维和多种维生素，有助于调节血糖、降低胆固醇。', '文本问答', 'session_006', 0.6, '中文', NULL, '2024-02-20 13:10:00'),

-- 2024年3月数据
(4, '如何使用藏药“藏茵陈”来治疗感冒？', '藏茵陈可用于煮水喝或煎服，对于风寒感冒引起的发热、头痛等症状有一定的缓解作用。', '文本问答', 'session_007', 1.1, '中文', TRUE, '2024-03-05 08:30:00'),
(5, '藏药中有哪些药材适合女性服用？', '藏药中的“当归”、“白芍”等药材对女性调理气血非常有益。', '语音问答', 'session_008', 1.3, '中文', TRUE, '2024-03-15 17:45:00'),
(6, '藏药“甘松”在现代医学中有应用吗？', '甘松在现代医学中被研究用于其抗抑郁和镇静作用。', '文本问答', 'session_009', 0.7, '中文', FALSE, '2024-03-22 12:10:00'),

-- 2024年4月数据
(7, '藏药“诃子”有什么特殊的药理作用？', '诃子具有收敛止泻、涩肠固精等功效，在藏医中广泛应用于消化系统疾病的治疗。', '文本问答', 'session_010', 1.0, '中文', TRUE, '2024-04-02 09:20:00'),
(8, 'What are the benefits of Tibetan medicine "Arura"?', 'Arura (Chebulic Myrobalan) is one of the most important herbs in Tibetan medicine, known for its detoxifying and rejuvenating properties.', '文本问答', 'session_011', 0.8, '英文', TRUE, '2024-04-10 15:50:00'),
(9, '给我介绍一种藏药药材吧！', '好的！藏药“獐牙菜”主要用于治疗肝胆疾病，具有利胆退黄、清热解毒的作用。', '语音问答', 'session_012', 0.9, '中文', TRUE, '2024-04-18 14:10:00'),

-- 2024年5月数据
(10, '藏药“余甘子”对身体有哪些好处？', '余甘子有助于增强免疫力、抗氧化、促进新陈代谢，长期食用对健康有益。', '文本问答', 'session_013', 1.2, '中文', TRUE, '2024-05-03 11:30:00'),
(11, 'How can Tibetan medicine "Bodhi" be used to improve mental health?', 'Bodhi (Ficus religiosa) leaves and bark are traditionally used in Tibetan medicine for their calming and mind-clearing effects, helping with stress and anxiety.', '文本问答', 'session_014', 1.0, '英文', TRUE, '2024-05-15 16:45:00'),
(12, '藏药“独一味”可以治疗哪些疾病？', '独一味主要用于治疗跌打损伤、骨折疼痛等症状，具有活血化瘀、消肿止痛的效果。', '文本问答', 'session_015', 0.8, '中文', TRUE, '2024-05-25 09:50:00');

drop table if exists user_knowledge_search;
CREATE TABLE user_knowledge_search (
                                       search_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '搜索ID',
                                       user_id INT COMMENT '用户ID',
                                       search_query VARCHAR(100) COMMENT '搜索关键词',
                                       search_result TEXT COMMENT '搜索结果',
                                       search_type VARCHAR(50) COMMENT '搜索类型，如关键字搜索、语义搜索',
                                       result_count INT COMMENT '搜索结果数量',
                                       search_source VARCHAR(100) COMMENT '搜索结果来源',
                                       is_successful BOOLEAN DEFAULT TRUE COMMENT '搜索是否成功',
                                       device_info VARCHAR(255) COMMENT '用户设备信息',
                                       timestamp DATETIME DEFAULT NOW() COMMENT '时间戳',
                                       FOREIGN KEY (user_id) REFERENCES user_info(id)
) COMMENT '用户知识搜索记录表';

-- 插入模拟数据（藏药相关）
INSERT INTO user_knowledge_search (
    user_id,
    search_query,
    search_result,
    search_type,
    result_count,
    search_source,
    is_successful,
    device_info,
    timestamp
) VALUES
-- 2024年1月数据
(1, '雪莲花功效', '雪莲花具有清热解毒、消肿止痛的功效，常用于风湿性关节炎等疾病。', '关键字搜索', 3, '藏药数据库', TRUE, 'iPhone 13, iOS 16', '2024-01-03 10:15:00'),
(2, '冬虫夏草产地', '冬虫夏草主要产于中国西藏、青海等地的高原地区。', '关键字搜索', 1, '药材百科', TRUE, 'Android Galaxy S22', '2024-01-05 14:30:00'),
(3, '红景天抗疲劳', '红景天有助于提高身体的抗疲劳能力，对心血管系统有益。', '语义搜索', 5, '健康知识库', TRUE, 'MacBook Pro M1', '2024-01-10 09:45:00'),

-- 2024年2月数据
(1, '藏药助眠药材', '夜交藤具有安神助眠的作用，适合失眠多梦的情况。', '关键字搜索', 2, '藏医文献', TRUE, 'Windows PC', '2024-02-01 16:20:00'),
(2, '藏红花使用方法', '藏红花可以泡茶饮用，或作为调料加入食物中。', '语义搜索', 4, '药材用法指南', TRUE, 'iPad Air 5', '2024-02-14 11:50:00'),
(3, '青稞健康益处', '青稞富含膳食纤维和维生素，有助于调节血糖、降低胆固醇。', '关键字搜索', 6, '营养学资料', TRUE, 'iPhone SE', '2024-02-20 13:10:00'),

-- 2024年3月数据
(4, '藏茵陈治疗感冒', '藏茵陈可用于煮水喝或煎服，缓解风寒感冒症状。', '关键字搜索', 2, '藏医诊疗指南', TRUE, 'Android OnePlus 9', '2024-03-05 08:30:00'),
(5, '藏药女性调理', '当归、白芍等藏药对女性调理气血非常有益。', '语义搜索', 3, '女性健康知识库', TRUE, 'Surface Laptop', '2024-03-15 17:45:00'),
(6, '甘松现代医学应用', '甘松在现代医学中被研究用于其抗抑郁和镇静作用。', '关键字搜索', 1, '药理学研究', FALSE, 'Linux Desktop', '2024-03-22 12:10:00'),

-- 2024年4月数据
(7, '诃子药理作用', '诃子具有收敛止泻、涩肠固精等功效，广泛应用于消化系统疾病。', '关键字搜索', 4, '藏药药理学', TRUE, 'iPhone 14 Pro', '2024-04-02 09:20:00'),
(8, 'Arura benefits', 'Arura (Chebulic Myrobalan) is known for its detoxifying and rejuvenating properties in Tibetan medicine.', '语义搜索', 5, 'International Medicine Journal', TRUE, 'Android Pixel 6', '2024-04-10 15:50:00'),
(9, '獐牙菜用途', '獐牙菜主要用于治疗肝胆疾病，具有利胆退黄、清热解毒的作用。', '关键字搜索', 3, '藏药药材大全', TRUE, 'MacBook Air M2', '2024-04-18 14:10:00'),

-- 2024年5月数据
(10, '余甘子健康益处', '余甘子有助于增强免疫力、抗氧化、促进新陈代谢。', '语义搜索', 6, '藏药养生指南', TRUE, 'Windows Surface Pro', '2024-05-03 11:30:00'),
(11, 'Bodhi mental health', 'Bodhi leaves and bark are used in Tibetan medicine for calming and mind-clearing effects.', '关键字搜索', 2, 'Tibetan Medicine Research', TRUE, 'iPhone XR', '2024-05-15 16:45:00'),
(12, '独一味治疗跌打损伤', '独一味具有活血化瘀、消肿止痛的效果，用于治疗跌打损伤。', '关键字搜索', 1, '藏药外用指南', TRUE, 'Samsung Tab S8', '2024-05-25 09:50:00');

drop table if exists user_upload;
CREATE TABLE user_upload (
                             upload_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '上传ID',
                             user_id INT COMMENT '用户ID',
                             upload_name VARCHAR(100) COMMENT '上传文件名称',
                             upload_path VARCHAR(100) COMMENT '上传文件存储路径',
                             file_type VARCHAR(50) COMMENT '文件类型，如图片、文档、视频',
                             file_size INT COMMENT '文件大小（字节）',
                             upload_url VARCHAR(255) COMMENT '文件访问链接',
                             is_public BOOLEAN DEFAULT FALSE COMMENT '是否公开',
                             status ENUM('成功', '失败', '处理中') DEFAULT '处理中' COMMENT '上传任务状态',
                             error_message TEXT COMMENT '错误信息',
                             timestamp DATETIME DEFAULT NOW() COMMENT '时间戳',
                             FOREIGN KEY (user_id) REFERENCES user_info(id)
) COMMENT '用户上传记录表';

-- 插入模拟数据
INSERT INTO user_upload (
    user_id,
    upload_name,
    upload_path,
    file_type,
    file_size,
    upload_url,
    is_public,
    status,
    error_message,
    timestamp
) VALUES
-- 2024年1月数据
(1, '藏药知识.pdf', '/uploads/documents/藏药知识.pdf', '文档', 2048000, 'http://example.com/uploads/documents/藏药知识.pdf', TRUE, '成功', NULL, '2024-01-03 10:15:00'),
(2, '雪莲花.jpg', '/uploads/images/雪莲花.jpg', '图片', 512000, 'http://example.com/uploads/images/雪莲花.jpg', FALSE, '成功', NULL, '2024-01-05 14:30:00'),
(3, '冬虫夏草.mp4', '/uploads/videos/冬虫夏草.mp4', '视频', 10485760, 'http://example.com/uploads/videos/冬虫夏草.mp4', TRUE, '失败', '文件格式不支持', '2024-01-10 09:45:00'),

-- 2024年2月数据
(1, '红景天功效.docx', '/uploads/documents/红景天功效.docx', '文档', 1024000, 'http://example.com/uploads/documents/红景天功效.docx', FALSE, '处理中', NULL, '2024-02-01 16:20:00'),
(2, '藏红花使用方法.png', '/uploads/images/藏红花使用方法.png', '图片', 768000, 'http://example.com/uploads/images/藏红花使用方法.png', TRUE, '成功', NULL, '2024-02-14 11:50:00'),
(3, '青稞营养成分.xlsx', '/uploads/documents/青稞营养成分.xlsx', '文档', 1536000, 'http://example.com/uploads/documents/青稞营养成分.xlsx', TRUE, '成功', NULL, '2024-02-20 13:10:00'),

-- 2024年3月数据
(4, '藏茵陈介绍.pptx', '/uploads/documents/藏茵陈介绍.pptx', '文档', 3072000, 'http://example.com/uploads/documents/藏茵陈介绍.pptx', FALSE, '成功', NULL, '2024-03-05 08:30:00'),
(5, '当归药材.jpg', '/uploads/images/当归药材.jpg', '图片', 614400, 'http://example.com/uploads/images/当归药材.jpg', TRUE, '处理中', NULL, '2024-03-15 17:45:00'),
(6, '甘松药理研究.mp4', '/uploads/videos/甘松药理研究.mp4', '视频', 12288000, 'http://example.com/uploads/videos/甘松药理研究.mp4', FALSE, '失败', '文件过大', '2024-03-22 12:10:00'),

-- 2024年4月数据
(7, '诃子功效说明.pdf', '/uploads/documents/诃子功效说明.pdf', '文档', 2560000, 'http://example.com/uploads/documents/诃子功效说明.pdf', TRUE, '成功', NULL, '2024-04-02 09:20:00'),
(8, 'Arura benefits.mp4', '/uploads/videos/Arura benefits.mp4', '视频', 11264000, 'http://example.com/uploads/videos/Arura benefits.mp4', TRUE, '成功', NULL, '2024-04-10 15:50:00'),
(9, '獐牙菜用途.png', '/uploads/images/獐牙菜用途.png', '图片', 819200, 'http://example.com/uploads/images/獐牙菜用途.png', FALSE, '处理中', NULL, '2024-04-18 14:10:00'),

-- 2024年5月数据
(10, '余甘子健康益处.docx', '/uploads/documents/余甘子健康益处.docx', '文档', 1280000, 'http://example.com/uploads/documents/余甘子健康益处.docx', TRUE, '成功', NULL, '2024-05-03 11:30:00'),
(11, 'Bodhi mental health.pdf', '/uploads/documents/Bodhi mental health.pdf', '文档', 2304000, 'http://example.com/uploads/documents/Bodhi mental health.pdf', FALSE, '失败', '文件损坏', '2024-05-15 16:45:00'),
(12, '独一味治疗跌打损伤.mp4', '/uploads/videos/独一味治疗跌打损伤.mp4', '视频', 13312000, 'http://example.com/uploads/videos/独一味治疗跌打损伤.mp4', TRUE, '处理中', NULL, '2024-05-25 09:50:00');