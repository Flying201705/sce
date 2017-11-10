package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.actor.mahj.VerticalFlatMahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGroupData;

public class TopHandMahjongs extends HorizontalGroup {
    private Assets mAssets;
    private HorizontalGroup mMatchs;
    private HorizontalGroup mHands;
    private Mahjong mLastestMahj;
    private ArrayList<MahjData> mMactchMahjs;
    private ArrayList<MahjData> mHandMahjs;

    public TopHandMahjongs() {
        mAssets = Assets.getInstance();

        mLastestMahj = new Mahjong(mAssets.mTopDefaultMahjBackground);
        mLastestMahj.setVisible(false);

        mMatchs = new HorizontalGroup();

        mHands = new HorizontalGroup();
        mHands.space(-5);

        addActor(mMatchs);
        addActor(mHands);
        addActor(mLastestMahj);

        reverse();
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
            VerticalFlatMahjong mahjActor = new VerticalFlatMahjong(mahj.getIndex());
            mMatchs.addActor(mahjActor);
        }
    }

    public void setHandMahjs(int where, ArrayList<MahjData> mahjs) {
        if (mahjs != null && mHandMahjs != null && mahjs.containsAll(mHandMahjs)) {
            return;
        }

        mHandMahjs = mahjs;
        mHands.clear();

        Texture texture_2 = mAssets.mTopDefaultMahjBackground;
        for (int i = 0; i < mahjs.size(); i++) {
            Mahjong mahjActor = new Mahjong(texture_2);
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
