package com.nova.game.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nova.game.Data.MahjData;

public class MahjActor extends Actor {
    private MahjData mMahjData;
    private Texture mImage;

    public MahjActor(Texture texture, MahjData mahjData) {
        mImage = texture;
        mMahjData = mahjData;
        setSize(mImage.getWidth(), mImage.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mImage, getX(), getY());
    }

    public MahjData getMahjData() {
        return mMahjData;
    }
}
