package com.nova.game.handler;

import com.google.gson.JsonObject;
import com.nova.game.Constants;
import com.nova.net.netty.ChannelManager;

import nova.common.GameCommand;
import nova.common.game.mahjong.handler.GameLogger;
import nova.common.game.mahjong.util.MahjGameCommand;

/**
 * Created by zhangxx on 17-7-14.
 */

public class GameRequestDispatcher {
    private ChannelManager mChannel;

    public GameRequestDispatcher() {
        mChannel = ChannelManager.getInstance();
    }

    public void createRoom() {
        JsonObject json = new JsonObject();
        json.addProperty("player", Constants.TEST_PLAYER_ID);
        json.addProperty("com", MahjGameCommand.REQUEST_ROOM_CREATE);
        mChannel.sendMessage(GameCommand.GAME_TYPE_MAHJ, json.toString());
    }

    public void joinRoom(int room) {
        JsonObject json = new JsonObject();
        json.addProperty("player", Constants.TEST_PLAYER_ID);
        json.addProperty("com", MahjGameCommand.REQUEST_ROOM_JOIN);
        json.addProperty("room", room);
        GameLogger.getInstance().e("GameRequestDispatcher", "joinRoom, json = " + json.toString());
        mChannel.sendMessage(GameCommand.GAME_TYPE_MAHJ, json.toString());
    }

    public void leaveRoom(int room) {
        JsonObject json = new JsonObject();
        json.addProperty("player", Constants.TEST_PLAYER_ID);
        json.addProperty("com", MahjGameCommand.REQUEST_ROOM_LEAVE);
        json.addProperty("room", room);
        GameLogger.getInstance().e("GameRequestDispatcher", "leaveRoom, json = " + json.toString());
        mChannel.sendMessage(GameCommand.GAME_TYPE_MAHJ, json.toString());
    }

    public void startGame() {
        JsonObject json = new JsonObject();
        json.addProperty("player", Constants.TEST_PLAYER_ID);
        json.addProperty("com", MahjGameCommand.REQUEST_GAME_START);
        mChannel.sendMessage(GameCommand.GAME_TYPE_MAHJ, json.toString());
    }

    public void startGame(int room) {
        JsonObject json = new JsonObject();
        json.addProperty("player", Constants.TEST_PLAYER_ID);
        json.addProperty("com", MahjGameCommand.REQUEST_GAME_ROOM_START);
        json.addProperty("room", room);
        mChannel.sendMessage(GameCommand.GAME_TYPE_MAHJ, json.toString());
    }

    public void activeOutData(int room, int playerId, int data) {
        JsonObject json = new JsonObject();
        json.addProperty("player", Constants.TEST_PLAYER_ID);
        json.addProperty("com", MahjGameCommand.REQUEST_OUT_DATA);
        json.addProperty("room", room);
        json.addProperty("index", playerId);
        json.addProperty("data", data);
        mChannel.sendMessage(GameCommand.GAME_TYPE_MAHJ, json.toString());
    }

    public void activeOperateData(int room, int playerId, int operateType) {
        JsonObject json = new JsonObject();
        json.addProperty("player", Constants.TEST_PLAYER_ID);
        json.addProperty("com", MahjGameCommand.REQUEST_OPERATE_DATA);
        json.addProperty("room", room);
        json.addProperty("index", playerId);
        json.addProperty("operate", operateType);
        mChannel.sendMessage(GameCommand.GAME_TYPE_MAHJ, json.toString());
    }
}
