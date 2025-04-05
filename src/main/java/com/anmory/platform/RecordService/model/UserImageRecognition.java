package com.anmory.platform.RecordService.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午3:56
 */

@Data
public class UserImageRecognition {
    private int recordId;
    private int userId;
    private String imageName;
    private String imagePath;
    private String imageResult;
    private Date timestamp;
}
