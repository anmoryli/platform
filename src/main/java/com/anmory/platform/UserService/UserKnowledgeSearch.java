package com.anmory.platform.UserService;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-29 下午11:41
 */

@Data
public class UserKnowledgeSearch {
    private Integer searchId;
    private Integer userId;
    private String searchQuery;
    private String searchResult;
    private String searchType;
    private Integer resultCount;
    private String searchSource;
    private Boolean isSuccessful;
    private String deviceInfo;
    private LocalDateTime timestamp;
}
