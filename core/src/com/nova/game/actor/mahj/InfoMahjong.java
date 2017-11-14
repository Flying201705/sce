package com.nova.game.actor.mahj;

import com.badlogic.gdx.graphics.Texture;
import com.nova.game.assetmanager.Assets;

/**
 * Created by zhangxx on 17-11-14.
 */

public class InfoMahjong extends Mahjong {

    public InfoMahjong(int index) {
        super(index);
    }

    @Override
    protected int getMahjType() {
        return MAHJ_TYPE_SMALL;
    }

    @Override
    protected Texture getMahjBackground() {
        return Assets.getInstance().mInfoMahjBackground;
    }

    @Override
    public int getOffsetY() {
        return 4;
    }
}
