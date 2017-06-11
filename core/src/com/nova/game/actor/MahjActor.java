package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nova.game.data.MahjData;

public class MahjActor extends Actor {
    private MahjData mMahjData;
    private Texture mImage;

    public MahjActor() {

    }

    public MahjActor(Texture texture) {
        setImage(texture);
    }

    public MahjActor(Texture texture, MahjData mahjData) {
        this(texture);
        mMahjData = mahjData;
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
            batch.draw(mImage, getX(), getY(), getWidth(), getHeight());
        }
    }

    public MahjData getMahjData() {
        return mMahjData;
    }
}
