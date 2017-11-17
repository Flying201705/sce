package com.nova.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.nova.game.utils.Log;

public class LatestOutMahjongMark extends Actor {
    private static final float DURATION = 1.5f;
    private Texture mImage;
    private float mOffset = 0f;
    private boolean mIncrement = true;

    public LatestOutMahjongMark() {
        mImage = new Texture("Animation/mark.png");
        setSize(mImage.getWidth(), mImage.getHeight());
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            mOffset = 0f;
            mIncrement = true;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (mIncrement) {
            mOffset += Gdx.graphics.getDeltaTime();
        } else {
            mOffset -= Gdx.graphics.getDeltaTime();
        }

        batch.draw(mImage, getX(), getY() + mOffset * 10);

        if (mOffset > DURATION) {
            mIncrement = false;
        } else if (mOffset <= 0f) {
            mIncrement = true;
        }
    }
}
