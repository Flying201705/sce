package com.nova.game.actor;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import com.nova.game.assetmanager.Assets;
import com.nova.game.utils.Log;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.util.MahjConstant;

public class Operate extends Actor {
    private Texture mPengImage;
    private Texture mGangImage;
    private Texture mChiImage;
    private Texture mTingImage;
    private Texture mHuImage;
    private Texture mImage;
    private int mType;
    private ArrayList<MahjData> mMatchData;
    private boolean mIsTypeDisplay = false;
    private boolean mIsMale = true;

    public Operate() {
        mPengImage = new Texture("Animation/eff_peng.png");
        mGangImage = new Texture("Animation/eff_gang.png");
        mChiImage = new Texture("Animation/eff_chi.png");
        mTingImage = new Texture("Animation/eff_ting.png");
        mHuImage = new Texture("Animation/eff_hu.png");

        setSize(mPengImage.getWidth(), mPengImage.getHeight());
        setOrigin(Align.center);
    }

    public void update(int type, ArrayList<MahjData> matchData) {
        if (mIsTypeDisplay ||
                (mType == type && ((mType & MahjConstant.MAHJ_MATCH_HU) == MahjConstant.MAHJ_MATCH_HU
                || (mType & MahjConstant.MAHJ_MATCH_TING) == MahjConstant.MAHJ_MATCH_TING))) {
            return;
        }

        if (((type & MahjConstant.MAHJ_MATCH_PENG) == MahjConstant.MAHJ_MATCH_PENG
                || (type & MahjConstant.MAHJ_MATCH_GANG) == MahjConstant.MAHJ_MATCH_GANG
                || (type & MahjConstant.MAHJ_MATCH_CHI) == MahjConstant.MAHJ_MATCH_CHI)
                && (mMatchData != null && matchData != null && mMatchData.containsAll(matchData))) {
            return;
        }

        mMatchData = matchData;
        mIsTypeDisplay = true;
        mType = type;

        int soundType = -1;
        if ((mType & MahjConstant.MAHJ_MATCH_PENG) == MahjConstant.MAHJ_MATCH_PENG) {
            soundType = MahjConstant.MAHJ_MATCH_PENG;
            mImage = mPengImage;
        } else if ((mType & MahjConstant.MAHJ_MATCH_GANG) == MahjConstant.MAHJ_MATCH_GANG) {
            soundType = MahjConstant.MAHJ_MATCH_GANG;
            mImage = mGangImage;
        } else if ((mType & MahjConstant.MAHJ_MATCH_CHI) == MahjConstant.MAHJ_MATCH_CHI) {
            soundType = MahjConstant.MAHJ_MATCH_CHI;
            mImage = mChiImage;
        } else if ((mType & MahjConstant.MAHJ_MATCH_HU) == MahjConstant.MAHJ_MATCH_HU) {
            soundType = MahjConstant.MAHJ_MATCH_HU;
            mImage = mHuImage;
        } else if ((mType & MahjConstant.MAHJ_MATCH_TING) == MahjConstant.MAHJ_MATCH_TING) {
            soundType = MahjConstant.MAHJ_MATCH_TING;
            mImage = mTingImage;
        }

        clearActions();
        SequenceAction sequenceAction = new SequenceAction(Actions.scaleTo(1.1f, 1.1f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f));
        SequenceAction action = new SequenceAction(Actions.repeat(3, sequenceAction), Actions.run(new Runnable() {
            @Override
            public void run() {
                mIsTypeDisplay = false;
            }
        }));
        addAction(action);

        Sound sound = Assets.getInstance().getOperateSound(mIsMale, soundType);
        if (sound != null) {
            sound.play();
        }
    }

    public void setSex(int sex) {
        mIsMale = sex > 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (mIsTypeDisplay && mImage != null) {
            batch.draw(mImage, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(),
                    0, 0, mImage.getWidth(), mImage.getHeight(), false, false);
        }
    }
}