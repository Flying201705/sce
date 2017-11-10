package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.nova.game.actor.mahj.HorizontalFlatMahjong;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGroupData;

public class LeftHandMahjongs extends VerticalGroup {
    private Assets mAssets;
    private VerticalGroup mMatchs;
    private VerticalGroup mHands;
    private Mahjong mLastestMahj;
    private ArrayList<MahjData> mMactchMahjs;
    private ArrayList<MahjData> mHandMahjs;

    public LeftHandMahjongs() {
        mAssets = Assets.getInstance();

        mLastestMahj = new Mahjong(mAssets.mLeftDefaultMahjBackground);
        mLastestMahj.setVisible(false);

        mMatchs = new VerticalGroup();
        mMatchs.space(-5);

        mHands = new VerticalGroup();
        mHands.space(-29);

        addActor(mMatchs);
        addActor(mHands);
        addActor(mLastestMahj);
    }

    public void updateMahjs(int where, MahjGroupData mahjGroupData) {
        if (mahjGroupData == null) {
            return;
        }
        setMatchMahjs(where, mahjGroupData.getMatchDatas());
        setHandMahjs(where, mahjGroupData.getDatas());
        setLastestMahjong(where, mahjGroupData.getLatestData());
    }

    public void setMatchMahjs(int where,  ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mMactchMahjs != null && mahjs.containsAll(mMactchMahjs)) {
//            return;
//        }

        mMactchMahjs = mahjs;
        mMatchs.clear();

        for (MahjData mahj : mahjs) {
            HorizontalFlatMahjong mahjActor = new HorizontalFlatMahjong(mahj.getIndex());
            mMatchs.addActor(mahjActor);
        }
    }

    public void setHandMahjs(int where, ArrayList<MahjData> mahjs) {
        if (mahjs != null && mHandMahjs != null && mahjs.containsAll(mHandMahjs)) {
            return;
        }

        mHandMahjs = mahjs;
        mHands.clear();

        Texture texture_3 = mAssets.mLeftDefaultMahjBackground;
        for (int i = 0; i < mahjs.size(); i++) {
            Mahjong mahjActor = new Mahjong(texture_3);
            mHands.addActor(mahjActor);
        }

    }

    public void setLastestMahjong(int where, MahjData mahjong) {
        if (mahjong != null && mahjong.getIndex() != 0) {
            mLastestMahj.setVisible(true);
        } else {
            mLastestMahj.setVisible(false);
        }
    }
}
