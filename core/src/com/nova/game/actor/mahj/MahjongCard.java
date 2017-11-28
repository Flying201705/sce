package com.nova.game.actor.mahj;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nova.game.assetmanager.Assets;
import com.nova.game.model.MahjGameController;

/**
 * Created by zhangxx on 17-11-28.
 */

public class MahjongCard extends Sprite {

    private Sprite mMahjGodCorner;
    private float mRotation;
    private int mIndex;

    public MahjongCard(int index, TextureRegion region) {
        super(region);
        mIndex = index;
        mMahjGodCorner = new Sprite(Assets.getInstance().mGodMahjCorner);
        setSize(region.getRegionWidth(), region.getRegionHeight());
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        mMahjGodCorner.setRotation(degrees);
        mRotation = degrees;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        drawGodCorner(batch);
    }

    private void drawGodCorner(Batch batch) {
        if (MahjGameController.getInstance().getGodData() != mIndex) {
            return;
        }

        if (Float.compare(mRotation, 90) == 0) {
            mMahjGodCorner.setPosition(getX() - 5, getY() + 8);
        } else if (Float.compare(mRotation, -90) == 0) {
            mMahjGodCorner.setPosition(getX() + getWidth() - mMahjGodCorner.getWidth() + 6, getY() + getHeight() - mMahjGodCorner.getHeight() - 5);
        } else {
            mMahjGodCorner.setPosition(getX() + 2, getY() + getHeight() - mMahjGodCorner.getHeight());
        }

        mMahjGodCorner.draw(batch);
    }
}
