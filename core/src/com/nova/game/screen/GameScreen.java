package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nova.game.Actor.TimeUnit;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.Constants;

public class GameScreen extends BaseScreen {
    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Texture mBg;
    private TimeUnit mTime;
    private Texture[] mMahjTexture;

    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void show() {
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        loadMahj();

        mBatch = new SpriteBatch();
        mBg = new Texture("ScenceGame/background.jpg");

        mTime = new TimeUnit();
        mTime.setPosition(570, 295);
        mStage.addActor(mTime);
    }

    private void loadMahj() {
        mMahjTexture = new Texture[Constants.MAHJ_COUNT];
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
}
