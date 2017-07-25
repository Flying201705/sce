package com.nova.net.http.util;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.net.http.HttpConstants;
import com.nova.net.http.bean.BaseBean;
import com.nova.net.http.bean.UserBean;
import com.nova.net.http.bean.UserInfoBean;

public class UserUtil extends HttpUtil {
	
	public static final int ACTION_LOGIN = 100;
	public static final int ACTION_REGISTER = 101;
	public static final int ACTION_GET_USERINFO = 102;
	public static final int ACTION_QUERY_ALLUSERS = 103;
	
	public class UserResult {
		private boolean mStatus;
		private UserInfoBean mUserInfo;
		private List<UserInfoBean> mUsers;
		
		public boolean getStatus() {
			return mStatus;
		}
		
		public void setStatus(boolean status) {
			mStatus = status;
		}
		
		public UserInfoBean getUserInfo() {
			return mUserInfo;
		}
		
		public void setUserInfo(UserInfoBean userInfo) {
			mUserInfo = userInfo;
		}
		
		public List<UserInfoBean> getUsers() {
			return mUsers;
		}
		
		public void setUsers(List<UserInfoBean> users) {
			mUsers = users;
		}
	}
	
	public interface onUserResultListener {
		public void onUserResult(int action, UserResult result);
	}
	
	private onUserResultListener mLoginResultListener;

	public UserUtil() {

	}
	
	private boolean checkResultSuccess(String result) {
		if (result.isEmpty() || HttpUtil.RESULT_FAIL.equals(result)) {
			return false;
		}
		return true;
	}
	
	public void onLogin(String openid, onUserResultListener listener) {
		mLoginResultListener = listener;
		HashMap<String, String> allP = new HashMap<String, String>();
		allP.put("openid", openid);
		allP.put("name", "小黄人");
		doPost(HttpConstants.HTTP_LOGIN, allP, mLoginResult);
	}
	
	private ResultCallback mLoginResult = new ResultCallback() {
		
		@Override
		public void getResult(String result) {
			if (!checkResultSuccess(result)) {
				return;
			}
			BaseBean base = new Gson().fromJson(result, new TypeToken<BaseBean>() {}.getType());
			UserResult userResult = new UserResult();
			if ("0".equals(base.getRespcode())) {
				userResult.setStatus(true);
				UserBean user = new Gson().fromJson(base.getData().toString(), UserBean.class);
			} else {
				userResult.setStatus(false);
			}
			mLoginResultListener.onUserResult(ACTION_LOGIN, userResult);
		}
	};
}
