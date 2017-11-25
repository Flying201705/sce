package com.nova.game.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.Constants;
import com.nova.game.assetmanager.Assets;
import com.nova.game.utils.UIUtil;
import com.nova.game.widget.SceButton;

import java.util.ArrayList;

/**
 * Created by zhangxx on 17-11-25.
 */

public class GameEndDialog extends Dialog {

    public static final int RESULT_WIN = 0;
    public static final int RESULT_LOSE = 1;
    public static final int RESULT_DRAW = 2;

    private ArrayList<Texture> mTextures = new ArrayList<Texture>();
    private Texture mImageTexture;
    private Label mHintLabel;

    public GameEndDialog() {
        this("", UIUtil.getTransparentWindowStyle());
    }

    public GameEndDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
        this.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        mTextures.add(new Texture("SceneGame/girl_win.png"));
        mTextures.add(new Texture("SceneGame/girl_lose.png"));
        mTextures.add(new Texture("SceneGame/girl_draw.png"));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = Assets.getInstance().getFont();

        mHintLabel = new Label("平局", style);
        mHintLabel.setFontScale(2.0f);
        mHintLabel.setPosition(getWidth() / 2 + 120, getHeight() / 2 + 50);
        mHintLabel.setColor(Color.GREEN);
        addActor(mHintLabel);
    }

    public void setResumeButton(ClickListener listener) {
        SceButton resumeButton = new SceButton("SceneGame/bt_start.png");
        resumeButton.setPosition(getWidth() / 2 + 100, getHeight() / 2 - resumeButton.getHeight() - 50);
        resumeButton.addListener(listener);
        addActor(resumeButton);
    }

    public void setResult(int result) {
        if (result > RESULT_DRAW) {
            return;
        }

        if (result == RESULT_WIN) {
            mHintLabel.setText("你赢了");
            mHintLabel.setColor(Color.GREEN);
        } else if (result == RESULT_LOSE) {
            mHintLabel.setText("你输了");
            mHintLabel.setColor(Color.RED);
        } else if (result == RESULT_DRAW) {
            mHintLabel.setText("平局");
            mHintLabel.setColor(Color.GREEN);
        }

        mImageTexture = mTextures.get(result);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (mImageTexture != null) {
            batch.draw(mImageTexture, getWidth() / 2 - mImageTexture.getWidth(), getHeight() - mImageTexture.getHeight() - 20);
        }
    }
}
