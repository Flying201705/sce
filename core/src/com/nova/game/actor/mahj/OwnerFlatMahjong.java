package com.nova.game.actor.mahj;

import com.badlogic.gdx.graphics.Texture;
import com.nova.game.assetmanager.Assets;

/**
 * Created by zhangxx on 17-10-18.
 */

public class OwnerFlatMahjong extends Mahjong {

    public OwnerFlatMahjong() {
        this(0);
    }

    public OwnerFlatMahjong(int index) {
        super(index);
    }

    @Override
    protected int getMahjType() {
        return MAHJ_TYPE_MIDDLE;
    }

    @Override
    protected Texture getMahjBackground() {
        return Assets.getInstance().mOwnerFlatMahjBackground;
    }

    @Override
    public int getOffsetY() {
        return 24;
    }
}
