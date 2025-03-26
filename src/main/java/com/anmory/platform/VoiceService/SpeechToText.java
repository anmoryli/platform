package com.anmory.platform.VoiceService;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class SpeechToText {
    public void transcribe() {
        Configuration configuration = new Configuration();

        // 设置模型路径
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        try {
            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);

            // 启动实时语音识别
            recognizer.startRecognition(true);
            System.out.println("开始实时语音识别，请对着麦克风说话...");

            SpeechResult result;
            while ((result = recognizer.getResult()) != null) {
                System.out.format("Hypothesis: %s\n", result.getHypothesis());
            }

            // 停止识别
            recognizer.stopRecognition();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}