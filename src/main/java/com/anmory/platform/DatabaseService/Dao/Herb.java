package com.anmory.platform.DatabaseService.Dao;

import lombok.Data;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-23 下午2:44
 */

@Data
public class Herb {
    private int herbId; // 数据库列名为 herb_id
    private int plantId;
    private String herbName; // 数据库列名为 herb_name
    private String herbTibetanName; // 数据库列名为 herb_tibetan_name
    private String herbAlias; // 数据库列名为 herb_alias
    private String herbDescription; // 数据库列名为 herb_description
    private String partUsed; // 数据库列名为 part_used
    private String herbFeatures; // 数据库列名为 herb_features
    private String flavorTropism; // 数据库列名为 flavor_tropism
    private String functionIndication; // 数据库列名为 function_indication
    private String clinicalApplication; // 数据库列名为 clinical_application
    private String pharmacologicalEffect; // 数据库列名为 pharmacological_effect
    private String notes; // 数据库列名为 notes
}
