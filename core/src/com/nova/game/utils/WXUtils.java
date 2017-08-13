package com.nova.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WXUtils {
    private static final String TAG = "WXUtils";

    public static boolean requestTokenWithHttpClient(final String url) {

        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(url).build();

        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == 200) {
                    String entityStr = httpResponse.getResultAsString();
                    Gdx.app.log(TAG, "entityStr = " + entityStr);

                    JsonReader jsonReader = new JsonReader();
                    JsonValue jsonValue = jsonReader.parse(entityStr);
                    String access_token = jsonValue.getString("access_token");
                    String refresh_token = jsonValue.getString("refresh_token");
                    final String openid = jsonValue.getString("openid");
                    Gdx.app.log(TAG, "mAccessToken:" + access_token + "; mRefreshToken:" + refresh_token + "; mOpenId:" + openid);

                    if (access_token != null && !access_token.isEmpty() && openid != null && !openid.isEmpty()) {
                        String url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s", access_token, openid);
                        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(url).build();
                        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                            @Override
                            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                                if (httpResponse.getStatus().getStatusCode() == 200) {
                                    String entityStr = httpResponse.getResultAsString();
                                    Gdx.app.log(TAG, "requestTokenWithHttpClient - entityStr_2:" + entityStr);

                                    JsonReader jsonReader = new JsonReader();
                                    JsonValue jsonValue = jsonReader.parse(entityStr);

                                    WXInfo wxInfo = WXInfo.getInstance();

                                    wxInfo.setNickName(jsonValue.getString("nickname"));
                                    wxInfo.setSex(jsonValue.getInt("sex"));
                                    wxInfo.setProvince(jsonValue.getString("province"));
                                    wxInfo.setCity(jsonValue.getString("city"));
                                    wxInfo.setCountry(jsonValue.getString("country"));
                                    wxInfo.setPrivilege(jsonValue.getString("privilege"));
                                    wxInfo.setUnionid(jsonValue.getString("unionid"));
                                    String headimgurl = jsonValue.getString("headimgurl");
                                    wxInfo.setHeadimgurl(headimgurl);

                                    if (headimgurl.contains("/0")) {
                                        headimgurl = headimgurl.replace("/0", "/132");
                                    }

                                    try {
                                        URL head_url = new URL(headimgurl);
                                        HttpURLConnection conn = (HttpURLConnection) head_url.openConnection();
                                        conn.setConnectTimeout(5000);
                                        conn.setRequestMethod("GET");
                                        if (conn.getResponseCode() == 200) {
                                            InputStream inputStream = conn.getInputStream();
                                            wxInfo.setHead(inputStream);
                                        }
                                    } catch (ProtocolException e) {
                                        e.printStackTrace();
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }

                            @Override
                            public void failed(Throwable t) {

                            }

                            @Override
                            public void cancelled() {

                            }
                        });
                    }
                }
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });

        return true;
    }
}
