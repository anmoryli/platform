package com.anmory.platform.DatabaseService.Dao;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午10:38
 */

@Data
public class Prescription {
    private Integer prescriptionId; // 药方ID
    private String prescriptionName; // 处方药名
    private String composition; // 成分
    private String dosage; // 成分的具体用量，JSON格式存储
    private String treatment; // 用途
    private String source; // 药方来源，如《本草纲目》、医生推荐等
    private String suitablePopulation; // 适用人群，如成人、儿童、孕妇等
    private String precautions; // 注意事项，如禁忌症、副作用等
    private String category; // 药方分类，如补益、清热、解毒等
    private String preparationMethod; // 药方的制备方法或使用说明
    private String doctorOrExpert; // 推荐医生或专家名称
    private String status; // 药方状态：启用、禁用、审核中
    private String imageUrl; // 药方相关图片路径
    private String filePath; // 药方相关文件存储路径
    private Float rating; // 用户评分
    private Integer reviewsCount; // 用户评价总数
    private String version; // 药方版本号
    private String medicineIds; // 药方所含药材的ID列表
    private Boolean isPublic; // 是否公开，1表示公开，0表示私有
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private String recordedBy;
}
