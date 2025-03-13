package com.anmory.platform.BotService.DeepSeekController;

import com.anmory.platform.BotService.Controller.BotController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-12 下午8:41
 */

@RestController
public class DSController {
    private static final Logger log = LoggerFactory.getLogger(BotController.class);
    private static final String API_URL = "https://api.deepseek.com"; // 替换为实际的AI服务端点
    private static final String AUTH_TOKEN = "Bearer sk-c1de8d51734546a8ba435dd905c3b02b"; // 替换为你的访问令牌

    @RequestMapping("/ds_chat")
    public static String chat(Map<String, String> userInput) {
        ObjectMapper mapper = new ObjectMapper();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", AUTH_TOKEN);

        try {
            String jsonInputString = mapper.writeValueAsString(userInput);
            StringEntity entity = new StringEntity(jsonInputString);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String result = EntityUtils.toString(responseEntity);
                    JsonNode rootNode = mapper.readTree(result);
                    JsonNode contentNode = rootNode.path("content");
                    if (!contentNode.isMissingNode()) {
                        return contentNode.asText();
                    } else {
                        System.out.println("DeepSeekController - Error: No content field in the response.");
                        return "AI助手无响应";
                    }
                } else {
                    System.out.println("DeepSeekController - Error: No response entity.");
                    return "AI助手无响应";
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (JsonProcessingException e) {
            System.out.println("DeepSeekController - Error processing JSON: " + e.getMessage());
            return "AI助手无响应";
        } catch (IOException e) {
            System.out.println("DeepSeekController - Error during HTTP request: " + e.getMessage());
            return "AI助手无响应";
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.out.println("DeepSeekController - Error closing HTTP client: " + e.getMessage());
            }
        }
    }
}
