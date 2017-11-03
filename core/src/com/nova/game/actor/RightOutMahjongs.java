package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;
import com.nova.game.actor.mahj.HorizontalFlatMahjong;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public class RightOutMahjongs extends Group {
    private Assets mAssets;
    private ArrayList<MahjData> mOutMahjs;

    public RightOutMahjongs() {
        mAssets = Assets.getInstance();
    }

    public void setOutMahjongs(ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mOutMahjs != null && mahjs.containsAll(mOutMahjs)) {
//            return;
//        }

        mOutMahjs = mahjs;
        clear();

        float currX = 0, currY = 0, offset = 15f;
        for (int i = mahjs.size() - 1; i >= 0; i--) {
            HorizontalFlatMahjong mahjActor = new HorizontalFlatMahjong(mahjs.get(i).getIndex(), Mahjong.MAHJ_DIRECTION_RIGHT);
            addActor(mahjActor);
            if (i >= 14) {
                currX = 2 * mahjActor.getWidth();
                currY = (i - 14) * (mahjActor.getHeight() - offset);
            } else if (i >= 6 && i < 14) {
                currX = mahjActor.getWidth();
                currY = (i - 5) * (mahjActor.getHeight() - offset);
            } else {
                currX = 0;
                currY = (i + 2) * (mahjActor.getHeight() - offset);
            }

            mahjActor.setPosition(currX, currY);
        }
    }
}
