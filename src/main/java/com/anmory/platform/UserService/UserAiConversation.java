package com.anmory.platform.UserService;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description TODO
 * @date 2025-04-27 上午10:54
 */

@Data
public class UserAiConversation {
    private Integer conversationId;
    private Integer userId;
    private String question;
    private String answer;
    private String conversationType;
    private String sessionId;
    private Float responseTime;
    private String language;
    private Boolean isSatisfied;
    private LocalDateTime createTime;
}
