package com.nova.game;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nova.game.handler.MahjGameResponeDispatcher;
import com.nova.game.model.PlayerInfoController;
import com.nova.net.netty.ChannelManager;
import com.nova.net.netty.handler.ResponseDispatcherManager;
import nova.common.GameCommand;

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        //初始化Log模块
		// GameLogger.create(new Log());

        // 初始化ResponeDispatcher
        ResponseDispatcherManager.getInstance().addGameResponseDispatcher(GameCommand.GAME_TYPE_MAHJ, new MahjGameResponeDispatcher());

        // 与netty服务器通信
        ChannelManager.getInstance().connect();

        // 初始化PlayerInfoController
        WeChatManager manager = new WeChatManager(getContext());
        PlayerInfoController.getInstance().setWeChatListener(manager);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new SceGame(), config);
        setLogLevel(LOG_DEBUG);
    }
}
