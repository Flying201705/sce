package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nova.game.actor.mahj.VerticalFlatMahjong;
import com.nova.game.assetmanager.Assets;

/**
 * Created by zhangxx on 17-10-18.
 */

public class GameInfo extends Actor {

    private Texture mMahjBg;
    private TextureRegion[][] mRemainNums;
    private int mRemainSize = 0;
    private int mGodIndex = 0;

    public GameInfo() {
        mMahjBg = Assets.getInstance().mTopDefaultMahjBackground;
        mRemainNums = Assets.getInstance().mMahjRemainNum;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawRemainSize(batch);
        drawGodData(batch, parentAlpha);
    }

    public void updateRemainSize(int size) {
        mRemainSize = size;
    }

    public void updateGodIndex(int index) {
        mGodIndex = index;
    }

    private void drawRemainSize(Batch batch) {
        int remain_ten = mRemainSize / 10;
        int remain_one = mRemainSize % 10;
        batch.draw(mMahjBg, getX() + 10, getY() + 10);
        batch.draw(mRemainNums[0][remain_ten], getX() + 15, getY() + 25);
        batch.draw(mRemainNums[0][remain_one], getX() + 30, getY() + 25);
    }

    private void drawGodData(Batch batch, float parentAlpha) {
        if (mGodIndex <= 0) {
            return;
        }

        VerticalFlatMahjong upFlatMahj = new VerticalFlatMahjong(mGodIndex);
        upFlatMahj.setPosition(getX() + 55, getY() + 10);
        upFlatMahj.draw(batch, parentAlpha);
    }
}
