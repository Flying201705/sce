package com.nova.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Head extends Actor {
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private int mDirection = 0;

    private Texture mImage;
    private FreeTypeFontGenerator mGeneratror;
    private BitmapFont mFont;

    private String mName = "刘备";
    private int mGold = 10000;

    public Head() {
        this(HORIZONTAL);
    }

    public Head(int direction) {
        mDirection = direction;

        mImage = new Texture("Head/Head0.png");
        mGeneratror = new FreeTypeFontGenerator(Gdx.files.internal("Font/font.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 30;
        parameter.characters = mName + "0123456789";
        mFont = mGeneratror.generateFont(parameter);
        mFont.setColor(Color.GREEN);

        if (mDirection == HORIZONTAL) {
            setSize(200, 102);
        } else {
            setSize(102, 150);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (mDirection == HORIZONTAL) {
            batch.draw(mImage, getX(), getY(), 80, 80);
            mFont.draw(batch, mName, getX() + 80, getY() + 80);
            mFont.draw(batch, String.valueOf(mGold), getX() + 80, getY() + 30);
        } else {
            batch.draw(mImage, getX(), getY() + getHeight() - 80, 80, 80);
            mFont.draw(batch, mName, getX(), getY() + 60);
            mFont.draw(batch, String.valueOf(mGold), getX(), getY() + 30);
        }
    }
}
