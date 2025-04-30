package com.anmory.platform.GraphService.Repository;

import com.anmory.platform.GraphService.Dao.Herb;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @description TODO
 * @date 2025-03-15 下午12:58
 */

@Repository
public interface HerbRepo extends Neo4jRepository<Herb, Long> {
    // 使用自定义查询
    @Query("MATCH (herb:Herb {name: $name}) RETURN herb")
    Herb findByName(String name);
}
