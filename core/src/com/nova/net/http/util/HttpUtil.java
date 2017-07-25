package com.nova.net.http.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpStatus;

import java.util.HashMap;

import nova.common.game.mahjong.handler.GameLogger;

public class HttpUtil {

	private static final int HTTP_RESULT_SUCCESS = 0;
	private static final int HTTP_RESULT_FAIL = 1;
	
	public static final String RESULT_FAIL = "1";

	public static void doPost(final String reqUrl,
							  final HashMap<String, String> params, ResultCallback callback) {

		mResultCallback = callback;
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
				httpGet.setUrl(reqUrl);
				httpGet.setContent(HttpParametersUtils.convertHttpParameters(params));
				Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
					public void handleHttpResponse(HttpResponse httpResponse) {
						int status = httpResponse.getStatus().getStatusCode();
						String result = httpResponse.getResultAsString();
						GameLogger.getInstance().e("HttpUtil", "handleHttpResponse, status = " + status + ", result = " + result);
						if (status == HttpStatus.SC_OK && !result.isEmpty()) {
							handlerResult(HTTP_RESULT_SUCCESS, result);
						} else {
							handlerResult(HTTP_RESULT_FAIL, null);
						}
					}

					public void failed(Throwable t) {
						GameLogger.getInstance().e("HttpUtil", "handleHttpResponse failed, error : " + t.toString());
						handlerResult(HTTP_RESULT_FAIL, null);
					}

					@Override
					public void cancelled() {
						GameLogger.getInstance().e("HttpUtil", "handleHttpResponse cancelled");
					}
				});
			}
		}).start();

	}

	private static void handlerResult(int resultcode, String result) {
		if (resultcode == HTTP_RESULT_FAIL) {
			mResultCallback.getResult(RESULT_FAIL);
		} else if (resultcode == HTTP_RESULT_SUCCESS) {
			mResultCallback.getResult(result);
		}
	}

	static ResultCallback mResultCallback = new ResultCallback() {

		@Override
		public void getResult(String result) {
			
		}
	};
}
