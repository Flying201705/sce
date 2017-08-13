package com.funlisten.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.nova.game.AndroidLauncher;
import com.nova.game.R;
import com.nova.game.Utils;
import com.nova.game.utils.WXInfo;
import com.nova.game.utils.WXUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    private static final String APP_ID = "wx2ce9b87064d829d5";
    private static final String APP_SECRET = "3661eb56e04171cbfb74b698be6cc295";

    private IWXAPI mWXapi;
    private WXInfo mWxInfo;

    private static final int SceneSession = 0;
    private static final int SceneTimeline = 1;

    public static String ReqWxLogin = "ReqWxLogin";
    public static String ReqWxShareImg = "ReqWxShareImg";
    public static String ReqWxShareTxt = "ReqWxShareTxt";
    public static String ReqWxShareUrl = "ReqWxShareUrl";
    public static String ReqWXPay = "ReqWXPay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Log.i(TAG, "onCreate");

        Intent intent = getIntent();

        mWXapi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        mWXapi.handleIntent(intent, this);
        mWXapi.registerApp(APP_ID);

        mWxInfo = WXInfo.getInstance();

        Button sign_in = (Button) findViewById(R.id.wx_sign_in);
        sign_in.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                reqLogin();
            }
        });

        Button entry_game = (Button) findViewById(R.id.entry_game);
        entry_game.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText accountView = (EditText) WXEntryActivity.this.findViewById(R.id.account);
                String openId = accountView.getText().toString();
                if (!TextUtils.isEmpty(openId)) {
                    entryGame(openId);
                }
            }
        });

        if (intent.hasExtra(ReqWxLogin)) {
            reqLogin();
        } else if (intent.hasExtra(ReqWxShareImg)) {
            String ImgPath = intent.getStringExtra("ImgPath");
            int nType = intent.getIntExtra("ShareType", 0);
            reqShareImg(ImgPath, nType);
        } else if (intent.hasExtra(ReqWxShareTxt)) {
            String ShareText = intent.getStringExtra("ShareText");
            int nType = intent.getIntExtra("ShareType", 0);
            reqShareTxt(ShareText, nType);
        } else if (intent.hasExtra(ReqWxShareUrl)) {
            String ShareUrl = intent.getStringExtra("ShareUrl");
            String ShareTitle = intent.getStringExtra("ShareTitle");
            String ShareDesc = intent.getStringExtra("ShareDesc");
            int nType = intent.getIntExtra("ShareType", 0);
            reqShareUrl(ShareUrl, ShareTitle, ShareDesc, nType);
        }
        finish();
    }

    @Override
    public void onReq(BaseReq req) {
        Log.i(TAG, "public void onReq(BaseReq req) !!!!!!!");
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                Log.i(TAG, "onReq:COMMAND_GETMESSAGE_FROM_WX");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                Log.i(TAG, "onReq:COMMAND_SHOWMESSAGE_FROM_WX");
                break;
            default:
                break;
        }

        Log.i(TAG, "onReq:" + req.getType());
    }

    @Override
    public void onResp(BaseResp resp) {

        Log.i(TAG, "onResp: =" + resp.getType());

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                requestTokenWithHttpClient(((SendAuth.Resp) resp).code);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                break;
            default:
                break;
        }
    }

    private void entryGame(String openId) {
        Intent intent = new Intent(this, AndroidLauncher.class);
        intent.putExtra("openid", openId);
        startActivity(intent);
        finish();
    }

    private void requestTokenWithHttpClient(final String code) {
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", APP_ID, APP_SECRET, code);

        WXUtils.requestTokenWithHttpClient(url);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        mWXapi.handleIntent(intent, this);
    }

    public void reqLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "sce_sign_in";
        mWXapi.sendReq(req);
        Log.d(TAG, "reqLogin!!!!");
    }

    public void reqShareImg(String ImgPath, int nType) {
        File file = new File(ImgPath);
        if (!file.exists()) {
            Log.d(TAG, "reqShare file not exists:" + ImgPath);
            return;
        }

        Bitmap bmp = BitmapFactory.decodeFile(ImgPath);
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 128, 72, true);
        bmp.recycle();
        msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        if (nType == SceneTimeline) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else if (nType == SceneSession) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        mWXapi.sendReq(req);
        Log.d(TAG, "reqShare Ok:" + ImgPath);
    }

    public void reqShareTxt(String text, int nType) {
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // msg.title = "Will be ignored";
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        if (nType == SceneTimeline) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else if (nType == SceneSession) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        mWXapi.sendReq(req);


        Log.d(TAG, "reqShareTxt Ok:" + text);
    }

    public void reqShareUrl(String url, String title, String desc, int nType) {
        WXWebpageObject textObj = new WXWebpageObject();
        textObj.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.title = title;
        msg.description = desc;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        if (nType == SceneTimeline) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else if (nType == SceneSession) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        mWXapi.sendReq(req);


        Log.d(TAG, "reqShareUrl Ok:" + url);
    }

    public void reqShareTxtCB(String text, int nType) {
        // send appdata with no attachment
        WXAppExtendObject appdata = new WXAppExtendObject("lallalallallal", "filePath");

        boolean bValue = appdata.checkArgs();
        if (!bValue) {
            Log.d(TAG, "reqShareTxtCB Error:" + text);
        }

        WXMediaMessage msg = new WXMediaMessage();
        msg.title = "11";
        msg.description = "22";
        msg.mediaObject = appdata;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("appdata");
        req.message = msg;

        if (nType == SceneTimeline) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else if (nType == SceneSession) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        mWXapi.sendReq(req);

        Log.d(TAG, "reqShareTxtCB Ok:" + text);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
