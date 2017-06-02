package com.nova.game.handler;

import java.util.ArrayList;
import java.util.HashMap;

import com.nova.game.model.MahjGameController;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGameData;
import nova.common.game.mahjong.data.MahjGroupData;
import nova.common.game.mahjong.data.MahjResponeData;
import nova.common.game.mahjong.handler.MahjGameHandler;

public class LocalMahjGameHandler implements MahjGameHandler {

	@Override
	public void onGameInfoChange(int roomId, MahjResponeData responeData) {
		updateGameData(responeData);
		updateGroupDatas(responeData.getGroupDatas());
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
	
	private void updateGroupDatas(HashMap<Integer, MahjGroupData> groupDatas) {
		MahjGameController.getInstance().setGroupDatas(groupDatas);
	}
}
