package com.nova.game;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nova.game.dialog.AudioManager;
import com.nova.game.handler.MahjGameResponeDispatcher;
import com.nova.game.handler.MahjMessageResponseDispatcher;
import com.nova.game.model.PlayerInfoController;
import com.nova.game.utils.Log;
import com.nova.net.netty.ChannelManager;
import com.nova.net.netty.handler.ResponseDispatcherManager;
import nova.common.GameCommand;
import nova.common.game.mahjong.handler.GameLogger;

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        //初始化Log模块
		GameLogger.create(new Log());

        // 初始化ResponeDispatcher
        ResponseDispatcherManager.getInstance().addGameResponseDispatcher(GameCommand.MAHJ_TYPE_GAME, new MahjGameResponeDispatcher());
        ResponseDispatcherManager.getInstance().addMessageResponseDispatcher(GameCommand.MAHJ_TYPE_MESSAGE, new MahjMessageResponseDispatcher());

        // 与netty服务器通信
        ChannelManager.getInstance().connect();

        // 初始化PlayerInfoController
        PlayerInfoController.getInstance().setWeChatListener(new WeChatManager(getContext()));
        PlayerInfoController.getInstance().setSharedPreferenceListener(new SharedPreferenceManager(getContext()));

        // 初始化AudioManager
        AudioManager.getInstance().setMediaRecorder(new MahjMediaRecorder());

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new SceGame(), config);
        setLogLevel(LOG_DEBUG);
    }
}
