package com.nova.game.model;

import com.badlogic.gdx.audio.Sound;
import com.nova.game.assetmanager.Assets;

import nova.common.game.mahjong.handler.MahjGameDispatcher;

class MahjController implements MahjGameDispatcher {

    public MahjController() {

    }

    public void startGame(int roomId) {

    }

    public void resumeGame(int roomId) {

    }

    public void stopGame(int roomId) {

    }

    @Override
    public void activeOutData(int playerId, int data) {
        Sound sound = Assets.getInstance().getMajongSound(true/*PlayerInfoController.getInstance().getPlayerSex(playerId) == 0*/, data);
        if (sound != null) {
            sound.play();
        }
    }

    @Override
    public void activeOperateData(int playerId, int operateType) {

    }
}
