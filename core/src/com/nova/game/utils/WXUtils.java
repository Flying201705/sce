package com.nova.game.utils;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.nova.game.model.PlayerInfoController;
import java.net.HttpURLConnection;
import java.net.URL;
import nova.common.room.data.PlayerInfo;

public class WXUtils {
    private static final String TAG = "WXUtils";

    public static boolean requestTokenWithHttpClient(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "requestTokenWithHttpClient run --!");
                try {
                    URL wx_url = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) wx_url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        JsonReader jsonReader = new JsonReader();
                        JsonValue jsonValue = jsonReader.parse(conn.getInputStream());
                        String access_token = jsonValue.getString("access_token");
                        String refresh_token = jsonValue.getString("refresh_token");
                        final String openid = jsonValue.getString("openid");
                        Log.i(TAG, "mAccessToken:" + access_token + "; mRefreshToken:" + refresh_token + "; mOpenId:" + openid);

                        if (access_token != null && !access_token.isEmpty() && openid != null && !openid.isEmpty()) {
                            String url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s", access_token, openid);
                            URL head_url = new URL(url);
                            HttpURLConnection infoConn = (HttpURLConnection) head_url.openConnection();
                            infoConn.setConnectTimeout(5000);
                            infoConn.setRequestMethod("GET");
                            if (infoConn.getResponseCode() == 200) {
                                JsonReader jr = new JsonReader();
                                JsonValue jv = jr.parse(infoConn.getInputStream());

                                /*WXInfo wxInfo = WXInfo.getInstance();

                                wxInfo.setNickName(jv.getString("nickname"));
                                wxInfo.setSex(jv.getInt("sex"));
                                wxInfo.setProvince(jv.getString("province"));
                                wxInfo.setCity(jv.getString("city"));
                                wxInfo.setCountry(jv.getString("country"));
                                wxInfo.setUnionid(jv.getString("unionid"));
                                String headimgurl = jv.getString("headimgurl");
                                if (headimgurl.contains("/0")) {
                                    headimgurl = headimgurl.replace("/0", "/132");
                                }
                                wxInfo.setHeadimgurl(headimgurl);*/
                                
                                PlayerInfo myInfo = new PlayerInfo();
                                myInfo.setOpenId(openid);
                                myInfo.setName(jv.getString("nickname"));
                                myInfo.setSex(jv.getInt("sex"));
                                String headimgurl = jv.getString("headimgurl");
                                if (headimgurl.contains("/0")) {
                                    headimgurl = headimgurl.replace("/0", "/132");
                                }
                                Log.d(TAG, "headimgurl:" + headimgurl);
                                myInfo.setHead(headimgurl);
                                PlayerInfoController.getInstance().saveOwnerInfo(myInfo);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }).start();


        return true;
    }
}
