package com.anmory.platform.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description TODO
 * @date 2025-04-27 下午5:06
 */

@Service
public class UserAiConversationService {
    @Autowired
    UserAiConversationMapper userApiConversationMapper;

    public int insert(int userId, String question, String answer, String conversationType, String sessionId, Float responseTime, String language, Boolean isSatisfied) {
        return userApiConversationMapper.insert(userId, question, answer, conversationType, sessionId, responseTime, language, isSatisfied);
    }
}
