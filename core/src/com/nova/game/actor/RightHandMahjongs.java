package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.nova.game.actor.mahj.HorizontalFlatMahjong;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGroupData;

public class RightHandMahjongs extends Group {
    private Assets mAssets;
    private Group mMatchs;
    private Group mHands;
    private Mahjong mLastestMahj;
    private ArrayList<MahjData> mMactchMahjs;
    private ArrayList<MahjData> mHandMahjs;

    private float mMatchY, mHandsY;

    public RightHandMahjongs() {
        mAssets = Assets.getInstance();

        mLastestMahj = new Mahjong(new Texture("SceneGame/mahj_right.png"));
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

        setMatchMahjs(where, mahjGroupData.getMatchDatas());
        setHandMahjs(where, mahjGroupData.getDatas());
        setLastestMahjong(where, mahjGroupData.getLatestData());
    }

    public void setMatchMahjs(int where, ArrayList<MahjData> mahjs) {
//        if (mahjs != null && mMactchMahjs != null && mahjs.containsAll(mMactchMahjs)) {
//            return;
//        }

        mMactchMahjs = mahjs;
        mMatchs.clear();

        mMatchY = 0;

        int count = 0, index = 0;
        for (MahjData mahj : mahjs) {
            HorizontalFlatMahjong mahjActor = new HorizontalFlatMahjong(mahj.getIndex(), Mahjong.MAHJ_DIRECTION_RIGHT);
            mMatchs.addActor(mahjActor);
            if (count >= 3) {
                if (index == mahj.getIndex()) {
                    count = 0;
                    mahjActor.setPosition(10, mMatchY - mahjActor.getHeight());
                } else {
                    count = 0;
                    mMatchY += mahjActor.getHeight();
                    mahjActor.setPosition(0, mMatchY);
                }
            } else {
                mMatchY += mahjActor.getHeight();
                mahjActor.setPosition(0, mMatchY);
            }
            count++;
            index = mahj.getIndex();
        }
    }

    public void setHandMahjs(int where, ArrayList<MahjData> mahjs) {
        if (mahjs == null) {
            return;
        }

        if (mHandMahjs != null && mahjs.containsAll(mHandMahjs)) {
            return;
        }

        mHandMahjs = mahjs;
        mHands.clear();

        mHandsY = mMatchY + 10;

        Texture texture = new Texture("SceneGame/mahj_right.png");
        for (int i = 0; i < mahjs.size(); i++) {
            Mahjong mahjActor = new Mahjong(texture);
            mahjActor.setPosition(0, mHandsY);
            mHands.addActor(mahjActor);
            mHandsY += mahjActor.getHeight() - 29;
        }
    }

    public void setLastestMahjong(int where, MahjData mahjong) {
        if (mahjong != null && mahjong.getIndex() != 0) {
            mLastestMahj.setPosition(0, mHandsY + 10);
            mLastestMahj.setVisible(true);
        } else {
            mLastestMahj.setVisible(false);
        }
    }
}
