package com.nova.game.handler;

import java.io.FileOutputStream;
import com.badlogic.gdx.Gdx;
import com.nova.game.Constants;
import com.nova.game.model.MahjRoomController;
import com.nova.game.utils.Log;
import com.nova.net.netty.handler.ResponseDispatcherManager;

import io.netty.buffer.ByteBuf;
import nova.common.game.mahjong.util.MahjGameCommand;

public class MahjMessageResponseDispatcher implements ResponseDispatcherManager.MessageResponseDispatcher {

	public void processor(int commandId, ByteBuf message) {
		int playerId = message.readInt();
		int length = message.readInt();
		byte[] c = new byte[length];
		message.readBytes(c);
		switch (commandId) {
		case MahjGameCommand.RESPONE_SEND_MESSAGE:
			MahjRoomController.getInstance().addPlayerMessage(playerId, new String(c));
		break;
		
		case MahjGameCommand.RESPONE_SEND_VOICE:
			try {
				String file = "sce/mahj/audios/" + System.currentTimeMillis() + ".amr";
				FileOutputStream fos = new FileOutputStream("/sdcard/" + file);
				fos.write(c);
				fos.close();

				MahjRoomController.getInstance().addPlayerSound(playerId, Gdx.audio.newSound(Gdx.files.external(file)));
				MahjRoomController.getInstance().getPlayerSound(playerId).play(1.0f);
			} catch (Exception e) {
				Log.e("MahjMessageResponseDispatcher", " e : " + e.toString());
			}
			break;

		case MahjGameCommand.RESPONE_SEND_MESSAGE_VOICE:
			MahjRoomController.getInstance().addPlayerMessage(playerId, Constants.COMMON_CHAT_MESSAGE[Integer.valueOf(new String(c))]);
			MahjRoomController.getInstance().addPlayerSound(playerId, Gdx.audio.newSound(Gdx.files.internal("sound/msg_w_" + Integer.valueOf(new String(c)) + ".ogg")));
			MahjRoomController.getInstance().getPlayerSound(playerId).play(1.0f);
			break;
		default:
			break;
		}
	}
}
