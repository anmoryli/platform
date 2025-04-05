package com.anmory.platform.RecordService.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午3:57
 */

@Data
public class UserAiConversation {
    private int conversationId;
    private int userId;
    private String question;
    private String answer;
    private Date timestamp;
}
