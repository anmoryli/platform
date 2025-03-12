# 快速开始

## 拉取项目

```text
git clone https://gitee.com/li-anmory/platform_java.git
```

## 运行

将项目拉取下来后，运行PlatformApplication

在浏览器输入

```text
localhost:8080/index.html
```

就可以看到效果

## 项目目录架构

快速生成目录结构

```text
tree /f >list.txt
```



```text
├─java
│  └─com
│      └─anmory
│          └─platform
│              │  PlatformApplication.java
│              │  
│              ├─AddService
│              │  │  fg.java
│              │  │  
│              │  └─Controller
│              │          AddController.java
│              │          
│              ├─BotService
│              │  │  tmp.java
│              │  │  
│              │  └─Controller
│              │          BotController.java
│              │          
│              ├─DatabaseService
│              │  ├─Controller
│              │  │      MedicineController.java
│              │  │      
│              │  ├─Dao
│              │  │      Medicine.java
│              │  │      
│              │  └─Service
│              │          MedicineService.java
│              │          
│              ├─DelService
│              │  │  tmp.java
│              │  │  
│              │  └─Controller
│              │          DelController.java
│              │          
│              ├─LoginService
│              │  ├─Controller
│              │  │      LoginController.java
│              │  │      
│              │  └─Dao
│              ├─MeService
│              │      MeController.java
│              │      
│              ├─ModifyService
│              │      ModifyController.java
│              │      
│              ├─RecognizeService
│              │  │  tmp.java
│              │  │  
│              │  ├─Config
│              │  │      CrosConfig.java
│              │  │      
│              │  └─Controller
│              │          RecognizeController.java
│              │          
│              ├─RegisterService
│              │  ├─Controller
│              │  │      RegisterController.java
│              │  │      
│              │  └─Dao
│              └─Util
│                      JdbcUtil.java
```

## 注意

如果需要用到图片识别功能，需要下载anaconda并在RecoginzeController的如下代码中配置

```java
String condaPath = "D:\\miniconda3\\Scripts\\conda.exe";
String envName = "D:\\miniconda3\\envs\\ultralytics";
String pythonScript = "D:\\Code\\Project\\SpringBoot\\Platform\\src\\main\\resources\\static\\python\\recognize\\predict.py";

```

参数说明：

```java
condaPath:你下载的conda.exe的所在位置，一般在/miniconda3/Scripts/conda/exe
envName:创建的虚拟环境的所在文件夹
pythonScript:所需要运行的py脚本的位置
```

