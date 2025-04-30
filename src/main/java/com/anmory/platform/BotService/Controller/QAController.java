package com.anmory.platform.BotService.Controller;

import com.anmory.platform.BotService.Service.GetEntityService;
import com.anmory.platform.UserService.User;
import com.anmory.platform.UserService.UserAiConversationService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * @author
 * @description TODO
 * @date 2025-03-24 下午9:43
 */

@RestController
public class QAController {
    private static final String BASE_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "sk-792025c6193c4f53afdcfddbaa1041e5";
    @Autowired
    Neo4jService neo4jService;
    @Autowired
    GetEntityService getEntityService;
    @Autowired
    UserAiConversationService userAiConversationService;
    @RequestMapping("/qa")
    public String qa(@RequestBody Map<String, String> requestMap, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("session_user_key");
        int userId = user == null ? -1 : user.getId();
        String userInput = requestMap.get("userInput");
        System.out.println(userInput);
        Instant start = Instant.now();
        // 创建请求体
        var requestBody = new JsonObject();
        requestBody.addProperty("model", "deepseek-chat");
        requestBody.addProperty("stream", false);

        // 添加消息
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", "你需要简短地恢复用户的问题，不超过30个字，简明扼要地指出答案，例如：" +
                "红景天的药效是缓解高原反应");

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", userInput);

        JsonArray messages = new JsonArray();
        messages.add(systemMessage);
        messages.add(userMessage);
        requestBody.add("messages", messages);

        // 记录请求体
        System.out.println("Request Body" + requestBody);

        // 发送HTTP POST请求
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setRequestProperty("Content-Type", "application/json"); // 修正 Content-Type
        conn.setDoOutput(true);

        // 记录请求头
        System.out.println("Request Headers:" + conn.getRequestProperties());
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 记录响应内容
            System.out.println("Response Body: " + response.toString());

            // 解析并打印响应内容
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
            String replyContent = jsonResponse.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .get("message").getAsJsonObject()
                    .get("content").getAsString();
            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            userAiConversationService.insert(userId, userInput, replyContent, "qa", null, Float.parseFloat(String.valueOf(duration.toMillis())), "zh", true);
            return replyContent;

        } else {
            // 记录错误响应内容
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
        }
        return "访问出错";
    }

//    private static String extractPlantName(String question) {
//        System.out.println("[question]" + question);
//        return question.split("可以治疗什么疾病")[0].trim();
//    }

//    private String queryDiseaseForPlant(Object entity) {
//        String cypher = String.format(
//                "MATCH (p:`药材` {Name: \"%s\"})-[r:`药效`]->(n:`药材`) RETURN n.Name LIMIT 1",plantName
//        );
//        System.out.println(cypher);
//        List<String> diseases = neo4jService.executeQuery(cypher);
//        return String.join(",",diseases);
//    }
}
