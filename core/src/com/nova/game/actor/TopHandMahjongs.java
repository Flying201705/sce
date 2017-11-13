package com.nova.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.actor.mahj.VerticalFlatMahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGroupData;

public class TopHandMahjongs extends Group {
    private Assets mAssets;
    private Group mMatchs;
    private Group mHands;
    private Mahjong mLastestMahj;
    private ArrayList<MahjData> mHandMahjs;

    private float mMatchX, mHandsX;

    public TopHandMahjongs() {
        mAssets = Assets.getInstance();

        mLastestMahj = new Mahjong(mAssets.mTopDefaultMahjBackground);
        mLastestMahj.setVisible(false);

        mMatchs = new Group();
        mHands = new Group();

        addActor(mLastestMahj);
        addActor(mHands);
        addActor(mMatchs);
    }

    public void updateMahjs(int where, MahjGroupData mahjGroupData) {
        if (mahjGroupData == null) {
            return;
        }

        // 测试用程序开始
        /**
        ArrayList<MahjData> datas = new ArrayList<MahjData>();
        for (int i = 0; i < 4; i++) {
            datas.add(new MahjData(12));
        }
        mahjGroupData = new MahjGroupData(0, datas);
        ArrayList<MahjData> matchdatas = new ArrayList<MahjData>();
        for (int i = 0; i < 3; i++) {
            matchdatas.add(new MahjData(23));
        }
        for (int i = 0; i < 4; i++) {
            matchdatas.add(new MahjData(24));
        }
        for (int i = 0; i < 3; i++) {
            matchdatas.add(new MahjData(24));
        }
        mahjGroupData.setMatchDatas(matchdatas);
        mahjGroupData.setLatestData(new MahjData(14));
         **/
        // 测试用程序结束

        setLastestMahjong(where, mahjGroupData.getLatestData());
        setHandMahjs(where, mahjGroupData.getDatas());
        setMatchMahjs(where, mahjGroupData.getMatchDatas());
    }

    public void setMatchMahjs(int where,  ArrayList<MahjData> mahjs) {
        mMatchs.clear();

        int count = 0, index = 0;
        mMatchX = mHandsX + 10;
        float offsetX = 3f;
        for (MahjData mahj : mahjs) {
            VerticalFlatMahjong mahjActor = new VerticalFlatMahjong(mahj.getIndex());
            mMatchs.addActor(mahjActor);
            if (count >= 3) {
                count = 0;
                if (index == mahj.getIndex()) {
                    mahjActor.setPosition(mMatchX - (mahjActor.getWidth() - offsetX), 10);
                    mMatchX += 5f;
                } else {
                    mMatchX += mahjActor.getWidth() - offsetX + 5f;
                    mahjActor.setPosition(mMatchX, 0);
                    count++;
                }
            } else {
                mMatchX += mahjActor.getWidth() - offsetX;
                mahjActor.setPosition(mMatchX, 0);
                count++;
            }
            index = mahj.getIndex();
        }
    }

    public void setHandMahjs(int where, ArrayList<MahjData> mahjs) {
        if (mahjs != null && mHandMahjs != null && mahjs.containsAll(mHandMahjs)) {
            return;
        }

        mHandMahjs = mahjs;
        mHands.clear();

        mHandsX = 5;
        for (int i = 0; i < mahjs.size(); i++) {
            Mahjong mahjActor = new Mahjong(mAssets.mTopDefaultMahjBackground);
            mHandsX += mahjActor.getWidth() - 3;
            mahjActor.setPosition(mHandsX, 0);
            mHands.addActor(mahjActor);
        }
    }

    public void setLastestMahjong(int where, MahjData mahjong) {
        if (mahjong != null && mahjong.getIndex() != 0) {
            mLastestMahj.setPosition(0, 0);
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
