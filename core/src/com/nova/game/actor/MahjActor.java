package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.data.MahjData;

public class MahjActor extends Actor {
    private MahjData mMahjData;
    private Texture mImage;
    private boolean mIsCanStandUp = false;
    private boolean mIsStandUp = false;

    public MahjActor() {

    }

    public MahjActor(Texture texture) {
        setImage(texture);
    }

    public MahjActor(Texture texture, MahjData mahjData) {
        this(texture);
        mMahjData = mahjData;
    }

    public void setCanStandUp(boolean canStandUp) {
        mIsCanStandUp = canStandUp;
    }

    public void standUp(boolean standUp) {
        mIsStandUp = standUp;
    }

    public void setImage(Texture image) {
        this.mImage = image;
        setSize(mImage.getWidth() * getScaleX(), mImage.getHeight() * getScaleY());
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        setSize(getWidth() * scaleXY, getHeight() * scaleXY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (mImage != null) {
            float y = getY();
            if (mIsCanStandUp && mIsStandUp) {
                y += 10;
            }
            batch.draw(mImage, getX(), y, getWidth(), getHeight());
        }
    }

    public MahjData getMahjData() {
        return mMahjData;
    }
}
