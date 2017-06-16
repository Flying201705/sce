package com.nova.game;

import com.badlogic.gdx.Screen;

public class BaseScreen implements Screen {
    protected BaseGame mGame;
        
    public BaseScreen(BaseGame game) {
        mGame = game;
    }
    
    @Override
    public void render(float delta) {
        
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void show() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
        
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public void doBackKeyAction() {
        mGame.goBack();
    }
}
