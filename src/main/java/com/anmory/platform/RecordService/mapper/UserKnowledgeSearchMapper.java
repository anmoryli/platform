package com.anmory.platform.RecordService.mapper;

import com.anmory.platform.RecordService.model.UserKnowledgeSearch;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午4:12
 */

@Mapper
public interface UserKnowledgeSearchMapper {
    @Select("select * from user_knowledge_search")
    List<UserKnowledgeSearch> selectAll();
    
    @Insert("insert into user_knowledge_search (user_id,search_query,search_result) values (#{userId},#{searchQuery},#{searchResult});")
    int insert(int userId,String searchQuery,String searchResult);
}
