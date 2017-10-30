package com.nova.game.actor;

import com.badlogic.gdx.graphics.Color;
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
    private Texture mNameBackground;
    private Texture mGodBackground;
    private Texture mGodTexture;
    private Texture mCrystalTexture;
    private Texture mAddTexture;
    private Image mHead;
    private String mName;
    private String mGold;

    private FreeTypeFontParameter mParameter;
    private BitmapFont mFont;

    public MyInfo() {
        mHeadBg = new Texture("Head/head_background.png");
        mNameBackground = new Texture("SceneMain/name_background.png");
        mGodBackground = new Texture("SceneMain/god_background.png");
        mGodTexture = new Texture("SceneMain/gold.png");
        mCrystalTexture = new Texture("SceneMain/crystal.png");
        mAddTexture = new Texture("SceneMain/icon_add.png");
        mName = "游客";
        mGold = "0";

        mParameter = new FreeTypeFontParameter();
        mParameter.size = 36;
        mFont = Assets.getInstance().mGeneratror.generateFont(mParameter);
    }

    public void setHeadImage(Texture headImage) {
        if (headImage == null) {
            return;
        }

        mHead = new Image(headImage);
        mHead.setBounds(0, 0, Constants.HEAD_WIDTH, Constants.HEAD_HEIGHT);
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
        batch.draw(mHeadBg, getX() - 1, getY() - 1, Constants.HEAD_WIDTH + 2, Constants.HEAD_HEIGHT + 2);
        batch.draw(mNameBackground, getX() + Constants.HEAD_WIDTH + 1, getY());
        mFont.setColor(Color.GREEN);
        mFont.getData().setScale(1.0f);
        mFont.draw(batch, mName, getX() + Constants.HEAD_WIDTH + 40, getY() + mNameBackground.getHeight() - 22);

        batch.draw(mGodBackground, getX() + Constants.HEAD_WIDTH + mNameBackground.getWidth(), getY());
        batch.draw(mGodTexture, getX() + Constants.HEAD_WIDTH + mNameBackground.getWidth() + 20, getY() + 12);
        mFont.setColor(Color.GOLD);
        mFont.getData().setScale(0.8f);
        mFont.draw(batch, mGold, getX() + Constants.HEAD_WIDTH + mNameBackground.getWidth() + mGodTexture.getWidth() + 20, getY() + 45);
        batch.draw(mAddTexture, getX() + Constants.HEAD_WIDTH + mNameBackground.getWidth() + 200, getY() + 12);

        batch.draw(mCrystalTexture, getX() + Constants.HEAD_WIDTH + mNameBackground.getWidth() + 265, getY() + 12);
        mFont.setColor(Color.WHITE);
        mFont.getData().setScale(0.8f);
        mFont.draw(batch, "4396", getX() + Constants.HEAD_WIDTH + mNameBackground.getWidth() + + mCrystalTexture.getWidth() + 270, getY() + 45);
        batch.draw(mAddTexture, getX() + Constants.HEAD_WIDTH + mNameBackground.getWidth() + mGodBackground.getWidth() - 55, getY() + 12);

        super.draw(batch, parentAlpha);
    }
}
