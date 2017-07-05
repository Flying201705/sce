package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.actor.Head;
import com.nova.game.actor.LeftHandMahjongs;
import com.nova.game.actor.LeftOutMahjongs;
import com.nova.game.actor.MahjActor;
import com.nova.game.actor.Match;
import com.nova.game.actor.MyHandMahjongs;
import com.nova.game.actor.MyOutMahjongs;
import com.nova.game.actor.OperationButton;
import com.nova.game.actor.RightHandMahjongs;
import com.nova.game.actor.RightOutMahjongs;
import com.nova.game.actor.TimeUnit;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.actor.TopHandMahjongs;
import com.nova.game.actor.TopOutMahjongs;
import com.nova.game.assetmanager.Assets;
import com.nova.game.model.MahjGameController;
import com.nova.game.widget.SceButton;

import java.util.ArrayList;
import java.util.HashMap;

import nova.common.game.mahjong.data.MahjData;
import nova.common.game.mahjong.data.MahjGroupData;
import nova.common.game.mahjong.util.MahjConstant;
import nova.common.game.mahjong.util.TestMahjConstant;

public class GameScreen extends BaseScreen {
    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Texture mBg;
    private TimeUnit mTime;
    private Assets mAssents;
    private SceButton mStartBt;
    private Head mMyHead;
    private MyHandMahjongs mMyHands;
    private RightHandMahjongs mRightHands;
    private TopHandMahjongs mTopHands;
    private LeftHandMahjongs mLeftHands;
    private MyOutMahjongs mMyOuts;
    private RightOutMahjongs mRightOuts;
    private TopOutMahjongs mTopOuts;
    private LeftOutMahjongs mLeftOuts;
    private Match mMatch;
    private OperationButton mOperationButton;
    private boolean mIsDealt = false;

    private int mMatchType;

    private MahjGameController mController = MahjGameController.create("local");

    private TimeUnit.TimeUnitListener mTimeListener = new TimeUnit.TimeUnitListener() {
        @Override
        public void onAnimationFinished() {
            mIsDealt = true;
            mStartBt.remove();
        }

        @Override
        public void onTimeOut() {
            mOperationButton.clear();
        }
    };

    private MyHandMahjongs.handOutDataCallback mCallBack = new MyHandMahjongs.handOutDataCallback() {
        @Override
        public void handleOutData(int index) {
            mController.handleOutData(index);
        }
    };

    private OperationButton.ButtonClickListener mButtonClick = new OperationButton.ButtonClickListener() {
        @Override
        public void operate(int type) {
            mTime.stopThinkingTime();
            mController.handleMatchData(type);
        }
    };

    public GameScreen(BaseGame game) {
        super(game);
        TestMahjConstant.setDebug(1);
        mController.startGame();
    }

    @Override
    public void show() {
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        mStage.setDebugAll(false);

        mAssents = Assets.getInstance();
        mAssents.loadMahjTexture();

        mBatch = new SpriteBatch();
        mBg = new Texture("ScenceGame/background.jpg");

        mTime = new TimeUnit();
        mTime.setPosition(570, 295);
        mTime.setTimeUnitListener(mTimeListener);
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

        mMyHead = new Head();
        mMyHead.setPosition(15, 100);
        mStage.addActor(mMyHead);

        mMyHands = new MyHandMahjongs();
        mMyHands.setPosition(120, 62);
        mMyHands.sethandOutDataCallback(mCallBack);
        mStage.addActor(mMyHands);

        mLeftHands = new LeftHandMahjongs();
        mLeftHands.setPosition(60, 600);
        mStage.addActor(mLeftHands);

        mRightHands = new RightHandMahjongs();
        mRightHands.setPosition(1220, 100);
        mStage.addActor(mRightHands);

        mTopHands = new TopHandMahjongs();
        mTopHands.setPosition(300, 660);
        mStage.addActor(mTopHands);

        mMyOuts = new MyOutMahjongs();
        mMyOuts.setBounds(390, 100, 500, 205);
        mStage.addActor(mMyOuts);

        mRightOuts = new RightOutMahjongs();
        mRightOuts.setBounds(1000, 200, 205, 305);
        mStage.addActor(mRightOuts);

        mTopOuts = new TopOutMahjongs();
        mTopOuts.setBounds(390, 420, 500, 200);
        mStage.addActor(mTopOuts);

        mLeftOuts = new LeftOutMahjongs();
        mLeftOuts.setBounds(100, 200, 205, 305);
        mStage.addActor(mLeftOuts);

        mMatch = new Match();
        mStage.addActor(mMatch);

        mOperationButton = new OperationButton();
        mOperationButton.setBounds(0, 100, 1280, 200);
        mOperationButton.setButtonClickListener(mButtonClick);
        mStage.addActor(mOperationButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        mBatch.draw(mBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mBatch.end();

        updateTimeUint();
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 tmp = new Vector2(screenX, screenY);
        mStage.screenToStageCoordinates(tmp);
        Actor actor = mStage.hit(tmp.x, tmp.y, true);
        if (actor instanceof MahjActor) {
            MahjActor mahjActor = (MahjActor) actor;
            if (mahjActor.isCanStandUp()) {
                if (mahjActor.isStandUp()) {
                    mController.handleOutData((mahjActor.getMahjData().getIndex()));
                } else {
                    mMyHands.clearStandUpMahjong();
                    mahjActor.standUp(true);
                }
            } else {
                mMyHands.clearStandUpMahjong();
            }
        } else {
            mMyHands.clearStandUpMahjong();
        }
        return true;
    }

    private void updateMahjong() {
        if (!mIsDealt) {
            return;
        }

        HashMap<Integer, MahjGroupData> playerDatas = mController.getGroupDatas();

        if (playerDatas.isEmpty()) {
            return;
        }

        mMyHands.updateMahjs(0, playerDatas.get(0));
        mLeftHands.updateMahjs(3, playerDatas.get(3));
        mRightHands.updateMahjs(1, playerDatas.get(1));
        mTopHands.updateMahjs(2, playerDatas.get(2));

        if (playerDatas.get(1) != null) {
            if (playerDatas.get(1).getOperateType() != 0) {
                mMatch.setPosition(980, 340);
                mMatch.update(playerDatas.get(1).getOperateType());
                playerDatas.get(1).setOperateType(0);
            }
            mRightOuts.setOutMahjongs(playerDatas.get(1).getOutDatas());
        }
        if (playerDatas.get(2) != null) {
            if (playerDatas.get(2).getOperateType() != 0) {
                mMatch.setPosition(550, 450);
                mMatch.update(playerDatas.get(2).getOperateType());
                playerDatas.get(2).setOperateType(0);
            }
            mTopOuts.setOutMahjongs(playerDatas.get(2).getOutDatas());
        }
        if (playerDatas.get(3) != null) {
            if (playerDatas.get(3).getOperateType() != 0) {
                mMatch.setPosition(200, 340);
                mMatch.update(playerDatas.get(3).getOperateType());
                playerDatas.get(3).setOperateType(0);
            }
            mLeftOuts.setOutMahjongs(playerDatas.get(3).getOutDatas());
        }
        if (playerDatas.get(0) != null) {
            if (playerDatas.get(0).getOperateType() != 0) {
                mMatch.setPosition(550, 150);
                mMatch.update(playerDatas.get(0).getOperateType());
                playerDatas.get(0).setOperateType(0);
            }
            mMyOuts.setOutMahjongs(playerDatas.get(0).getOutDatas());

            int matchType = 0;
            MahjData latestData = playerDatas.get(0).getLatestData();
            if (latestData != null && latestData.getIndex() != 0) {
                matchType = playerDatas.get(0).updateMatchTypeForGetMahj();
                Gdx.app.log("liuhao", "--1-- matchType=" + matchType);
            }
            if (matchType == 0 && playerDatas.get(mController.getCurrentPlayer()) != null) {
                ArrayList<MahjData> outDatas = playerDatas.get(mController.getCurrentPlayer()).getOutDatas();
                if (outDatas != null && outDatas.size() > 0) {
                    matchType = playerDatas.get(0).updateMatchType(new MahjData(outDatas.get(outDatas.size() - 1).getIndex()));
                    Gdx.app.log("liuhao", "--2-- matchType=" + matchType);
                }
            }

            if (matchType > 0 && mMatchType != matchType) {
                mMatchType = matchType;
                mTime.startThinkingTime();
                mOperationButton.update(matchType);
            }
        }
    }

    private void updateTimeUint() {
        if (!mIsDealt) {
            return;
        }

        if (!mTime.isAnimation()) {
            mTime.updateCurrPlayer(mController.getCurrentPlayer());
        }
    }
}
