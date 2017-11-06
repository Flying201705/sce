package com.nova.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.nova.game.utils.UIUtil;
import com.nova.game.widget.SceButton;

public class BaseDialog extends Dialog {
    final static int TITLE_HEIGHT = 80;

    private TextButton mButton;
    private TextButton mPrimaryButton;
    private TextButton mSecondaryButton;
    private SceButton mCloseButton;
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
    };

    public BaseDialog() {
        this("");
    }

    public BaseDialog(String title) {
        this(title, false);
    }

    public BaseDialog(String title, boolean isTranslucent) {
        this(title, UIUtil.getDefaultWindowStyle(isTranslucent));
    }

    public BaseDialog(Drawable background) {
        this("", background);
    }

    public BaseDialog(String title, Drawable background) {
        this(title, UIUtil.getDefaultWindowStyle(background));
    }

    public BaseDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
        if (title != null && !title.isEmpty()) {
            padTop(TITLE_HEIGHT);
        } else {
            padTop(0);
        }
        getTitleLabel().setFontScale(1.2f);
        this.setBounds((Constants.WORLD_WIDTH - Constants.DEFAULT_DIALOG_WIDTH) / 2, (Constants.WORLD_HEIGHT - Constants.DEFAULT_DIALOG_HEIGHT) / 2,
                Constants.DEFAULT_DIALOG_WIDTH, Constants.DEFAULT_DIALOG_HEIGHT);
        addListener(mDialogClickListener);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mCanceledOnTouchOutside = cancel;
    }

    public void setCloseButton(ClickListener listener) {
        mCloseButton = new SceButton("Dialog/btn_close.png");
        mCloseButton.setPosition(getWidth() - 45, getHeight() - 45);
        mCloseButton.addListener(listener);
        addActor(mCloseButton);
    }

    public void setPrimaryButton(String button, ClickListener listener) {
        mPrimaryButton = new TextButton(button, UIUtil.getTextButtonStyle());
        mPrimaryButton.addListener(listener);
        mPrimaryButton.setBounds(80, 50, getWidth() / 2 - 100, 100);
        mPrimaryButton.getLabel().setFontScale(1.1f);
        addActor(mPrimaryButton);
    }

    public void setSecondaryButton(String button, ClickListener listener) {
        mSecondaryButton = new TextButton(button, UIUtil.getTextButtonStyle());
        mSecondaryButton.addListener(listener);
        mSecondaryButton.setBounds(getWidth() / 2 + 50, 50, getWidth() / 2 - 100, 100);
        mSecondaryButton.getLabel().setFontScale(1.1f);
        addActor(mSecondaryButton);
    }

    public void setButton(String button, ClickListener listener) {
        mButton = new TextButton(button, UIUtil.getTextButtonStyle());
        mButton.addListener(listener);
        mButton.setBounds(getWidth() / 4 + 50, 50, getWidth() / 2 - 100, 100);
        mButton.getLabel().setFontScale(1.1f);
        addActor(mButton);
    }
}
