package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nova.game.data.MahjData;

public class MahjActor extends Actor {
    private MahjData mMahjData;
    private Texture mImage;

    public MahjActor(Texture texture) {
        mImage = texture;
        setSize(mImage.getWidth(), mImage.getHeight());
    }

    public MahjActor(Texture texture, MahjData mahjData) {
        this(texture);
        mMahjData = mahjData;
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        setSize(getWidth() * scaleXY, getHeight() * scaleXY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mImage, getX(), getY(), getWidth(), getHeight());
    }

    public MahjData getMahjData() {
        return mMahjData;
    }
}
