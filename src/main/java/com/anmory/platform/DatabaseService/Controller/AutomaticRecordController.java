package com.anmory.platform.DatabaseService.Controller;

import com.anmory.platform.BotService.Tools.NL2SQL;
import com.anmory.platform.DatabaseService.Dao.Plant;
import com.anmory.platform.DatabaseService.Service.GetField;
import com.anmory.platform.DatabaseService.Service.GetTable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-24 下午11:43
 */

@RestController
@RequestMapping("/automatic")
public class AutomaticRecordController {
    @Autowired
    GetTable getTable;
    @Autowired
    GetField getField;
    @Autowired
    OpenAiChatModel openAiChatModel;
    @RequestMapping("/record")
    public String record(String type,String name) {
        String table = getTable.getField(type);
        if(table == null) {
            return "请输入正确的类型";
        }
        Object field = getField.getField(table);
        String prompt = "请你根据" + field.toString() + "的属性，生成一个插入" + table + "的" +name + "的详细信息，除了图片可以没有，其他都必须有," +
                "不要用json格式，不要用markdown格式，每一个字段请换行处理，并且最后请加上仅供参考，请把字段名称翻译成中文返回哈";
        String response = ChatClient.create(openAiChatModel)
                .prompt()
                .user(prompt)
                .call()
                .content();
        System.out.println(response);
        return response;
    }
}
