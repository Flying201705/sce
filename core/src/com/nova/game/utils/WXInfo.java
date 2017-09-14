package com.nova.game.utils;

public class WXInfo {
    private static final String TAG = "WXInfo";
    private static WXInfo sWxInfo;
    private boolean mIsLogin;
    private String mAccessToken;
    private String mRefreshToken;
    private String mOpenId;
    private String mNickName;
    private int mSex;
    private String mProvince;
    private String mCity;
    private String mCountry;
    private String mHeadimgurl;
    private String mPrivilege;
    private String mUnionid;

    public static WXInfo getInstance() {
        if (sWxInfo == null) {
            sWxInfo = new WXInfo();
        }
        return sWxInfo;
    }
    
    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }
    
    public String getAccessToken() {
        return mAccessToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.mRefreshToken = refreshToken;
    }
    
    public String getRefreshToken() {
        return mRefreshToken;
    }
    
    public void setOpenId(String openId) {
        this.mOpenId = openId;
    }
    
    public String getOpenId() {
        return mOpenId;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        this.mNickName = nickName;
    }

    public int getSex() {
        return mSex;
    }

    public void setSex(int sex) {
        this.mSex = sex;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        this.mProvince = province;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public String getHeadimgurl() {
        return mHeadimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.mHeadimgurl = headimgurl;
    }

    public String getPrivilege() {
        return mPrivilege;
    }

    public void setPrivilege(String privilege) {
        this.mPrivilege = privilege;
    }

    public String getUnionid() {
        return mUnionid;
    }

    public void setUnionid(String unionid) {
        this.mUnionid = unionid;
    }

    public void loginOk() {
        mIsLogin = true;
    }

    public boolean isLogined() {
        return mIsLogin;
    }
}
