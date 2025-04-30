package com.anmory.platform.GraphService.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description TODO
 * @date 2025-04-05 下午6:56
 */

@Service
public class SelfGraphService {
    private static final String BASE_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "sk-c1de8d51734546a8ba435dd905c3b02b";
    public String selfGraph(String userInput) throws IOException {
        System.out.println(userInput);
        // 创建请求体
        var requestBody = new JsonObject();
        requestBody.addProperty("model", "deepseek-chat");
        requestBody.addProperty("stream", false);

        // 添加消息
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", "你需要对用户的需求格式化输出，输出的语句是cypher语句，" +
                "并且你只能输出cypher语句，cypher语句必须是能够创建neo4j实体之间关系的语句，具体你可以参考这个，根据实际情况来" +
                "# 创建药材节点\n" +
                "            herb = create_or_get_node(\"Herb\", {\"name\": herb_name, \"origin\": origin})\n" +
                "\n" +
                "            # 创建关系\n" +
                "            for effect in effects:\n" +
                "                effect_node = create_or_get_node(\"Effect\", {\"name\": effect})\n" +
                "                graph.create(Relationship(herb, \"药效\", effect_node))\n" +
                "                logging.info(f\"Created relationship 药效 between {herb_name} and {effect}\")\n" +
                "\n" +
                "            for disease in diseases:\n" +
                "                disease_node = create_or_get_node(\"Disease\", {\"name\": disease})\n" +
                "                graph.create(Relationship(herb, \"相关疾病\", disease_node))\n" +
                "                logging.info(f\"Created relationship 相关疾病 between {herb_name} and {disease}\")\n" +
                "\n" +
                "            for part in parts:\n" +
                "                part_node = create_or_get_node(\"Part\", {\"name\": part})\n" +
                "                graph.create(Relationship(herb, \"药用部位\", part_node))\n" +
                "                logging.info(f\"Created relationship 药用部位 between {herb_name} and {part}\")\n" +
                "\n" +
                "            for chemical in chemicals:\n" +
                "                chemical_node = create_or_get_node(\"Chemical\", {\"name\": chemical})\n" +
                "                graph.create(Relationship(herb, \"化学组成\", chemical_node))\n" +
                "                logging.info(f\"Created relationship 化学组成 between {herb_name} and {chemical}\")" +
                "这个是全部你可以参考的，但你只需要根据用户的需求提供所有符合要求的cypher语句");
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
}
