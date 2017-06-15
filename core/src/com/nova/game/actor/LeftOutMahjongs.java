package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.SnapshotArray;
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

        float currX = getWidth(), currY = getHeight();
        for (int i = 0; i < mahjs.size(); i++) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjMatchLeftTexture(mahjs.get(i).getIndex()));
            addActor(mahjActor);

            if (currY - mahjActor.getHeight() < 0) {
                currX -= mahjActor.getWidth();
                currY = getHeight();
            }

            mahjActor.setPosition(currX - mahjActor.getWidth(), currY - mahjActor.getHeight());

            currY -= mahjActor.getHeight() - 15;
        }
    }
}
