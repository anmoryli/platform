package com.anmory.platform.RecordService.service;

import com.anmory.platform.RecordService.mapper.UserAiConversationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAiConversationServiceTest {
    @Autowired
    UserAiConversationMapper userAiConversationMapper;
//    @Test
//    void getAll() {
//        System.out.println(userAiConversationMapper.selectAll());
//        System.out.println(userAiConversationMapper.insert(2, "你好", "你好"));
//    }
}