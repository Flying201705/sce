package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.nova.game.actor.mahj.VerticalFlatMahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public class MyOutMahjongs extends Group {
    private Assets mAssets;
    private ArrayList<MahjData> mOutMahjs;

    public MyOutMahjongs() {
        mAssets = Assets.getInstance();
    }

    public void setOutMahjongs(ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mOutMahjs != null && mahjs.containsAll(mOutMahjs)) {
//            return;
//        }

        mOutMahjs = mahjs;
        clear();

        int count = 0;
        float x = 0, y = getHeight(), offestY = 15f;
        for (MahjData mahj : mahjs) {
            VerticalFlatMahjong mahjActor = new VerticalFlatMahjong(mahj.getIndex());
            if (count < 6) {
                if (count == 0) {
                    x += 2 * mahjActor.getWidth();
                    y -= mahjActor.getHeight();
                } else {
                    x += mahjActor.getWidth() - 2;
                }
            } else if (count >= 6 && count < 14) {
                if (count == 6) {
                    x = mahjActor.getWidth();
                    y -= mahjActor.getHeight() - offestY;
                } else {
                    x += mahjActor.getWidth() - 2;
                }
            } else if (count >= 14) {
                if (count == 14) {
                    x = 0;
                    y -= mahjActor.getHeight() - offestY;
                } else {
                    x += mahjActor.getWidth() - 2;
                }

            }
            mahjActor.setPosition(x, y);
            addActor(mahjActor);
            count++;
        }
    }
}
