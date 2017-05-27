package com.nova.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class BaseStage extends Stage {
    private BaseScreen mScreen;

    public BaseStage(BaseScreen screen) {
        super(new ScalingViewport(Scaling.stretch, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, new OrthographicCamera()));

        mScreen = screen;
    }

    @Override
    public boolean keyDown(int keyCode) {
        return true;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            mScreen.doBackKeyAction();
        }
        return super.keyUp(keyCode);
    }
}
