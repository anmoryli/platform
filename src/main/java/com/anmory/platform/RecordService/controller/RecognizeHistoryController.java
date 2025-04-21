package com.anmory.platform.RecordService.controller;

import com.anmory.platform.RecordService.model.UserImageRecognition;
import com.anmory.platform.RecordService.service.UserImageRecognitionService;
import com.anmory.platform.UserService.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-08 下午11:57
 */

@RestController
public class RecognizeHistoryController {
    @Autowired
    UserImageRecognitionService userImageRecognitionService;
    @RequestMapping("/getRecognitionHistory")
    public List<UserImageRecognition> getRecognitionHistory(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        return userImageRecognitionService.getAll(user.getId());
    }
}