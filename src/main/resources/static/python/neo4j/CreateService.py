from py2neo import Graph, Node, Relationship

# 连接到本地 Neo4j 数据库
graph = Graph("bolt://localhost:7687", auth=("neo4j", "lmjnb666"))

def create_or_get_node(label, properties):
    node = graph.nodes.match(label, **properties).first()
    if not node:
        node = Node(label, **properties)
        graph.create(node)
    return node

import logging

# 设置日志记录
logging.basicConfig(level=logging.INFO)

# 读取 TXT 文件
with open('import.txt', 'r', encoding='utf-8') as file:
    lines = file.readlines()
    for line in lines:
        # 去除行尾的换行符
        line = line.strip()
        logging.info(f"Processing line: {line}")

        try:
            # 分割字段
            herb_name, origin, effects, diseases, parts, chemicals = line.split(',')

            # 解析多值字段
            effects = [effect.strip() for effect in effects.split(';')]
            diseases = [disease.strip() for disease in diseases.split(';')]
            parts = [part.strip() for part in parts.split(';')]
            chemicals = [chemical.strip() for chemical in chemicals.split(';')]

            # 创建药材节点
            herb = create_or_get_node("Herb", {"name": herb_name, "origin": origin})

            # 创建关系
            for effect in effects:
                effect_node = create_or_get_node("Effect", {"name": effect})
                graph.create(Relationship(herb, "药效", effect_node))
                logging.info(f"Created relationship 药效 between {herb_name} and {effect}")

            for disease in diseases:
                disease_node = create_or_get_node("Disease", {"name": disease})
                graph.create(Relationship(herb, "相关疾病", disease_node))
                logging.info(f"Created relationship 相关疾病 between {herb_name} and {disease}")

            for part in parts:
                part_node = create_or_get_node("Part", {"name": part})
                graph.create(Relationship(herb, "药用部位", part_node))
                logging.info(f"Created relationship 药用部位 between {herb_name} and {part}")

            for chemical in chemicals:
                chemical_node = create_or_get_node("Chemical", {"name": chemical})
                graph.create(Relationship(herb, "化学组成", chemical_node))
                logging.info(f"Created relationship 化学组成 between {herb_name} and {chemical}")

        except Exception as e:
            logging.error(f"Error processing line {line}: {e}")