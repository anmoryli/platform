package com.anmory.platform.UploadService;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午8:18
 */


@Data
public class UserUpload {
    private int uploadId;
    private int userId;
    private String uploadName;
    private String uploadPath;
    private Date timestamp;
}
