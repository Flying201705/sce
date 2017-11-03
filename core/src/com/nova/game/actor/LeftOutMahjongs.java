package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.SnapshotArray;
import com.nova.game.actor.mahj.HorizontalFlatMahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;
import java.util.List;

import nova.common.game.mahjong.data.MahjData;

public class LeftOutMahjongs extends Group {
    private Assets mAssets;
    private ArrayList<MahjData> mOutMahjs;

    public LeftOutMahjongs() {
        mAssets = Assets.getInstance();
    }

    public void setOutMahjongs(ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mOutMahjs != null && mahjs.containsAll(mOutMahjs)) {
//            return;
//        }

        mOutMahjs = mahjs;
        clear();

        float currX = getWidth(), currY = getHeight(), offset = 15f;
        for (int i = 0; i < mahjs.size(); i++) {
            HorizontalFlatMahjong mahjActor = new HorizontalFlatMahjong(mahjs.get(i).getIndex());
            addActor(mahjActor);

            if (i < 6) {
                currX = getWidth() - mahjActor.getWidth();
                currY = getHeight() - (i + 2) * (mahjActor.getHeight() - offset);
            } else if (i >= 6 && i < 14) {
                currX = getWidth() - 2 * mahjActor.getWidth();
                currY = getHeight() - (i + 1) * (mahjActor.getHeight() - offset);
            } else if (i >= 14) {
                currX = getWidth() - 3 * mahjActor.getWidth();
                currY = getHeight() - i* (mahjActor.getHeight() - offset);
            }
            mahjActor.setPosition(currX, currY);
        }
    }
}
