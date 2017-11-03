package com.nova.game.actor.mahj;

import com.badlogic.gdx.graphics.Texture;
import com.nova.game.actor.mahj.Mahjong;
/**
 * Created by zhangxx on 17-10-17.
 */

public class OwnerMahjong extends Mahjong {

    public OwnerMahjong() {
        this(0);
    }

    public OwnerMahjong(int index) {
        super(index);
    }

    @Override
    protected int getMahjType() {
        return MAHJ_TYPE_BIG;
    }

    @Override
    protected Texture getMahjBackground() {
        return new Texture("SceneGame/mahj_me.png");
    }
}
