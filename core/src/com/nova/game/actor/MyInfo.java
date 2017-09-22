package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nova.game.Constants;
import com.nova.game.assetmanager.Assets;

public class MyInfo extends Group {
    private Texture mHeadBg;
    private Image mHead;
    private String mName;
    private String mGold;

    private FreeTypeFontParameter mParameter;
    private BitmapFont mFont;

    public MyInfo() {
        mHeadBg = new Texture("Head/head_bg.png");
        mName = "游客";
        mGold = "10000";

        mParameter = new FreeTypeFontParameter();
        mParameter.size = 36;
        mFont = Assets.getInstance().mGeneratror.generateFont(mParameter);
    }

    public void setHeadImage(Texture headImage) {
        if (headImage == null) {
            return;
        }

        mHead = new Image(headImage);
        mHead.setBounds(5, 5, Constants.HEAD_WIDTH, Constants.HEAD_HEIGHT);
        addActor(mHead);
    }

    public void setName(String name) {
        if (name == null) {
            return;
        }

        mName = name;

        mParameter.characters += name;
        mFont = Assets.getInstance().mGeneratror.generateFont(mParameter);
    }

    public void setGold(int gold) {
        mGold = String.valueOf(gold);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(mHeadBg, getX(), getY(), Constants.HEAD_WIDTH + 10, Constants.HEAD_HEIGHT + 10);
        mFont.draw(batch, mName, getX() + Constants.HEAD_WIDTH + 15, getY() + Constants.HEAD_HEIGHT);
        mFont.draw(batch, mGold, getX() + Constants.HEAD_WIDTH + 15, getY() + (Constants.HEAD_HEIGHT / 2));

        super.draw(batch, parentAlpha);
    }
}
