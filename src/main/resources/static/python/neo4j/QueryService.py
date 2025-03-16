from flask import Flask, jsonify, request
from py2neo import Graph, NodeMatcher

# 初始化 Flask 应用
app = Flask(__name__)

# 连接到本地 Neo4j 数据库
graph = Graph("bolt://localhost:7687", auth=("neo4j", "lmjnb666"))

# 创建一个 NodeMatcher 实例用于查询节点
matcher = NodeMatcher(graph)

@app.route('/')
def index():
    return "Welcome to the Query Service!"

# 1. 返回该药材相关的所有结点信息
@app.route('/herb/<herb_name>', methods=['GET'])
def get_herb_info(herb_name):
    # 查找药材节点
    herb_node = matcher.match("Herb", name=herb_name).first()

    if not herb_node:
        return jsonify({"error": f"Herb {herb_name} not found"}), 404

    # 查询与药材相关的所有关系和节点
    query = """
    MATCH (h:Herb {name: $herb_name})-[:药效|相关疾病|药用部位|化学组成]->(n)
    RETURN h, n
    """
    results = graph.run(query, herb_name=herb_name).data()

    # 整理结果
    nodes = {}
    for record in results:
        node_type = list(record['n'].labels)[0]
        if node_type not in nodes:
            nodes[node_type] = []
        nodes[node_type].append(dict(record['n']))

    # 添加药材本身的信息
    nodes["Herb"] = [dict(herb_node)]

    return jsonify(nodes)

# 2. 根据疾病查询相关药材
@app.route('/disease/<disease_name>', methods=['GET'])
def get_herbs_by_disease(disease_name):
    query = """
    MATCH (h:Herb)-[:相关疾病]->(d:Disease {name: $disease_name})
    RETURN h
    """
    results = graph.run(query, disease_name=disease_name).data()

    if not results:
        return jsonify({"error": f"No herbs found for disease {disease_name}"}), 404

    herbs = [dict(record['h']) for record in results]

    return jsonify(herbs)

# 3. 根据药材返回他的化学组成
@app.route('/herb/<herb_name>/chemicals', methods=['GET'])
def get_chemicals_by_herb(herb_name):
    query = """
    MATCH (h:Herb {name: $herb_name})-[:化学组成]->(c:Chemical)
    RETURN c
    """
    results = graph.run(query, herb_name=herb_name).data()

    if not results:
        return jsonify({"error": f"No chemicals found for herb {herb_name}"}), 404

    chemicals = [dict(record['c']) for record in results]

    return jsonify(chemicals)

# 4. 根据药材查询可以治疗的相关疾病
@app.route('/herb/<herb_name>/diseases', methods=['GET'])
def get_diseases_by_herb(herb_name):
    query = """
    MATCH (h:Herb {name: $herb_name})-[:相关疾病]->(d:Disease)
    RETURN d
    """
    results = graph.run(query, herb_name=herb_name).data()

    if not results:
        return jsonify({"error": f"No diseases found for herb {herb_name}"}), 404

    diseases = [dict(record['d']) for record in results]

    return jsonify(diseases)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5050)