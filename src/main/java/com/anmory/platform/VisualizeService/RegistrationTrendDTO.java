package com.anmory.platform.VisualizeService;

import lombok.Data;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-30 上午7:50
 */

@Data
public class RegistrationTrendDTO {
    private String period; // 用于折线图的时间段（如 '2024-01'）
    private Integer adminCount; // 管理员注册数量
    private Integer userCount; // 普通用户注册数量
    private Integer totalCount; // 总注册数量
    private String category; // 用于柱状图的分类（如邮箱域名）
    private String dayOfWeek; // 用于热力图的星期
    private Integer hourOfDay; // 用于热力图的小时
    private Integer count; // 通用计数字段
}
