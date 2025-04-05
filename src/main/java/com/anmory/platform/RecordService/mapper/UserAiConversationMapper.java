package com.anmory.platform.RecordService.mapper;

import com.anmory.platform.RecordService.model.UserAiConversation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午4:06
 */

@Mapper
public interface UserAiConversationMapper {
    @Select("select * from user_ai_conversation")
    List<UserAiConversation> selectAll();

    @Insert("insert into user_ai_conversation(user_id,question,answer) values (#{userId},#{question},#{answer})")
    int insert(int userId,String question,String answer);
}
