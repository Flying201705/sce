package com.nova.game.model;

import nova.common.room.data.PlayerInfo;

/**
 * Created by zhangxx on 17-10-25.
 */

public interface SharedPreferenceListener {
    public void saveOwnerInfo(PlayerInfo myinfo);
    public String getOwnerOpenId();
    public String getOwnerName();
}
