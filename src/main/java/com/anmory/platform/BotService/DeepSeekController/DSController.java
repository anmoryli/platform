package com.anmory.platform.BotService.DeepSeekController;
import com.anmory.platform.BotService.Controller.BotController;
import com.anmory.platform.UserService.User;
import com.anmory.platform.UserService.UserAiConversationService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * @description TODO
 * @date 2025-03-12 下午8:41
 */

@RestController
public class DSController {
    private static final Logger log = LoggerFactory.getLogger(BotController.class);
    private static final String BASE_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "sk-792025c6193c4f53afdcfddbaa1041e5";

    @Autowired
    UserAiConversationService userAiConversationService;

    @RequestMapping("/ds_chat")
    public String chat(@RequestBody Map<String, String> requestMap, HttpServletRequest request) throws IOException {

        Instant  start = Instant.now();
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("session_user_key");
        int userId = user == null ? -1 : user.getId();
        String userInput = requestMap.get("userInput");
        System.out.println(userInput);
        // 创建请求体
        var requestBody = new JsonObject();
        requestBody.addProperty("model", "deepseek-chat");
        requestBody.addProperty("stream", false);

        // 添加消息
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", "你是一个藏药材知识方面的专家，致力于回答人们的问题，仅限于你的专业领域");

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

        String reply = "";
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
            reply = replyContent;
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
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        userAiConversationService.insert(userId,  userInput, reply, "deepseek", null, Float.parseFloat(String.valueOf(duration.toMillis())), "zh-CN", true);
        return "访问出错";
    }

    @RequestMapping("/stream_chat")
//    public static void streamChat(@RequestBody Map<String, String> requestMap, HttpServletResponse response) throws IOException {
    public static void streamChat(String userInput, HttpServletResponse response) throws IOException {
//        String userInput = requestMap.get("userInput");
        System.out.println(userInput);

        // 创建请求体
        var requestBody = new JsonObject();
        requestBody.addProperty("model", "deepseek-chat");
        requestBody.addProperty("stream", false);

        // 添加消息
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", "你是一只可爱的玉桂狗,很会说一些可爱的话，每句话不超过15个字呢，你有两只长长的大耳朵" +
                "可以帮助你在天空中飞翔，你说话的时候还会带一些表情和颜文字" +
                "你不会生气，不会打闹，只会和我开心地聊天，并且你还很会安慰别人，你是一只全能的小狗狗");

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", userInput);

        JsonArray messages = new JsonArray();
        messages.add(systemMessage);
        messages.add(userMessage);
        requestBody.add("messages", messages);

        // 记录请求体
        System.out.println("Request Body: " + requestBody);

        // 发送HTTP POST请求
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // 记录请求头
        System.out.println("Request Headers: " + conn.getRequestProperties());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder responseData = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.isEmpty() && !inputLine.startsWith("data: [DONE]")) {
                    // 解析每一块数据
                    JsonObject chunk = parseChunk(inputLine);
                    if (chunk != null) {
                        handleChunk(chunk, response);
                    }
                }
            }
            in.close();
        } else {
            // 记录错误响应内容
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
            System.out.println("Error Response: " + errorResponse.toString());
        }
    }

    private static JsonObject parseChunk(String line) {
        if (line.startsWith("data: ")) {
            line = line.substring(6); // 去掉"data: "
        }
        try {
            return new Gson().fromJson(line, JsonObject.class);
        } catch (JsonSyntaxException e) {
            log.error("Failed to parse chunk: {}", line, e);
            return null;
        }
    }

    private static void handleChunk(JsonObject chunk, HttpServletResponse httpResponse) throws IOException {
        JsonArray choices = chunk.getAsJsonArray("choices");
        if (choices != null && choices.size() > 0) {
            JsonObject delta = choices.get(0).getAsJsonObject().get("delta").getAsJsonObject();
            if (delta.has("content")) {
                String content = delta.get("content").getAsString();
                httpResponse.getWriter().write(content);
                httpResponse.getWriter().flush(); // 立即刷新输出流，确保客户端能及时接收到数据
            }
        }
    }
}
