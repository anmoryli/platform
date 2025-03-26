package com.anmory.platform.VoiceService;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioRecorder {
    private AudioFormat format;
    private TargetDataLine line;

    public AudioRecorder() {
        format = new AudioFormat(44100, 16, 2, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void startRecording(String filePath) {
        line.start();
        AudioInputStream audioInputStream = new AudioInputStream(line);
        File file = new File(filePath);
        try {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        line.stop();
        line.close();
    }
}