package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.nova.game.actor.HandMahjongs;
import com.nova.game.actor.TimeUnit;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.assetmanager.Assets;
import com.nova.game.model.MahjGameController;
import com.nova.game.widget.SceButton;

import java.util.HashMap;

import nova.common.game.mahjong.data.MahjGroupData;

public class GameScreen extends BaseScreen {
    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Texture mBg;
    private TimeUnit mTime;
    private Assets mAssents;
    private SceButton mStartBt;
    private HandMahjongs mMyHands, mLeftHands, mRightHands, mTopHands;
    private boolean mShowMahjong = false;

    private MahjGameController mController = MahjGameController.create("local");

    private int[] mahjhand = new int[]{11, 11, 11, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19};

    private TimeUnit.AnimationFinishedListener mAnimListener = new TimeUnit.AnimationFinishedListener() {
        @Override
        public void onFinished() {
            mShowMahjong = true;
            mStartBt.remove();
        }
    };

    public GameScreen(BaseGame game) {
        super(game);
        mController.startGame();
    }

    @Override
    public void show() {
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        mAssents = Assets.getInstance();
        mAssents.loadMahjTexture();

        mBatch = new SpriteBatch();
        mBg = new Texture("ScenceGame/background.jpg");

        mTime = new TimeUnit();
        mTime.setPosition(570, 295);
        mTime.setOnAnimationFinishedListener(mAnimListener);
        mStage.addActor(mTime);

        mStartBt = new SceButton("ScenceGame/bt_start.png");
        mStartBt.setPosition(540, 100);
        mStartBt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mTime.startTime();
            }
        });
        mStage.addActor(mStartBt);

        mMyHands = new HandMahjongs(0);
        mMyHands.setPosition(120, 62);
        mStage.addActor(mMyHands);

        mLeftHands = new HandMahjongs(3);
        mLeftHands.setPosition(60, 600);
        mStage.addActor(mLeftHands);

        mRightHands = new HandMahjongs(1);
        mRightHands.setPosition(1250, 600);
        mStage.addActor(mRightHands);

        mTopHands = new HandMahjongs(2);
        mTopHands.setPosition(300, 660);
        mStage.addActor(mTopHands);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        mBatch.draw(mBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mBatch.end();

        updateMahjong();

        mStage.act();
        mStage.draw();
    }

    @Override
    public void dispose() {
        mAssents.clearMahjTexture();
        mBatch.dispose();
        mBg.dispose();
        mStage.dispose();
    }

    private void updateMahjong() {
        if (!mShowMahjong) {
            return;
        }

        HashMap<Integer, MahjGroupData> playerDatas = mController.getGroupDatas();

        if (playerDatas.isEmpty()) {
            return;
        }

        mMyHands.setMahjs(0, playerDatas.get(0).getDatas());

        mLeftHands.setMahjs(3, playerDatas.get(3).getDatas());

        mRightHands.setMahjs(1, playerDatas.get(1).getDatas());

        mTopHands.setMahjs(2, playerDatas.get(2).getDatas());
    }
}
