package com.nova.game;

import com.nova.game.screen.LoadScreen;
import com.nova.game.screen.TestScreen;

public class SceGame extends BaseGame {

    public SceGame(SceApi api) {
        mApi = api;
    }

    @Override
    public void create() {
        setScreen(new LoadScreen(this), false);
        // setScreen(new TestScreen(this), false);
    }
}
