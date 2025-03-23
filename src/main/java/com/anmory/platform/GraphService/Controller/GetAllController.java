package com.anmory.platform.GraphService.Controller;

import com.anmory.platform.GraphService.Service.GetAllService;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-15 下午9:11
 */

@Slf4j
@RestController
public class GetAllController {
    @Autowired
    private GetAllService getAllService;

    @GetMapping("/getAllNodes")
    public List<Map<String, Object>> getAllNodes() {
        return getAllService.getAllNodes();
    }

    @GetMapping("/echarts")
    public Map<String, Object> getEChartsData() {
        log.info("获取nodes数据成功");
        return getAllService.getEChartsData();
    }

    @GetMapping("/queryByName")
    public Map<String, Object> queryByName(@RequestParam String name) {
        log.info("[queryByName]获取结点成功");
        return getAllService.query(name);
    }

    // 在spring关闭应用时，关闭neo4j连接
    @PreDestroy
    public void cleanup() {
        getAllService.close();
    }
}

