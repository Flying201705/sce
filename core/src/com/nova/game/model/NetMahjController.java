package com.nova.game.model;

import com.nova.game.handler.GameRequestDispatcher;

class NetMahjController extends MahjController {

    private GameRequestDispatcher mRequest = new GameRequestDispatcher();

    public NetMahjController() {

    }

    @Override
    public void startGame(int roomId) {
        super.startGame(roomId);
        if (roomId >= 0) {
            mRequest.startGame(roomId);
        } else {
            mRequest.startGame();
        }
    }

    @Override
    public void resumeGame(int roomId) {
        super.resumeGame(roomId);
        if (roomId >= 0) {
            mRequest.resumeGame(roomId);
        }
    }

    @Override
    public void stopGame(int roomId) {
        super.stopGame(roomId);
        mRequest.stopGame(roomId);
    }

    @Override
    public void activeOutData(int playerId, int data) {
        super.activeOutData(playerId, data);
        mRequest.activeOutData(MahjRoomController.getInstance().getRoomId(), data);
    }

    @Override
    public void activeOperateData(int playerId, int operateType) {
        super.activeOperateData(playerId, operateType);
        mRequest.activeOperateData(MahjRoomController.getInstance().getRoomId(), operateType);
    }
}
