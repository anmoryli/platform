package com.anmory.platform.VoiceService;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-16 下午6:46
 */

@RestController
public class VoiceController {
    // 控制台获取以下信息
    private static String APP_ID = "96ed9cf1";
    private static String API_SECRET = "MmM4YzQxZDVjOGVlNzNmZTg5ZWRlZjc1";
    private static String API_KEY = "d61c1fa5dba05ea1c3bb3dec297d7a15";

    private static String FILE_URL_PREFIX = "https://upload-ost-api.xfyun.cn/file"; // 上传文件的地址开头

    private static String OPEN_URL_PREFIX = "https://ost-api.xfyun.cn/v2"; // 创建、查询任务的地址开头
    private static final Gson gson=new Gson();
    private static final String AUDIO_PATH="audioExample/audio_sample_little.wav"; // 在此指定音频文件路径
    private static final int SLICE_SIZE = 15728640;// 15M，每块范围 5M~32M
    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    @PostMapping("/voice")
    public void voice(@RequestParam("audio") MultipartFile file) throws IOException, InterruptedException {
        System.out.println("[VoiceService] 收到语音识别请求: " + file.getOriginalFilename());

        // 确保 audio 目录存在
        String audioDirPath = "audio"; // 相对路径
        Path audioDir = Paths.get(audioDirPath);

        if (!Files.exists(audioDir)) {
            Files.createDirectories(audioDir); // 如果目录不存在，则创建
        }

        // 构造目标文件路径（避免文件名冲突）
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = audioDir.resolve(fileName); // 使用 resolve 构造完整路径

        // 转换为绝对路径
        Path absoluteFilePath = filePath.toAbsolutePath();
        System.out.println("[VoiceService] 文件的绝对路径: " + absoluteFilePath);

        // 将上传的文件保存到 audio 目录
        File audioFile = absoluteFilePath.toFile(); // 转换为 File 对象
        try (FileOutputStream fos = new FileOutputStream(audioFile)) {
            fos.write(file.getBytes()); // 将文件内容写入目标文件
        }

        System.out.println("[VoiceService] 文件已保存到: " + absoluteFilePath);

        // 后续逻辑：使用保存的文件进行处理
        File audio = new File(absoluteFilePath.toString()); // 需极速转写的音频文件
        JSONObject jsonObjectUploadRespOrCompleteResp = null;

        try (FileInputStream fis = new FileInputStream(audio)) {
            FileCaller fileCaller = FileCaller.builder()
                    .apiKey(API_KEY)
                    .apiSecret(API_SECRET)
                    .client(client)
                    .ulrPrefix(FILE_URL_PREFIX)
                    .build();

            /** 1. 上传音频文件 */
            if (audio.length() < 31457280) {
                /** 1.1 如果是小文件走单个上传接口，建议不大于30M */
                FileResp<FileResp.UploadData> uploadResp = fileCaller.fileUpload(FileReq.Upload.builder()
                        .appId(APP_ID)
                        .fileName(absoluteFilePath.toString())
                        .requestId("20211213152023")
                        .data(IOUtils.toByteArray(fis))
                        .build());

                // 缓存上传结果数据
                String uploadRespData = JSONObject.toJSONString(uploadResp.getData());
                System.out.println("小文件上传返回的信息：" + uploadResp);
                jsonObjectUploadRespOrCompleteResp = JSONObject.parseObject(uploadRespData);
            } else {
                /** 1.2 如果是大文件走分块上传接口 */
                /** 1.2.1 初始化分块信息 */
                FileResp<FileResp.InitData> initResp = fileCaller.fileInit(FileReq.Init.builder()
                        .requestId("20211213155434")
                        .appId(APP_ID)
                        .build());

                // 缓存初始化分块信息
                String initRespData = JSONObject.toJSONString(initResp.getData());
                JSONObject jsonObjectInitResp = JSONObject.parseObject(initRespData);
                System.out.println("初始化分块信息-返回的upload_id：" + jsonObjectInitResp.get("upload_id"));

                /** 1.2.2 分块上传 */
                int len = 0;
                byte[] slice = new byte[SLICE_SIZE];
                int sliceIdIndex = 1;
                while ((len = fis.read(slice)) > 0) {
                    // 上传分片
                    if (fis.available() == 0) {
                        slice = Arrays.copyOfRange(slice, 0, len);
                    }
                    FileResp<Void> partUploadResp = fileCaller.filePartUpload(FileReq.PartUpload.builder()
                            .requestId("20211213152023")
                            .appId(APP_ID)
                            .uploadId(jsonObjectInitResp.get("upload_id").toString()) // 使用初始化分块信息返回的upload_id
                            .sliceId(sliceIdIndex)
                            .data(slice)
                            .build());
                    System.out.println("第" + sliceIdIndex + "块分块上传-返回的信息：" + partUploadResp);
                    sliceIdIndex++;
                }

                /** 1.2.3 分块上传完成 */
                FileResp<Void> completeResp = fileCaller.fileUploadComplete(FileReq.Complete.builder()
                        .appId(APP_ID)
                        .requestId("2021164834")
                        .uploadId(jsonObjectInitResp.get("upload_id").toString()) // 使用初始化分块信息返回的upload_id
                        .build());

                // 缓存分块上传完成结果
                String completeRespData = JSONObject.toJSONString(completeResp.getData());
                System.out.println("分块上传完成-返回的信息：" + completeResp);
                jsonObjectUploadRespOrCompleteResp = JSONObject.parseObject(completeRespData);
            }
        }

        /** 3. 创建任务 */
        OpenCaller openCaller = OpenCaller.builder()
                .apiKey(API_KEY)
                .apiSecret(API_SECRET)
                .client(client)
                .ulrPrefix(OPEN_URL_PREFIX)
                .build();
        String requestId = "20211213173212"; // 可以自定义
        OpenResp createResp = openCaller.create(OpenReq.Create.builder()
                .common(OpenReq.Common.builder().appId(APP_ID).build())
                .business(OpenReq.Business.builder()
                        .requestId(requestId)
                        .accent("mandarin")
                        .language("zh_cn")
                        .domain("pro_ost_ed")
                        .build())
                .data(OpenReq.Data.builder()
                        .audioUrl(jsonObjectUploadRespOrCompleteResp.getString("url")) // 上传文件或分块上传完成返回的url
                        .encoding("raw")
                        .format("audio/L16;rate=16000")
                        .audioSrc("http")
                        .build())
                .build());

        // 缓存创建任务的结果
        String createRespData = JSONObject.toJSONString(createResp.getData());
        System.out.println("创建任务-返回的信息：" + createResp);
        JSONObject jsonObjectCreateResp = JSONObject.parseObject(createRespData);

        /** 4. 查询任务 */
        OpenResp queryResp = openCaller.query(OpenReq.Query.builder()
                .common(OpenReq.Common.builder().appId(APP_ID).build())
                .business(OpenReq.QueryBusiness.builder()
                        .taskId(jsonObjectCreateResp.getString("task_id")) // 创建任务返回的task_id
                        .build())
                .build());

        // 缓存查询任务的结果
        String queryRespData = JSONObject.toJSONString(queryResp.getData());
        System.out.println("查询任务-返回的信息：" + queryResp);
        JSONObject jsonObjectQueryResp = JSONObject.parseObject(queryRespData);

        while (true) {
            if ("5".equals(jsonObjectQueryResp.getString("task_status"))) {
                System.out.println("极速转写-最终结果==>：任务已取消...");
                break; // 跳出循环
            } else if ("3".equals(jsonObjectQueryResp.getString("task_status")) || "4".equals(jsonObjectQueryResp.getString("task_status"))) {
                System.out.println("极速转写-最终结果（更多字段请参考queryResp进行解析）==>");
                JsonParse jsonParse = JSONObject.parseObject(queryRespData, JsonParse.class);
                List<Lattice> latticeList = jsonParse.result.lattice;
                for (Lattice tempLattice : latticeList) {
                    List<Rt> rtList = tempLattice.json_1best.st.rt;
                    for (Rt tempRt : rtList) {
                        List<Ws> wsList = tempRt.ws;
                        for (Ws tempWs : wsList) {
                            List<Cw> cwList = tempWs.cw;
                            for (Cw tempCw : cwList) {
                                System.out.print(tempCw.w);
                            }
                        }
                    }
                }
                break; // 跳出循环
            } else {
                Thread.sleep(2000); // 两秒查询一次
                // 再次查询任务
                queryResp = openCaller.query(OpenReq.Query.builder()
                        .common(OpenReq.Common.builder().appId(APP_ID).build())
                        .business(OpenReq.QueryBusiness.builder()
                                .taskId(jsonObjectCreateResp.getString("task_id")) // 创建任务返回的task_id
                                .build())
                        .build());

                // 更新缓存的查询结果
                queryRespData = JSONObject.toJSONString(queryResp.getData());
                jsonObjectQueryResp = JSONObject.parseObject(queryRespData);
                System.out.println("极速转写-转写中...");
            }
        }
    }

    /** 解析极速转写结果 */
    public static class JsonParse {
        public String task_id;
        public String task_status;
        public String task_type;
        public String force_refresh;
        public Result result;
    }

    public static class Result {
        public List<Lattice> lattice;
    }

    public static class Lattice {
        public Json_1best json_1best;
    }

    public static class Json_1best {
        public St st;
    }

    public static class St {
        public List<Rt> rt;
        public String rl;
    }

    public static class Rt {
        public List<Ws> ws;
    }

    public static class Ws {
        public List<Cw> cw;
    }

    public static class Cw {
        public String w;
    }
}
