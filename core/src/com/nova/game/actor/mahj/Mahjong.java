package com.nova.game.actor.mahj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nova.game.assetmanager.Assets;

/**
 * Created by zhangxx on 17-10-17.
 */

public class Mahjong extends Actor {

    private Texture mBackground;
    private Sprite mMahjSprite;

    public static final int MAHJ_TYPE_SMALL = 0;
    public static final int MAHJ_TYPE_MIDDLE = 1;
    public static final int MAHJ_TYPE_BIG = 2;

    public static final int MAHJ_DIRECTION_LEFT = 0;
    public static final int MAHJ_DIRECTION_RIGHT = 1;

    public Mahjong(Texture bg) {
        this(0, bg);
    }

    public Mahjong(int index) {
        this(index, null);
    }

    public Mahjong(int index, int direction) {
        this(index, null);
    }

    private Mahjong(int index, Texture bg) {
        mBackground = bg;
        if (mBackground == null) {
            mBackground = getMahjBackground();
        }

        if (index > 0) {
            mMahjSprite = new Sprite(getMahj(index));
        }
    }

    public int getOffsetX() {
        return 0;
    }

    public int getOffsetY() {
        return 0;
    }

    public float getMahjRotation() {
        return 0;
    }

    @Override
    public void setRotation(float degrees) {
        // super.setRotation(degrees);
        if (mMahjSprite != null) {
            mMahjSprite.setRotation(degrees);
        }
    }

    public void setDirection(int direction) {
        if (mMahjSprite != null) {
            mMahjSprite.setRotation(getRotationForDirection(direction));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(mBackground, getX(), getY());
        if (mMahjSprite != null) {
            mMahjSprite.setPosition(getX() + getOffsetX(), getY() + getOffsetY());
            mMahjSprite.draw(batch);
        }
    }

    protected  int getMahjType() {
        return MAHJ_TYPE_BIG;
    }

    protected Texture getMahjBackground() {
        return new Texture("SceneGame/mahj_me.png");
    }

    private TextureRegion getMahj(int index) {
        return Assets.getInstance().getMahjTextureRegion(index, getMahjType());
    }

    private float getRotationForDirection(int direction) {
        if (direction == MAHJ_DIRECTION_LEFT) {
            return -90;
        } else if (direction == MAHJ_DIRECTION_RIGHT) {
            return 90;
        }

        return 0;
    }
}
