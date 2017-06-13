package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;
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

        for (MahjData mahjData : mahjs) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjMatchMeTexture(mahjData.getIndex()));
            mahjActor.setScale(0.5f);
            addActor(mahjActor);
        }

        int count = 0;
        float currX = getWidth(), currY = 0;
        SnapshotArray<Actor> children = getChildren();
        for (Actor actor : children) {
            if (count > 10) {
                count = 0;
                currX = getWidth();
                currY += actor.getHeight();
            }
            currX -= actor.getWidth();

            actor.setPosition(currX, currY);
            count++;
        }
    }
}
