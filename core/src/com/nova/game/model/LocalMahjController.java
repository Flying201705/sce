package com.nova.game.model;

import com.nova.game.handler.LocalMahjGameHandler;

import nova.common.GameCommand;
import nova.common.game.mahjong.MahjGameManager;
import nova.common.game.mahjong.handler.FileRecorderManager;
import nova.common.game.mahjong.handler.MahjGameDispatcher;
import nova.common.game.mahjong.util.TestMahjConstant;
import nova.common.room.RoomController;
import nova.common.room.RoomManager;
import nova.common.room.data.PlayerInfo;

public class LocalMahjController extends MahjController {

	private MahjGameDispatcher mDispatcher;
	private int mRoomId;

	public LocalMahjController() {
		// 初始化LOG文件系统
		FileRecorderManager.getInstance().setFilePath("/sdcard/sce/mahj/temp/");
		FileRecorderManager.getInstance().startRecord();
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
		handleGameStart();
	}

	@Override
	public void resumeGame(int roomId) {
		super.resumeGame(roomId);
		RoomController.getInstance(GameCommand.MAHJ_TYPE_GAME).getRoomManager(mRoomId).resumeGame();
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

	private void handleGameStart() {
		RoomController mahjRoomController = RoomController.getInstance(GameCommand.MAHJ_TYPE_GAME);
		PlayerInfo player = PlayerInfoController.getInstance().getOwnerInfo();
		mRoomId = mahjRoomController.searchSuitableRoom(player);
		RoomManager roomManager = mahjRoomController.getRoomManager(mRoomId);
		roomManager.setRoomHandler(new LocalMahjGameHandler());
		((MahjGameManager)roomManager.getGameManager()).setHandler(new LocalMahjGameHandler());
		setDispatcher((MahjGameManager)roomManager.getGameManager());
		roomManager.setTestGameDelay(0);
		roomManager.startGame();
	}
}
