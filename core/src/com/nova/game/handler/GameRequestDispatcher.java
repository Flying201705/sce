package com.nova.game.handler;

import com.google.gson.JsonObject;
import com.nova.net.netty.ChannelManager;

import nova.common.GameCommand;
import nova.common.game.mahjong.util.MahjGameCommand;

/**
 * Created by zhangxx on 17-7-14.
 */

public class GameRequestDispatcher {
    private ChannelManager mChannel;
    private static final int TEST_PLAYER_ID = 100;

    public GameRequestDispatcher() {
        mChannel = ChannelManager.getInstance();
    }

    public void startGame() {
        JsonObject json = new JsonObject();
        json.addProperty("player", TEST_PLAYER_ID);
        json.addProperty("com", MahjGameCommand.REQUEST_GAME_START);
        mChannel.sendMessage(GameCommand.GAME_TYPE_MAHJ, json.toString());
    }

    public void startGame(int room) {
        JsonObject json = new JsonObject();
        json.addProperty("player", TEST_PLAYER_ID);
        json.addProperty("com", MahjGameCommand.REQUEST_ROOM_GAME_START);
        json.addProperty("room", String.valueOf(room));
        mChannel.sendMessage(GameCommand.GAME_TYPE_MAHJ, json.toString());
    }
}
