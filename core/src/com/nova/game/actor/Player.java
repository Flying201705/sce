package com.nova.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nova.game.assetmanager.Assets;
import com.nova.game.utils.UIUtil;

import nova.common.room.data.PlayerInfo;

public class Player extends Actor {
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private int mDirection = 0;

    private PlayerInfo mPlayerInfo;

    private Texture mImage;
    private BitmapFont mFont;

    private String mName = "游客";
    private int mGold = 0;

    public Player() {
        this(HORIZONTAL);
    }

    public Player(int direction) {
        mDirection = direction;

        mImage = new Texture("Head/Head0.png");
        mFont = Assets.getInstance().getFont();

        if (mDirection == HORIZONTAL) {
            setSize(200, 102);
        } else {
            setSize(102, 150);
        }
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        if (playerInfo == null) {
            return;
        }

        mPlayerInfo = playerInfo;

        mName = mPlayerInfo.getName();
        mGold = mPlayerInfo.getGold();
        if (mPlayerInfo.getHeaddatas() != null && mPlayerInfo.getHeaddatas().length > 0) {
            mImage = UIUtil.bytes2Texture(mPlayerInfo.getHeaddatas());
        }else if (mPlayerInfo.getSex() == 1) {
            mImage = new Texture("Head/Head1.png");
        } else {
            mImage = new Texture("Head/Head0.png");
        }
    }

    public PlayerInfo getPlayerInfo() {
        return mPlayerInfo;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (mDirection == HORIZONTAL) {
            batch.draw(mImage, getX(), getY(), 80, 80);
            mFont.setColor(Color.GREEN);
            mFont.getData().setScale(0.65f);
            mFont.draw(batch, mName, getX() + 85, getY() + 65);
            mFont.setColor(Color.GOLD);
            mFont.getData().setScale(0.6f);
            mFont.draw(batch, String.valueOf(mGold), getX() + 85, getY() + 35);
        } else {
            batch.draw(mImage, getX(), getY() + getHeight() - 80, 80, 80);
            mFont.setColor(Color.GREEN);
            mFont.getData().setScale(0.65f);
            mFont.draw(batch, mName, getX(), getY() + 60);
            mFont.setColor(Color.GOLD);
            mFont.getData().setScale(0.6f);
            mFont.draw(batch, String.valueOf(mGold), getX(), getY() + 40);
        }
    }
}
