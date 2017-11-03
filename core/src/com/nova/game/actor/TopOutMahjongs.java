package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;
import com.nova.game.actor.mahj.VerticalFlatMahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public class TopOutMahjongs extends Group {
    private Assets mAssets;
    private ArrayList<MahjData> mOutMahjs;

    public TopOutMahjongs() {
        mAssets = Assets.getInstance();
    }

    public void setOutMahjongs(ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mOutMahjs != null && mahjs.containsAll(mOutMahjs)) {
//            return;
//        }

        mOutMahjs = mahjs;
        clear();

        float currX = 0, currY = 0, offset = 5f;
        for (int i = 0; i < mahjs.size(); i++) {
            VerticalFlatMahjong mahjActor = new VerticalFlatMahjong(mahjs.get(i).getIndex());
            addActor(mahjActor);

            if (i < 6) {
                currX = getWidth() - (i + 2) * (mahjActor.getWidth() - offset);
                currY = 0;
            } else if (i >= 6 && i < 14) {
                currX = getWidth() - (i + 1) * (mahjActor.getWidth() - offset);
                currY = mahjActor.getHeight();
            } else if (i >= 14) {
                currX = getWidth() - i * (mahjActor.getWidth() - offset);
                currY = 2 * mahjActor.getHeight();
            }
            mahjActor.setPosition(currX, currY);
        }
    }
}
