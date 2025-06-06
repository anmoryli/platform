package com.anmory.platform.BotService.Controller;

import com.anmory.platform.BotService.Service.Nl2sqlPromptService;
import com.anmory.platform.UserService.User;
import com.anmory.platform.UserService.UserAiConversationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * @author
 * @description TODO
 * @date 2025-04-24 下午3:47
 */

@RestController
@RequestMapping("/bot")
public class NL2SQLController {
    @Autowired
    OpenAiChatModel openAiChatModel;

    @Autowired
    Nl2sqlPromptService nl2sqlPromptService;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private UserAiConversationService userAiConversationService;

    private String jdbcSelect(String sql) {
        return jdbcTemplate.queryForList(sql).toString();
    }

    private int jdbcUpdate(String sql) {
        return jdbcTemplate.update(sql);
    }

    private boolean jdbcCreate(String sql) {
        try{
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @RequestMapping("/nl2sql")
    public String nl2sql(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("session_user_key");
        int userId = user == null ? -1 : user.getId();
        String naturalLanguage = requestMap.get("userInput");
        System.out.println(naturalLanguage);
        String prompt = nl2sqlPromptService.selectPromptGenSql();
        String promptProcess = nl2sqlPromptService.selectPromptProcessRet();
        System.out.println("NL2SQL被调用");
        String sql = ChatClient.create(openAiChatModel)
                .prompt(prompt)
                .user(naturalLanguage)
//                .tools(new NL2SQL())
                .call()
                .content();
        System.out.println(sql);
        int function = decideFunction(sql);
        switch(function) {
            case 1:
                Instant start = Instant.now();
                String select = jdbcSelect(sql);
                String ret = ChatClient.create(openAiChatModel)
                        .prompt(promptProcess)
                        .user(select)
                        .call()
                        .content();
                System.out.println(ret);
                Instant end = Instant.now();
                Duration duration = Duration.between(start, end);
                userAiConversationService.insert(userId, naturalLanguage, ret, "NL2SQL", (String) null, (float) duration.toMillis(), "zh", true);
                return ret;
            case 2:
                Instant start2 = Instant.now();
                int update = jdbcUpdate(sql);
                String ret2 = ChatClient.create(openAiChatModel)
                        .prompt(promptProcess)
                        .user(String.valueOf(update))
                        .call()
                        .content();
                System.out.println(ret2);
                Instant end2 = Instant.now();
                Duration duration2 = Duration.between(start2, end2);
                userAiConversationService.insert(userId, naturalLanguage, ret2, "NL2SQL", (String) null, (float) duration2.toMillis(), "zh", true);
                return ret2;
            case 3:
                Instant start3 = Instant.now();
                String ret3;
                if(jdbcCreate(sql)) {
                    ret3 = ChatClient.create(openAiChatModel)
                            .prompt(promptProcess)
                            .user("执行成功")
                            .call()
                            .content();
                    System.out.println(ret3);
                    Instant end3 = Instant.now();
                    Duration duration3 = Duration.between(start3, end3);
                    userAiConversationService.insert(userId, naturalLanguage, ret3, "NL2SQL", (String) null, (float) duration3.toMillis(), "zh", true);
                    return ret3;
                }
                else{
                    Instant start4 = Instant.now();
                    ret3 = ChatClient.create(openAiChatModel)
                            .prompt(promptProcess)
                            .user("执行失败")
                            .call()
                            .content();
                    System.out.println(ret3);
                    Instant end4 = Instant.now();
                    Duration duration4 = Duration.between(start4, end4);
                    userAiConversationService.insert(userId, naturalLanguage, ret3, "NL2SQL", (String) null, (float) duration4.toMillis(), "zh", true);
                    return ret3;
                }
            default:
                return "操作错误，请重试";
        }
    }

    private int decideFunction(String sql) {
        if (sql.contains("select")) {
            return 1;
        } else if (sql.contains("update") || sql.contains("insert") || sql.contains("delete")) {
            return 2;
        } else if (sql.contains("create") || sql.contains("drop") || sql.contains("alter")) {
            return 3;
        }
        return 0;
    }
}
