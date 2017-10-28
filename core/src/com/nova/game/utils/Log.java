package com.nova.game.utils;

import com.badlogic.gdx.Gdx;

import nova.common.game.mahjong.handler.GameLogger;

public class Log implements GameLogger.LoggerHandler {

	private static final boolean DEBUG = true;
	private static final String TAG = "nova_mahj";

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Gdx.app.log(TAG, tag + " - " + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Gdx.app.debug(TAG, tag + " - " + msg);
        }
    }

    public static void e(String tag, String msg) {
        Gdx.app.error(TAG, tag + " - " + msg);
    }

    @Override
    public void info(String tag, String msg) {
        i(tag, msg);
    }

    @Override
    public void debug(String tag, String msg) {
        d(tag, msg);
    }

    @Override
    public void error(String tag, String msg) {
        e(tag, msg);
    }
}
