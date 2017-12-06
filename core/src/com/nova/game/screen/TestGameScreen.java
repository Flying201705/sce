package com.nova.game.screen;

import com.nova.game.BaseGame;

/**
 * Created by zhangxx on 17-12-5.
 */

public class TestGameScreen extends GameScreen {
    public TestGameScreen(BaseGame game) {
        super(game);
    }

    @Override
    protected String getGameType() {
        return "test";
    }
}
