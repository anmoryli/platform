# INSERT INTO medicinalplants (name, category, efficacyScore, distribution, description, `usage`)
# VALUES
# -- 新发现物种（文献[4][7]）
# ('青藏红景天', '高山植物类', 88, '青藏高原', '景天科新发现物种，含红景天苷和酪醇', '抗缺氧、抗疲劳'),
# ('滇南三七', '根茎类', 95, '云南南部', '五加科新记录种，皂苷含量高于传统三七', '活血化瘀、抗血栓'),
# ('秦岭石斛', '兰科植物', 102, '陕西秦岭', '兰科石斛属新物种，含石斛碱和联苄类化合物', '滋阴养胃、抗肿瘤'),
#
# -- 抗癌活性植物（文献[8][10]）
# ('南方红豆杉', '树皮类', 15, '福建、广东', '紫杉醇含量达0.03%的珍稀树种', '提取紫杉醇治疗乳腺癌'),
# ('喜树', '果实类', 67, '长江流域', '含喜树碱的珙桐科植物', '抑制拓扑异构酶Ⅰ抗肿瘤'),
# ('长春花', '全草类', 89, '海南岛', '夹竹桃科植物含长春新碱', '治疗白血病和淋巴瘤'),
#
# -- 民族特色药材（文献[4][6]）
# ('藏菖蒲', '根茎类', 74, '西藏林芝', '天南星科特有物种，含α-细辛醚', '治疗癫痫和神经系统疾病'),
# ('蒙黄芪', '补益类', 105, '内蒙古草原', '豆科蒙古黄芪变种，毛蕊异黄酮含量高', '增强免疫力、抗辐射'),
# ('苗药血藤', '藤本类', 83, '贵州山区', '木兰科新开发民族药，含木脂素类成分', '活血通经、抗炎镇痛'),
#
# -- 濒危保护物种（文献[7]）
# ('霍山石斛', '兰科植物', 120, '安徽霍山', '国家一级保护植物，含石斛多糖和氨基酸', '生津养胃、增强免疫力'),
# ('天山雪莲', '高山植物类', 92, '新疆天山', '菊科濒危物种，含雪莲内酯和黄酮', '温肾壮阳、抗紫外线损伤'),
# ('金线莲', '全草类', 78, '福建武夷山', '兰科珍稀药用植物，含金线莲苷', '清热解毒、保肝护肝'),
#
# -- 代谢产物特色（文献[2][9]）
# ('黄花蒿', '全草类', 150, '全球温带', '青蒿素含量达1.2%的菊科植物', '抗疟疾首选药物'),
# ('丹参', '根类', 97, '四川中江', '含丹参酮ⅡA的唇形科植物', '改善心血管微循环'),
# ('雷公藤', '藤本类', 45, '浙江丽水', '卫矛科植物含雷公藤甲素', '治疗类风湿性关节炎'),
#
# -- 传统道地药材（文献[3][6]）
# ('川贝母', '鳞茎类', 110, '四川阿坝', '百合科川贝母干燥鳞茎', '润肺止咳、化痰平喘'),
# ('怀山药', '根茎类', 85, '河南焦作', '薯蓣科道地药材，含薯蓣皂苷', '健脾益胃、降血糖'),
# ('浙白术', '根茎类', 93, '浙江磐安', '菊科白术栽培变种，含白术内酯', '健脾燥湿、抗溃疡'),
#
# -- 特殊功效植物（文献[8][10]）
# ('绞股蓝', '全草类', 88, '陕西平利', '葫芦科植物含绞股蓝皂苷', '降血脂、抗衰老'),
# ('银杏叶', '叶类', 76, '江苏邳州', '银杏科植物叶含黄酮和萜内酯', '改善脑循环、抗氧化');

INSERT INTO medicinalplants
(name, category, efficacyScore, distribution, description, `usage`)
VALUES
-- 海洋药用生物（文献[7][8]）
('海人草', '藻类', 68, '南海诸岛', '松节藻科植物，含海人草酸和卤代单萜', '驱蛔虫、抗真菌[7,8](@ref)'),
('珊瑚菜', '珊瑚类', 51, '海南三沙', '柳珊瑚目软珊瑚，含前列腺素类物质', '抗凝血、抗炎镇痛[8](@ref)'),

-- 真菌类药材（文献[3][7]）
('桑黄', '真菌类', 89, '长白山', '锈革孔菌科真菌，含多糖和三萜类', '抗肿瘤、保肝护肝[7](@ref)'),
('蝉花', '虫草类', 77, '云贵高原', '麦角菌科真菌蝉拟青霉', '镇静安神、免疫调节[3,7](@ref)'),

-- 跨境民族药材（文献[4][8]）
('泰豆蔻', '果实类', 82, '泰国清迈', '姜科植物白豆蔻变种', '温中行气、化湿消痞[8](@ref)'),
('缅血竭', '树脂类', 93, '缅甸掸邦', '棕榈科麒麟竭属树脂', '活血定痛、敛疮生肌[4](@ref)'),

-- 药食同源品种（文献[6][9]）
('黄精', '根茎类', 88, '安徽九华山', '百合科植物多花黄精', '补气养阴、降血糖[6](@ref)'),
('砂仁', '果实类', 79, '广东阳春', '姜科阳春砂成熟果实', '化湿开胃、抗溃疡[9](@ref)'),

-- 代谢组学特色（文献[11][12]）
('迷迭香', '全草类', 85, '地中海地区', '含鼠尾草酸和迷迭香酚', '抗氧化、改善记忆[11](@ref)'),
('辣木', '叶类', 91, '印度南部', '辣木科植物含异硫氰酸酯', '降血压、调节血脂[12](@ref)'),

-- 基因组学新种（文献[6][7]）
('秦艽', '根类', 104, '甘肃陇南', '龙胆科新测序物种', '抗炎镇痛、保肝利胆[6](@ref)'),
('川续断', '根类', 97, '四川阿坝', '川续断科全基因组解析', '续筋接骨、抗骨质疏松[7](@ref)'),

-- 合成生物学应用（文献[11][12]）
('紫杉', '树皮类', 18, '北美西部', '紫杉醇合成途径解析', '抗癌药物前体生产[11](@ref)'),
('长春花', '全草类', 87, '云南西双版纳', '长春新碱生物合成研究', '治疗白血病[12](@ref)'),

-- 传统炮制药材（文献[9]）
('制附子', '根类', 65, '四川江油', '毛茛科乌头炮制品', '回阳救逆需严格炮制[9](@ref)'),
('酒大黄', '根茎类', 73, '青海果洛', '蓼科掌叶大黄炮制品', '活血化瘀、通便[9](@ref)'),

-- 珍稀濒危物种（文献[7]）
('铁皮石斛', '兰科植物', 115, '浙江乐清', '国家一级保护植物', '生津养胃、增强免疫[7](@ref)'),
('天山雪莲', '高山植物', 94, '新疆天山', '含雪莲内酯和黄酮', '温肾壮阳、抗辐射[7](@ref)'),

-- 民族医药特色（文献[4][8]）
('傣百解', '藤本类', 81, '云南西双版纳', '傣医常用解药', '清热解毒、退热[8](@ref)'),
('蒙药广枣', '果实类', 76, '内蒙古通辽', '蒙医心脑血管用药', '行气活血、安神[4](@ref)'),

-- 现代药理研究（文献[10][12]）
('雷公藤', '藤本类', 46, '浙江丽水', '含雷公藤甲素', '治疗类风湿关节炎[10](@ref)'),
('延胡索', '块茎类', 89, '浙江东阳', '含延胡索乙素', '镇痛镇静、抗心律失常[12](@ref)');