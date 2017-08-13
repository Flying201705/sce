package com.nova.game;

import android.content.Context;
import android.content.Intent;

import com.funlisten.wxapi.WXEntryActivity;

public class WeChatManager implements SceApi {
    private Context mContext;

    public WeChatManager(Context context) {
        mContext = context;
    }

    @Override
    public void loginWeChat() {
        Intent intent = new Intent(mContext, WXEntryActivity.class);
        intent.putExtra(WXEntryActivity.ReqWxLogin, "wxlogin");
        mContext.startActivity(intent);
    }
}
