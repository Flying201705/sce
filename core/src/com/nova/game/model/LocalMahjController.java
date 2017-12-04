package com.nova.game.model;

import com.nova.game.handler.LocalMahjGameHandler;

import nova.common.GameCommand;
import nova.common.game.mahjong.MahjGameManager;
import nova.common.game.mahjong.handler.FileLogRecorderManager;
import nova.common.game.mahjong.handler.MahjGameDispatcher;
import nova.common.game.mahjong.util.TestMahjConstant;
import nova.common.room.RoomController;
import nova.common.room.RoomManager;

public class LocalMahjController extends MahjController {

	private MahjGameDispatcher mDispatcher;

	public LocalMahjController() {
		// 初始化LOG文件系统
		FileLogRecorderManager.getInstance().setFilePath("/sdcard/sce/mahj/temp/");
		FileLogRecorderManager.getInstance().startRecord();
	}
	
	@Override
	public void startGame(int roomId) {
		super.startGame(roomId);
		/**---测试程序开始---**/
		// TestMahjConstant.setDebug(4);
		/**---测试程序结束---**/
		/**----开启测试LOG开始----**/
		// MahjGameManager.debug = true;
		/**----开启测试LOG结束----**/
		RoomManager room = RoomController.getInstance(GameCommand.MAHJ_TYPE_GAME).getRoomManager(-1);
		room.setRoomHandler(new LocalMahjGameHandler());
		MahjGameManager gameManager = (MahjGameManager)room.getGameManager();
		gameManager.setHandler(new LocalMahjGameHandler());
		setDispatcher(gameManager);
		room.setTestGameDelay(0);
		room.startGame();
	}

	@Override
	public void resumeGame(int roomId) {
		super.resumeGame(roomId);

	}

	@Override
	public void activeOutData(int playerId, int data) {
		super.activeOutData(playerId, data);
		mDispatcher.activeOutData(playerId, data);
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
