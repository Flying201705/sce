package com.nova.game.screen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.actor.GameInfo;
import com.nova.game.actor.Player;
import com.nova.game.actor.LeftHandMahjongs;
import com.nova.game.actor.LeftOutMahjongs;
import com.nova.game.actor.Match;
import com.nova.game.actor.MyHandMahjongs;
import com.nova.game.actor.MyOutMahjongs;
import com.nova.game.actor.OperationButton;
import com.nova.game.actor.RightHandMahjongs;
import com.nova.game.actor.RightOutMahjongs;
import com.nova.game.actor.TimeUnit;
import com.nova.game.BaseGame;
import com.nova.game.actor.TopHandMahjongs;
import com.nova.game.actor.TopOutMahjongs;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.model.MahjGameController;
import com.nova.game.model.PlayerInfoController;
import com.nova.game.widget.SceButton;

import java.util.HashMap;

import nova.common.game.mahjong.data.MahjGroupData;
import nova.common.room.data.PlayerInfo;

public class GameScreen extends BaseGameScreen {
    // 游戏信息展示区域
    private GameInfo mGameInfo;
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
        super.show();

        // 游戏信息展示区域
        mGameInfo = new GameInfo();
        mGameInfo.setPosition(0, 600);
        mStage.addActor(mGameInfo);

        mTime.setTimeUnitListener(mTimeListener);
        mTime.startTime();

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
        mMyHands.setPosition(80, 6);
        mMyHands.sethandOutDataCallback(mCallBack);
        mStage.addActor(mMyHands);

        mLeftHands = new LeftHandMahjongs();
        mLeftHands.setBounds(200, 180, 50, 450);
        mStage.addActor(mLeftHands);

        mRightHands = new RightHandMahjongs();
        mRightHands.setBounds(1060, 180, 50, 450);
        mStage.addActor(mRightHands);

        mTopHands = new TopHandMahjongs();
        mTopHands.setPosition(470, 650);
        mStage.addActor(mTopHands);

        mMyOuts = new MyOutMahjongs();
        mMyOuts.setBounds(450, 175, 355, 138);
        mStage.addActor(mMyOuts);

        mRightOuts = new RightOutMahjongs();
        mRightOuts.setBounds(780, 240, 150, 305);
        mStage.addActor(mRightOuts);

        mTopOuts = new TopOutMahjongs();
        mTopOuts.setBounds(430, 500, 340, 171);
        mStage.addActor(mTopOuts);

        mLeftOuts = new LeftOutMahjongs();
        mLeftOuts.setBounds(343, 210, 150, 320);
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
        mMyPlayer.setPosition(20, 20);
        PlayerInfo myPlayerInfo = PlayerInfoController.getInstance().getOwnerInfo();
        mMyPlayer.setPlayerInfo(myPlayerInfo);
        mStage.addActor(mMyPlayer);

        mRightPlayer = new Player(Player.VERTICAL);
        mRightPlayer.setPosition(1130, 360);
        mRightPlayer.setVisible(false);
        mStage.addActor(mRightPlayer);

        mTopPlayer = new Player();
        mTopPlayer.setPosition(257, 630);
        mTopPlayer.setVisible(false);
        mStage.addActor(mTopPlayer);

        mLeftPlayer = new Player(Player.VERTICAL);
        mLeftPlayer.setPosition(56, 360);
        mLeftPlayer.setVisible(false);
        mStage.addActor(mLeftPlayer);
    }

    private void updatePlayer() {
        HashMap<Integer, PlayerInfo> players = mController.getPlayerInfos();
        // PlayerInfo myPlayerInfo = players.get(getPlayerIdByPosition(0));
        PlayerInfo rightPlayerInfo = players.get(getPlayerIdByPosition(1));
        PlayerInfo topPlayerInfo = players.get(getPlayerIdByPosition(2));
        PlayerInfo leftPlayerInfo = players.get(getPlayerIdByPosition(3));
        /*if (myPlayerInfo != null) {
            mMyPlayer.setPlayerInfo(myPlayerInfo);
            mMyPlayer.setVisible(true);
        }*/

        if (rightPlayerInfo != null) {
            mRightPlayer.setPlayerInfo(rightPlayerInfo);
            mRightPlayer.setVisible(true);
        } else {
            mRightPlayer.setVisible(false);
        }

        if (topPlayerInfo != null) {
            mTopPlayer.setPlayerInfo(topPlayerInfo);
            mTopPlayer.setVisible(true);
        } else {
            mTopPlayer.setVisible(false);
        }

        if (leftPlayerInfo != null) {
            mLeftPlayer.setPlayerInfo(leftPlayerInfo);
            mLeftPlayer.setVisible(true);
        } else {
            mLeftPlayer.setVisible(false);
        }
    }

    @Override
    public void render(float delta) {
        updateGameInfo();
        updateTimeUint();
        updateMahjong();
        updatePlayer();

        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 tmp = new Vector2(screenX, screenY);
        mStage.screenToStageCoordinates(tmp);
        Actor actor = mStage.hit(tmp.x, tmp.y, true);
        if (actor instanceof Mahjong) {
            Mahjong mahjActor = (Mahjong) actor;
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

    @Override
    public void handleQuitEvent() {
        super.handleQuitEvent();
        mController.stopGame();
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
    }

    private void updateGameInfo() {
        mGameInfo.updateRemainSize(mController.getRemainingSize());
        mGameInfo.updateGodIndex(mController.getGodData());
    }

    private int getPlayerIdByPosition(int position) {
        int playerId = position + mController.getOwnerPlayerIndex();
        if (playerId >= 4) {
            playerId = playerId - 4;
        }
        return playerId;
    }
}
