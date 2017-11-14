package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nova.game.actor.mahj.InfoMahjong;
import com.nova.game.actor.mahj.VerticalFlatMahjong;
import com.nova.game.assetmanager.Assets;

/**
 * Created by zhangxx on 17-10-18.
 */

public class GameInfo extends Actor {

    private Texture mGameInfoBg;
    private Texture mMahjBg;
    private TextureRegion[][] mRemainNums;
    private int mRemainSize = 0;
    private int mGodIndex = 0;

    public GameInfo() {
        mGameInfoBg = Assets.getInstance().mGameInfoBackground;
        mMahjBg = Assets.getInstance().mTopDefaultMahjBackground;
        mRemainNums = Assets.getInstance().mMahjRemainNum;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(mGameInfoBg, getX(), getY());
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
        float remainX = getX() + 23, remainY = getY() + 12;
        batch.draw(mMahjBg, remainX, remainY);
        batch.draw(mRemainNums[0][remain_ten], remainX + 5, remainY + 15);
        batch.draw(mRemainNums[0][remain_one], remainX + 20, remainY + 15);
    }

    private void drawGodData(Batch batch, float parentAlpha) {
        if (mGodIndex <= 0) {
            return;
        }

        InfoMahjong godMahj = new InfoMahjong(mGodIndex);
        godMahj.setPosition(getX() + 87, getY() + 12);
        godMahj.draw(batch, parentAlpha);
    }
}
