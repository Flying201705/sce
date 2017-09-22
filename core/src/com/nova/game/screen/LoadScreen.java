package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.Constants;
import com.nova.game.assetmanager.Assets;
import com.nova.game.utils.Log;
import com.nova.game.utils.WXInfo;
import com.nova.game.widget.SceButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadScreen extends BaseScreen {
    private static final String TAG = "LoadScreen";

    private static final int LOAD_HEAD_START = 0;
    private static final int LOAD_HEAD_OK = 1;
    private static final int LOAD_HEAD_FAILE = 2;

    private int mLoadHeadStatus = -1;

    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Assets mAssents;

    private Texture mBg, mProgressBg, mProgressBar;
    private Image mLogo;
    private SceButton mWXLogon;

    private int mProgressX;

    public LoadScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void show() {
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        mBatch = new SpriteBatch();

        mBg = new Texture("SceneLoad/bg.jpg");
        mLogo = new Image(new Texture("SceneLoad/logo.png"));
        mLogo.setPosition((Constants.WORLD_WIDTH - mLogo.getWidth()) / 2, (Constants.WORLD_HEIGHT - mLogo.getHeight()) / 2);
        mStage.addActor(mLogo);

        mWXLogon = new SceButton("SceneLoad/btn_weixin_logon.png");
        mWXLogon.setPosition((Constants.WORLD_WIDTH - mWXLogon.getWidth()) / 2, 60);
        mWXLogon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mGame.loginWeChat();
            }
        });
        mStage.addActor(mWXLogon);

        mProgressBg = new Texture("SceneLoad/progress_bg.png");
        mProgressBar = new Texture("SceneLoad/progress_bar.png");
        mProgressX = (Gdx.graphics.getWidth() - mProgressBg.getWidth()) / 2;
    }

    @Override
    public void resume() {
        Log.i(TAG, "show isLogined:" + WXInfo.getInstance().isLogined());
        if (WXInfo.getInstance().isLogined()) {
            mAssents = Assets.getInstance();
            mAssents.load();

            mWXLogon.setVisible(false);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mBatch.begin();
        mBatch.draw(mBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (WXInfo.getInstance().isLogined()) {
            mBatch.draw(mProgressBg, mProgressX, 10);
            mBatch.draw(mProgressBar, mProgressX, 10, mProgressBar.getWidth() * mAssents.getProgress(), mProgressBar.getHeight());
        }
        mBatch.end();

        mStage.act();
        mStage.draw();

        if (WXInfo.getInstance().isLogined()) {
            loadImageFromNet(WXInfo.getInstance().getHeadimgurl());

            if (mAssents.update() && (mLoadHeadStatus == LOAD_HEAD_OK || mLoadHeadStatus == LOAD_HEAD_FAILE)) {
                mGame.setScreen(new MainScreen(mGame));
                dispose();
            }
        }
    }

    @Override
    public void dispose() {
        mBg.dispose();
        mProgressBg.dispose();
        mProgressBar.dispose();

        mBatch.dispose();
        mStage.dispose();
    }

    private void loadImageFromNet(String imgUrl) {
        if (imgUrl == null) {
            return;
        }

        if (mLoadHeadStatus == LOAD_HEAD_START || mLoadHeadStatus == LOAD_HEAD_OK) {
            return;
        }

        Log.i(TAG, "loadImageFromNet imgUrl = " + imgUrl);

        mLoadHeadStatus = LOAD_HEAD_START;
        try {
            URL head_url = new URL(imgUrl);
            HttpURLConnection conn = (HttpURLConnection) head_url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inStream = conn.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                inStream.close();

                byte[] bytes = outStream.toByteArray();
                Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
                mAssents.mOwnerHeadTexture = new Texture(pixmap);
                pixmap.dispose();
                mLoadHeadStatus = LOAD_HEAD_OK;
            }
        } catch (Exception e) {
            mLoadHeadStatus = LOAD_HEAD_FAILE;
            Log.e(TAG, e.toString());
        }
    }
}
