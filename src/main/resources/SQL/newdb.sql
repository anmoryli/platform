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