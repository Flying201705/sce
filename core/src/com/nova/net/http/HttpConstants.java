package com.nova.net.http;

public class HttpConstants {

	public final static String HTTP_HEAD = "http://";

	// 内网
	// public final static String HTTP_IP="18.8.10.154:8610";

	// 外网
	public final static String HTTP_IP = "zhang395295759.xicp.net:35137";

	public final static String HTTP_CONTEXT = "/hongdong/";

	public final static String HTTP_REQUEST = HTTP_HEAD + HTTP_IP + HTTP_CONTEXT;

	/**
	 * 用户协议
	 */
	public static String HTTP_AGREEMENT = HTTP_REQUEST + "agreement.jsp";

	/**
	 * 用户注册
	 */
	public static String HTTP_REGISTER = HTTP_REQUEST + "user/userRegist.do";

	/**
	 * 用户登录通过openId
	 */
	public static String HTTP_LOGIN = HTTP_REQUEST + "user/userLoginForOpenId.do";
	
	/**
	 * 查询所有用户
	 */
	public static String HTTP_ALL_USERS = HTTP_REQUEST + "user/allUsers.do";

	/**
	 * 修改用户图像
	 */
	public static String HTTP_UPDATE_USERICON = HTTP_REQUEST + "user/updateUserIcon.do";

	/**
	 * 根据ID 获取个人资料
	 */
	public static String HTTP_GET_USERINFO = HTTP_REQUEST + "user/getUserInfoId.do";

	/**
	 * 版本更新
	 */
	public static String HTTP_VERSION_UPDATE = HTTP_REQUEST + "version/update.do";
}
