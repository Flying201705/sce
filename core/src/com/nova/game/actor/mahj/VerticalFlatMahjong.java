package com.nova.game.actor.mahj;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by zhangxx on 17-10-18.
 */

public class VerticalFlatMahjong extends Mahjong {

    public VerticalFlatMahjong(int index) {
        super(index);
    }

    @Override
    protected int getMahjType() {
        return MAHJ_TYPE_SMALL;
    }

    @Override
    protected Texture getMahjBackground() {
        return new Texture("SceneGame/mahj_flat_vertical.png");
    }

    @Override
    public int getOffsetY() {
        return 14;
    }
}
