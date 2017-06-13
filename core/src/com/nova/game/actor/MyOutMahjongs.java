package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public class MyOutMahjongs extends Table {
    private Assets mAssets;
    private ArrayList<MahjData> mOutMahjs;

    public MyOutMahjongs() {
        mAssets = Assets.getInstance();
        align(Align.topLeft);
    }

    public void setOutMahjongs(ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mOutMahjs != null && mahjs.containsAll(mOutMahjs)) {
//            return;
//        }

        mOutMahjs = mahjs;
        clear();

        int count = 0;
        for (MahjData mahj : mahjs) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjMatchMeTexture(mahj.getIndex()));
            mahjActor.setScale(0.5f);
            if (count >= 10) {
                row();
                count = 0;
            }
            add(mahjActor);
            count++;
        }
    }
}
