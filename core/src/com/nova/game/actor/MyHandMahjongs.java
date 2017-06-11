package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGroupData;

public class MyHandMahjongs extends HorizontalGroup {
    private Assets mAssets;
    private HorizontalGroup mMatchs;
    private HorizontalGroup mHands;
    private MahjActor mLastestMahj;
    private ArrayList<MahjData> mMactchMahjs;
    private ArrayList<MahjData> mHandMahjs;

    public MyHandMahjongs() {
        mAssets = Assets.getInstance();

        mMatchs = new HorizontalGroup();

        mHands = new HorizontalGroup();
        mHands.space(-2);

        mLastestMahj = new MahjActor();
        mLastestMahj.setScale(0.8f);

        addActor(mMatchs);
        addActor(mHands);
        addActor(mLastestMahj);
    }

    public void updateMahjs(int where, MahjGroupData mahjGroupData) {
        if (mahjGroupData == null) {
            return;
        }
        setMatchMahjs(mahjGroupData.getMatchDatas());
        setHandMahjs(mahjGroupData.getDatas());
        setLastestMahjong(mahjGroupData.getLatestData());
    }

    public void setMatchMahjs(ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mMactchMahjs != null /*&& mahjs.containsAll(mMactchMahjs)*/) {
//            return;
//        }

        mMactchMahjs = mahjs;
        mMatchs.clear();

        for (MahjData mahj : mahjs) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjMatchMeTexture(mahj.getIndex()));
            mahjActor.setScale(0.8f);
            mMatchs.addActor(mahjActor);
        }

//        layout();
    }

    public void setHandMahjs(ArrayList<MahjData> mahjs) {
        if (mahjs != null && mHandMahjs != null && mahjs.containsAll(mHandMahjs)) {
            return;
        }

        mHandMahjs = mahjs;
        mHands.clear();

        for (MahjData mahj : mahjs) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjHandMeTexture(mahj.getIndex()));
            mahjActor.setScale(0.8f);
            mHands.addActor(mahjActor);
        }
    }

    public void setLastestMahjong(MahjData mahjong) {
        if (mahjong != null && mahjong.getIndex() != 0) {
            mLastestMahj.setVisible(true);
            mLastestMahj.setImage(mAssets.getMahjHandMeTexture(mahjong.getIndex()));
        } else {
            mLastestMahj.setVisible(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
