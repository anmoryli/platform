package com.anmory.platform.DatabaseService.Dao;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午11:20
 */

@Data
public class Literature {
    private int literatureId; // 主键，唯一标识每篇文献
    private String title; // 文献标题
    private String tibetanTitle; // 藏文标题
    private String authors; // 作者列表，多个作者用逗号分隔
    private Integer publicationYear; // 出版年份
    private String journalOrPublisher; // 期刊名称或出版社
    private String volumeIssue; // 卷号和期号（如果是期刊文章）
    private String pages; // 页码范围
    private String abstractText; // 文献摘要
    private String downloadLink; // 文献下载链接（如果可以在线获取）
    private String filePath; // 本地存储路径（如果文献已下载到服务器）
    private String mainPlant; // 主要涉及的植物名称
    private String researchField; // 研究领域，例如化学成分、药理作用等
    private String keywords; // 关键词，用于快速检索
    private Date createTime; // 记录创建时间
    private Date updateTime; // 记录更新时间
}
