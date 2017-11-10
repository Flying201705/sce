package com.nova.game;

import android.media.MediaRecorder;

import com.nova.game.dialog.MediaRecorderInterface;
import com.nova.game.utils.Log;

/**
 * Created by zhangxx on 17-11-8.
 */

public class MahjMediaRecorder implements MediaRecorderInterface {

    private static final String TAG = "MahjMediaRecorder";
    private MediaRecorder mRecorder;

    @Override
    public void startRecord(String path) {
        try {
            mRecorder = new MediaRecorder();
            // 设置输出文件
            mRecorder.setOutputFile(path);
            // 设置meidaRecorder的音频源是麦克风
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置文件音频的输出格式为amr
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            // 设置音频的编码格式为amr
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            // 严格遵守google官方api给出的mediaRecorder的状态流程图
            mRecorder.prepare();

            mRecorder.start();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void stopRecord() {
        // 严格按照api流程进行
		mRecorder.stop();
		mRecorder.release();
        mRecorder = null;
    }

    @Override
    public int getMaxAmplitude() {
        return mRecorder.getMaxAmplitude();
    }
}
