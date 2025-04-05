package com.anmory.platform.RecordService.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午3:58
 */

@Data
public class UserKnowledgeSearch {
    private int searchId;
    private int userId;
    private String searchQuery;
    private String searchResult;
    private Date timestamp;
}
