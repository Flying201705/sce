package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;

public class Match extends Actor {
    private Texture mImage;

    public Match() {
        mImage = new Texture("Animation/eff_peng.png");

        setSize(mImage.getWidth(), mImage.getHeight());
        setOrigin(Align.center);

        SequenceAction sequenceAction = new SequenceAction(Actions.scaleTo(1.1f, 1.1f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f));

        RepeatAction repeatAction = Actions.repeat(3, sequenceAction);
        addAction(repeatAction);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mImage, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(),
                0, 0, mImage.getWidth(), mImage.getHeight(), false, false);

    }
}
