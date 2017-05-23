package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;

public class MainScreen extends BaseScreen {
    SpriteBatch mBatch;
    Texture mImg;

    public MainScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void show() {
        mBatch = new SpriteBatch();
        mImg = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        mBatch.draw(mImg, 0, 0);
        mBatch.end();
    }

    @Override
    public void dispose() {
        mBatch.dispose();
        mImg.dispose();
    }
}
