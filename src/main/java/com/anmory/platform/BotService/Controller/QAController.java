package com.anmory.platform.BotService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-24 下午9:43
 */

@RestController
public class QAController {
    @Autowired
    Neo4jService neo4jService;
    @RequestMapping("/qa")
    public String qa(String question) {
        if(question.contains("可以治疗什么疾病")) {
            String plantName = extractPlantName(question);
            System.out.println("[plantName]" + plantName);
            return queryDiseaseForPlant(plantName);
        }
        return "无法理解您的问题,请重新提问";
    }

    private static String extractPlantName(String question) {
        System.out.println("[question]" + question);
        return question.split("可以治疗什么疾病")[0].trim();
    }

    private String queryDiseaseForPlant(String plantName) {
        String cypher = String.format(
                "MATCH (p:`药材` {Name: \"%s\"})-[r:`药效`]->(n:`药材`) RETURN n.Name LIMIT 1",plantName
        );
        System.out.println(cypher);
        List<String> diseases = neo4jService.executeQuery(cypher);
        return String.join(",",diseases);
    }
}
