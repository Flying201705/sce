package com.nova.game;

import com.nova.game.screen.LoadScreen;

public class SceGame extends BaseGame {

    @Override
    public void create() {
        setScreen(new LoadScreen(this), false);
    }
}
