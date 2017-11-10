package com.nova.game.dialog;

/**
 * Created by zhangxx on 17-11-8.
 */

public interface MediaRecorderInterface {

    public void startRecord(String path);
    public void stopRecord();
    public int getMaxAmplitude();
}
