package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.actor.Player;
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
import com.nova.game.model.MahjGameController;
import com.nova.game.widget.SceButton;

import java.util.HashMap;

import nova.common.game.mahjong.data.MahjGroupData;
import nova.common.room.data.PlayerInfo;

public class GameScreen extends BaseScreen {
    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Texture mBg;
    private TimeUnit mTime;
    private SceButton mStartBt;
    private Player mMyPlayer;
    private Player mRightPlayer;
    private Player mTopPlayer;
    private Player mLeftPlayer;
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

    private MahjGameController mController = MahjGameController.create(getGameType());

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

    protected String getGameType() {
        return "local";
    }

    public GameScreen(BaseGame game) {
        this(game, -1);
    }

    public GameScreen(BaseGame game, int roomId) {
        super(game);
        mController.startGame(roomId);
    }

    @Override
    public void show() {
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        mStage.setDebugAll(false);

        mBatch = new SpriteBatch();
        mBg = new Texture("SceneGame/background.jpg");

        mTime = new TimeUnit();
        mTime.setPosition(570, 295);
        mTime.setTimeUnitListener(mTimeListener);
        mTime.startTime();
        mStage.addActor(mTime);

        mStartBt = new SceButton("SceneGame/bt_start.png");
        mStartBt.setPosition(540, 100);
        mStartBt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mTime.startTime();
            }
        });
        // mStage.addActor(mStartBt);

        initPlayer();

        mMyHands = new MyHandMahjongs();
        mMyHands.setPosition(50, 6);
        mMyHands.sethandOutDataCallback(mCallBack);
        mStage.addActor(mMyHands);

        mLeftHands = new LeftHandMahjongs();
        mLeftHands.setPosition(150, 600);
        mStage.addActor(mLeftHands);

        mRightHands = new RightHandMahjongs();
        mRightHands.setPosition(1120, 100);
        mStage.addActor(mRightHands);

        mTopHands = new TopHandMahjongs();
        mTopHands.setPosition(300, 660);
        mStage.addActor(mTopHands);

        mMyOuts = new MyOutMahjongs();
        mMyOuts.setBounds(390, 100, 500, 205);
        mStage.addActor(mMyOuts);

        mRightOuts = new RightOutMahjongs();
        mRightOuts.setBounds(900, 200, 205, 305);
        mStage.addActor(mRightOuts);

        mTopOuts = new TopOutMahjongs();
        mTopOuts.setBounds(390, 420, 500, 200);
        mStage.addActor(mTopOuts);

        mLeftOuts = new LeftOutMahjongs();
        mLeftOuts.setBounds(180, 200, 205, 305);
        mStage.addActor(mLeftOuts);

        mMatch = new Match();
        mStage.addActor(mMatch);

        mOperationButton = new OperationButton();
        mOperationButton.setBounds(0, 100, 1280, 200);
        mOperationButton.setButtonClickListener(mButtonClick);
        mStage.addActor(mOperationButton);
    }

    private void initPlayer() {
        mMyPlayer = new Player();
        mMyPlayer.setPosition(15, 100);
        mStage.addActor(mMyPlayer);

        mRightPlayer = new Player(Player.VERTICAL);
        mRightPlayer.setPosition(1170, 280);
        mRightPlayer.setVisible(false);
        mStage.addActor(mRightPlayer);

        mTopPlayer = new Player();
        mTopPlayer.setPosition(100, 620);
        mTopPlayer.setVisible(false);
        mStage.addActor(mTopPlayer);

        mLeftPlayer = new Player(Player.VERTICAL);
        mLeftPlayer.setPosition(15, 280);
        mLeftPlayer.setVisible(false);
        mStage.addActor(mLeftPlayer);
    }

    private void updatePlayer() {
        HashMap<Integer, PlayerInfo> players = mController.getPlayerInfos();
        PlayerInfo myPlayerInfo = players.get(getPlayerIdByPosition(0));
        PlayerInfo rightPlayerInfo = players.get(getPlayerIdByPosition(1));
        PlayerInfo topPlayerInfo = players.get(getPlayerIdByPosition(2));
        PlayerInfo leftPlayerInfo = players.get(getPlayerIdByPosition(3));
        if (myPlayerInfo != null) {
            mMyPlayer.setPlayerInfo(myPlayerInfo);
            mMyPlayer.setVisible(true);
        }

        if (rightPlayerInfo != null) {
            mRightPlayer.setPlayerInfo(rightPlayerInfo);
            mRightPlayer.setVisible(true);
        }

        if (topPlayerInfo != null) {
            mTopPlayer.setPlayerInfo(topPlayerInfo);
            mTopPlayer.setVisible(true);
        }

        if (leftPlayerInfo != null) {
            mLeftPlayer.setPlayerInfo(leftPlayerInfo);
            mLeftPlayer.setVisible(true);
        }
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
        updatePlayer();

        mStage.act();
        mStage.draw();
    }

    @Override
    public void dispose() {
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

        MahjGroupData myGroupData = playerDatas.get(getPlayerIdByPosition(0));
        MahjGroupData rightGroupData = playerDatas.get(getPlayerIdByPosition(1));
        MahjGroupData topGroupData = playerDatas.get(getPlayerIdByPosition(2));
        MahjGroupData leftGroupData = playerDatas.get(getPlayerIdByPosition(3));

        mMyHands.updateMahjs(0, myGroupData);
        mRightHands.updateMahjs(1, rightGroupData);
        mTopHands.updateMahjs(2, topGroupData);
        mLeftHands.updateMahjs(3, leftGroupData);

        if (rightGroupData != null) {
            if (rightGroupData.getOperateType() != 0) {
                mMatch.setPosition(980, 340);
                mMatch.update(rightGroupData.getOperateType());
                rightGroupData.setOperateType(0);
            }
            mRightOuts.setOutMahjongs(rightGroupData.getOutDatas());
        }
        if (topGroupData != null) {
            if (topGroupData.getOperateType() != 0) {
                mMatch.setPosition(550, 450);
                mMatch.update(topGroupData.getOperateType());
                topGroupData.setOperateType(0);
            }
            mTopOuts.setOutMahjongs(topGroupData.getOutDatas());
        }
        if (leftGroupData != null) {
            if (leftGroupData.getOperateType() != 0) {
                mMatch.setPosition(200, 340);
                mMatch.update(leftGroupData.getOperateType());
                leftGroupData.setOperateType(0);
            }
            mLeftOuts.setOutMahjongs(leftGroupData.getOutDatas());
        }
        if (myGroupData != null) {
            if (myGroupData.getOperateType() != 0) {
                mMatch.setPosition(550, 150);
                mMatch.update(myGroupData.getOperateType());
                myGroupData.setOperateType(0);
            }
            mMyOuts.setOutMahjongs(myGroupData.getOutDatas());
        }

        mOperationButton.update(mController.getMatchType());
    }

    private void updateTimeUint() {
        if (!mIsDealt) {
            return;
        }

        if (!mTime.isAnimation()) {
            mTime.updateCurrPlayer(mController.getCurrentPlayer());
        }

        // 临时调试
        mTime.updateRemainSize(mController.getRemainingSize());
        mTime.updateGodIndex(mController.getGodData());
    }

    private int getPlayerIdByPosition(int position) {
        int playerId = position + mController.getOwnerPlayerIndex();
        if (playerId >= 4) {
            playerId = playerId - 4;
        }
        return playerId;
    }
}
