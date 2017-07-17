package com.nova.game.handler;

import com.nova.game.model.MahjGameController;
import com.nova.net.netty.handler.ResponseDispatcherManager;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGameData;
import nova.common.game.mahjong.data.MahjGroupData;
import nova.common.game.mahjong.data.MahjResponeData;
import nova.common.game.mahjong.data.MahjResponeResolver;
import nova.common.game.mahjong.handler.GameLogger;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * Created by zhangxx on 17-7-14.
 */

public class MahjGameResponeDispatcher implements ResponseDispatcherManager.GameResponseDispatcher {

    @Override
    public void processor(int commandId, String message) {
        GameLogger.getInstance().e("MahjGameResponeDispatcher", "processor, commandId = " + commandId + ", message = " + message);
        switch (commandId) {
            case 1250:
                processorGameInfoChange(message);
                break;
            default:
                break;
        }
    }

    private void processorGameInfoChange(String message) {
        Type type = new TypeToken<MahjResponeData>() {}.getType();
        Gson gson = new Gson();
        MahjResponeData responeData = gson.fromJson(message, type);
        updateGameData(responeData);
        updateGroupDatas(responeData);
    }

    private void updateGameData(MahjResponeData responeData) {
        MahjGameData gameData = new MahjGameData();
        gameData.setBanker(responeData.getBanker());
        gameData.setCurrent(responeData.getCurrent());
        gameData.setGod(responeData.getGod());
        ArrayList<MahjData> remainingdatas = new ArrayList<MahjData>();
        for (int index : responeData.getRemainingDatas()) {
            remainingdatas.add(new MahjData(index));
        }
        gameData.setDatas(remainingdatas);
        MahjGameController.getInstance().setGameData(gameData);
    }

    private void updateGroupDatas(MahjResponeData responeData) {
        MahjGameController.getInstance().setGroupDatas(MahjResponeResolver.getGroupDatasForResponeData(responeData));
    }
}
