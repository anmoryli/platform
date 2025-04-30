package com.anmory.platform.VisualizeService;

/**
 * @description TODO
 * @date 2025-04-29 下午11:58
 */
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface VisualizeMapper {

    // 1.1 用户注册趋势 - 每日注册用户数量
    @Select("SELECT DATE(create_time) AS date, COUNT(*) AS count " +
            "FROM user_info " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY DATE(create_time) " +
            "ORDER BY date")
    List<Map<String, Object>> getDailyRegistrationTrend(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 1.1 用户注册趋势 - 按邮箱域名分组
    @Select("SELECT SUBSTRING_INDEX(email, '@', -1) AS domain, COUNT(*) AS count " +
            "FROM user_info " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY domain " +
            "ORDER BY count DESC")
    List<Map<String, Object>> getRegistrationByEmailDomain(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 1.2 用户活跃度 - 月活跃用户 (MAU)
    @Select("SELECT DATE_FORMAT(active_date, '%Y-%m') AS month, COUNT(DISTINCT user_id) AS mau " +
            "FROM (" +
            "   SELECT user_id, DATE(create_time) AS active_date FROM user_image_recognition " +
            "   WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "   UNION " +
            "   SELECT user_id, DATE(create_time) FROM user_ai_conversation " +
            "   WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "   UNION " +
            "   SELECT user_id, DATE(timestamp) FROM user_knowledge_search " +
            "   WHERE timestamp BETWEEN #{startTime} AND #{endTime} " +
            "   UNION " +
            "   SELECT user_id, DATE(timestamp) FROM user_upload " +
            "   WHERE timestamp BETWEEN #{startTime} AND #{endTime} " +
            ") AS activity " +
            "GROUP BY month " +
            "ORDER BY month")
    List<Map<String, Object>> getMonthlyActiveUsers(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 1.3 用户功能使用频率 - 各功能使用次数
    @Select("SELECT '图像识别' AS function_type, COUNT(*) AS count " +
            "FROM user_image_recognition " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "UNION " +
            "SELECT 'AI对话' AS function_type, COUNT(*) AS count " +
            "FROM user_ai_conversation " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "UNION " +
            "SELECT '知识搜索' AS function_type, COUNT(*) AS count " +
            "FROM user_knowledge_search " +
            "WHERE timestamp BETWEEN #{startTime} AND #{endTime} " +
            "UNION " +
            "SELECT '文件上传' AS function_type, COUNT(*) AS count " +
            "FROM user_upload " +
            "WHERE timestamp BETWEEN #{startTime} AND #{endTime}")
    List<Map<String, Object>> getFunctionUsageFrequency(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 2.1 用户上传审核状态 - 文件状态分布
    @Select("SELECT status, COUNT(*) AS count " +
            "FROM user_upload " +
            "WHERE timestamp BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY status")
    List<Map<String, Object>> getUploadStatusDistribution(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 2.2 上传文件质量 - 图片识别准确率
    @Select("SELECT AVG(accuracy) AS avg_accuracy, MIN(accuracy) AS min_accuracy, MAX(accuracy) AS max_accuracy " +
            "FROM user_image_recognition " +
            "WHERE image_path IN (SELECT upload_path FROM user_upload WHERE file_type = '图片') " +
            "AND create_time BETWEEN #{startTime} AND #{endTime}")
    Map<String, Object> getImageRecognitionAccuracy(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 2.3 数据库存储利用率 - 文件存储总量
    @Select("SELECT file_type, SUM(file_size) AS total_size " +
            "FROM user_upload " +
            "WHERE timestamp BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY file_type")
    List<Map<String, Object>> getStorageUsageByFileType(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 3.1 图像识别性能 - 按模型的准确率
    @Select("SELECT model_used, AVG(accuracy) AS avg_accuracy, COUNT(*) AS count " +
            "FROM user_image_recognition " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY model_used")
    List<Map<String, Object>> getImageRecognitionAccuracyByModel(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 3.2 AI 对话响应性能 - 响应时间分布
    @Select("SELECT FLOOR(response_time * 10) / 10 AS time_bucket, COUNT(*) AS count " +
            "FROM user_ai_conversation " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY time_bucket " +
            "ORDER BY time_bucket")
    List<Map<String, Object>> getConversationResponseTimeDistribution(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 3.3 知识搜索性能 - 搜索成功率
    @Select("SELECT search_type, " +
            "SUM(CASE WHEN is_successful = TRUE THEN 1 ELSE 0 END) / COUNT(*) AS success_rate, " +
            "COUNT(*) AS total_count " +
            "FROM user_knowledge_search " +
            "WHERE timestamp BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY search_type")
    List<Map<String, Object>> getKnowledgeSearchSuccessRate(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 4.1 药材和植物数据完整性 - 药材描述缺失率
    @Select("SELECT " +
            "(SUM(CASE WHEN herb_description IS NULL OR herb_description = '' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS missing_rate " +
            "FROM herb")
    Map<String, Object> getHerbDescriptionMissingRate();

    // 4.2 药方使用情况 - 按分类的药方数量
    @Select("SELECT category, COUNT(*) AS count, AVG(rating) AS avg_rating " +
            "FROM prescription " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY category")
    List<Map<String, Object>> getPrescriptionByCategory(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 4.3 文献覆盖率 - 按研究领域的文献数量
    @Select("SELECT research_field, COUNT(*) AS count " +
            "FROM literature " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY research_field")
    List<Map<String, Object>> getLiteratureByResearchField(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 5.1 用户上传文件类型分布
    @Select("SELECT file_type, COUNT(*) AS count " +
            "FROM user_upload " +
            "WHERE timestamp BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY file_type")
    List<Map<String, Object>> getUploadFileTypeDistribution(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // 5.2 AI 对话语言分布
    @Select("SELECT language, COUNT(*) AS count " +
            "FROM user_ai_conversation " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY language")
    List<Map<String, Object>> getConversationLanguageDistribution(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
