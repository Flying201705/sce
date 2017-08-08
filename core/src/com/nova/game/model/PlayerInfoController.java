package com.nova.game.model;

import com.nova.net.http.util.UserUtil;
import java.util.HashMap;
import nova.common.room.data.PlayerInfo;

/**
 * Created by zhangxx on 17-8-7.
 */

public class PlayerInfoController {

    public interface PlayerInfoChangeListener {
        public void onPlayerInfoChange(int id);
    }

    private static PlayerInfoController mInstance;
    private PlayerInfo mMyInfo;
    private PlayerInfoChangeListener mPlayerInfoChangeListener;
    private HashMap<Integer, PlayerInfo> mPlayerCaches = new HashMap<Integer, PlayerInfo>();
    private UserUtil.onUserResultListener mUserListener = new UserUtil.onUserResultListener() {

        @Override
        public void onUserResult(int action, UserUtil.UserResult result) {
            if (result.getStatus()) {
                PlayerInfo player = result.getPlayerInfo();
                mPlayerCaches.put(player.getId(), player);
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

    public void setPlayerInfoChangeListener(PlayerInfoChangeListener listener) {
        mPlayerInfoChangeListener = listener;
    }

    public PlayerInfo getOwnerInfo() {
        return mMyInfo;
    }

    public void saveOwnerInfo(PlayerInfo info) {
        mMyInfo = info;
        mPlayerCaches.put(info.getId(), info);
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

    private void updatePlayerInfo(PlayerInfo info, PlayerInfo cache) {
        info.setName(cache.getName());
        info.setGold(cache.getGold());
        info.setHead(cache.getHead());
        info.setSex(cache.getSex());
        info.setVip(cache.getVip());
    }
}
