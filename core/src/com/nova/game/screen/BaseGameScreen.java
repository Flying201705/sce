package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nova.game.BaseDialog;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.actor.TimeUnit;
import com.nova.game.assetmanager.Assets;
import com.nova.game.dialog.ChatDialog;

/**
 * Created by zhangxx on 17-10-27.
 */

public class BaseGameScreen extends BaseScreen {
    protected BaseStage mStage;
    protected SpriteBatch mBatch;
    protected TimeUnit mTime;
    private Texture mBg;
    private ChatDialog mChatDialog;
    private BaseDialog mQuitDialog;

    private ClickListener mMessageListener = new ClickListener() {
        public void clicked(InputEvent event, float x, float y) {
            mChatDialog.setVisible(true);
            mStage.addActor(mChatDialog);
        };
    };

    public BaseGameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void show() {
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        mStage.setDebugAll(false);

        mBatch = new SpriteBatch();
        mBg = Assets.getInstance().mBaseGameScreenBackground;

        mTime = new TimeUnit();
        mTime.setPosition(564, 330);
        mStage.addActor(mTime);

        TextureRegion[][] smTR = TextureRegion.split(new Texture(Gdx.files.internal("SceneGame/btn_message.png")), 50, 51);
        ImageButton smBtn = new ImageButton(new TextureRegionDrawable(smTR[0][1]), new TextureRegionDrawable(smTR[0][0]));
        smBtn.setPosition(35, 120);
        smBtn.addListener(mMessageListener);
        mStage.addActor(smBtn);

        mChatDialog = new ChatDialog();
        mChatDialog.setVisible(false);

        mQuitDialog = new BaseDialog(getQuitTitle());
        mQuitDialog.setVisible(false);
        mQuitDialog.setPrimaryButton(getQuitPrimary(), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleQuitEvent();
                mGame.goBack();
            }
        });
        mQuitDialog.setSecondaryButton(getQuitSecondary(), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mQuitDialog.setVisible(false);
                mQuitDialog.remove();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        mBatch.draw(mBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mBatch.end();

        mStage.act();
        mStage.draw();
    }

    @Override
    public void dispose() {
        mBatch.dispose();
        mBg.dispose();
        mStage.dispose();
    }

    @Override
    public void doBackKeyAction() {
        if (mChatDialog.isVisible()) {
            mChatDialog.setVisible(false);
            mChatDialog.remove();
            return;
        }

        if (mQuitDialog.isVisible()) {
            mQuitDialog.setVisible(false);
            mQuitDialog.remove();
        } else {
            mQuitDialog.setVisible(true);
            mStage.addActor(mQuitDialog);
        }
    }

    public String getQuitTitle() {
        return "退出游戏";
    }

    public String getQuitPrimary() {
        return "退出";
    }

    public String getQuitSecondary() {
        return "取消";
    }

    public void handleQuitEvent() {

    }
}
