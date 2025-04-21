drop table if exists source;
drop table if exists plant;
create table plant(
    plant_id int primary key auto_increment,
    plant_name varchar(100), # 植物名
    plant_latin varchar(100), # 拉丁学名
    plant_alias varchar(100), # 别称
    plant_tibetan_name varchar(100), # 藏文名
    plant_family_name varchar(100),# 科名
    plant_genus_name varchar(100), # 属名
    plant_features varchar(1024), # 特征
    plant_origin varchar(100), # 采集地点
    plant_protect_level varchar(1024), # 保护等级
    notes varchar(1024) # 附注
);

drop table if exists herb;
create table herb(
                     herb_id int auto_increment primary key ,
                     plant_id int not null,
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

drop table if exists potion;
create table potion(
    potion_id int primary key auto_increment,
    potion_name varchar(100) comment '药剂名',
    efficacy text comment '功效',
    dosage_form varchar(100) comment '剂型',
    create_time datetime default now(),
    update_time datetime default now() on update now()
) comment '药剂表';

drop table if exists prescription;
create table prescription(
    prescription_id int primary key auto_increment,
    potion_id int comment '药剂id',
    prescription_name varchar(100) comment '处方药名',
    composition text comment '成分',
    treatment varchar(100) comment '用途',
    create_time datetime default now(),
    update_time datetime default now() on update now(),
    foreign key (potion_id) references potion(potion_id)
) comment '药方表';

drop table if exists literature;
CREATE TABLE literature (
                            literature_id BIGINT(20) primary key NOT NULL AUTO_INCREMENT COMMENT '主键，唯一标识每篇文献',
                            title VARCHAR(512) NOT NULL COMMENT '文献标题',
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
                            update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
)COMMENT='藏药植物药文献库表';

drop table if exists user_image_recognition;
create table user_image_recognition(
                                       record_id int primary key auto_increment,
                                       user_id int,
                                       image_name varchar(100),
                                       image_path varchar(100),
                                       image_result text,
                                       timestamp datetime default now(),
                                       foreign key (user_id) references user_info(id)
);

drop table if exists user_ai_conversation;
create table user_ai_conversation(
                                     conversation_id int primary key auto_increment,
                                     user_id int,
                                     question text,
                                     answer text,
                                     timestamp datetime default now(),
                                     foreign key (user_id) references user_info(id)
);

drop table if exists user_knowledge_search;
create table user_knowledge_search(
                                      search_id int primary key auto_increment,
                                      user_id int,
                                      search_query varchar(100),
                                      search_result text,
                                      timestamp datetime default now(),
                                      foreign key (user_id) references user_info(id)
);

drop table if exists user_upload;
create table user_upload(
                            upload_id int primary key auto_increment,
                            user_id int,
                            upload_name varchar(100),
                            upload_path varchar(100),
                            timestamp datetime default now(),
                            foreign key (user_id) references user_info(id)
);