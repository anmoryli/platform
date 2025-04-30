package com.anmory.platform.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description TODO
 * @date 2025-04-29 下午11:46
 */

@Service
public class UserKnowledgeSearchService {
    @Autowired
    UserKnowledgeSearchMapper userKnowledgeSearchMapper;

    public int insert(int userId, String searchQuery, String searchResult, String searchType, Integer resultCount, String searchSource, Boolean isSuccessful, String deviceInfo) {
        return userKnowledgeSearchMapper.insert(userId, searchQuery, searchResult, searchType, resultCount, searchSource, isSuccessful, deviceInfo);
    }

    public List<UserKnowledgeSearch> selectAll() {
        return userKnowledgeSearchMapper.selectAll();
    }
}
