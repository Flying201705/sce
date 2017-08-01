package com.nova.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.nova.game.utils.UIUtil;

public class BaseDialog extends Dialog {
    final static int TITLE_HEIGHT = 80;

    private TextButton mButton;
    private TextButton mPrimaryButton;
    private TextButton mSecondaryButton;
    private boolean mCanceledOnTouchOutside;

    private ClickListener mDialogClickListener = new ClickListener() {
        public void clicked(InputEvent event, float x, float y) {
            if (!mCanceledOnTouchOutside) {
                return;
            }

            if (x < getX() || x > (getX() + getWidth())
                    || y < getY() || y > (getY() + getHeight())) {
                setVisible(false);
            }
        }

        ;
    };

    public BaseDialog() {
        this("");
    }

    public BaseDialog(String title) {
        super(title, UIUtil.getDefaultWindowStyle());
        if (title != null && !title.isEmpty()) {
            padTop(TITLE_HEIGHT);
        } else {
            padTop(0);
        }
        this.setBounds((Constants.WORLD_WIDTH - Constants.DEFAULT_DIALOG_WIDTH) / 2, (Constants.WORLD_HEIGHT - Constants.DEFAULT_DIALOG_HEIGHT) / 2,
                Constants.DEFAULT_DIALOG_WIDTH, Constants.DEFAULT_DIALOG_HEIGHT);
        addListener(mDialogClickListener);
    }

    public BaseDialog(Drawable background) {
        super("", UIUtil.getDefaultWindowStyle(background));
        this.setBounds((Constants.WORLD_WIDTH - Constants.DEFAULT_DIALOG_WIDTH) / 2, (Constants.WORLD_HEIGHT - Constants.DEFAULT_DIALOG_HEIGHT) / 2,
                Constants.DEFAULT_DIALOG_WIDTH, Constants.DEFAULT_DIALOG_HEIGHT);
        addListener(mDialogClickListener);
    }

    public BaseDialog(String title, boolean isTranslucent) {
        super(title, UIUtil.getDefaultWindowStyle(isTranslucent));
        if (title != null && !title.isEmpty()) {
            padTop(TITLE_HEIGHT);
        } else {
            padTop(0);
        }
        this.setBounds((Constants.WORLD_WIDTH - Constants.DEFAULT_DIALOG_WIDTH) / 2, (Constants.WORLD_HEIGHT - Constants.DEFAULT_DIALOG_HEIGHT) / 2,
                Constants.DEFAULT_DIALOG_WIDTH, Constants.DEFAULT_DIALOG_HEIGHT);
        ImageButton closeButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("drawable/icon_close.png")))));
        closeButton.setPosition(getWidth() - closeButton.getWidth() - 25, getHeight() - closeButton.getHeight() - 25);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setVisible(false);
            }
        });
        addActor(closeButton);
        addListener(mDialogClickListener);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mCanceledOnTouchOutside = true;
    }

    public void setPrimaryButton(String button, ClickListener listener) {
        mPrimaryButton = new TextButton(button, UIUtil.getTextButtonStyle());
        mPrimaryButton.addListener(listener);
        mPrimaryButton.setBounds(80, 25, 350, 100);
        addActor(mPrimaryButton);
    }

    public void setSecondaryButton(String button, ClickListener listener) {
        mSecondaryButton = new TextButton(button, UIUtil.getTextButtonStyle());
        mSecondaryButton.addListener(listener);
        mSecondaryButton.setBounds(550, 25, 350, 100);
        addActor(mSecondaryButton);
    }

    public void setButton(String button, ClickListener listener) {
        mButton = new TextButton(button, UIUtil.getTextButtonStyle());
        mButton.addListener(listener);
        mButton.setBounds((getWidth() - 350) / 2, 25, 350, 100);
        addActor(mButton);
    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        // TODO Auto-generated method stub
//        Color color = batch.getColor();  
//        batch.setColor(this.getColor());
//        batch.getColor().a *= parentAlpha;
//
//        super.draw(batch, parentAlpha);
//        
//        batch.setColor(color);    
//    }
}
