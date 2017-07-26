package com.nova.game.model;

import com.nova.game.handler.GameRequestDispatcher;
import com.nova.game.handler.MahjGameResponeDispatcher;
import com.nova.net.netty.handler.ResponseDispatcherManager;

import nova.common.GameCommand;

class NetMahjController extends MahjController {

    private GameRequestDispatcher mRequest = new GameRequestDispatcher();

    public NetMahjController() {
        ResponseDispatcherManager.getInstance().addGameResponseDispatcher(GameCommand.GAME_TYPE_MAHJ, new MahjGameResponeDispatcher());
    }

    @Override
    public void startGame() {
        super.startGame();
        mRequest.startGame();
    }

    @Override
    public void activeOutData(int playerId, int data) {
        super.activeOutData(playerId, data);
        mRequest.activeOutData(MahjRoomController.getInstance().getRoomId(), playerId, data);
    }

    @Override
    public void activeOperateData(int playerId, int operateType) {
        super.activeOperateData(playerId, operateType);
        mRequest.activeOperateData(MahjRoomController.getInstance().getRoomId(), playerId, operateType);
    }
}
