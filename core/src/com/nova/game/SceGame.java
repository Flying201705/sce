package com.nova.game;

import com.nova.game.screen.MainScreen;

public class SceGame extends BaseGame {

    @Override
    public void create() {
        setScreen(new MainScreen(this));
    }
}
