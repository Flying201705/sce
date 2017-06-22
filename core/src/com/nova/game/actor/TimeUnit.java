package com.nova.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class TimeUnit extends Actor {
    private static final float FRAME_DURATION = 1.0f / 15.0f;

    private Texture mBg;
    private Array<Texture> mTimePoint = new Array<Texture>();
    private Animation<Texture> mTimeAnim;
    private float mAnimationTime;
    private float mTimeCount;
    private boolean mShowAnim;
    private boolean mShowTime;
    private boolean mThinkingTimes;
    private int mCurrPlayer = -1;
    private int mPlayerTimes;

    private BitmapFont mFont;

    private TimeUnitListener mListener;

    public interface TimeUnitListener {
        void onAnimationFinished();
        void onTimeOut();
    }

    public TimeUnit() {
        mBg = new Texture("Actors/TimeBack.png");
        setSize(mBg.getWidth(), mBg.getHeight());

        mTimePoint.add(new Texture("Actors/TimePoint0.png"));
        mTimePoint.add(new Texture("Actors/TimePoint1.png"));
        mTimePoint.add(new Texture("Actors/TimePoint2.png"));
        mTimePoint.add(new Texture("Actors/TimePoint3.png"));

        mTimeAnim = new Animation<Texture>(FRAME_DURATION, mTimePoint, Animation.PlayMode.LOOP);

        mFont = new BitmapFont();
        mFont.setColor(Color.RED);
        mFont.getData().setScale(3f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(mBg, getX(), getY());

        if (mShowAnim) {
            mAnimationTime += Gdx.graphics.getDeltaTime();
            if (mAnimationTime > 2f) {
                mShowAnim = false;
                if (mListener != null) {
                    mListener.onAnimationFinished();
                }
            }
            batch.draw(mTimeAnim.getKeyFrame(mAnimationTime), getX(), getY());
        } else {
            if (mCurrPlayer > -1) {
                batch.draw(mTimePoint.get(mCurrPlayer), getX(), getY());
            }
        }

        if (mShowTime) {
            mTimeCount += Gdx.graphics.getDeltaTime();
            if (mTimeCount >= 1f) {
                mTimeCount = 0f;
                mPlayerTimes--;
                if (mPlayerTimes < 0) {
                    mPlayerTimes = 0;
                    if (mThinkingTimes && mListener != null) {
                        mListener.onTimeOut();
                    }
                }
            }
            mFont.draw(batch, String.valueOf(mPlayerTimes), getX() + 54, getY() + 84);
        }
    }

    @Override
    public void clear() {
        super.clear();
        mBg.dispose();
        mTimePoint.clear();
        mFont.dispose();
    }

    public void setTimeUnitListener(TimeUnitListener listener) {
        mListener = listener;
    }

    public void removeTimeUnitListener() {
        mListener = null;
    }

    public void startTime() {
        mAnimationTime = 0.0f;
        mShowAnim = true;
    }

    public boolean isAnimation() {
       return mShowAnim;
    }

    public void updateCurrPlayer(int curr) {
        mShowTime = curr >= 0;

        if (mCurrPlayer != curr) {
            resetTime();
        }
        mCurrPlayer = curr;
    }

    public void startThinkingTime() {
        if (!mThinkingTimes) {
            mThinkingTimes = true;
            resetTime();
        }
    }

    public void stopThinkingTime() {
        if (mThinkingTimes) {
            mThinkingTimes = false;
            resetTime();
        }
    }

    private void resetTime() {
        mPlayerTimes = 4;
        mTimeCount = 0f;
    }
}
