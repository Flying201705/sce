package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.Constants;
import com.nova.game.assetmanager.Assets;

public class LoadScreen extends BaseScreen {
    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Assets mAssents;

    private Texture mBg, mProgressBg, mProgressBar;
    private Image mLogo;

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

        mProgressBg = new Texture("SceneLoad/progress_bg.png");
        mProgressBar = new Texture("SceneLoad/progress_bar.png");
        mProgressX = (Gdx.graphics.getWidth() - mProgressBg.getWidth()) / 2;

        mAssents = Assets.getInstance();
        mAssents.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mBatch.begin();
        mBatch.draw(mBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mBatch.draw(mProgressBg, mProgressX, 10);
        Gdx.app.log("liuhao", "progress = " + mAssents.getProgress());
        mBatch.draw(mProgressBar, mProgressX, 10, mProgressBar.getWidth() * mAssents.getProgress(), mProgressBar.getHeight());
        mBatch.end();

        mStage.act();
        mStage.draw();

        if (mAssents.update()) {
            mGame.setScreen(new MainScreen(mGame));
            dispose();
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
}
