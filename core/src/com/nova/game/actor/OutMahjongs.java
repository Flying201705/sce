package com.nova.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public abstract class OutMahjongs extends Group {
    private ArrayList<MahjData> mOutMahjs;
    private LatestOutMahjongMark mLatestmahjongMark;
    protected float mLatestX, mLatestY, mLatestW, mLatestH;
    private boolean mLastOutMarkShow = false;

    public OutMahjongs() {
        mLatestmahjongMark = new LatestOutMahjongMark();
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
        mLastOutMarkShow = show;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (mLastOutMarkShow) {
            float x = getX() + mLatestX + (mLatestW - mLatestmahjongMark.getWidth()) / 2;
            float y = getY() + mLatestY + mLatestH / 2;
            mLatestmahjongMark.setPosition(x, y);
            mLatestmahjongMark.draw(batch, parentAlpha);
        }
    }
}
