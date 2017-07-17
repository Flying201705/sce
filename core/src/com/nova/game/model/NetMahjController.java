package com.nova.game.model;

import com.nova.game.constant.GameCommand;
import com.nova.game.handler.GameRequestDispatcher;
import com.nova.game.handler.MahjGameResponeDispatcher;
import com.nova.net.netty.handler.ResponseDispatcherManager;

class NetMahjController extends MahjController {

    private GameRequestDispatcher mRequest = new GameRequestDispatcher();

    @Override
    public void startGame() {
        super.startGame();
        ResponseDispatcherManager.getInstance().addGameResponseDispatcher(GameCommand.GAME_TYPE_MAHJ, new MahjGameResponeDispatcher());
        mRequest.startGame(0);
    }
}
