package com.anmory.platform.RecordService.service;

import com.anmory.platform.RecordService.mapper.UserKnowledgeSearchMapper;
import com.anmory.platform.RecordService.model.UserKnowledgeSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午4:19
 */

@Service
public class UserKnowledgeSearchService {
    @Autowired
    UserKnowledgeSearchMapper userKnowledgeSearchMapper;
    public List<UserKnowledgeSearch> getAll()
    {
        return userKnowledgeSearchMapper.selectAll();
    }

    public int insert(int userId,String searchQuery,String searchResult)
    {
        return userKnowledgeSearchMapper.insert(userId,searchQuery,searchResult);
    }

    public int countKnowledgeSearch()
    {
        return userKnowledgeSearchMapper.countKnowledgeSearch();
    }
}
