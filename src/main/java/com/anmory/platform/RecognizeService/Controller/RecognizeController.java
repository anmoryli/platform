package com.anmory.platform.RecognizeService.Controller;

import com.anmory.platform.RecordService.service.UserImageRecognitionService;
import com.anmory.platform.UserService.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://127.0.0.1", maxAge = 3600)
public class RecognizeController {
    @Autowired
    UserImageRecognitionService userImageRecognitionService;
    @PostMapping("/recognize")
    public String recognize(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        System.out.println("[RecognizeService] 收到图片识别请求");

        StringBuilder output = new StringBuilder();
        Process process = null;
        File tempFile = null;

        try {
            // 1. 获取图片字节流
            byte[] imageBytes = file.getBytes();
            System.out.println("[RecognizeService] 成功读取图片字节流");

            // 2. 构建Python执行环境
            String condaPath = "/root/anaconda3/bin/conda"; // Conda 可执行文件路径
            String envName = "platform"; // Conda 环境名称
            String pythonScript = "/root/predict.py"; // Python 脚本路径

            // 指定临时文件目录
            String tempDirPath = "/root/platform/tmp";
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

            // 3. 使用单一的ProcessBuilder实例
            ProcessBuilder pb = new ProcessBuilder(
                    condaPath, "run", "-n", envName, // 使用 -n 指定 Conda 环境名称
                    "python", pythonScript, tempFile.getAbsolutePath()
            );
            System.out.println("[RecognizeService] 将要执行的命令: " + String.join(" ", pb.command()));

            // 4. 配置进程环境
            pb.redirectErrorStream(true);
            Map<String, String> env = pb.environment();
            env.put("PYTHONIOENCODING", "utf-8");
            env.put("PYTHONUTF8", "1");
            env.put("CONDA_NO_PLUGINS", "true");
            env.put("LANG", "en_US.UTF-8");
            System.out.println("[RecognizeService] 进程环境变量配置完成");

            // 5. 启动进程
            process = pb.start();
            System.out.println("[RecognizeService] Python进程已启动");

            // 6. 读取标准输出和错误流
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                    userImageRecognitionService.insert(user.getId(),tempFile.getName(),tempFile.getAbsolutePath(),output.toString());
                }
                System.out.println("[RecognizeService] 读取Python进程的标准输出");
            }

            // 7. 等待进程结束
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("[RecognizeService] Python进程退出码非零: " + exitCode);
            }
            System.out.println("[RecognizeService] Python进程成功结束");

        } catch (IOException e) {
            System.out.println("[RecognizeService] 文件操作异常: " + e.getMessage());
            throw new RuntimeException("[RecognizeService] 文件操作失败: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("[RecognizeService] 进程中断异常: " + e.getMessage());
            Thread.currentThread().interrupt(); // 恢复中断状态
            throw new RuntimeException("[RecognizeService] 进程中断: " + e.getMessage());
        } finally {
            // 8. 资源清理
            if (process != null) {
                process.destroy();
                System.out.println("[RecognizeService] 已销毁Python进程");
            }
//            if (tempFile != null && tempFile.exists()) {
//                boolean deleted = tempFile.delete();
//                if (deleted) {
//                    System.out.println("[RecognizeService] 已删除临时文件: " + tempFile.getAbsolutePath());
//                } else {
//                    System.out.println("[RecognizeService] 无法删除临时文件: " + tempFile.getAbsolutePath());
//                }
//            }
        }
        return output.toString();
    }
}