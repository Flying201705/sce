package com.nova.game.model;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.HashMap;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGameData;
import nova.common.game.mahjong.data.MahjGroupData;

public class MahjGameController {
	private static final Object mLock = new Object();
	private static MahjGameController mController;
	private MahjController mManager;
	private MahjGameData mGameData;
	private HashMap<Integer, MahjGroupData> mGroupDatas = new HashMap<Integer, MahjGroupData>();
	private int mMatchType = 0;
	
	private MahjGameController(String type) {
		createGameManagerByType(type);
		mGameData = new MahjGameData();
	}
	
	private void createGameManagerByType(String type) {
		if (type.equals("local")) {
			mManager = new LocalMahjController();
		} else if (type.equals("net")) {
			mManager = new NetMahjController();
		} else {
			mManager = new MahjController();
		}
	}
	
	public static MahjGameController create(String type) {
		synchronized (mLock) {
			if (mController == null) {
				mController = new MahjGameController(type);
			}
			return mController;
		}
	}
	
	public static MahjGameController getInstance() {
		synchronized (mLock) {
			if (mController == null) {
				throw new RuntimeException("MahjGameController not yet created!?!");
			}
			return mController;
		}
	}
	
	public void startGame() {
		mManager.startGame();
	}
	
	public void stopGame() {
		mManager.stopGame();
	}
	
	public void setGameData(MahjGameData data) {
		mGameData = data;
	}

	public int getOwnerPlayerIndex() {
		return MahjRoomController.getInstance().getOwnerPlayerIndex();
	}
	
	public int getBanker() {
		return mGameData.getBanker();
	}
	
	public int getCurrentPlayer() {
		return mGameData.getCurrent();
	}
	
	public int getGodData() {
		return mGameData.getGod();
	}
	
	public int getRemainingSize() {
		return mGameData.getDatas().size();
	}
	
	public ArrayList<MahjData> getRemainingDatas() {
		return mGameData.getDatas();
	}
	
	public void setGroupDatas(HashMap<Integer, MahjGroupData> groupDatas) {
		mGroupDatas.clear();
		mGroupDatas.putAll(groupDatas);
		updateMatchType();
	}
	
	public HashMap<Integer, MahjGroupData> getGroupDatas() {
		return mGroupDatas;
	}

	public int getMatchType() {
		return mMatchType;
	}

	public void handleOutData(int data) {
		mManager.activeOutData(0, data);
	}
	
	public void handleMatchData(int matchType) {
		mManager.activeOperateData(0, matchType);
	}

	private void updateMatchType() {
		mMatchType = 0;
		int ownerPlayerId = getOwnerPlayerIndex();
		if (mGroupDatas.get(ownerPlayerId) == null) {
			return;
		}

        if (mGameData.getCurrent() == ownerPlayerId) {
			mMatchType = mGroupDatas.get(ownerPlayerId).updateMatchTypeForGetMahj();
			MahjData latestData = mGroupDatas.get(ownerPlayerId).getLatestData();
			if (latestData != null && latestData.getIndex() != 0) {
				mMatchType = mGroupDatas.get(ownerPlayerId).updateMatchTypeForGetMahj();
			}
		} else {
			if (mGroupDatas.get(getCurrentPlayer()) != null) {
				ArrayList<MahjData> outDatas = mGroupDatas.get(getCurrentPlayer()).getOutDatas();
				if (outDatas != null && outDatas.size() > 0) {
					mMatchType = mGroupDatas.get(0).updateMatchType(new MahjData(outDatas.get(outDatas.size() - 1).getIndex()));
				}
			}
		}
	}
}
