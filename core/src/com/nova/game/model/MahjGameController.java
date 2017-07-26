package com.nova.game.model;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.HashMap;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGameData;
import nova.common.game.mahjong.data.MahjGroupData;
import nova.common.game.mahjong.util.MahjConstant;

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
		boolean isOwnerOut = isOwnerOut(groupDatas);
		mGroupDatas.clear();
		mGroupDatas.putAll(groupDatas);
		updateMatchType(isOwnerOut);
	}
	
	public HashMap<Integer, MahjGroupData> getGroupDatas() {
		return mGroupDatas;
	}

	public int getMatchType() {
		return mMatchType;
	}

	public void handleOutData(int data) {
		mManager.activeOutData(getOwnerPlayerIndex(), data);
	}
	
	public void handleMatchData(int matchType) {
		mManager.activeOperateData(getOwnerPlayerIndex(), matchType);
	}

	private void updateMatchType(boolean isOwnerOut) {
		mMatchType = 0;
		int ownerPlayerId = getOwnerPlayerIndex();
		if (mGroupDatas.get(ownerPlayerId) == null) {
			return;
		}

        if (getCurrentPlayer() == ownerPlayerId) {
			mMatchType = mGroupDatas.get(ownerPlayerId).updateMatchTypeForGetMahj();
			MahjData latestData = mGroupDatas.get(ownerPlayerId).getLatestData();
			if (latestData != null && latestData.getIndex() != 0) {
				mMatchType = mGroupDatas.get(ownerPlayerId).updateMatchTypeForGetMahj();
			}
		} else {
			if (isOwnerOut) {
				mMatchType = mGroupDatas.get(ownerPlayerId).getTingDatas().size() > 0 ? MahjConstant.MAHJ_MATCH_TING : 0;
			} else if (mGroupDatas.get(getCurrentPlayer()) != null) {
				ArrayList<MahjData> outDatas = mGroupDatas.get(getCurrentPlayer()).getOutDatas();
				if (outDatas != null && outDatas.size() > 0) {
					mMatchType = mGroupDatas.get(ownerPlayerId).updateMatchType(new MahjData(outDatas.get(outDatas.size() - 1).getIndex()));
				}
			}
		}
	}

	private boolean isOwnerOut(HashMap<Integer, MahjGroupData> groups) {
		int ownerPlayerId = getOwnerPlayerIndex();
		if (mGroupDatas.get(ownerPlayerId) == null) {
			return false;
		}

		boolean isOwnerOutDataAdd = isOutDatasAdd(mGroupDatas.get(getOwnerPlayerIndex()).getOutDatas(), groups.get(getOwnerPlayerIndex()).getOutDatas());
		boolean isOtherOutDataAdd = false;
		for (int i = 0; i < groups.size(); i++) {
			if (getOwnerPlayerIndex() == i) {
				continue;
			}
			if (isOutDatasAdd(mGroupDatas.get(i).getOutDatas(), groups.get(i).getOutDatas())) {
				isOtherOutDataAdd = true;
				break;
			}
		}
		return isOwnerOutDataAdd && !isOtherOutDataAdd;
	}

	private boolean isOutDatasAdd(ArrayList<MahjData> oldOutDatas, ArrayList<MahjData> newOutDatas) {
		int oldOutDataSize = oldOutDatas != null ? oldOutDatas.size() : 0;
		int newOutDataSize = newOutDatas != null ? newOutDatas.size() : 0;
		return newOutDataSize > oldOutDataSize;
	}
}
