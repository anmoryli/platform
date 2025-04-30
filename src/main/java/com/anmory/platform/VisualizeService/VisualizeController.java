package com.anmory.platform.VisualizeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visualize")
public class VisualizeController {

    @Autowired
    private VisualizeService visualizeService;

    /**
     * 1.1 用户注册趋势 - 每日注册用户数量
     * 前端推荐图表：折线图（Line Chart）
     * 数据格式：[{ "date": "2024-01-01", "count": 5 }, ...]
     */
    @GetMapping("/daily-registration-trend")
    public List<Map<String, Object>> getDailyRegistrationTrend(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getDailyRegistrationTrend(startTime, endTime);
    }

    /**
     * 1.1 用户注册趋势 - 按邮箱域名分组
     * 前端推荐图表：堆叠柱状图（Stacked Bar Chart）
     * 数据格式：[{ "domain": "qq.com", "count": 10 }, ...]
     */
    @GetMapping("/registration-by-email-domain")
    public List<Map<String, Object>> getRegistrationByEmailDomain(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getRegistrationByEmailDomain(startTime, endTime);
    }

    /**
     * 1.2 用户活跃度 - 月活跃用户 (MAU)
     * 前端推荐图表：折线图（Line Chart）
     * 数据格式：[{ "month": "2024-01", "mau": 100 }, ...]
     */
    @GetMapping("/monthly-active-users")
    public List<Map<String, Object>> getMonthlyActiveUsers(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getMonthlyActiveUsers(startTime, endTime);
    }

    /**
     * 1.3 用户功能使用频率 - 各功能使用次数
     * 前端推荐图表：饼图（Pie Chart）
     * 数据格式：[{ "function_type": "图像识别", "count": 50 }, ...]
     */
    @GetMapping("/function-usage-frequency")
    public List<Map<String, Object>> getFunctionUsageFrequency(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getFunctionUsageFrequency(startTime, endTime);
    }

    /**
     * 2.1 用户上传审核状态 - 文件状态分布
     * 前端推荐图表：环形图（Doughnut Chart）
     * 数据格式：[{ "status": "成功", "count": 30 }, ...]
     */
    @GetMapping("/upload-status-distribution")
    public List<Map<String, Object>> getUploadStatusDistribution(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getUploadStatusDistribution(startTime, endTime);
    }

    /**
     * 2.2 上传文件质量 - 图片识别准确率
     * 前端推荐图表：箱线图（Box Plot）
     * 数据格式：{ "avg_accuracy": 0.9, "min_accuracy": 0.8, "max_accuracy": 0.95 }
     */
    @GetMapping("/image-recognition-accuracy")
    public Map<String, Object> getImageRecognitionAccuracy(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getImageRecognitionAccuracy(startTime, endTime);
    }

    /**
     * 2.3 数据库存储利用率 - 文件存储总量
     * 前端推荐图表：面积图（Area Chart）
     * 数据格式：[{ "file_type": "图片", "total_size": 1024000 }, ...]
     */
    @GetMapping("/storage-usage-by-file-type")
    public List<Map<String, Object>> getStorageUsageByFileType(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getStorageUsageByFileType(startTime, endTime);
    }

    /**
     * 3.1 图像识别性能 - 按模型的准确率
     * 前端推荐图表：柱状图（Bar Chart）
     * 数据格式：[{ "model_used": "yolo", "avg_accuracy": 0.9, "count": 50 }, ...]
     */
    @GetMapping("/image-recognition-accuracy-by-model")
    public List<Map<String, Object>> getImageRecognitionAccuracyByModel(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getImageRecognitionAccuracyByModel(startTime, endTime);
    }

    /**
     * 3.2 AI 对话响应性能 - 响应时间分布
     * 前端推荐图表：直方图（Histogram）
     * 数据格式：[{ "time_bucket": 0.8, "count": 10 }, ...]
     */
    @GetMapping("/conversation-response-time-distribution")
    public List<Map<String, Object>> getConversationResponseTimeDistribution(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getConversationResponseTimeDistribution(startTime, endTime);
    }

    /**
     * 3.3 知识搜索性能 - 搜索成功率
     * 前端推荐图表：柱状图（Bar Chart）
     * 数据格式：[{ "search_type": "关键字搜索", "success_rate": 0.95, "total_count": 100 }, ...]
     */
    @GetMapping("/knowledge-search-success-rate")
    public List<Map<String, Object>> getKnowledgeSearchSuccessRate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getKnowledgeSearchSuccessRate(startTime, endTime);
    }

    /**
     * 4.1 药材和植物数据完整性 - 药材描述缺失率
     * 前端推荐图表：进度条（Progress Bar）
     * 数据格式：{ "missing_rate": 10.5 }
     */
    @GetMapping("/herb-description-missing-rate")
    public Map<String, Object> getHerbDescriptionMissingRate() {
        return visualizeService.getHerbDescriptionMissingRate();
    }

    /**
     * 4.2 药方使用情况 - 按分类的药方数量
     * 前端推荐图表：柱状图（Bar Chart）
     * 数据格式：[{ "category": "补益", "count": 5, "avg_rating": 4.2 }, ...]
     */
    @GetMapping("/prescription-by-category")
    public List<Map<String, Object>> getPrescriptionByCategory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getPrescriptionByCategory(startTime, endTime);
    }

    /**
     * 4.3 文献覆盖率 - 按研究领域的文献数量
     * 前端推荐图表：柱状图（Bar Chart）
     * 数据格式：[{ "research_field": "药理作用", "count": 20 }, ...]
     */
    @GetMapping("/literature-by-research-field")
    public List<Map<String, Object>> getLiteratureByResearchField(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getLiteratureByResearchField(startTime, endTime);
    }

    /**
     * 5.1 用户上传文件类型分布
     * 前端推荐图表：饼图（Pie Chart）
     * 数据格式：[{ "file_type": "图片", "count": 40 }, ...]
     */
    @GetMapping("/upload-file-type-distribution")
    public List<Map<String, Object>> getUploadFileTypeDistribution(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getUploadFileTypeDistribution(startTime, endTime);
    }

    /**
     * 5.2 AI 对话语言分布
     * 前端推荐图表：饼图（Pie Chart）
     * 数据格式：[{ "language": "中文", "count": 100 }, ...]
     */
    @GetMapping("/conversation-language-distribution")
    public List<Map<String, Object>> getConversationLanguageDistribution(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return visualizeService.getConversationLanguageDistribution(startTime, endTime);
    }
}