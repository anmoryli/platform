# drop database if exists medicine;
# create database medicine;
# use medicine;
# drop table if exists user_image_recognition;
# drop table if exists user_ai_conversation;
# drop table if exists user_knowledge_search;
# drop table if exists user_upload;
# drop table if exists user_info;
# drop table if exists source;
# drop table if exists herb;
# drop table if exists picture;
# drop table if exists plant;

# ==========================================================================
# || ！！！！！！！！！！把数据库换成本地的再运行，不要直接运行drop！！！！！！！！！！！||
# ==========================================================================
# ==========================================================================
# || ！！！！！！！！！！把数据库换成本地的再运行，不要直接运行drop！！！！！！！！！！！||
# ==========================================================================
# ==========================================================================
# || ！！！！！！！！！！把数据库换成本地的再运行，不要直接运行drop！！！！！！！！！！！||
# ==========================================================================
# ==========================================================================
# || ！！！！！！！！！！把数据库换成本地的再运行，不要直接运行drop！！！！！！！！！！！||
# ==========================================================================
# ==========================================================================
# || ！！！！！！！！！！把数据库换成本地的再运行，不要直接运行drop！！！！！！！！！！！||
# ==========================================================================

create table user_info(
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) not null ,
    password VARCHAR(100) not null ,
    is_admin int,
    record_nums int,
    email VARCHAR(100),
    create_time datetime default now(),
    update_time datetime default now() on update now()
);

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


