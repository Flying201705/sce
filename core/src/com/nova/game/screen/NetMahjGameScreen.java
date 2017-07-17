package com.nova.game.screen;

import com.nova.game.BaseGame;

/**
 * Created by zhangxx on 17-7-14.
 */

public class NetMahjGameScreen extends GameScreen {
    public NetMahjGameScreen(BaseGame game) {
        super(game);
    }

    @Override
    protected String getGameType() {
        return "net";
    }
}
