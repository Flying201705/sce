package com.nova.game.utils;

import com.badlogic.gdx.Gdx;

public class Log {

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
        if (DEBUG) {
            Gdx.app.error(TAG, tag + " - " + msg);
        }
    }
}
