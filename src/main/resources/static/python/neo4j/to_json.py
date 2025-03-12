import json

# 初始化数据结构
nodes = []
links = []
categories = [
    {"name": "药材名称"},
    {"name": "功效/配伍药材"},
    {"name": "使用方法"},
    {"name": "采集地点"},
    {"name": "特殊用法"}
]

# 用于跟踪已添加的节点
node_id_map = {}
current_node_id = 0

def add_node(name, category):
    global current_node_id
    if name and name not in node_id_map:
        node_id_map[name] = current_node_id
        nodes.append({"name": name, "category": category})
        current_node_id += 1

def add_link(source, target, relation):
    if source is not None and target is not None:
        links.append({"source": source, "target": target, "value": relation})

# 读取 herb.txt 文件
with open("herb.txt", encoding="utf-8") as f:
    for line in f:
        fields = [field.strip() for field in line.split(',')]
        if len(fields) < 5:
            continue  # 跳过格式不正确的行
        
        herb_name, effect, compatible_herbs, usage_method, collection_site = fields[:5]
        special_usage = fields[5] if len(fields) > 5 else ""

        # 添加药材名称节点
        add_node(herb_name, 0)

        # 添加功效/配伍药材节点
        if effect:
            add_node(effect, 1)
            add_link(node_id_map[herb_name], node_id_map[effect], "功效")
        
        if compatible_herbs:
            for herb in compatible_herbs.split('、'):
                add_node(herb, 1)
                add_link(node_id_map[herb_name], node_id_map[herb], "配伍药材")

        # 添加使用方法节点
        if usage_method:
            add_node(usage_method, 2)
            add_link(node_id_map[herb_name], node_id_map[usage_method], "使用方法")

        # 添加采集地点节点
        if collection_site:
            add_node(collection_site, 3)
            add_link(node_id_map[herb_name], node_id_map[collection_site], "采集地点")

        # 添加特殊用法节点
        if special_usage:
            add_node(special_usage, 4)
            add_link(node_id_map[herb_name], node_id_map[special_usage], "特殊用法")

# 构建最终的 JSON 数据
json_data = {
    "data": nodes,
    "links": links
}

# 写入 JSON 文件
with open("data.json", "w", encoding="utf-8") as outfile:
    json.dump(json_data, outfile, ensure_ascii=False, indent=4)

print("JSON file created successfully.")