
# config.py

from py2neo import Graph

# 使用完整的Bolt URI来指定主机、端口、用户名和密码
graph = Graph("bolt://localhost:7687", auth=("neo4j", "lmjnb666"))