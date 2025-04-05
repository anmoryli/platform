package com.anmory.platform.UploadService;

import com.anmory.platform.UserService.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午8:14
 */

@RestController
public class UploadController {
    @Autowired
    UserUploadService userUploadService;
    @RequestMapping("/uploadImg")
    public boolean uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        File tempFile = null;
        // 1. 获取图片字节流
        byte[] imageBytes = file.getBytes();
        System.out.println("[RecognizeService] 成功读取图片字节流");

        // 指定临时文件目录
        String tempDirPath = "/root/platform/peding-review-imgs";
        File tempDir = new File(tempDirPath);

        // 确保目录存在
        if (!tempDir.exists()) {
            boolean created = tempDir.mkdirs();
            if (!created) {
                throw new IOException("无法创建临时文件目录: " + tempDirPath);
            }
            System.out.println("[RecognizeService] 创建临时文件目录: " + tempDirPath);
        }

        // 创建临时文件并写入图片数据
        tempFile = File.createTempFile("image", ".jpg", tempDir);
        System.out.println("[RecognizeService] 创建临时文件: " + tempFile.getAbsolutePath());
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(imageBytes);
            System.out.println("[RecognizeService] 成功将图片数据写入临时文件");
        }
        userUploadService.insert(user.getId(), tempFile.getName(), tempFile.getAbsolutePath());
        return true;
    }
}
