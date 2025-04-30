package com.anmory.platform.UserService;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-29 下午11:44
 */

@Mapper
public interface UserKnowledgeSearchMapper {
    @Insert("insert into user_knowledge_search(user_id, search_query, search_result, search_type, result_count, search_source, is_successful, device_info) values(#{userId}, #{searchQuery}, #{searchResult}, #{searchType}, #{resultCount}, #{searchSource}, #{isSuccessful}, #{deviceInfo})")
    int insert(int userId, String searchQuery, String searchResult, String searchType, Integer resultCount, String searchSource, Boolean isSuccessful, String deviceInfo);

    @Select("select * from medicine.user_knowledge_search")
    List<UserKnowledgeSearch> selectAll();
}
