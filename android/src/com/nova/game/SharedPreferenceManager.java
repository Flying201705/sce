package com.nova.game;

import android.content.Context;
import android.content.SharedPreferences;

import com.nova.game.model.SharedPreferenceListener;

import nova.common.room.data.PlayerInfo;

/**
 * Created by zhangxx on 17-10-25.
 */

public class SharedPreferenceManager implements SharedPreferenceListener {

    private Context mContext;
    public SharedPreferenceManager(Context context) {
        mContext = context;
    }

    @Override
    public void saveOwnerInfo(PlayerInfo myinfo) {
        SharedPreferences sp = mContext.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        sp.edit().putString("id", String.valueOf(myinfo.getId())).commit();
        sp.edit().putString("openid", myinfo.getOpenId()).commit();
        sp.edit().putString("name", myinfo.getName()).commit();
    }

    @Override
    public String getOwnerOpenId() {
        SharedPreferences sp = mContext.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return sp.getString("openid", "");
    }

    @Override
    public String getOwnerName() {
        SharedPreferences sp = mContext.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return sp.getString("name", "");
    }
}
