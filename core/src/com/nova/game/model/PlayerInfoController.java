package com.nova.game.model;

import com.nova.game.utils.Log;
import com.nova.net.http.util.UserUtil;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import nova.common.room.data.PlayerInfo;

/**
 * Created by zhangxx on 17-8-7.
 */

public class PlayerInfoController {

    public interface PlayerInfoChangeListener {
        public void onPlayerInfoChange(int id);
    }

    private static final String TAG = "PlayerInfoController";
    private static PlayerInfoController mInstance;
    private WeChatListener mWeChatListener;
    private PlayerInfo mMyInfo;
    private PlayerInfoChangeListener mPlayerInfoChangeListener;
    private HashMap<Integer, PlayerInfo> mPlayerCaches = new HashMap<Integer, PlayerInfo>();
    private UserUtil.onUserResultListener mUserListener = new UserUtil.onUserResultListener() {

        @Override
        public void onUserResult(int action, UserUtil.UserResult result) {
            if (result.getStatus()) {
                PlayerInfo player = result.getPlayerInfo();
                mPlayerCaches.put(player.getId(), player);
                if (player.getOpenId().equals(mMyInfo.getOpenId())) {
                    updatePlayerInfo(mMyInfo, player);
                }
                loadImageFromNet(player.getId(), player.getHead());
                if (mPlayerInfoChangeListener != null) {
                    mPlayerInfoChangeListener.onPlayerInfoChange(player.getId());
                }
            }
        }
    };

    private PlayerInfoController() {

    }

    public static PlayerInfoController getInstance() {
        if (mInstance == null) {
            mInstance = new PlayerInfoController();
        }

        return mInstance;
    }
    
    public void setWeChatListener(WeChatListener listener) {
    	mWeChatListener = listener;
    }
    
    public void loginWeChat() {
    	if (mWeChatListener != null) {
    		mWeChatListener.loginWeChat();
    	}
    }
    
    public void setPlayerInfoChangeListener(PlayerInfoChangeListener listener) {
        mPlayerInfoChangeListener = listener;
    }

    public PlayerInfo getOwnerInfo() {
        return mMyInfo;
    }

    public void saveOwnerInfo(PlayerInfo info) {
        mMyInfo = info;
        // mPlayerCaches.put(info.getId(), info);
        uploadPlayerInfo(mMyInfo);
    }

    public int getOwnerPlayerId() {
        if (mMyInfo == null) {
            return -1;
        }
        return mMyInfo.getId();
    }

    public void updatePlayerInfo(PlayerInfo info) {
        if (mPlayerCaches.containsKey(info.getId())) {
            PlayerInfo cachePlayerInfo = mPlayerCaches.get(info.getId());
            updatePlayerInfo(info, cachePlayerInfo);
        } else {
            new UserUtil().onQuery(String.valueOf(info.getId()), mUserListener);
        }
    }
    
    private void uploadPlayerInfo(PlayerInfo myinfo) {
    	new UserUtil().onLoginForOpenId(myinfo.getOpenId(), myinfo.getName(), myinfo.getSex(), myinfo.getHead(), mUserListener);
    }

    private void updatePlayerInfo(PlayerInfo info, PlayerInfo cache) {
        info.setId(cache.getId());
        info.setOpenId(cache.getOpenId());
        info.setName(cache.getName());
        info.setGold(cache.getGold());
        info.setHead(cache.getHead());
        info.setSex(cache.getSex());
        info.setVip(cache.getVip());
    }
    
    private void loadImageFromNet(int id, String imgUrl) {
        if (imgUrl == null) {
            return;
        }

        Log.i(TAG, "loadImageFromNet imgUrl = " + imgUrl);

        try {
            URL head_url = new URL(imgUrl);
            HttpURLConnection conn = (HttpURLConnection) head_url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inStream = conn.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                inStream.close();

                byte[] bytes = outStream.toByteArray();
                mPlayerCaches.get(id).setHeaddatas(bytes);
                if (getOwnerPlayerId() == id) {
                    getOwnerInfo().setHeaddatas(bytes);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
