package com.nova.net.http.util;

import java.util.HashMap;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.game.utils.Log;
import com.nova.net.http.HttpConstants;
import com.nova.net.http.bean.BaseBean;
import nova.common.room.data.PlayerInfo;

public class UserUtil extends HttpUtil {
	
	public static final int ACTION_LOGIN = 100;
	public static final int ACTION_REGISTER = 101;
	public static final int ACTION_GET_USERINFO = 102;
	public static final int ACTION_QUERY_ALLUSERS = 103;
	
	public class UserResult {
		private boolean mStatus;
		private PlayerInfo mPlayerInfo;
		private List<PlayerInfo> mPlayers;
		
		public boolean getStatus() {
			return mStatus;
		}
		
		public void setStatus(boolean status) {
			mStatus = status;
		}
		
		public PlayerInfo getPlayerInfo() {
			return mPlayerInfo;
		}
		
		public void setPlayerInfo(PlayerInfo userInfo) {
			mPlayerInfo = userInfo;
		}
		
		public List<PlayerInfo> getPlayers() {
			return mPlayers;
		}
		
		public void setPlayers(List<PlayerInfo> users) {
			mPlayers = users;
		}
	}
	
	public interface onUserResultListener {
		public void onUserResult(int action, UserResult result);
	}
	
	private onUserResultListener mUserResultListener;

	public UserUtil() {

	}
	
	private boolean checkResultSuccess(String result) {
		if (result.isEmpty() || HttpUtil.RESULT_FAIL.equals(result)) {
			return false;
		}
		return true;
	}
	
	public void onLoginForOpenId(String openid, String name, int sex, String headimgurl, onUserResultListener listener) {
		if (openid == null || openid.isEmpty()) {
			return;
		}
		Log.e("UserUtil", "onLoginForOpenId, openid " + openid + " name " + name + " headimgurl " + headimgurl);
		mUserResultListener = listener;
		HashMap<String, String> allP = new HashMap<String, String>();
		allP.put("openid", openid);
		if (name != null) {
			allP.put("name", name);
		}
		allP.put("sex", String.valueOf(sex));
		if (headimgurl != null) {
			allP.put("head", headimgurl);
		}
		doPost(HttpConstants.HTTP_LOGIN, allP, mUserResultCallback);
	}

	private ResultCallback mUserResultCallback = new ResultCallback() {
		
		@Override
		public void getResult(String result) {
			if (!checkResultSuccess(result)) {
				return;
			}

			BaseBean base = JSON.parseObject(result, BaseBean.class);
			UserResult userResult = new UserResult();
			if ("0".equals(base.getRespcode())) {
				PlayerInfo player = new Gson().fromJson(base.getData().toString(), new TypeToken<PlayerInfo>() {}.getType());
				userResult.setStatus(true);
                userResult.setPlayerInfo(player);
			} else {
				userResult.setStatus(false);
			}

			mUserResultListener.onUserResult(ACTION_LOGIN, userResult);
		}
	};

	public void onQuery(String id, onUserResultListener listener) {
		mUserResultListener = listener;
		HashMap<String, String> allP = new HashMap<String, String>();
		allP.put("id", id);
		doPost(HttpConstants.HTTP_GET_USERINFO, allP, mUserResultCallback);
	}
}
