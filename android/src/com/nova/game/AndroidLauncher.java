package com.nova.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nova.net.http.util.UserUtil;
import com.nova.net.netty.ChannelManager;

import nova.common.game.mahjong.handler.GameLogger;

public class AndroidLauncher extends AndroidApplication {
    Handler mLoginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            UserUtil.UserResult result = (UserUtil.UserResult)msg.obj;
            if (result.getStatus()) {
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

		// 用户登录
		new UserUtil().onLogin("76fdsa0dwfscf-fscvae", new UserUtil.onUserResultListener() {

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
