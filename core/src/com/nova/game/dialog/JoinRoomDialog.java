package com.nova.game.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.BaseDialog;
import com.nova.game.Constants;
import com.nova.game.utils.UIUtil;
import com.nova.game.widget.SceButton;

public class JoinRoomDialog extends BaseDialog {
    
    private String mInput = "";
    private TextField mTextField;
    private SceButton mOkBtn;
    private SceButton mDeleteBtn;
    private SceButton mOneBtn;
    private SceButton mTwoBtn;
    private SceButton mThreeBtn;
    private SceButton mFourBtn;
    private SceButton mFiveBtn;
    private SceButton mSixBtn;
    private SceButton mSevenBtn;
    private SceButton mEightBtn;
    private SceButton mNineBtn;
    private SceButton mZeroBtn;
    
    private ClickListener mCloseClickListener = new ClickListener() {
    	public void clicked(InputEvent event, float x, float y) {
    		mInput = "";
            mTextField.setText(mInput);
            if (hasParent()) {
                remove();
            }
    	};
    };
    
    private ClickListener mDeleteClickListener = new ClickListener() {
        public void clicked(InputEvent event, float x, float y) {
            if (!mInput.isEmpty()) {
                mInput = mInput.substring(0, (mInput.length() - 1));
                mTextField.setText(mInput);
            }
        };
    };
    
    private ClickListener mNumberClickListener  = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Actor actor = event.getListenerActor();
            if (actor instanceof SceButton) {
                mInput = mInput + actor.getName();
                mTextField.setText(mInput);
            }
        };
    };
    
    public JoinRoomDialog() {
        super();
        setClip(false);
        this.setBounds((Constants.WORLD_WIDTH - 800) / 2, (Constants.WORLD_HEIGHT - 630) / 2, 800, 630);
        
        mTextField = new TextField("", UIUtil.getTextFieldStyle());
        mTextField.setTouchable(Touchable.disabled);
        mTextField.setBounds(50, getHeight() - 150, getWidth() - 100, 100);
        this.addActor(mTextField);
        setCloseButton(mCloseClickListener);
        
        mDeleteBtn = new SceButton("JoinRoom/btn_del.png");
        mDeleteBtn.setPosition(50, 50);
        mZeroBtn = new SceButton("JoinRoom/btn_num_0.png");
        mZeroBtn.setPosition(250, 50);
        mZeroBtn.setName("0");
        mOkBtn = new SceButton("JoinRoom/btn_ok.png");
        mOkBtn.setPosition(450, 50);


        mOneBtn = new SceButton("JoinRoom/btn_num_1.png");
        mOneBtn.setPosition(50, 320);
        mOneBtn.setName("1");
        mTwoBtn = new SceButton("JoinRoom/btn_num_2.png");
        mTwoBtn.setPosition(250, 320);
        mTwoBtn.setName("2");
        mThreeBtn = new SceButton("JoinRoom/btn_num_3.png");
        mThreeBtn.setPosition(450, 320);
        mThreeBtn.setName("3");

        mFourBtn = new SceButton("JoinRoom/btn_num_4.png");
        mFourBtn.setPosition(50, 230);
        mFourBtn.setName("4");
        mFiveBtn = new SceButton("JoinRoom/btn_num_5.png");
        mFiveBtn.setPosition(250, 230);
        mFiveBtn.setName("5");
        mSixBtn = new SceButton("JoinRoom/btn_num_6.png");
        mSixBtn.setPosition(450, 230);
        mSixBtn.setName("6");

        mSevenBtn = new SceButton("JoinRoom/btn_num_7.png");
        mSevenBtn.setPosition(50, 140);
        mSevenBtn.setName("7");
        mEightBtn = new SceButton("JoinRoom/btn_num_8.png");
        mEightBtn.setPosition(250, 140);
        mEightBtn.setName("8");
        mNineBtn = new SceButton("JoinRoom/btn_num_9.png");
        mNineBtn.setPosition(450, 140);
        mNineBtn.setName("9");
        
        mDeleteBtn.addListener(mDeleteClickListener);
        mOneBtn.addListener(mNumberClickListener);
        mTwoBtn.addListener(mNumberClickListener);
        mThreeBtn.addListener(mNumberClickListener);
        mFourBtn.addListener(mNumberClickListener);
        mFiveBtn.addListener(mNumberClickListener);
        mSixBtn.addListener(mNumberClickListener);
        mSevenBtn.addListener(mNumberClickListener);
        mEightBtn.addListener(mNumberClickListener);
        mNineBtn.addListener(mNumberClickListener);
        mZeroBtn.addListener(mNumberClickListener);

        this.addActor(mOkBtn);
        this.addActor(mDeleteBtn);
        this.addActor(mOneBtn);
        this.addActor(mTwoBtn);
        this.addActor(mThreeBtn);
        this.addActor(mFourBtn);
        this.addActor(mFiveBtn);
        this.addActor(mSixBtn);
        this.addActor(mSevenBtn);
        this.addActor(mEightBtn);
        this.addActor(mNineBtn);
        this.addActor(mZeroBtn);
    }
    
    public int getInput() {
    	if (mInput == null || mInput.isEmpty()) {
    		return -1;
    	}
    	
    	return Integer.parseInt(mInput);
    }
    
    public void setJoinClickListener(ClickListener listener) {
    	mOkBtn.addListener(listener);
    }
    
    @Override
    public float getPrefWidth() {
        return 1080;
    }
    
    @Override
    public float getPrefHeight() {
        return 720;
    }
}
