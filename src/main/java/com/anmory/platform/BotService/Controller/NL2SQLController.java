package com.anmory.platform.BotService.Controller;

import com.anmory.platform.BotService.Service.Nl2sqlPromptService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @Autowired
    Nl2sqlPromptService nl2sqlPromptService;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;

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
    public String nl2sql(@RequestBody Map<String, String> requestMap) {
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
                String select = jdbcSelect(sql);
                String ret = ChatClient.create(openAiChatModel)
                        .prompt(promptProcess)
                        .user(select)
                        .call()
                        .content();
                System.out.println(ret);
                return ret;
            case 2:
                int update = jdbcUpdate(sql);
                String ret2 = ChatClient.create(openAiChatModel)
                        .prompt(promptProcess)
                        .user(String.valueOf(update))
                        .call()
                        .content();
                System.out.println(ret2);
                return ret2;
            case 3:
                String ret3;
                if(jdbcCreate(sql)) {
                    ret3 = ChatClient.create(openAiChatModel)
                            .prompt(promptProcess)
                            .user("执行成功")
                            .call()
                            .content();
                    System.out.println(ret3);
                    return ret3;
                }
                else{
                    ret3 = ChatClient.create(openAiChatModel)
                            .prompt(promptProcess)
                            .user("执行失败")
                            .call()
                            .content();
                    System.out.println(ret3);
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
