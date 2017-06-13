package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public class TopOutMahjongs extends Table {
    private Assets mAssets;
    private ArrayList<MahjData> mOutMahjs;

    public TopOutMahjongs() {
        mAssets = Assets.getInstance();
        align(Align.bottomRight);
    }

    public void setOutMahjongs(ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mOutMahjs != null && mahjs.containsAll(mOutMahjs)) {
//            return;
//        }

        mOutMahjs = mahjs;
        clear();

        int count = 0;
        for (int i = mahjs.size(); i > 0; i--) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjMatchMeTexture(mahjs.get(i - 1).getIndex()));
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
