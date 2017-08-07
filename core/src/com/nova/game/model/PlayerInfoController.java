package com.nova.game.model;

import com.nova.net.http.bean.UserBean;
import com.nova.net.http.util.UserUtil;
import java.util.HashMap;
import nova.common.room.data.PlayerInfo;

/**
 * Created by zhangxx on 17-8-7.
 */

public class PlayerInfoController {

    private static PlayerInfoController mInstance;
    private PlayerInfo mMyInfo;
    private HashMap<Integer, PlayerInfo> mPlayerCaches = new HashMap<Integer, PlayerInfo>();

    private UserUtil.onUserResultListener mUserListener = new UserUtil.onUserResultListener() {

        @Override
        public void onUserResult(int action, UserUtil.UserResult result) {
            if (result.getStatus()) {
                UserBean user = result.getUserInfo();
                PlayerInfo playerInfo = new PlayerInfo(user.getId(), user.getName(), user.getUserIcon(), 0);
                mPlayerCaches.put(playerInfo.getId(), playerInfo);
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
            info.setName(cachePlayerInfo.getName());
        } else {
            new UserUtil().onQuery(String.valueOf(info.getId()), mUserListener);
        }
    }
}
