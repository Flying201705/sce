package com.nova.game.actor.mahj;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by zhangxx on 17-10-18.
 */

public class HorizontalFlatMahjong extends Mahjong {

    public HorizontalFlatMahjong(int index) {
        this(index, MAHJ_DIRECTION_LEFT);
    }

    public HorizontalFlatMahjong(int index, int direction) {
        super(index);
        setDirection(direction);
    }

    @Override
    protected int getMahjType() {
        return MAHJ_TYPE_SMALL;
    }

    @Override
    protected Texture getMahjBackground() {
        return new Texture("SceneGame/mahj_flat_horizontal.png");
    }

    @Override
    public int getOffsetX() {
        return 6;
    }

    @Override
    public int getOffsetY() {
        return 8;
    }
}
