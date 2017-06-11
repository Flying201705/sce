package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;
import java.util.List;

import nova.common.game.mahjong.data.MahjData;

public class LeftOutMahjongs extends HorizontalGroup {
    private Assets mAssets;
    private ArrayList<MahjData> mOutMahjs;
    private List<VerticalGroup> mGroups = new ArrayList<VerticalGroup>();

    public LeftOutMahjongs() {
        mAssets = Assets.getInstance();

        for (int i = 0; i < 3; i++) {
            VerticalGroup group = new VerticalGroup();
            group.space(-15);
            group.align(Align.bottom);
            group.columnAlign(Align.bottom);
            addActor(group);
            mGroups.add(group);
        }
        reverse();
    }

    public void setOutMahjongs(ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mOutMahjs != null && mahjs.containsAll(mOutMahjs)) {
//            return;
//        }

        mOutMahjs = mahjs;

        int i;
        for (i = 0; i < 3; i++) {
            mGroups.get(i).clear();
        }

        VerticalGroup group;

        for (i = 0; i < mahjs.size(); i++) {
            group = mGroups.get(i / 10);
            MahjActor mahjActor = new MahjActor(mAssets.getMahjMatchLeftTexture(mahjs.get(i).getIndex()));
            group.addActor(mahjActor);
        }
    }
}
