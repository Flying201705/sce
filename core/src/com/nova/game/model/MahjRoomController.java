package com.nova.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import nova.common.game.mahjong.util.GameTimer;
import nova.common.game.mahjong.util.TimerCallback;
import nova.common.room.data.PlayerInfo;

/**
 * Created by zhangxx on 17-7-20.
 */

public class MahjRoomController {

    private class ChatMessage {
        String message;
        long time;

        public ChatMessage(String message, long time) {
            this.message = message;
            this.time = time;
        }

        public String getMessage() {
            return this.message;
        }

        public long getTime() {
            return this.time;
        }
    }

    private static final Object mLock = new Object();
    private static MahjRoomController mController;

    private int mRoomId = -1;
    private int mOwnerPlayerIndex = 0;
    private HashMap<Integer, PlayerInfo> mPlayerInfos = new HashMap<Integer, PlayerInfo>();
    private HashMap<Integer, Sound> mPlayerSounds = new HashMap<Integer, Sound>();
    private HashMap<Integer, ChatMessage> mPlayerMessages = new HashMap<Integer, ChatMessage>();
    private boolean mRoomStartComplete = false;
    private int mRoomResult = 0;
    private String mRoomResultReson = "";
    private GameTimer mGameTimer;

    private TimerCallback mCallback = new TimerCallback() {

        @Override
        public void handleMessage() {
            for (int i = 0; i < 4; i++) {
                if (mPlayerMessages.get(i) != null) {
                    if (System.currentTimeMillis() - mPlayerMessages.get(i).getTime() >= 3000) {
                        mPlayerMessages.remove(i);
                    }
                }
            }
        }
    };

    private MahjRoomController() {
        mGameTimer = new GameTimer(mCallback);
        mGameTimer.start();
    }

    public static MahjRoomController getInstance() {
        synchronized (mLock) {
            if (mController == null) {
                mController = new MahjRoomController();
            }
            return mController;
        }
    }

    public void setRoomId(int roomId) {
        mRoomId = roomId;
    }

    public int getRoomId() {
        return mRoomId;
    }

    public void setPlayerInfos(HashMap<Integer, PlayerInfo> players) {
        mPlayerInfos.clear();
        mPlayerInfos.putAll(players);
        updateOwnerPlayerIndex();
        updatePlayerInfos();
    }

    public HashMap<Integer, PlayerInfo> getPlayerInfos() {
        return mPlayerInfos;
    }

    public int getOwnerPlayerIndex() {
        return mOwnerPlayerIndex;
    }

    public void addPlayerSound(int playerId, Sound sound) {
        mPlayerSounds.put(playerId, sound);
    }

    public Sound getPlayerSound(int playerId) {
        return mPlayerSounds.get(playerId);
    }

    public void addPlayerMessage(int playerId, String message) {
        ChatMessage chat = new ChatMessage(message, System.currentTimeMillis());
        mPlayerMessages.put(playerId, chat);
    }

    public String getPlayerMessage(int playerId) {
        if (mPlayerMessages.get(playerId) != null) {
            return mPlayerMessages.get(playerId).getMessage();
        }

        return null;
    }

    public boolean isRoomStart() {
        return mRoomStartComplete;
    }

    public void onRoomGameStartComplete(int resultCode) {
        mRoomStartComplete = true;
        if (resultCode >= 0) {
            mRoomId = resultCode;
        }
    }

    public void setRoomResult(int result) {
        mRoomResult = result;
    }

    public void resetRoomResult() {
        mRoomResult = 0;
    }

    public int getRoomResult() {
        return mRoomResult;
    }

    public void setmRoomResultReson(String reson) {
        mRoomResultReson = reson;
    }

    public String getmRoomResultReson() {
        return mRoomResultReson;
    }

    public void replacePlayerPosition(int tarPosition) {
        String account = Gdx.app.getPreferences("LoginInfo").getString("account");
        int sourPosition = -1;
        Set set = mPlayerInfos.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == null) {
                continue;
            }
            if (((PlayerInfo) entry.getValue()).getId() == getPlayerId(account)) {
                sourPosition = (Integer) (entry.getKey());
                break;
            }
        }

        if (sourPosition == tarPosition || sourPosition <= 0) {
            return;
        }

        /*if (sourPosition > 0) {
            GameRequestDispatcher request = new GameRequestDispatcher();
            request.replacePlayerPosition(mRoomId, sourPosition, tarPosition);
        }*/
    }

    public int getPlayerId(String account) {
        return 0;
    }

    private void updatePlayerInfos() {
        for (int i = 0; i < mPlayerInfos.size(); i++) {
            if (mPlayerInfos.get(i) == null) {
                continue;
            }
            PlayerInfoController.getInstance().updatePlayerInfo(mPlayerInfos.get(i));
        }
    }

    private void updateOwnerPlayerIndex() {
        for (int i = 0; i < mPlayerInfos.size(); i++) {
            if (mPlayerInfos.get(i).getId() == PlayerInfoController.getInstance().getOwnerPlayerId()) {
                mOwnerPlayerIndex = i;
                break;
            }
        }
    }
}
