package com.nova.game.dialog;

import java.io.File;
import java.util.UUID;

public class AudioManager {

	private MediaRecorderInterface mRecorder;
	private static final String AUDIO_PATH = "/sdcard/sce/mahj/audios/";
	private String mCurrentFilePathString;

	private boolean isPrepared;// 是否准备好了

	private static AudioManager mInstance;

	private AudioManager() {
	}

	public static AudioManager getInstance() {
		if (mInstance == null) {
			synchronized (AudioManager.class) {
				if (mInstance == null) {
					mInstance = new AudioManager();
				}
			}
		}
		return mInstance;

	}

	public void setMediaRecorder(MediaRecorderInterface recorder) {
		mRecorder = recorder;
	}

	/**
	 * 回调函数，准备完毕，准备好后，button才会开始显示录音框
	 * 
	 * @author nickming
	 *
	 */
	public interface AudioStageListener {
		void wellPrepared();
	}

	public AudioStageListener mListener;

	public void setOnAudioStageListener(AudioStageListener listener) {
		mListener = listener;
	}

	// 准备方法
	public void prepareAudio() {
		// 一开始应该是false的
		isPrepared = false;

		File dir = new File(AUDIO_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String fileNameString = generalFileName();
		File file = new File(dir, fileNameString);

		mCurrentFilePathString = file.getAbsolutePath();

		mRecorder.startRecord(file.getAbsolutePath());
		// 准备结束
		isPrepared = true;
		// 已经准备好了，可以录制了
		if (mListener != null) {
			mListener.wellPrepared();
		}
	}

	/**
	 * 随机生成文件的名称
	 * 
	 * @return
	 */
	private String generalFileName() {
		// TODO Auto-generated method stub

		return UUID.randomUUID().toString() + ".amr";
	}

	// 获得声音的level
	public int getVoiceLevel(int maxLevel) {
		// mRecorder.getMaxAmplitude()这个是音频的振幅范围，值域是1-32767
		if (isPrepared) {
			try {
				// 取证+1，否则去不到7
				return maxLevel * mRecorder.getMaxAmplitude() / 32768 + 1;
			} catch (Exception e) {
				// TODO Auto-generated catch block

			}
		}

		return 1;
	}

	// 释放资源
	public void release() {
		mRecorder.stopRecord();
	}

	// 取消,因为prepare时产生了一个文件，所以cancel方法应该要删除这个文件，
	// 这是与release的方法的区别
	public void cancel() {
		release();
		if (mCurrentFilePathString != null) {
			File file = new File(mCurrentFilePathString);
			file.delete();
			mCurrentFilePathString = null;
		}

	}

	public String getCurrentFilePath() {
		// TODO Auto-generated method stub
		return mCurrentFilePathString;
	}

}
