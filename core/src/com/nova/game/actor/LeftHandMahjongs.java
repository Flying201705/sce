package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.nova.game.actor.mahj.HorizontalFlatMahjong;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.assetmanager.Assets;
import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGroupData;

public class LeftHandMahjongs extends Group {
    private Assets mAssets;
    private Group mMatchs;
    private Group mHands;
    private Mahjong mLastestMahj;
    private ArrayList<MahjData> mHandMahjs;
    private float mMatchY, mHandsY;

    public LeftHandMahjongs() {
        mAssets = Assets.getInstance();

        mLastestMahj = new Mahjong(mAssets.mLeftDefaultMahjBackground);
        mLastestMahj.setVisible(false);

        mMatchs = new Group();

        mHands = new Group();

        addActor(mMatchs);
        addActor(mHands);
        addActor(mLastestMahj);
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

        setMatchMahjs(where, mahjGroupData.getMatchDatas());
        setHandMahjs(where, mahjGroupData.getDatas());
        setLastestMahjong(where, mahjGroupData.getLatestData());
    }

    public void setMatchMahjs(int where,  ArrayList<MahjData> mahjs) {
        mMatchs.clear();

        mMatchY = getHeight();
        int count = 0, index = 0;
        float offsetY = 17f;
        for (MahjData mahj : mahjs) {
            HorizontalFlatMahjong mahjActor = new HorizontalFlatMahjong(mahj.getIndex());
            mMatchs.addActor(mahjActor);
            if (count >= 3) {
                count = 0;
                if (index == mahj.getIndex()) {
                    mahjActor.setPosition(-10, mMatchY + (mahjActor.getHeight() - offsetY) + 5f);
                    mMatchY -= 5f;
                } else {
                    mMatchY -= mahjActor.getHeight() - offsetY + 5f;
                    mahjActor.setPosition(-10, mMatchY);
                    count++;
                }
            } else {
                mMatchY -= mahjActor.getHeight() - offsetY;
                mahjActor.setPosition(-10, mMatchY);
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

        mHandsY = mMatchY - 20;
        for (int i = 0; i < mahjs.size(); i++) {
            Mahjong mahjActor = new Mahjong(mAssets.mLeftDefaultMahjBackground);
            mHandsY -= mahjActor.getHeight() - 29;
            mahjActor.setPosition(0, mHandsY);
            mHands.addActor(mahjActor);
        }
    }

    public void setLastestMahjong(int where, MahjData mahjong) {
        if (mahjong != null && mahjong.getIndex() != 0) {
            mLastestMahj.setPosition(0, mHandsY - (mLastestMahj.getHeight() - 20f));
            mLastestMahj.setVisible(true);
        } else {
            mLastestMahj.setVisible(false);
        }
    }
}
