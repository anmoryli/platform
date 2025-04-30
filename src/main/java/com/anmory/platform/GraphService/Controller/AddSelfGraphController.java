package com.anmory.platform.GraphService.Controller;

import com.anmory.platform.GraphService.Service.GetAllService;
import com.anmory.platform.GraphService.Service.SelfGraphService;
import org.neo4j.driver.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description TODO
 * @date 2025-04-05 下午6:54
 */

@RestController
public class AddSelfGraphController {
    @Autowired
    SelfGraphService selfGraphService;
    @Autowired
    GetAllService getAllService;
    @RequestMapping("/addSelfGraph")
    public String addSelfGraph(String userInput) throws Exception {
        // 调用服务生成 Cypher 查询
        String cypher = selfGraphService.selfGraph(userInput);
        // 1. 设置 Neo4j 数据库连接信息
        String uri = "bolt://localhost:7687"; // Neo4j Bolt 协议地址
        String username = "neo4j";           // 数据库用户名
        String password = "lmjnb666";        // 数据库密码
        // 用于存储返回的结果
        StringBuilder resultBuilder = new StringBuilder();
        // 2. 创建驱动实例
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password))) {
            // 3. 打开会话
            try (Session session = driver.session()) {
                // 4. 在事务中执行 Cypher 查询
                session.writeTransaction(tx -> {
                    // 执行 Cypher 查询
                    Result result = tx.run(cypher);
                    // 提取返回的数据
                    while (result.hasNext()) {
                        var record = result.next();
                        // 假设返回的节点包含 "name" 和 "type" 属性
                        String nodeName = record.get("name").asString();
                        String nodeType = record.get("type").asString();
                        resultBuilder.append("Node Name: ").append(nodeName)
                                .append(", Node Type: ").append(nodeType)
                                .append("\n");
                    }
                    System.out.println("Cypher 查询已成功执行！");
                    return null;
                });
            } catch (Exception e) {
                System.err.println("执行 Cypher 查询时出错: " + e.getMessage());
                return "error";
            }
        } catch (Exception e) {
            System.err.println("连接 Neo4j 数据库失败: " + e.getMessage());
            return "error";
        }
        // 返回新增的图数据库信息
        return resultBuilder.toString();
    }
}
