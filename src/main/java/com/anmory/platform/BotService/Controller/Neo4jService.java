package com.anmory.platform.BotService.Controller;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-24 下午9:52
 */

@Service
public class Neo4jService {
    private final Driver driver;

    public Neo4jService() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j","lmjnb666"));
    }
    public List<String> executeQuery(String cypher) {
        try(Session session = driver.session()){
            return session.run(cypher)
                    .list(record -> record.get(0).asString());
        }
    }
}
