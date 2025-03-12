from py2neo import Graph, Node, Relationship

# 假设配置文件config.py中定义了graph对象
from config import graph

# 使用 'utf-8' 编码打开文件
import os
os.chdir("D:/CodeFile/mycode/platform_web")
with open("./herb.txt", encoding="utf-8") as f:
    for line in f.readlines():
        rela_array = line.strip().split(",")  # 移除\n并分割行
        print(rela_array)

        # 使用MERGE语句创建或匹配节点，同时确保属性值安全传递
        graph.run("MERGE (p:Person {cate: $cate, Name: $name})", cate=rela_array[3], name=rela_array[0])
        graph.run("MERGE (p:Person {cate: $cate, Name: $name})", cate=rela_array[4], name=rela_array[1])

        # 创建关系，同样确保参数的安全性
        graph.run(
            "MATCH (e:Person {Name: $source}), (cc:Person {Name: $target}) "
            "MERGE (e)-[r:%s {relation: $relation}]->(cc) "
            "RETURN r" % rela_array[2],
            source=rela_array[0],
            target=rela_array[1],
            relation=rela_array[2]
        )