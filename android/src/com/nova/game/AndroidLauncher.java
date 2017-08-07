package com.nova.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nova.game.handler.MahjGameResponeDispatcher;
import com.nova.game.model.PlayerInfoController;
import com.nova.net.http.bean.UserBean;
import com.nova.net.http.util.UserUtil;
import com.nova.net.netty.ChannelManager;
import com.nova.net.netty.handler.ResponseDispatcherManager;

import nova.common.GameCommand;
import nova.common.game.mahjong.handler.GameLogger;
import nova.common.room.data.PlayerInfo;

public class AndroidLauncher extends AndroidApplication {
    Handler mLoginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            UserUtil.UserResult result = (UserUtil.UserResult)msg.obj;
            if (result.getStatus()) {
                UserBean user = result.getUserInfo();
                PlayerInfo info = new PlayerInfo();
                info.setId(user.getId());
                info.setName(user.getName());
                PlayerInfoController.getInstance().saveOwnerInfo(info);
                Toast.makeText(AndroidLauncher.this, "登录成功!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AndroidLauncher.this, "登录失败!", Toast.LENGTH_SHORT).show();
            }
        }
    };

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new SceGame(), config);

		//初始化Log模块
		GameLogger.create(new Log());
        // 初始化ResponeDispatcher
        ResponseDispatcherManager.getInstance().addGameResponseDispatcher(GameCommand.GAME_TYPE_MAHJ, new MahjGameResponeDispatcher());

        String openId = getIntent().getStringExtra("openid");
        GameLogger.getInstance().e("AndroidLauncher", "onCreate, openId = " + openId);
		// 用户登录
		new UserUtil().onLogin(openId, new UserUtil.onUserResultListener() {

			@Override
			public void onUserResult(int action, UserUtil.UserResult result) {
                Message message = mLoginHandler.obtainMessage();
                message.obj = result;
                mLoginHandler.sendMessage(message);
			}
		});

		// 与netty服务器通信
		ChannelManager.getInstance().connect();
	}
}
