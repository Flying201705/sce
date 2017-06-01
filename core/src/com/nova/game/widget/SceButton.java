package com.nova.game.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SceButton extends Button {

    public SceButton(String path){
        super(new TextureRegionDrawable(new TextureRegion(new Texture(path))));
    }

    @Override
    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        if (isPressed()) {
            parentAlpha = parentAlpha * 0.5f;
        }
        super.drawBackground(batch, parentAlpha, x, y);
    }
}
