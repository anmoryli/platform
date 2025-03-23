drop table if exists herb;
create table herb(
    herb_id int auto_increment primary key ,
    herb_name varchar(100) unique,
    herb_tibetan_name varchar(100) unique ,
    herb_alias varchar(100),
    herb_description varchar(1024),
    part_used varchar(200),
    herb_features varchar(1024),
    flavor_tropism varchar(1024), # 性味与归经
    function_indication varchar(1024), # 功能与主治
    clinical_application varchar(1024), # 临床应用
    pharmacological_effect varchar(1024), # 药理作用
    research varchar(1024), # 考证
    notes varchar(1024) # 附注
);

INSERT INTO herb (herb_name, herb_tibetan_name, herb_alias, herb_description, part_used, herb_features, flavor_tropism, function_indication, clinical_application, pharmacological_effect, research, notes)
VALUES
    ('黄芪', 'བོད་ཡིག1', '北芪', '豆科植物蒙古黄芪或膜荚黄芪的干燥根。', '根', '性温，味甘。归脾、肺经。', '温、甘；归脾、肺经', '补气固表，利尿托毒，排脓，敛疮生肌。', '用于气虚乏力等', '增强机体免疫功能等', '现代研究发现含有多种氨基酸等成分。', '常与党参配伍使用。'),
    ('当归', 'བོད་ཡིག2', '秦归', '伞形科植物当归的干燥根。', '根', '性温，味甘、辛。归肝、心、脾经。', '温、甘、辛；归肝、心、脾经', '补血活血，调经止痛，润肠通便。', '用于血虚萎黄等', '改善血液循环等', '含有挥发油等成分。', '常与熟地黄配伍使用。'),
    ('甘草', 'བོད་ཡིག3', '国老', '豆科植物甘草的干燥根及根茎。', '根及根茎', '性平，味甘。归心、肺、脾、胃经。', '平、甘；归心、肺、脾、胃经', '补脾益气，祛痰止咳，缓急止痛，调和诸药。', '用于脾胃虚弱等', '抗炎、保护肝脏等作用。', '含有甘草甜素等成分。', '常与人参配伍使用。'),
    ('枸杞子', 'བོད་ཡིག4', '杞子', '茄科植物宁夏枸杞的干燥成熟果实。', '果实', '性平，味甘。归肝、肾经。', '平、甘；归肝、肾经', '滋补肝肾，益精明目。', '用于虚劳精亏等', '具有增强免疫力、抗氧化等作用。', '含有多种氨基酸、维生素等成分。', '常与菊花配伍使用。'),
    ('白术', 'བོད་ཡིག5', '于术', '菊科植物白术的干燥根茎。', '根茎', '性温，味甘、苦。归脾、胃经。', '温、甘、苦；归脾、胃经', '健脾益气，燥湿利水，止汗，安胎。', '用于脾虚食少等', '调节肠胃功能等', '含有苍术酮等成分。', '常与茯苓配伍使用。'),
    ('丹参', 'བོད་ཡ6', '赤参', '唇形科植物丹参的干燥根和根茎。', '根及根茎', '性微寒，味苦。归心、肝经。', '微寒、苦；归心、肝经', '活血祛瘀，通经止痛，清心除烦，凉血消痈。', '用于胸痹心痛等', '扩张冠状动脉等', '含有丹参酮等成分。', '常与三七配伍使用。');

drop table if exists plant;
create table plant(
    plant_id int primary key auto_increment,
    plant_name varchar(100),
    plant_latin varchar(100),
    plant_alias varchar(100),
    plant_tibetan_name varchar(100),
    plant_family_name varchar(100),
    plant_genus_name varchar(100),
    plant_features varchar(1024),
    plant_origin varchar(100),
    plant_protect_level varchar(1024),
    notes varchar(1024)
);

INSERT INTO plant (plant_name, plant_latin, plant_alias, plant_tibetan_name, plant_family_name, plant_genus_name, plant_features, plant_origin, plant_protect_level, notes)
VALUES
    ('蒙古黄芪', 'Astragalus membranaceus var. mongholicus', '北芪', 'བོད་ཡིག1', '豆科', '黄芪属', '多年生草本植物，高50-70厘米。', '中国北部', '二级保护植物', '黄芪是重要的中药材之一，广泛用于补气。'),
    ('当归属植物', 'Angelica sinensis', '秦归', 'བོད་ཡིག2', '伞形科', '当归属', '多年生草本植物，高60-120厘米。', '中国西部', '三级保护植物', '当归是常用的补血药材，广泛用于妇科疾病治疗。'),
    ('甘草', 'Glycyrrhiza uralensis', '国老', 'བོད་ཡིག3', '豆科', '甘草属', '多年生草本植物，高30-100厘米。', '中国北部', '三级保护植物', '甘草是重要的中药材之一，广泛用于调理脾胃。'),
    ('宁夏枸杞', 'Lycium barbarum', '杞子', 'བོད་ཡིག4', '茄科', '枸杞属', '落叶灌木，高约2米。', '中国西部', '无保护级别', '枸杞子是常用的滋补药材，广泛用于养肝明目。'),
    ('白术', 'Atractylodes macrocephala', '于术', 'བོད་ཡིག5', '菊科', '苍术属', '多年生草本植物，高30-80厘米。', '中国中部', '无保护级别', '白术是重要的健脾药材，广泛用于脾胃虚弱。'),
    ('丹参', 'Salvia miltiorrhiza', '赤参', 'བོད་ཡིག6', '唇形科', '鼠尾草属', '多年生草本植物，高30-100厘米。', '中国东部', '无保护级别', '丹参是常用的活血化瘀药材，广泛用于心血管疾病的治疗。');


drop table if exists source;
create table source(
    id int primary key auto_increment,
    plant_id int,
    herb_id int,
    foreign key (plant_id) references plant(plant_id),
    foreign key (herb_id) references herb(herb_id)
);

INSERT INTO source (plant_id, herb_id)
VALUES
    (34, 35), -- 蒙古黄芪 -> 黄芪
    (35, 36), -- 当归属植物 -> 当归
    (36, 37), -- 甘草 -> 甘草
    (37, 38), -- 宁夏枸杞 -> 枸杞子
    (38, 39), -- 白术 -> 白术
    (39, 40); -- 丹参 -> 丹参

drop table if exists picture;
create table picture(
    id int auto_increment primary key ,
    herb_id int,
    pic_name varchar(100),
    pic_cate varchar(100),
    pic_part varchar(100),
    file_name varchar(100),
    description varchar(1024),
    pic_place varchar(100),
    pic_time datetime,
    pic_person varchar(100),
    foreign key (herb_id) references herb(herb_id)
);

INSERT INTO picture (herb_id, pic_name, pic_cate, pic_part, file_name, description, pic_place, pic_time, pic_person)
VALUES
    (35, '黄芪根部', '根部', '根', 'huangqi_root.jpg', '黄芪的干燥根部特写。', '内蒙古', '2023-09-01 10:00:00', '张三'),
    (36, '当归根部', '根部', '根', 'danggui_root.jpg', '当归的干燥根部特写。', '四川', '2023-09-05 11:00:00', '王五'),
    (37, '甘草根部', '根部', '根及根茎', 'gancao_root.jpg', '甘草的干燥根部特写。', '内蒙古', '2023-08-25 09:00:00', '孙七'),
    (38, '枸杞子', '果实', '果实', 'gouqizi_fruit.jpg', '枸杞子的成熟果实特写。', '宁夏', '2023-08-30 10:00:00', '吴九'),
    (39, '白术根茎', '根茎', '根茎', 'baizhu_rhizome.jpg', '白术的干燥根茎特写。', '河南', '2023-09-15 14:00:00', '郑十'),
    (40, '丹参根茎', '根茎', '根及根茎', 'danshen_rhizome.jpg', '丹参的干燥根茎特写。', '山东', '2023-09-20 15:00:00', '李四');
