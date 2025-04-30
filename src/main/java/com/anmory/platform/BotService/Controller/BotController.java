package com.anmory.platform.BotService.Controller;

import com.anmory.platform.UserService.UserAiConversationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author
 * @description TODO
 * @date 2025-02-25 上午6:53
 */

@RestController
public class BotController {

    @Autowired
    UserAiConversationService userAiConversationService;

    private static final Logger log = LoggerFactory.getLogger(BotController.class);
    private static final String API_URL = "https://spark-api-open.xf-yun.com/v1/chat/completions"; // 替换为实际的AI服务端点
    private static final String AUTH_TOKEN = "Bearer CTQUYSHCIGdRVonLfhjF:pFWDzXNDyCVEzsxFqcHG"; // 替换为你的访问令牌

    @RequestMapping("/chat")
    public String chat(@RequestBody Map<String, String> request) {
        String userInput = request.get("userInput").trim();
        if (userInput.isEmpty()) {
            return "请输入内容";
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", AUTH_TOKEN);

            ObjectMapper objectMapper = new ObjectMapper();

            // 构建请求体
//
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "4.0Ultra"); // 根据需求选择模型版本
            List<Map<String, String>> messages = new ArrayList<>();

            // 系统角色消息
            messages.add(new HashMap<>() {{
                put("role", "system");
                put("content", "你是一个藏药植物药方面的专家，你只能回答该方面的问题，其他任何问题你都不能回答");
            }});

            // 用户消息
            messages.add(new HashMap<>() {{
                put("role", "user");
                put("content", userInput);
            }});

            requestBody.put("messages", messages);
            requestBody.put("temperature", 0.5);
            requestBody.put("top_k", 4);
            requestBody.put("max_tokens", 8000);
            requestBody.put("presence_penalty", 1.0);
            requestBody.put("frequency_penalty", 1.0);
            requestBody.put("stream", true); // 流式响应

            // 将请求体转换为 JSON 字符串
            String jsonRequest = objectMapper.writeValueAsString(requestBody);
            StringEntity entity = new StringEntity(jsonRequest, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);

            // 发送请求并处理响应
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                // 处理流式响应
                StringBuilder botResponse = new StringBuilder();
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("data:")) {
                                line = line.substring(5).trim(); // 去除"data:"前缀
                                if (line.equals("[DONE]")) {
                                    break; // 流式响应结束
                                }
                                try {
                                    JsonNode jsonResponse = objectMapper.readTree(line);
                                    JsonNode choicesNode = jsonResponse.path("choices");
                                    if (choicesNode.isArray() && choicesNode.size() > 0) {
                                        JsonNode deltaNode = choicesNode.get(0).path("delta");
                                        String deltaContent = deltaNode.path("content").asText();
                                        if (deltaContent != null && !deltaContent.isEmpty()) {
                                            botResponse.append(deltaContent);
                                        }
                                    }
                                } catch (IOException e) {
                                    log.error("Error parsing JSON: ", e);
                                }
                            }
                        }
                    }
                }
                return botResponse.toString();
            }

        } catch (IOException e) {
            log.error("Error: ", e);
            return "";
        }
    }

    @RequestMapping("/xunfei")
    public String chat_2(String userInput) {
        if (userInput.isEmpty()) {
            return "请输入内容";
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", AUTH_TOKEN);

            ObjectMapper objectMapper = new ObjectMapper();

            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "4.0Ultra"); // 根据需求选择模型版本
            List<Map<String, String>> messages = new ArrayList<>();

            // 系统角色消息
            messages.add(new HashMap<>() {{
                put("role", "system");
                put("content", "你是一个藏药植物药方面的专家，你只能回答该方面的问题，其他任何问题你都不能回答");
            }});

            // 用户消息
            messages.add(new HashMap<>() {{
                put("role", "user");
                put("content", userInput);
            }});

            requestBody.put("messages", messages);
            requestBody.put("temperature", 0.5);
            requestBody.put("top_k", 4);
            requestBody.put("max_tokens", 8000);
            requestBody.put("presence_penalty", 1.0);
            requestBody.put("frequency_penalty", 1.0);
            requestBody.put("stream", true); // 流式响应

            // 将请求体转换为 JSON 字符串
            String jsonRequest = objectMapper.writeValueAsString(requestBody);
            StringEntity entity = new StringEntity(jsonRequest, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);

            // 发送请求并处理响应
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                // 处理流式响应
                StringBuilder botResponse = new StringBuilder();
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("data:")) {
                                line = line.substring(5).trim(); // 去除"data:"前缀
                                if (line.equals("[DONE]")) {
                                    break; // 流式响应结束
                                }
                                try {
                                    JsonNode jsonResponse = objectMapper.readTree(line);
                                    JsonNode choicesNode = jsonResponse.path("choices");
                                    if (choicesNode.isArray() && choicesNode.size() > 0) {
                                        JsonNode deltaNode = choicesNode.get(0).path("delta");
                                        String deltaContent = deltaNode.path("content").asText();
                                        if (deltaContent != null && !deltaContent.isEmpty()) {
                                            botResponse.append(deltaContent);
                                        }
                                    }
                                } catch (IOException e) {
                                    log.error("Error parsing JSON: ", e);
                                }
                            }
                        }
                    }
                }
                return botResponse.toString();
            }

        } catch (IOException e) {
            log.error("Error: ", e);
            return "";
        }
    }
}