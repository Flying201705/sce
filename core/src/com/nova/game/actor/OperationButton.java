package com.nova.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.widget.SceButton;

import nova.common.game.mahjong.util.MahjConstant;

public class OperationButton extends HorizontalGroup {
    private SceButton mPengButton;
    private SceButton mGangButton;
    private SceButton mChiButton;
    private SceButton mTingButton;
    private SceButton mGuoButton;
    private SceButton mHuButton;

    private ButtonClickListener mButtonClickListener;

    private ClickListener mClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Actor actor = event.getTarget();
            if (actor == null) {
                return;
            }

            int type = 0;
            if (actor == mPengButton) {
                type = MahjConstant.MAHJ_MATCH_PENG;
            } else if (actor == mGangButton) {
                type = MahjConstant.MAHJ_MATCH_GANG;
            } else if (actor == mChiButton) {
                type = MahjConstant.MAHJ_MATCH_CHI;
            } else if (actor == mTingButton) {
                type = MahjConstant.MAHJ_MATCH_TING;
            } else if (actor == mHuButton) {
                type = MahjConstant.MAHJ_MATCH_HU;
            } else if (actor == mGuoButton) {
                type = 0;
            }

            if (mButtonClickListener != null) {
                mButtonClickListener.operate(type);
            }

            clear();
        }
    };

    public interface ButtonClickListener {
        void operate(int type);
    }

    public OperationButton() {
        mPengButton = new SceButton("Animation/btn_peng.png");
        mGangButton = new SceButton("Animation/btn_gang.png");
        mChiButton = new SceButton("Animation/btn_chi.png");
        mTingButton = new SceButton("Animation/btn_ting.png");
        mGuoButton = new SceButton("Animation/btn_guo.png");
        mHuButton = new SceButton("Animation/btn_hu.png");

        mPengButton.addListener(mClickListener);
        mGangButton.addListener(mClickListener);
        mChiButton.addListener(mClickListener);
        mTingButton.addListener(mClickListener);
        mGuoButton.addListener(mClickListener);
        mHuButton.addListener(mClickListener);

        right();
    }

    public void update(int matchType) {
        if ((matchType & MahjConstant.MAHJ_MATCH_PENG) == MahjConstant.MAHJ_MATCH_PENG) {
            addActor(mPengButton);
        }

        if ((matchType & MahjConstant.MAHJ_MATCH_GANG) == MahjConstant.MAHJ_MATCH_GANG) {
            addActor(mGangButton);
        }

        if ((matchType & MahjConstant.MAHJ_MATCH_HU) == MahjConstant.MAHJ_MATCH_HU) {
            addActor(mHuButton);
        }

        if ((matchType & MahjConstant.MAHJ_MATCH_TING) == MahjConstant.MAHJ_MATCH_TING) {
            addActor(mTingButton);
        }

        if ((matchType & MahjConstant.MAHJ_MATCH_CHI) == MahjConstant.MAHJ_MATCH_CHI) {
            addActor(mChiButton);
        }

        if (matchType > 0) {
            addActor(mGuoButton);
        }
    }

    public void setButtonClickListener(ButtonClickListener listener) {
        mButtonClickListener = listener;
    }

}
