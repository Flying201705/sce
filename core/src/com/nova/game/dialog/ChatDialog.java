package com.nova.game.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.BaseDialog;
import com.nova.game.Constants;
import com.nova.game.assetmanager.Assets;
import com.nova.game.handler.GameRequestDispatcher;
import com.nova.game.model.MahjRoomController;
import com.nova.game.model.PlayerInfoController;
import com.nova.game.utils.Log;
import com.nova.game.utils.UIUtil;

public class ChatDialog extends BaseDialog {
	// zhangxx add for audio begin
	private AudioManager mAudioManager = AudioManager.getInstance();
	// zhangxx add for audio end
	private TextField mTextField;
	private TextButton mVoiceButton;
	private TextButton mSendButton;
	private TextButton mSwitchButton;
	private boolean mIsVoiceMode;
	
	public ChatDialog() {
		super("聊天");
		setBounds((Constants.WORLD_WIDTH - 800) / 2, (Constants.WORLD_HEIGHT - 630) / 2, 800, 630);

		mTextField = new TextField("", UIUtil.getTextFieldStyle());
		mTextField.setBounds(170, 50, 460, 50);
		mTextField.setMessageText("点击输入");
		mTextField.setTextFieldListener(new TextFieldListener() {
			
			@Override
			public void keyTyped(TextField textField, char c) {
				Log.e("zhangxx", "c = " + c);
			}
		});
        addActor(mTextField);
        
        List.ListStyle style = new List.ListStyle();
        style.font = Assets.getInstance().getFont();
        style.font.setColor(Color.DARK_GRAY);
        style.fontColorUnselected = Color.DARK_GRAY;
        style.selection = UIUtil.file2Drawable("SceneGame/edit_background.png", 4, 4, 4, 4);
        final List<String> list = new List<String>(style);
        list.setItems(Constants.COMMON_CHAT_MESSAGE);
        list.setColor(Color.DARK_GRAY);
        ScrollPane.ScrollPaneStyle sstyle = new ScrollPane.ScrollPaneStyle();
        sstyle.background = UIUtil.file2Drawable("SceneGame/edit_background.png", 4, 4, 4, 4);
        ScrollPane scrollPane = new ScrollPane(list, sstyle);
        scrollPane.setSize(1100, 500);
        scrollPane.setPosition(40, 160);
        scrollPane.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		super.clicked(event, x, y);
        		Log.e("zhangxx", "select item : " + list.getSelectedIndex());
        		MahjRoomController.getInstance().addPlayerMessage(PlayerInfoController.getInstance().getOwnerPlayerId(), list.getSelected());
        		Gdx.audio.newSound(Gdx.files.internal("sound/msg_w_" + list.getSelectedIndex() + ".ogg")).play();
				new GameRequestDispatcher().sendTextAndVoice(MahjRoomController.getInstance().getRoomId(), String.valueOf(list.getSelectedIndex()));
        		setVisible(false);
        	}
        });
        addActor(scrollPane);
		
        mSwitchButton = new TextButton("语音", UIUtil.getTextButtonStyle());
        mSwitchButton.setBounds(50, 50, 100, 50);
		mSwitchButton.addListener(mSwitchListener);
		mVoiceButton = new TextButton("长按 说话", UIUtil.getTextButtonStyle());
		mVoiceButton.setBounds(170, 50, 460, 50);
		mVoiceButton.setVisible(false);
		mVoiceButton.addListener(mVoiceListener);
        mSendButton = new TextButton("发送", UIUtil.getTextButtonStyle());
        mSendButton.setBounds(650, 50, 100, 50);
        mSendButton.addListener(mSendListener);
        
        addActor(mSwitchButton);
        addActor(mVoiceButton);
        addActor(mSendButton);
	}
	
	private ClickListener mSwitchListener = new ClickListener() {
		public void clicked(InputEvent event, float x, float y) {
			mIsVoiceMode = !mIsVoiceMode;
			if (mIsVoiceMode) {
				mSwitchButton.setText("消息");
				mVoiceButton.setVisible(true);
				mTextField.setVisible(false);
			} else {
				mSwitchButton.setText("语音");
				mVoiceButton.setVisible(false);
				mTextField.setVisible(true);
			}
		};
	};
	
	private ClickListener mSendListener = new ClickListener() {
		public void clicked(InputEvent event, float x, float y) {
			String inputText = mTextField.getText();
			if (inputText != null && !inputText.isEmpty()) {
				MahjRoomController.getInstance().addPlayerMessage(PlayerInfoController.getInstance().getOwnerPlayerId(), inputText);
				new GameRequestDispatcher().sendText(MahjRoomController.getInstance().getRoomId(), inputText);
			}
			setVisible(false);
		};
	};
	
	private ClickListener mVoiceListener = new ClickListener() {
		public void touchUp(InputEvent arg0, float arg1, float arg2, int arg3, int arg4) {
			mVoiceButton.setText("长按 说话");
    		// zhangxx add for audio begin
    		mAudioManager.release();
			new GameRequestDispatcher().sendVoice(MahjRoomController.getInstance().getRoomId(), mAudioManager.getCurrentFilePath());
    		// zhangxx add for audio end
		}
		
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			mVoiceButton.setText("松开 结束");
    		// zhangxx add for audio begin
    		mAudioManager.prepareAudio();
    		// zhangxx add for audio end
			return super.touchDown(event, x, y, pointer, button);
		}
	};
}
