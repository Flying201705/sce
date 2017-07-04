package com.nova.game;

import nova.common.game.mahjong.handler.GameLogger;

public class Log implements GameLogger.LoggerHandler {

	private static final boolean DEBUG = true;
	private static final String TAG = "nova_wsk";
	
	@Override
    public void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(TAG, tag + " : " + msg);
        }
    }

	@Override
    public void e(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.e(TAG, tag + " : " + msg);
        }
    }
}
