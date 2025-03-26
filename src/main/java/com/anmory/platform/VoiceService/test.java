package com.anmory.platform.VoiceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
public class test {
    // 控制台获取以下信息
    private static String APP_ID = "96ed9cf1";
    private static String API_SECRET = "MmM4YzQxZDVjOGVlNzNmZTg5ZWRlZjc1";
    private static String API_KEY = "d61c1fa5dba05ea1c3bb3dec297d7a15";

    private static String FILE_URL_PREFIX = "https://upload-ost-api.xfyun.cn/file"; // 上传文件的地址开头

    private static String OPEN_URL_PREFIX = "https://ost-api.xfyun.cn/v2"; // 创建、查询任务的地址开头
    private static OkHttpClient client = new OkHttpClient.Builder().build();
    private static final Gson gson=new Gson();
    private static final String AUDIO_PATH="D:\\Code\\Project\\SpringBoot\\Platform\\audio\\1742970655180_recording.wav"; // 在此指定音频文件路径
    private static final int SLICE_SIZE = 15728640;// 15M，每块范围 5M~32M

    @RequestMapping("/test")
    public  void voice(MultipartFile file) throws IOException, InterruptedException {
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
        String authPath = absoluteFilePath.toString();
        System.out.println("[VoiceService] 文件的绝对路径: " + absoluteFilePath);

// 提取单独的文件名
        String justFileName = filePath.getFileName().toString(); // 获取文件名部分
        System.out.println("[VoiceService] 单独的文件名: " + justFileName);

// 将上传的文件保存到 audio 目录
        File audioFile = absoluteFilePath.toFile(); // 转换为 File 对象
        try (FileOutputStream fos = new FileOutputStream(audioFile)) {
            fos.write(file.getBytes()); // 将文件内容写入目标文件
        }

        System.out.println("[VoiceService] 文件已保存到: " + absoluteFilePath);

        // 后续逻辑：使用保存的文件进行处理
        File audio = new File(authPath); // 需极速转写的音频文件
        FileInputStream fis = new FileInputStream(audio);
        FileCaller fileCaller = FileCaller.builder().apiKey(API_KEY).apiSecret(API_SECRET).client(client).ulrPrefix(FILE_URL_PREFIX).build();
        JSONObject jsonObjectUploadRespOrCompleteResp;
        /** 1. 上传音频文件 */
        if(audio.length()<31457280){
            /** 1.1 如果是小文件走单个上传接口，建议不大于30M */
            FileResp<FileResp.UploadData> uploadResp = fileCaller.fileUpload(FileReq.Upload.builder().
                    appId(APP_ID).
                    fileName(justFileName).
                    requestId("20211213152023").
                    data(IOUtils.toByteArray(fis))
                    .build());
            System.out.println("小文件上传返回的信息："+uploadResp);
            jsonObjectUploadRespOrCompleteResp=JSON.parseObject(JSON.toJSONString(uploadResp.getData()));
        }else{ // 如果是大文件走分块上传接口
            /** 1.2 如果是大文件走分块上传接口*/
            /** 1.2.1 初始化分块信息 */
            FileResp<FileResp.InitData> initResp = fileCaller.fileInit(FileReq.Init.builder().
                    requestId("20211213155434").
                    appId(APP_ID)
                    .build());
            JSONObject jsonObjectInitResp=JSON.parseObject(JSON.toJSONString(initResp.getData()));
            System.out.println("初始化分块信息-返回的upload_id："+jsonObjectInitResp.get("upload_id"));
            /** 1.2.2 分块上传 */
            // 分片上传文件
            int len = 0;
            byte[] slice = new byte[SLICE_SIZE];
            int sliceIdIndex=1;
            while ((len = fis.read(slice)) > 0) {
                // 上传分片
                if (fis.available() == 0) {
                    slice = Arrays.copyOfRange(slice, 0, len);
                }
                FileResp<Void> partUploadResp = fileCaller.filePartUpload(FileReq.PartUpload.builder().
                        requestId("20211213152023").
                        appId(APP_ID).
                        uploadId(jsonObjectInitResp.get("upload_id").toString()). // 使用初始化分块信息返回的upload_id
                                sliceId(sliceIdIndex).
                        data(slice).
                        build());
                System.out.println("第"+sliceIdIndex+"块分块上传-返回的信息："+partUploadResp);
                sliceIdIndex++;
            }
            /** 1.2.3 分块上传完成 */
            FileResp<Void> completeResp = fileCaller.fileUploadComplete(FileReq.Complete.builder().
                    appId(APP_ID).requestId("2021164834").
                    uploadId(jsonObjectInitResp.get("upload_id").toString()). // 使用初始化分块信息返回的upload_id
                            build());
            System.out.println("分块上传完成-返回的信息："+completeResp);
            jsonObjectUploadRespOrCompleteResp=JSON.parseObject(JSON.toJSONString(completeResp.getData()));
        }

        /** 3. 创建任务 */
        OpenCaller openCaller = OpenCaller.builder().apiKey(API_KEY).apiSecret(API_SECRET).
                client(client).ulrPrefix(OPEN_URL_PREFIX).build();
        String requestId="20211213173212"; // 可以自定义
        OpenResp createResp = openCaller.create(OpenReq.Create.builder().
                common(OpenReq.Common.builder().appId(APP_ID).build()).
                business(OpenReq.Business.builder().
                        requestId(requestId).
                        // callbackUrl("http://IP:端口号/xxx").
                                accent("mandarin").
                        language("zh_cn").
                        domain("pro_ost_ed").
                        build()).
                data(OpenReq.Data.builder().audioUrl(jsonObjectUploadRespOrCompleteResp.get("url").toString()). // 上传文件或分块上传完成返回的url
                        encoding("raw").format("audio/L16;rate=16000").audioSrc("http").build()).
                build());
        System.out.println("创建任务-返回的信息："+createResp);
        JSONObject jsonObjectCreateResp=JSON.parseObject(JSON.toJSONString(createResp.getData()));

        /** 4. 查询任务 */
        OpenResp queryResp = openCaller.query(OpenReq.Query.builder().
                common(OpenReq.Common.builder().appId(APP_ID).build()).
                business(OpenReq.QueryBusiness.builder().taskId(jsonObjectCreateResp.get("task_id").toString()).build()). // 创建任务返回的task_id
                        build());
        System.out.println("查询任务-返回的信息："+queryResp);
        JSONObject jsonObjectQueryResp=JSON.parseObject(JSON.toJSONString(queryResp.getData()));
        while(true){
            if(jsonObjectQueryResp.get("task_status").equals("5")){
                System.out.println("极速转写-最终结果==>：任务已取消..."); // 控制台打印取消信息
                break; // 跳出循环
            }else if(jsonObjectQueryResp.get("task_status").equals("3")||jsonObjectQueryResp.get("task_status").equals("4")){
                // System.out.println("极速转写-最终结果==>：\n"+queryResp); // 控制台打印最终转写结果
                System.out.println("极速转写-最终结果（更多字段请参考queryResp进行解析）==>：");
                JsonParse jsonParse=gson.fromJson(jsonObjectQueryResp.toJSONString(),JsonParse.class);
                List<Lattice> latticeList=jsonParse.result.lattice;
                for(int i=0;i<latticeList.size();i++){
                    Lattice tempLattice=latticeList.get(i);
                    // String rl=tempLattice.json_1best.st.rl;
                    List<Rt> rtList=tempLattice.json_1best.st.rt;
                    for(int j=0;j<rtList.size();j++){
                        Rt tempRt=rtList.get(j);
                        List<Ws> wsList=tempRt.ws;
                        for(int k=0;k<wsList.size();k++){
                            Ws tempWs=wsList.get(k);
                            List<Cw> cwList=tempWs.cw;
                            for(int l=0;l<cwList.size();l++){
                                Cw tempCw=cwList.get(l);
                                System.out.print(tempCw.w);
                            }
                        }
                    }
                }
                break; // 跳出循环
            }else{
                Thread.sleep(2000); // 两秒查询一次
                // 再次查询任务
                queryResp = openCaller.query(OpenReq.Query.builder().
                        common(OpenReq.Common.builder().appId(APP_ID).build()).
                        business(OpenReq.QueryBusiness.builder().taskId(jsonObjectCreateResp.get("task_id").toString()).build()). // 创建任务返回的task_id
                                build());
                jsonObjectQueryResp=JSON.parseObject(JSON.toJSONString(queryResp.getData()));
                System.out.println("极速转写-转写中...");
            }
        }

    }
    /** 解析极速转写结果 */
    public class JsonParse{
        String task_id;
        String task_status;
        String task_type;
        String force_refresh;
        Result result;
    }
    class Result{
        List<Lattice> lattice;
    }
    class Lattice{
        Json_1best json_1best;
    }
    class Json_1best{
        St st;
    }
    class St{
        List<Rt> rt;
        String rl;
    }
    class Rt{
        List<Ws> ws;
    }
    class Ws{
        List<Cw> cw;
    }
    class Cw{
        String w;
    }
}
