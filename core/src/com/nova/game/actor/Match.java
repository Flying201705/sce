package com.nova.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;

import nova.common.game.mahjong.util.MahjConstant;

public class Match extends Actor {
    private Texture mPengImage;
    private Texture mGangImage;
    private Texture mChiImage;
    private Texture mTingImage;
    private Texture mHuImage;
    private Texture mImage;
    private int mType;

    public Match() {
        mPengImage = new Texture("Animation/eff_peng.png");
        mGangImage = new Texture("Animation/eff_gang.png");
        mChiImage = new Texture("Animation/eff_chi.png");
        mTingImage = new Texture("Animation/eff_chi.png");
        mHuImage = new Texture("Animation/eff_hu.png");

        setSize(mPengImage.getWidth(), mPengImage.getHeight());
        setOrigin(Align.center);
    }

    public void update(int type) {
        Gdx.app.log("liuhao", "action update type = " + type);
        if (mType == 0) {
            mType = type;
            switch (mType) {
                case MahjConstant.MAHJ_MATCH_PENG:
                    mImage = mPengImage;
                    break;
                case MahjConstant.MAHJ_MATCH_GANG:
                    mImage = mGangImage;
                    break;
                case MahjConstant.MAHJ_MATCH_CHI:
                    mImage = mChiImage;
                    break;
                case MahjConstant.MAHJ_MATCH_TING:
                    mImage = mTingImage;
                    break;
                case MahjConstant.MAHJ_MATCH_HU:
                    mImage = mHuImage;
                    break;
                default:
                    break;
            }

            clearActions();
            SequenceAction sequenceAction = new SequenceAction(Actions.scaleTo(1.1f, 1.1f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f));
            SequenceAction action = new SequenceAction(Actions.repeat(3, sequenceAction), Actions.run(new Runnable() {
                @Override
                public void run() {
                    Gdx.app.log("liuhao", "action stop");
                    mType = 0;
                }
            }));
            addAction(action);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (mType != 0 && mImage != null) {
            batch.draw(mImage, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(),
                    0, 0, mImage.getWidth(), mImage.getHeight(), false, false);
        }

    }
}
