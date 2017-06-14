package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;
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

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SnapshotArray<Actor> children = mHands.getChildren();
                for (Actor actor : children) {
                    if (actor instanceof MahjActor) {
                        ((MahjActor) actor).standUp(false);
                    }
                }

                Actor hitA = hit(x, y, true);
                if (hitA instanceof MahjActor) {
                    ((MahjActor) hitA).standUp(true);
                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });
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
        if (isListMahjDataEqual(mahjs, mMactchMahjs)) {
            return;
        }

        mMactchMahjs = mahjs;
        mMatchs.clear();

        for (MahjData mahj : mahjs) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjMatchMeTexture(mahj.getIndex()));
            mahjActor.setScale(0.8f);
            mMatchs.addActor(mahjActor);
        }
    }

    public void setHandMahjs(ArrayList<MahjData> mahjs) {
        if (isListMahjDataEqual(mahjs, mHandMahjs)) {
            return;
        }

        mHandMahjs = mahjs;
        mHands.clear();

        for (MahjData mahj : mahjs) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjHandMeTexture(mahj.getIndex()));
            mahjActor.setScale(0.8f);
            mahjActor.setCanStandUp(true);
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

    private boolean isListMahjDataEqual(ArrayList<MahjData> list1, ArrayList<MahjData> list2) {
        if (list1 == null || list2 == null) {
            return false;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).getIndex() != list2.get(i).getIndex()) {
                return false;
            }
        }

        return true;
    }
}
