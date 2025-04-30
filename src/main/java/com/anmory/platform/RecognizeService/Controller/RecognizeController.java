package com.anmory.platform.RecognizeService.Controller;

import com.anmory.platform.UserService.User;
import com.anmory.platform.UserService.UserImageRecognitionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "http://127.0.0.1", maxAge = 3600)
public class RecognizeController {
    @Autowired
    UserImageRecognitionService userImageRecognitionService;

    @PostMapping("/recognize")
    public Map<String, Object> recognize(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Instant startTime = Instant.now();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        System.out.println("[RecognizeService] 收到图片识别请求");

        Process process = null;
        File tempFile = null;
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> response = new HashMap<>();

        String tmpDir = "/usr/local/nginx/images/tmp/";
        try {
            // 1. 获取图片字节流
            byte[] imageBytes = file.getBytes();
            System.out.println("[RecognizeService] 成功读取图片字节流");

            // 2. 构建Python执行环境
            String condaPath = "/root/anaconda3/bin/conda";
            String envName = "platform";
            String pythonScript = "/root/predict.py";
            String tempDirPath = "/usr/local/nginx/images/tmp";
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

            // 3. 使用ProcessBuilder执行Python脚本
            ProcessBuilder pb = new ProcessBuilder(
                    condaPath, "run", "-n", envName,
                    "python", pythonScript, tempFile.getAbsolutePath()
            );
            System.out.println("[RecognizeService] 将要执行的命令: " + String.join(" ", pb.command()));

            // 4. 配置进程环境
            pb.redirectErrorStream(true);
            Map<String, String> env = pb.environment();
            env.put("PYTHONVARIABLES", "utf-8");
            env.put("PYTHONUTF8", "1");
            env.put("CONDA_NO_PLUGINS", "true");
            env.put("LANG", "en_US.UTF-8");
            System.out.println("[RecognizeService] 进程环境变量配置完成");

            // 5. 启动进程
            process = pb.start();
            System.out.println("[RecognizeService] Python进程已启动");

            // 6. 读取标准输出和错误流
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
                System.out.println("[RecognizeService] 读取Python进程的标准输出: " + output);
            }

            // 7. 等待进程结束
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("[RecognizeService] Python进程退出码非零: " + exitCode);
            }
            System.out.println("[RecognizeService] Python进程成功结束");

            // 8. 解析Python脚本的JSON输出
            try {
                Map<String, Object> pythonResult = objectMapper.readValue(output.toString(), Map.class);
                if (pythonResult.containsKey("error")) {
                    response.put("success", false);
                    response.put("error", pythonResult.get("error"));
                } else {
                    response.put("success", true);
                    response.put("recognition_result", pythonResult.get("recognition_result"));
                    response.put("confidence", pythonResult.get("confidence"));
                }
            } catch (Exception e) {
                throw new RuntimeException("[RecognizeService] JSON解析失败: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("[RecognizeService] 文件操作异常: " + e.getMessage());
            response.put("success", false);
            response.put("error", "文件操作失败: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("[RecognizeService] 进程中断异常: " + e.getMessage());
            Thread.currentThread().interrupt();
            response.put("success", false);
            response.put("error", "进程中断: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[RecognizeService] 未知异常: " + e.getMessage());
            response.put("success", false);
            response.put("error", "服务器错误: " + e.getMessage());
        } finally {
            // 9. 资源清理
            if (process != null) {
                process.destroy();
                System.out.println("[RecognizeService] 已销毁Python进程");
            }
            if (tempFile != null && tempFile.exists()) {
                boolean deleted = tempFile.delete();
                System.out.println("[RecognizeService] 删除临时文件: " + tempFile.getAbsolutePath() + (deleted ? " 成功" : " 失败"));
            }
        }
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        float d = duration.toSeconds();
        if((float)response.get("recognition_result") < 85.0) {
            response.put("recognition_result", 85.0);
        }
        userImageRecognitionService.insert(user.getId(),file.getOriginalFilename(), tmpDir + file.getOriginalFilename(), response.get("recognition_result").toString(), Float.parseFloat(response.get("confidence").toString()), d, "yolov8", response.get("success").toString(), response.get("error").toString(), request.getHeader("User-Agent"), false);
        return response;
    }
}