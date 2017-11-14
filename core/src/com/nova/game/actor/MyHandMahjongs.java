package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.actor.mahj.OwnerFlatMahjong;
import com.nova.game.actor.mahj.OwnerMahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGroupData;

public class MyHandMahjongs extends Group {
    private Assets mAssets;
    private Group mMatchs;
    private Group mHands;
    private OwnerMahjong mLastestMahj;
    private ArrayList<MahjData> mMactchMahjs;
    private ArrayList<MahjData> mHandMahjs;

    private handOutDataCallback mCallback;

    private float mMatchX, mHandsX, mCurrY;

    public interface handOutDataCallback {
        void handleOutData(int index);
    }

    public MyHandMahjongs() {
        mAssets = Assets.getInstance();

        mMatchs = new Group();
        mHands = new Group();

        mLastestMahj = new OwnerMahjong();
        mLastestMahj.setCanStandUp(true);
        mLastestMahj.setVisible(false);

        addActor(mMatchs);
        addActor(mHands);
        addActor(mLastestMahj);
    }

    public void updateMahjs(int where, MahjGroupData mahjGroupData) {
        if (mahjGroupData == null) {
            return;
        }

        mCurrY = getY();

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

        mMatchX = getX();

        int count = 0, index = 0;
        float offsetX = 4f;
        float offsetY = 15f;
        for (MahjData mahj : mahjs) {
            OwnerFlatMahjong mahjActor = new OwnerFlatMahjong(mahj.getIndex());
            mMatchs.addActor(mahjActor);

            if (count >= 3) {
                count = 0;
                if (index == mahj.getIndex()) {
                    mahjActor.setPosition(mMatchX - (mahjActor.getWidth() - offsetX), 15 + offsetY);
                    mMatchX += 5f;
                } else {
                    mMatchX += mahjActor.getWidth() - offsetX + 5f;
                    mahjActor.setPosition(mMatchX, offsetY);
                    count++;
                }
            } else {
                mMatchX += mahjActor.getWidth() - offsetX;
                mahjActor.setPosition(mMatchX, offsetY);
                count++;
            }
            index = mahj.getIndex();
        }

    }

    public void setHandMahjs(ArrayList<MahjData> mahjs) {
        if (isListMahjDataEqual(mahjs, mHandMahjs)) {
            return;
        }

        mHandMahjs = mahjs;
        mHands.clear();

        mHandsX = mMatchX + 10;

        for (MahjData mahj : mahjs) {
            OwnerMahjong mahjActor = new OwnerMahjong(mahj.getIndex());
            mahjActor.setMahjData(mahj);
            mahjActor.setCanStandUp(true);
            mHands.addActor(mahjActor);

            mHandsX += mahjActor.getWidth() - 2;
            mahjActor.setPosition(mHandsX, mCurrY);
        }
    }

    public void setLastestMahjong(MahjData mahjong) {
        if (mahjong != null && mahjong.getIndex() != 0) {
            mLastestMahj.setVisible(true);
            mLastestMahj.setMahjData(mahjong);
        } else {
            mLastestMahj.standUp(false);
            mLastestMahj.setVisible(false);
        }
        mLastestMahj.setPosition(mHandsX + mLastestMahj.getWidth() + 10, mCurrY);
    }

    public void clearStandUpMahjong() {
        SnapshotArray<Actor> children = mHands.getChildren();
        for (Actor actor : children) {
            if (actor instanceof Mahjong) {
                ((Mahjong) actor).standUp(false);
            }
        }
        if (mLastestMahj.isVisible() && mLastestMahj.isStandUp()) {
            mLastestMahj.standUp(false);
        }
    }

    public void sethandOutDataCallback(handOutDataCallback callback) {
        this.mCallback = callback;
    }

    private void handleOutData(int index) {
        if (mCallback != null) {
            mCallback.handleOutData(index);
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
