package com.nova.game.actor.mahj;

import com.badlogic.gdx.graphics.Texture;
import com.nova.game.assetmanager.Assets;

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
        return Assets.getInstance().mVerticalFlatMahjBackground;
    }

    @Override
    public int getOffsetY() {
        return 14;
    }
}
