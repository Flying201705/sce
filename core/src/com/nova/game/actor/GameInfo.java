package com.nova.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nova.game.assetmanager.Assets;

/**
 * Created by zhangxx on 17-10-18.
 */

public class GameInfo extends Actor {

    private int mRemainSize = 0;
    private int mGodIndex = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawRemainSize(batch);
        drawGodData(batch);
    }

    public void updateRemainSize(int size) {
        mRemainSize = size;
    }

    public void updateGodIndex(int index) {
        mGodIndex = index;
    }

    private void drawRemainSize(Batch batch) {
        Texture remainTexture = new Texture("SceneGame/hand_top.png");
        batch.draw(remainTexture, getX() + 35, getY() + 37, 20, 26);
        BitmapFont font = new BitmapFont();
        font.setColor(Color.GREEN);
        font.getData().setScale(1f);
        font.draw(batch, String.valueOf(mRemainSize), getX() + 37, getY() + 55);
    }

    private void drawGodData(Batch batch) {
        if (mGodIndex <= 0) {
            return;
        }

        MahjActor godActor = new MahjActor(Assets.getInstance().getMahjHandMeTexture(mGodIndex));
        godActor.setScale(0.2f);
        godActor.setCanStandUp(true);
        godActor.setPosition(getX() + 75, getY() + 37);
        godActor.draw(batch, 1.0f);
    }
}
