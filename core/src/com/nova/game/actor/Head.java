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
    private Texture mImage;
    private FreeTypeFontGenerator mGeneratror;
    private BitmapFont mFont;

    private String mName = "刘备";

    public Head() {
        mImage = new Texture("Head/Head0.png");
        mGeneratror = new FreeTypeFontGenerator(Gdx.files.internal("Font/font.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 36;
        parameter.characters = mName;
        mFont = mGeneratror.generateFont(parameter);
        mFont.setColor(Color.GREEN);

        setSize(200, 102);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mImage, getX(), getY(), 80, 80);
        mFont.draw(batch, mName, getX() + 80, getY() + 80);
    }
}
