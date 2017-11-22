package com.nova.game.handler;

import com.google.gson.JsonObject;
import com.nova.game.model.PlayerInfoController;
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
        json.addProperty("player", PlayerInfoController.getInstance().getOwnerPlayerId());
        json.addProperty("com", MahjGameCommand.REQUEST_ROOM_CREATE);
        mChannel.sendMessage(GameCommand.MAHJ_TYPE_GAME, json.toString());
    }

    public void joinRoom(int room) {
        JsonObject json = new JsonObject();
        json.addProperty("player", PlayerInfoController.getInstance().getOwnerPlayerId());
        json.addProperty("com", MahjGameCommand.REQUEST_ROOM_JOIN);
        json.addProperty("room", room);
        GameLogger.getInstance().e("GameRequestDispatcher", "joinRoom, json = " + json.toString());
        mChannel.sendMessage(GameCommand.MAHJ_TYPE_GAME, json.toString());
    }

    public void leaveRoom(int room) {
        JsonObject json = new JsonObject();
        json.addProperty("player", PlayerInfoController.getInstance().getOwnerPlayerId());
        json.addProperty("com", MahjGameCommand.REQUEST_ROOM_LEAVE);
        json.addProperty("room", room);
        GameLogger.getInstance().e("GameRequestDispatcher", "leaveRoom, json = " + json.toString());
        mChannel.sendMessage(GameCommand.MAHJ_TYPE_GAME, json.toString());
    }

    public void startGame() {
        JsonObject json = new JsonObject();
        json.addProperty("player", PlayerInfoController.getInstance().getOwnerPlayerId());
        json.addProperty("com", MahjGameCommand.REQUEST_GAME_START);
        // 测试程序
        // json.addProperty("debug", 4);
        // 测试程序
        mChannel.sendMessage(GameCommand.MAHJ_TYPE_GAME, json.toString());
    }

    public void startGame(int room) {
        JsonObject json = new JsonObject();
        json.addProperty("player", PlayerInfoController.getInstance().getOwnerPlayerId());
        json.addProperty("com", MahjGameCommand.REQUEST_GAME_ROOM_START);
        json.addProperty("room", room);
        mChannel.sendMessage(GameCommand.MAHJ_TYPE_GAME, json.toString());
    }

    public void stopGame(int room) {
        JsonObject json = new JsonObject();
        json.addProperty("player", PlayerInfoController.getInstance().getOwnerPlayerId());
        json.addProperty("com", MahjGameCommand.REQUEST_GAME_STOP);
        json.addProperty("room", room);
        mChannel.sendMessage(GameCommand.MAHJ_TYPE_GAME, json.toString());
    }

    public void resumeGame(int room) {
        JsonObject json = new JsonObject();
        json.addProperty("player", PlayerInfoController.getInstance().getOwnerPlayerId());
        json.addProperty("com", MahjGameCommand.REQUEST_GAME_RESUME);
        json.addProperty("room", room);
        mChannel.sendMessage(GameCommand.MAHJ_TYPE_GAME, json.toString());
    }

    public void activeOutData(int room, int data) {
        JsonObject json = new JsonObject();
        json.addProperty("player", PlayerInfoController.getInstance().getOwnerPlayerId());
        json.addProperty("com", MahjGameCommand.REQUEST_OUT_DATA);
        json.addProperty("room", room);
        json.addProperty("data", data);
        mChannel.sendMessage(GameCommand.MAHJ_TYPE_GAME, json.toString());
    }

    public void activeOperateData(int room, int operateType) {
        JsonObject json = new JsonObject();
        json.addProperty("player", PlayerInfoController.getInstance().getOwnerPlayerId());
        json.addProperty("com", MahjGameCommand.REQUEST_OPERATE_DATA);
        json.addProperty("room", room);
        json.addProperty("operate", operateType);
        mChannel.sendMessage(GameCommand.MAHJ_TYPE_GAME, json.toString());
    }

    public void sendText(int room, String text) {
        mChannel.sendText(GameCommand.MAHJ_TYPE_MESSAGE, room, PlayerInfoController.getInstance().getOwnerPlayerId(), text);
    }

    public void sendVoice(int room, String file) {
        mChannel.sendVoice(GameCommand.MAHJ_TYPE_MESSAGE, room, PlayerInfoController.getInstance().getOwnerPlayerId(), file);
    }

    public void sendTextAndVoice(int room, String position) {
        mChannel.sendTextAndVoice(GameCommand.MAHJ_TYPE_MESSAGE, room, PlayerInfoController.getInstance().getOwnerPlayerId(), position);
    }
}
