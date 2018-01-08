package com.nova.game.handler;

import java.util.ArrayList;
import java.util.HashMap;

import com.nova.game.model.MahjGameController;
import com.nova.game.model.MahjRoomController;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGameData;
import nova.common.game.mahjong.data.MahjGroupData;
import nova.common.game.mahjong.data.MahjResponeData;
import nova.common.game.mahjong.data.MahjResponeResolver;
import nova.common.game.mahjong.data.MahjResultData;
import nova.common.game.mahjong.handler.MahjGameHandler;
import nova.common.room.data.PlayerInfo;
import nova.common.room.handler.RoomHandler;

public class LocalMahjGameHandler implements MahjGameHandler, RoomHandler {

	@Override
	public void onGameInfoChange(int roomId, MahjResponeData responeData) {
		MahjGameController.getInstance().setGameMessageProcessFinish(false);
		updateGameData(responeData);
		updateGroupDatas(MahjResponeResolver.getGroupDatasForResponeData(responeData));
		MahjGameController.getInstance().setGameMessageProcessFinish(true);
	}

	@Override
	public void handleGameResult(int roomId, HashMap<Integer, MahjResultData> results) {

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
	
	private void updateGroupDatas(HashMap<Integer, MahjGroupData> groupDatas) {
		MahjGameController.getInstance().setGroupDatas(groupDatas);
	}

	@Override
	public void onRoomInfoChange(int roomId, HashMap<Integer, PlayerInfo> playerInfos) {
		MahjRoomController.getInstance().setPlayerInfos(playerInfos);
	}
}
