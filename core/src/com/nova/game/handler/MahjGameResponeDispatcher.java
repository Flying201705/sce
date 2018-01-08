package com.nova.game.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nova.game.model.MahjGameController;
import com.nova.game.model.MahjRoomController;
import com.nova.net.netty.handler.ResponseDispatcherManager;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGameData;
import nova.common.game.mahjong.data.MahjResponeData;
import nova.common.game.mahjong.data.MahjResponeResolver;
import nova.common.game.mahjong.data.MahjResultData;
import nova.common.game.mahjong.handler.GameLogger;
import nova.common.game.mahjong.util.MahjGameCommand;
import nova.common.room.data.PlayerInfo;

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
            case MahjGameCommand.RESPONE_GAME_INFO_UPDATE:
                processorGameInfoChange(message);
                break;
            case MahjGameCommand.RESPONE_GAME_RESULT:
                processorGameResult(message);
                break;
            case MahjGameCommand.RESPONE_PLAYER_INFO_UPDATE:
                processorPlayerInfoChange(message);
                break;
            case MahjGameCommand.RESPONE_ROOM_INFO_UPDATE:
                processorRoomInfoChange(message);
                break;
            case MahjGameCommand.RESPONE_ROOM_STATE_UPDATE:
                processorRoomStateChange(message);
            default:
                break;
        }
    }

    private void processorGameInfoChange(String message) {
        MahjGameController.getInstance().setGameMessageProcessFinish(false);
        Type type = new TypeToken<MahjResponeData>() {}.getType();
        Gson gson = new Gson();
        MahjResponeData responeData = gson.fromJson(message, type);
        updateGameData(responeData);
        updateGroupDatas(responeData);
        MahjGameController.getInstance().setGameMessageProcessFinish(true);
    }

    private void updateGameData(MahjResponeData responeData) {
        MahjGameData gameData = new MahjGameData();
        gameData.setBanker(responeData.getBanker());
        gameData.setWinner(responeData.getWinner());
        gameData.setCurrent(responeData.getCurrent());
        gameData.setLastout(responeData.getLastout());
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

    private void processorGameResult(String message) {
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        JsonArray datas = json.get("result").getAsJsonArray();
        Type type = new TypeToken<ArrayList<MahjResultData>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<MahjResultData> temps = gson.fromJson(datas.toString(), type);
        HashMap<Integer, MahjResultData> results = new HashMap<Integer, MahjResultData>();
        for (int i = 0; i < temps.size(); i++) {
            results.put(i, temps.get(i));
        }

        /**
        // LOG打印跟踪番数
        for (int i = 0; i < 4; i++) {
            if (results.get(i).getOrders() != null && results.get(i).getOrders().size() > 0) {
                Log.e("zhangxx", "Player " + i + " 总番数：" + results.get(i).getTotalFan() + "， 赢：" + results.get(i).getBalance());
                Log.e("zhangxx", "   " + results.get(i).getOrders());
                for (FanData data : results.get(i).getFanDatas()) {
                    Log.e("zhangxx", "   "  + data.getNum() + "番" + "  " + data.getFanDisplayType());
                }
            } else {
                Log.e("zhangxx", "Player " + i + " 总番数：" + results.get(i).getTotalFan() + "， 输：" + results.get(i).getBalance());
            }
        }
        **/
        MahjGameController.getInstance().setResultDatas(results);
    }

    private void processorPlayerInfoChange(String message) {
        try {
            JsonObject json = new JsonParser().parse(message).getAsJsonObject();
            int roomId = json.get("room").getAsInt();
            MahjRoomController.getInstance().setRoomId(roomId);
            Type type = new TypeToken<ArrayList<PlayerInfo>>() {}.getType();
            Gson gson = new Gson();
            ArrayList<PlayerInfo> infos = gson.fromJson(json.get("players").toString(), type);
            HashMap<Integer, PlayerInfo> playerInfos = new HashMap<Integer, PlayerInfo>();
            for (int i = 0; i < infos.size(); i++) {
                playerInfos.put(i, infos.get(i));
            }
            MahjRoomController.getInstance().changePlayerInfos(playerInfos);
        } catch (Exception e) {
            GameLogger.getInstance().e("MahjGameResponeDispatcher", "processorPlayerInfoChange, e = " + e.toString());
        }
    }

    private void processorRoomInfoChange(String message) {
        try {
            JsonObject json = new JsonParser().parse(message).getAsJsonObject();
            int roomId = json.get("room").getAsInt();
            MahjRoomController.getInstance().setRoomId(roomId);
            Type type = new TypeToken<ArrayList<PlayerInfo>>() {}.getType();
            Gson gson = new Gson();
            ArrayList<PlayerInfo> infos = gson.fromJson(json.get("players").toString(), type);
            HashMap<Integer, PlayerInfo> playerInfos = new HashMap<Integer, PlayerInfo>();
            for (int i = 0; i < infos.size(); i++) {
                playerInfos.put(i, infos.get(i));
            }
            MahjRoomController.getInstance().setPlayerInfos(playerInfos);
        } catch (Exception e) {
            GameLogger.getInstance().e("MahjGameResponeDispatcher", "processorRoomInfoChange, e = " + e.toString());
        }
    }

    private void processorRoomStateChange(String message) {
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        int result = json.get("result").getAsInt();
        String reson = json.get("reson").getAsString();
        MahjRoomController.getInstance().setRoomResult(result);
        MahjRoomController.getInstance().setmRoomResultReson(reson);
    }
}
