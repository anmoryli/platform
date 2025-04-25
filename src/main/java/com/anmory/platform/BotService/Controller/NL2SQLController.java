package com.anmory.platform.BotService.Controller;

import com.anmory.platform.BotService.Tools.NL2SQL;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-24 下午3:47
 */

@RestController
@RequestMapping("/bot")
public class NL2SQLController {
    @Autowired
    OpenAiChatModel openAiChatModel;

    @RequestMapping("/nl2sql")
    public String nl2sql(String naturalLanguage) {
        System.out.println("布豪!!我被调用辣");
        String response = ChatClient.create(openAiChatModel)
                .prompt()
                .user(naturalLanguage)
                .tools(new NL2SQL())
                .call()
                .content();
        System.out.println(response);
        return response;
    }
}
