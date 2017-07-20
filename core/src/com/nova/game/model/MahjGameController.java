package com.nova.game.model;

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
	}
	
	public HashMap<Integer, MahjGroupData> getGroupDatas() {
		return mGroupDatas;
	}
	
	public void handleOutData(int data) {
		mManager.activeOutData(0, data);
	}
	
	public void handleMatchData(int matchType) {
		mManager.activeOperateData(0, matchType);
	}
}
