package com.nova.net.http.util;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.net.http.HttpConstants;
import com.nova.net.http.bean.BaseBean;
import com.nova.net.http.bean.UserBean;

public class UserUtil extends HttpUtil {
	
	public static final int ACTION_LOGIN = 100;
	public static final int ACTION_REGISTER = 101;
	public static final int ACTION_GET_USERINFO = 102;
	public static final int ACTION_QUERY_ALLUSERS = 103;
	
	public class UserResult {
		private boolean mStatus;
		private UserBean mUserInfo;
		private List<UserBean> mUsers;
		
		public boolean getStatus() {
			return mStatus;
		}
		
		public void setStatus(boolean status) {
			mStatus = status;
		}
		
		public UserBean getUserInfo() {
			return mUserInfo;
		}
		
		public void setUserInfo(UserBean userInfo) {
			mUserInfo = userInfo;
		}
		
		public List<UserBean> getUsers() {
			return mUsers;
		}
		
		public void setUsers(List<UserBean> users) {
			mUsers = users;
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
	
	public void onLogin(String openid, onUserResultListener listener) {
		mUserResultListener = listener;
		HashMap<String, String> allP = new HashMap<String, String>();
		allP.put("openid", openid);
		doPost(HttpConstants.HTTP_LOGIN, allP, mUserResultCallback);
	}
	
	private ResultCallback mUserResultCallback = new ResultCallback() {
		
		@Override
		public void getResult(String result) {
			if (!checkResultSuccess(result)) {
				return;
			}
			BaseBean base = new Gson().fromJson(result, new TypeToken<BaseBean>() {}.getType());
			UserResult userResult = new UserResult();
			if ("0".equals(base.getRespcode())) {
				UserBean user = new Gson().fromJson(base.getData().toString(), UserBean.class);
				userResult.setStatus(true);
                userResult.setUserInfo(user);
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
