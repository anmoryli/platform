package com.anmory.platform.BotService.Controller;

import com.anmory.platform.UserService.User;
import com.anmory.platform.UserService.UserAiConversationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-27 上午10:28
 */

@RestController
public class ImgController {
    @Autowired
    OpenAiImageModel openAiImageModel;
    @Autowired
    UserAiConversationService userAiConversationService;
    @RequestMapping("/genImg")
    public String genImg(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("session_user_key");
        int userId = user == null ? -1 : user.getId();
        String prompt = requestMap.get("userInput");
        Instant start = Instant.now();
        ImageResponse response = openAiImageModel.call(
                new ImagePrompt(prompt,
                        OpenAiImageOptions.builder()
                                .withQuality("hd")
                                .withN(1)
                                .withHeight(1024)
                                .withWidth(1024).build())
        );
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        userAiConversationService.insert(userId, prompt, response.getResult().getOutput().getUrl().toString(), "image", "genImg", (float) duration.toSeconds(), "zh", true);
        return response.getResult().getOutput().getUrl().toString();
    }
}
