package com.anmory.platform.DatabaseService.Dao;


import lombok.Data;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-23 下午3:13
 */

@Data
public class Plant {
    private int plantId;
    private String plantName;
    private String plantLatin;
    private String plantAlias;
    private String plantTibetanName;
    private String plantFamilyName;
    private String plantGenusName;
    private String imgPath;
    private String plantFeatures;
    private String plantOrigin;
    private String plantProtectLevel;
    private String notes;
}
