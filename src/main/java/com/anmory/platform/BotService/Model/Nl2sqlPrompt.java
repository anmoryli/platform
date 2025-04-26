package com.anmory.platform.BotService.Model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-26 上午1:40
 */

@Data
public class Nl2sqlPrompt {
    private Integer id;
    private String promptGenSql;
    private String promptProcessRet;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private Integer version;
    private String status;
    private String language;
    private String targetDb;
    private Integer executionCount;
    private LocalDateTime lastExecutionTime;
    private String errorLog;
    private String metadata;
    private String description;
    private Boolean isPublic;
    private String accessLevel;
}
