package com.anmory.platform.VisualizationService;

import com.anmory.platform.RecordService.service.UserAiConversationService;
import com.anmory.platform.RecordService.service.UserImageRecognitionService;
import com.anmory.platform.RecordService.service.UserKnowledgeSearchService;
import com.anmory.platform.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午7:53
 */

@RestController
public class VisualizationController {
    @Autowired
    UserService userService;
    @Autowired
    UserAiConversationService userAiConversationService;
    @Autowired
    UserKnowledgeSearchService userKnowledgeSearchService;
    @Autowired
    UserImageRecognitionService userImageRecognitionService;
    @RequestMapping("/getVisualData")
    public Map<String, Integer> getVisualData() throws Exception{
        Map<String, Integer> ret = new HashMap<>();
        int cntUsers = userService.countUsers();
        ret.put("cntUsers", cntUsers);

        int cntKnowledgeSearch = userKnowledgeSearchService.countKnowledgeSearch();
        ret.put("cntKnowledgeSearch", cntKnowledgeSearch);

        int cntImages = userImageRecognitionService.countImages();
        ret.put("cntImages", cntImages);
        return ret;
    }
}
