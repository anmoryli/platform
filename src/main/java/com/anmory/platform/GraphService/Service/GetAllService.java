package com.anmory.platform.GraphService.Service;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetAllService {
    private final Driver driver;

    public GetAllService() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "lmjnb666"));
    }

    // 获取节点类别（返回数字）
    private int getCategory(String category) {
        Map<String, Integer> categoryMap = new HashMap<>();
        // 分配数字给每个类别
        categoryMap.put("藏药", 0);         // 藏药
        categoryMap.put("药效", 1);         // 药效
        categoryMap.put("配伍", 2);         // 配伍
        categoryMap.put("采集地点", 3);     // 采集地点

        // 如果遇到未知的类别，则返回一个默认值，比如4
        return categoryMap.getOrDefault(category, 4); // 默认返回 4 表示未知类别
    }

    public List<Map<String, Object>> getAllNodes() {
        List<Map<String, Object>> nodes = new ArrayList<>();
        try (Session session = driver.session()) {
            Result result = session.run("MATCH (n:药材) RETURN n");
            while (result.hasNext()) {
                Record record = result.next();
                Map<String, Object> nodeProperties = nodeToMap(record.get("n").asNode());
                nodes.add(nodeProperties);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    public Map<String, Object> getEChartsData() {
        // 初始化 JSON 数据结构
        Map<String, Object> jsonData = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();  // 存储节点
        List<Map<String, Object>> links = new ArrayList<>(); // 存储关系
        jsonData.put("data", data);
        jsonData.put("links", links);

        // 查询所有节点和关系的 Cypher 语句
        String query = "MATCH (p)-[r]->(n) RETURN p.Name AS pName, r.relation AS relation, n.Name AS nName, p.cate AS pCate, n.cate AS nCate";

        // 辅助数据结构
        Set<String> uniqueNodes = new HashSet<>(); // 用于去重节点
        Map<String, Integer> nameDict = new HashMap<>(); // 节点名称到索引的映射
        int count = 0;

        try (Session session = driver.session()) {
            // 第一步：查询所有节点和关系
            Result result = session.run(query);

            // 遍历结果，收集唯一节点
            while (result.hasNext()) {
                Record record = result.next();
                String pName = record.get("pName").asString();
                String nName = record.get("nName").asString();
                String pCate = record.get("pCate").asString();
                String nCate = record.get("nCate").asString();

                // 添加节点到唯一集合
                uniqueNodes.add(pName + "_" + pCate);
                uniqueNodes.add(nName + "_" + nCate);
            }

            // 第二步：为每个唯一节点分配索引并添加到 data 列表中
            for (String node : uniqueNodes) {
                String[] parts = node.split("_");
                String nodeName = parts[0];
                String category = parts[1];

                if (!nameDict.containsKey(nodeName)) {
                    nameDict.put(nodeName, count++);
                    Map<String, Object> dataItem = new HashMap<>();
                    dataItem.put("name", nodeName);
                    dataItem.put("category", getCategory(category)); // 使用数字类别
                    data.add(dataItem);
                }
            }

            // 第三步：重新运行查询以处理关系
            result = session.run(query);
            while (result.hasNext()) {
                Record record = result.next();
                String pName = record.get("pName").asString();
                String nName = record.get("nName").asString();
                String relation = record.get("relation").asString();

                // 构造关系对象
                Map<String, Object> linkItem = new HashMap<>();
                linkItem.put("source", nameDict.get(pName));
                linkItem.put("target", nameDict.get(nName));
                linkItem.put("value", relation);
                links.add(linkItem);
            }

            // 第四步：查询孤立节点（没有关系的节点）
            String isolatedQuery = "MATCH (n) WHERE NOT (n)--() RETURN n";
            Result isolatedNodesResult = session.run(isolatedQuery);

            while (isolatedNodesResult.hasNext()) {
                Record record = isolatedNodesResult.next();
                org.neo4j.driver.types.Node neo4jNode = record.get("n").asNode();
                addUniqueNode(data, neo4jNode, nameDict, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonData;
    }

    // 将 Neo4j 节点转换为 Map 并添加到数据中
    private void addUniqueNode(List<Map<String, Object>> data, org.neo4j.driver.types.Node node, Map<String, Integer> nameDict, int count) {
        String nodeName = node.get("Name").isNull() ? "" : node.get("Name").asString();
        String cate = node.get("cate").isNull() ? "Unknown" : node.get("cate").asString();

        if (!nameDict.containsKey(nodeName)) {
            nameDict.put(nodeName, count++);
            Map<String, Object> dataItem = new HashMap<>();
            dataItem.put("name", nodeName);
            dataItem.put("category", getCategory(cate)); // 使用数字类别
            data.add(dataItem);
        }
    }

    private void addUniqueNode(List<Map<String, Object>> nodes, Map<String, Object> properties) {
        String nodeName = (String) properties.get("name");
        if (nodeName == null || nodeName.isEmpty()) {
            return;
        }

        Optional<Map<String, Object>> existingNode = nodes.stream()
                .filter(node -> node.get("name").equals(nodeName))
                .findFirst();

        if (!existingNode.isPresent()) {
            Map<String, Object> node = new HashMap<>(properties);
            node.put("category", getCategory((String) properties.get("cate"))); // 使用数字类别
            nodes.add(node);
        }
    }

    private Map<String, Object> nodeToMap(org.neo4j.driver.types.Node node) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", node.get("Name").isNull() ? "" : node.get("Name").asString());
        map.put("cate", node.get("cate").isNull() ? "Unknown" : node.get("cate").asString()); // 确保有默认值
        map.put("labels", new ArrayList<>((Collection) node.labels()));
        return map;
    }

    public Map<String, Object> query(String name) {
        Map<String, Object> jsonData = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();
        List<Map<String, Object>> links = new ArrayList<>();
        jsonData.put("data", data);
        jsonData.put("links", links);

        String query = "MATCH (p:药材)-[r]->(n:药材 {Name: $name}) RETURN p.Name AS pName, r.relation AS relation, n.Name AS nName, p.cate AS pCate, n.cate AS nCate " +
                "UNION ALL " +
                "MATCH (p:药材 {Name: $name})-[r]->(n:药材) RETURN p.Name AS pName, r.relation AS relation, n.Name AS nName, p.cate AS pCate, n.cate AS nCate";

        Set<String> uniqueNodes = new HashSet<>();
        Map<String, Integer> nameDict = new HashMap<>();
        int count = 0;

        try (Session session = driver.session()) {
            Result result = session.run(query, Values.parameters("name", name));

            while (result.hasNext()) {
                Record record = result.next();
                String pName = record.get("pName").asString();
                String nName = record.get("nName").asString();
                String relation = record.get("relation").asString();
                String pCate = record.get("pCate").asString();
                String nCate = record.get("nCate").asString();

                uniqueNodes.add(pName + "_" + pCate);
                uniqueNodes.add(nName + "_" + nCate);
            }

            for (String node : uniqueNodes) {
                String[] parts = node.split("_");
                String nodeName = parts[0];
                String category = parts[1];

                if (!nameDict.containsKey(nodeName)) {
                    nameDict.put(nodeName, count++);
                    Map<String, Object> dataItem = new HashMap<>();
                    dataItem.put("name", nodeName);
                    dataItem.put("category", getCategory(category)); // 使用数字类别
                    data.add(dataItem);
                }
            }

            result = session.run(query, Values.parameters("name", name));
            while (result.hasNext()) {
                Record record = result.next();
                String pName = record.get("pName").asString();
                String nName = record.get("nName").asString();
                String relation = record.get("relation").asString();

                Map<String, Object> linkItem = new HashMap<>();
                linkItem.put("source", nameDict.get(pName));
                linkItem.put("target", nameDict.get(nName));
                linkItem.put("value", relation);
                links.add(linkItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonData;
    }

    public void close() {
        driver.close();
    }
}