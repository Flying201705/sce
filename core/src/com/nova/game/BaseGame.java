package com.nova.game;

import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.nova.game.assetmanager.Assets;

public abstract class BaseGame extends Game {
    private Stack<Screen> mScreenStack = new Stack<Screen>();

    protected SceApi mApi;

    @Override
    public void setScreen(Screen screen) {
        setScreen(screen, true);
    }

    public void setScreen(Screen screen, boolean toHistory) {
        super.setScreen(screen);

        if (toHistory) {
            Screen topScreen = mScreenStack.isEmpty() ? null : mScreenStack.peek();
            if (screen != null && screen != topScreen) {
                mScreenStack.push(screen);
            }
        }
    }

    public void goBack() {
        Screen currScreen = getScreen();
        Screen topScreen = mScreenStack.isEmpty() ? null : mScreenStack.peek();
        if (currScreen == topScreen) {
            mScreenStack.pop();
        }
        topScreen = mScreenStack.isEmpty() ? null : mScreenStack.peek();
        if (topScreen != null) {
            setScreen(topScreen);
            currScreen.dispose();
        } else {
            Assets.getInstance().clear();
            Gdx.app.exit();
        }
    }

    public void loginWeChat() {
        if (mApi != null) {
            mApi.loginWeChat();
        }
    }
}
