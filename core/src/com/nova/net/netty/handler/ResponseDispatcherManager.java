package com.nova.net.netty.handler;

import java.util.HashMap;

import io.netty.buffer.ByteBuf;

/**
 * Created by zhangxx on 17-6-22.
 */

public class ResponseDispatcherManager {
    public interface GameResponseDispatcher {
        public void processor(int commandId, String message);
    }

    public interface  MessageResponseDispatcher {
        public void processor(int commandId, ByteBuf message);
    }

    private static ResponseDispatcherManager mInstance;
    private HashMap<Integer, GameResponseDispatcher> mGameResponseDispatcherMaps = new HashMap<Integer, GameResponseDispatcher>();
    private HashMap<Integer, MessageResponseDispatcher> mMessageResponseDispatcherMaps = new HashMap<Integer, MessageResponseDispatcher>();

    private ResponseDispatcherManager() {

    }

    public static ResponseDispatcherManager getInstance() {
        if (mInstance == null) {
            mInstance = new ResponseDispatcherManager();
        }

        return mInstance;
    }

    public void addGameResponseDispatcher(int type, GameResponseDispatcher dispatcher) {
        mGameResponseDispatcherMaps.put(type, dispatcher);
    }

    public void addMessageResponseDispatcher(int type, MessageResponseDispatcher dispatcher) {
        mMessageResponseDispatcherMaps.put(type, dispatcher);
    }

    public GameResponseDispatcher getGameResponseDispatcher(int type) {
        return mGameResponseDispatcherMaps.get(type);
    }

    public MessageResponseDispatcher getMessageResponseDispatcher(int type) {
        return mMessageResponseDispatcherMaps.get(type);
    }
}
