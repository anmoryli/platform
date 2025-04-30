package com.anmory.platform.UserService;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * @description TODO
 * @date 2025-04-27 下午4:02
 */
@Mapper
public interface UserAiConversationMapper {
    @Insert("INSERT INTO user_ai_conversation (user_id, " +
            "question, answer, conversation_type, " +
            "session_id, response_time, language, " +
            "is_satisfied) VALUES (#{userId}, #{question}, " +
            "#{answer}, #{conversationType}, #{sessionId}, " +
            "#{responseTime}, #{language},#{isSatisfied})")
    int insert(int userId,
               String question,
               String answer,
               String conversationType,
               String sessionId,
               Float responseTime,
               String language,
               Boolean isSatisfied);
}
