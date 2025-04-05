package com.anmory.platform.RecordService.service;

import com.anmory.platform.RecordService.mapper.UserAiConversationMapper;
import com.anmory.platform.RecordService.model.UserAiConversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午4:10
 */

@Service
public class UserAiConversationService {

    @Autowired
    UserAiConversationMapper userAiConversationMapper;
    public List<UserAiConversation> getAll() {
        return userAiConversationMapper.selectAll();
    }

    public int insert(int userId,String question,String answer) {
        return userAiConversationMapper.insert(userId,question,answer);
    }
}
