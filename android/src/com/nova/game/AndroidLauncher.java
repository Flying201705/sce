package com.nova.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nova.net.netty.ChannelManager;

import nova.common.game.mahjong.handler.GameLogger;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new SceGame(), config);

		//初始化Log模块
		GameLogger.create(new Log());

		// 测试与netty服务器通信
		// ChannelManager.getInstance().connect();
	}
}
