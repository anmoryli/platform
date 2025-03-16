package com.anmory.platform.GraphService.Service;

import org.neo4j.driver.Session;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Service;

import org.neo4j.driver.Driver;

import java.util.*;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-15 下午9:11
 */

@Service
public class GetAllService {
    // 定义一个私有的、最终的Driver对象，用于与Neo4j数据库建立连接
    private final Driver driver;

    /**
     * 构造方法，初始化Database驱动
     * 通过"bolt://localhost:7687"地址以及用户名"neo4j"和密码"lmjnb666"来创建与Neo4j数据库的连接
     */
    public GetAllService() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "lmjnb666"));
    }

    /**
     * 获取数据库中所有节点的信息
     *
     * @return 返回一个包含所有节点属性的列表，每个节点的属性以Map形式存储
     */
    public List<Map<String, Object>> getAllNodes() {
        List<Map<String, Object>> nodes = new ArrayList<>();

        // 创建一个Session对象，用于执行数据库操作，并确保在使用完毕后关闭Session
        try (Session session = driver.session()) {
            // 执行Cypher查询语句，匹配并返回数据库中的所有节点及其属性
            Result result = session.run("MATCH (n) RETURN properties(n)");

            // 遍历查询结果，提取每个节点的属性
            while (result.hasNext()) {
                Record record = result.next();
                // 打印当前节点的属性
                System.out.println(record.get("properties(n)"));
                // 将节点属性转换为Map对象，并添加到节点列表中
                Map<String, Object> nodeProperties = record.get("properties(n)").asMap();
                nodes.add(nodeProperties);
            }
        } catch (Exception e) {
            // 打印异常信息，以便于调试和日志记录
            e.printStackTrace();
        }

        // 返回包含所有节点属性的列表
        return nodes;
    }

    public Map<String, Object> getEChartsData() {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> links = new ArrayList<>();

        try (Session session = driver.session()) {
            // 获取所有节点及其关系
            Result result = session.run(
                    "MATCH (n)-[r]->(m) RETURN n, r, m"
            );

            while (result.hasNext()) {
                Record record = result.next();
                Map<String, Object> startNode = record.get("n").asMap();
                Map<String, Object> endNode = record.get("m").asMap();
                Map<String, Object> relationship = record.get("r").asMap();

                addNode(nodes, startNode);
                addNode(nodes, endNode);

                Map<String, Object> link = new HashMap<>();
                link.put("source", startNode.get("name"));
                link.put("target", endNode.get("name"));
                link.put("relationship", relationship); // 如果需要展示关系类型或属性
                links.add(link);
            }

            // 处理没有关系的孤立节点
            Result isolatedNodesResult = session.run("MATCH (n) WHERE NOT (n)--() RETURN n");
            while (isolatedNodesResult.hasNext()) {
                Record record = isolatedNodesResult.next();
                Map<String, Object> isolatedNode = record.get("n").asMap();
                addNode(nodes, isolatedNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> echartsData = new HashMap<>();
        echartsData.put("nodes", nodes);
        echartsData.put("links", links);

        return echartsData;
    }

    private void addNode(List<Map<String, Object>> nodes, Map<String, Object> properties) {
        String name = (String) properties.get("name");
        if (name == null) {
            return; // 跳过没有名字的节点
        }

        Optional<Map<String, Object>> existingNode = nodes.stream()
                .filter(node -> node.get("name").equals(name))
                .findFirst();

        if (!existingNode.isPresent()) {
            Map<String, Object> node = new HashMap<>();
            node.put("name", name);
            node.put("category", getCategory(properties));
            if (properties.containsKey("origin")) {
                node.put("origin", properties.get("origin"));
            }
            nodes.add(node);
        }
    }

    private String getCategory(Map<String, Object> properties) {
        List<String> labels = (List<String>) properties.get("labels");

        // 如果 labels 是 null 或者为空，则返回 "未知"
        if (labels == null || labels.isEmpty()) {
            return "未知";
        }

        if (labels.contains("Herb")) {
            return "药材";
        } else if (labels.contains("Effect")) {
            return "功能";
        } else if (labels.contains("Disease")) {
            return "症状";
        } else if (labels.contains("Part")) {
            return "部位";
        } else if (labels.contains("Chemical")) {
            return "成分";
        }

        return "未知";
    }

    /**
     * 关闭与Neo4j数据库的连接
     * 在不再需要与数据库通信时，调用此方法释放资源
     */
    public void close() {
        driver.close();
    }
}

