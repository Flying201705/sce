package com.nova.game.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.nova.game.Constants;
import com.nova.game.assetmanager.Assets;
import com.nova.game.model.MahjRoomController;
import com.nova.game.utils.UIUtil;

import nova.common.room.data.PlayerInfo;

public class Player extends Actor {
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    private int mDirection = 0;

    private PlayerInfo mPlayerInfo;
    private Texture mImage;
    private BitmapFont mFont;
    private TextureRegion[][] mGoldNums;

    private String mName = "游客";
    private int mGold = 0;

    public Player() {
        this(HORIZONTAL);
    }

    public Player(int direction) {
        mDirection = direction;

        mImage = new Texture("Head/Head0.png");
        mFont = Assets.getInstance().getFont();

        mGoldNums = TextureRegion.split(new Texture("Head/gold_img.png"), 15, 21);

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
            drawGold(batch, getX() + 85, getY() + 15);
        } else {
            batch.draw(mImage, getX(), getY() + getHeight() - 80, 80, 80);
            mFont.setColor(Color.GREEN);
            mFont.getData().setScale(0.65f);
            mFont.draw(batch, mName, getX(), getY() + 60);
            drawGold(batch, getX(), getY() + 20);
        }

        drawMessage(batch, parentAlpha);
    }

    private void drawGold(Batch batch, float x, float y) {
        int bitCount = 0;
        String goldStr = String.valueOf(mGold);
        while (goldStr.length() > 0) {
            if (goldStr.length() == 1) {
                int goldNum = Integer.valueOf(goldStr);
                batch.draw(mGoldNums[0][goldNum % 10], x + bitCount * 15, y);
                break;
            }
            int bit = Integer.valueOf(goldStr.substring(0, 1));
            batch.draw(mGoldNums[0][bit % 10], x + bitCount * 15, y);
            goldStr = goldStr.substring(1, goldStr.length());
            bitCount++;
        }
    }

    private void drawMessage(Batch batch, float parentAlpha) {
        // 消息
        String message = MahjRoomController.getInstance().getPlayerMessage(mPlayerInfo.getId());
        if (message != null) {
            Label.LabelStyle ls = new Label.LabelStyle();
            // ls.background = new NinePatchDrawable(new NinePatch(getPlayerMessageBackground(), 40, 40, 40, 40));
            ls.font = Assets.getInstance().getFont();
            Label label = new Label(message, ls);
            label.setWidth(330);
            label.setWrap(true);
            float messageX;
            float messageY;
            if (getX() < Constants.WORLD_WIDTH / 2) {
                messageX = getX();
            } else {
                messageX = getX() - 250;
            }

            if (getY() < Constants.WORLD_HEIGHT - 50) {
                messageY = getY() + 100;
            } else {
                messageY = getY() - 20;
            }

            label.setPosition(messageX, messageY);
            label.draw(batch, parentAlpha);
        }
    }
}
