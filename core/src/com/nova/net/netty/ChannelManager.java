package com.nova.net.netty;

import com.nova.net.netty.util.NetWorkUtil;

import java.io.FileInputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import nova.common.game.mahjong.util.MahjGameCommand;

public class ChannelManager {
	private static ChannelManager mInstance;
	private Channel mChannel;
	private int mNetType;
	
	private ChannelManager() {
		
	}
	
	public static ChannelManager getInstance() {
		if (mInstance == null) {
			mInstance = new ChannelManager();
		}
		return mInstance;
	}
	
	public void initChannel(Channel ch) {
		mChannel = ch;
	}
	
	public void updateNetType(int type) {
		mNetType = type;
		
		if (mNetType > 0) {
			isChannelActive();
		}
	}
	
	private boolean isChannelActive() {
		if (mChannel != null && mChannel.isActive()) {
			return true;
		}
		connect();
		return false;
	}
	
	public void connect() {
		// 与netty服务器通信
		NettyClient client = new NettyClient(NetWorkUtil.HOST, NetWorkUtil.PORT);
		client.start();
	}

	public void sendMessage(int messagetype, String message) {
		if (!isChannelActive()) {
			return;
		}
		
		ByteBuf messageData = Unpooled.buffer();
		messageData.writeInt(messagetype);
		messageData.writeInt(message.length());
		messageData.writeBytes(message.getBytes());
		mChannel.writeAndFlush(messageData);
	}

	public void sendText(int messagetype, int room, int playerId, String text) {
		if (!isChannelActive()) {
			return;
		}
		
		ByteBuf bytebuf = Unpooled.buffer();
		bytebuf.writeInt(messagetype);
		// type: 0-文本 1-语音 2-文本&语音
		bytebuf.writeInt(MahjGameCommand.MessageType.TYPE_MESSAGE);
		bytebuf.writeInt(room);
		bytebuf.writeInt(playerId);
		bytebuf.writeInt(text.getBytes().length);
		bytebuf.writeBytes(text.getBytes());
		mChannel.writeAndFlush(bytebuf);
	}
	
	public void sendVoice(int messagetype, int room, int playerId, String file) {
		if (!isChannelActive()) {
			return;
		}
		
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] filebuf = new byte[fis.available()];
			fis.read(filebuf);
			fis.close();
			ByteBuf bytebuf = Unpooled.buffer();
			bytebuf.writeInt(messagetype);
			// type: 0-文本 1-语音 2-文本&语音
			bytebuf.writeInt(MahjGameCommand.MessageType.TYPE_VOICE);
			bytebuf.writeInt(room);
			bytebuf.writeInt(playerId);
			bytebuf.writeInt(filebuf.length);
			bytebuf.writeBytes(filebuf);
			mChannel.writeAndFlush(bytebuf);
		} catch (Exception e) {
			
		}
	}
	
	public void sendTextAndVoice(int messagetype, int room, int playerId, String position) {
		if (!isChannelActive()) {
			return;
		}
		
		ByteBuf bytebuf = Unpooled.buffer();
		bytebuf.writeInt(messagetype);
		// type: 0-文本 1-语音 2-文本&语音
		bytebuf.writeInt(MahjGameCommand.MessageType.TYPE_MESSAGE_VOICE);
		bytebuf.writeInt(room);
		bytebuf.writeInt(playerId);
		bytebuf.writeInt(position.getBytes().length);
		bytebuf.writeBytes(position.getBytes());
		mChannel.writeAndFlush(bytebuf);
	}
}