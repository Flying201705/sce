package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.nova.game.utils.Log;

public class LatestOutMahjongMark extends Actor {
    private static final float DURATION = 1.5f;
    private Texture mImage;

    public LatestOutMahjongMark() {
        mImage = new Texture("Animation/mark.png");
        setSize(mImage.getWidth(), mImage.getHeight());
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();

        clearActions();

        float x = getX(), y = getY();

        RepeatAction repeatAction = Actions.forever(Actions.sequence(Actions.moveTo(x, y + 10, DURATION),
                Actions.moveTo(x, y, DURATION)));
        addAction(repeatAction);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(mImage, getX(), getY());
    }
}
