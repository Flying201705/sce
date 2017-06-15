package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;
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

        for (int i = mahjs.size() - 1; i >= 0; i--) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjMatchRightTexture(mahjs.get(i).getIndex()));
            addActor(mahjActor);
        }

        float currX = 0, currY = 0;
        SnapshotArray<Actor> children = getChildren();
        for (int j = children.size - 1; j >= 0; j--) {
            Actor actor = children.get(j);

            if (currY + actor.getHeight() > getHeight()) {
                currX += actor.getWidth();
                currY = 0;
            }

            actor.setPosition(currX, currY);

            currY += actor.getHeight() - 15;
        }
    }
}
