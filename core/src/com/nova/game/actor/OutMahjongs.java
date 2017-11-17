package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.nova.game.utils.Log;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public abstract class OutMahjongs extends Group {
    private ArrayList<MahjData> mOutMahjs;
    private LatestOutMahjongMark mLatestmahjongMark;
    protected float mLatestX, mLatestY, mLatestW, mLatestH;

    public OutMahjongs() {
        mLatestmahjongMark = new LatestOutMahjongMark();
        mLatestmahjongMark.setVisible(false);
    }

    public void setOutMahjongs(ArrayList<MahjData> mahjs) {
        int newCount = (mahjs == null ? 0 : mahjs.size());
        int oldCount = (mOutMahjs == null ? 0 : mOutMahjs.size());
        if (oldCount != newCount) {
            mOutMahjs = mahjs;
            updateOutMahjongs(mahjs);
        }
    }

    protected abstract void updateOutMahjongs(ArrayList<MahjData> mahjs);

    public void setLatestOutMark(boolean show) {
        mLatestmahjongMark.setVisible(show);
        if (show) {
            Log.i("liuhao", "setLatestOutMark x:" + mLatestX + "; y:" + mLatestY);

            float x = mLatestX + (mLatestW - mLatestmahjongMark.getWidth()) / 2;
            float y = mLatestY + mLatestH / 2;

            mLatestmahjongMark.setPosition(x, y);
            addActor(mLatestmahjongMark);
        }
    }
}
