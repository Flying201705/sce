package com.nova.game;

import com.nova.game.screen.LoadScreen;

public class SceGame extends BaseGame {

    public SceGame(SceApi api) {
        mApi = api;
    }

    @Override
    public void create() {
        setScreen(new LoadScreen(this), false);
    }
}
