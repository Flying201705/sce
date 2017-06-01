package com.nova.game.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TimeUnit extends Actor {
    private Texture mBg;

    public TimeUnit() {
        mBg = new Texture("Actors/TimeBack.png");
        setSize(mBg.getWidth(), mBg.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mBg, getX(), getY());
    }

}
