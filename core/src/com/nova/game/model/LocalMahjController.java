package com.nova.game.model;

import com.nova.game.handler.LocalMahjGameHandler;
import nova.common.game.mahjong.MahjGameManager;
import nova.common.game.mahjong.handler.MahjGameDispatcher;
import nova.common.room.RoomController;
import nova.common.room.RoomManager;

public class LocalMahjController extends MahjController {

	private MahjGameDispatcher mDispatcher;
	
	@Override
	public void startGame() {
		super.startGame();
		
		RoomManager room = RoomController.getInstance("mahj").getRoomManager(-1);
		MahjGameManager gameManager = (MahjGameManager)room.getGameManager();
		gameManager.setHandler(new LocalMahjGameHandler());
		setDispatcher(gameManager);
		room.setTestGameDelay(0);
		room.startGame();
	}
	
	@Override
	public void activeOutData(int playerId, int index) {
		super.activeOutData(playerId, index);
		mDispatcher.activeOutData(playerId, index);
	}
	
	@Override
	public void activeOperateData(int playerId, int operateType) {
		super.activeOperateData(playerId, operateType);
		mDispatcher.activeOperateData(playerId, operateType);
	}
	
	private void setDispatcher(MahjGameDispatcher dispatcher) {
		mDispatcher = dispatcher;
	}
}
