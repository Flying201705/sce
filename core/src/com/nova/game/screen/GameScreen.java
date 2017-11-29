package com.nova.game.screen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.actor.GameInfo;
import com.nova.game.actor.Player;
import com.nova.game.actor.LeftHandMahjongs;
import com.nova.game.actor.LeftOutMahjongs;
import com.nova.game.actor.Operate;
import com.nova.game.actor.MyHandMahjongs;
import com.nova.game.actor.MyOutMahjongs;
import com.nova.game.actor.MatchButton;
import com.nova.game.actor.RightHandMahjongs;
import com.nova.game.actor.RightOutMahjongs;
import com.nova.game.actor.TimeUnit;
import com.nova.game.BaseGame;
import com.nova.game.actor.TopHandMahjongs;
import com.nova.game.actor.TopOutMahjongs;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.assetmanager.Assets;
import com.nova.game.dialog.GameEndDialog;
import com.nova.game.model.MahjGameController;
import com.nova.game.model.MahjRoomController;
import com.nova.game.model.PlayerInfoController;

import java.util.HashMap;

import nova.common.game.mahjong.data.MahjGroupData;
import nova.common.game.mahjong.util.MahjConstant;
import nova.common.room.data.PlayerInfo;

public class GameScreen extends BaseGameScreen {
    // 游戏信息展示区域
    private GameInfo mGameInfo;
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
    private Operate mMyOperate;
    private Operate mRightOperate;
    private Operate mTopOperate;
    private Operate mLeftOperate;
    private MatchButton mMatchButton;
    private GameEndDialog mEndDialog;
    private boolean mIsDealt = false;
    private int mLatestOutPlayer = -1;

    private MahjGameController mController = MahjGameController.create(getGameType());

    private TimeUnit.TimeUnitListener mTimeListener = new TimeUnit.TimeUnitListener() {
        @Override
        public void onAnimationFinished() {
            mIsDealt = true;
        }

        @Override
        public void onTimeOut() {
            mMatchButton.clear();
        }
    };

    private MyHandMahjongs.handOutDataCallback mCallBack = new MyHandMahjongs.handOutDataCallback() {
        @Override
        public void handleOutData(int index) {
            mController.handleOutData(index);
        }
    };

    private MatchButton.ButtonClickListener mButtonClick = new MatchButton.ButtonClickListener() {
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
        mGameInfo.setPosition(10, 630);
        mStage.addActor(mGameInfo);

        mTime.setTimeUnitListener(mTimeListener);
        mTime.startTime();

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
        mLeftOuts.setBounds(353, 210, 150, 320);
        mStage.addActor(mLeftOuts);

        mMyOperate = new Operate();
        mMyOperate.setPosition(550, 150);
        mStage.addActor(mMyOperate);
        mRightOperate = new Operate();
        mRightOperate.setPosition(980, 340);
        mStage.addActor(mRightOperate);
        mTopOperate = new Operate();
        mTopOperate.setPosition(550, 450);
        mStage.addActor(mTopOperate);
        mLeftOperate = new Operate();
        mLeftOperate.setPosition(200, 340);
        mStage.addActor(mLeftOperate);

        mMatchButton = new MatchButton();
        mMatchButton.setBounds(0, 100, 1280, 200);
        mMatchButton.setButtonClickListener(mButtonClick);
        mStage.addActor(mMatchButton);

        mEndDialog = new GameEndDialog();
        mEndDialog.setVisible(false);
        mEndDialog.setResumeButton(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mController.resumeGame(MahjRoomController.getInstance().getRoomId());
            }
        });
        mStage.addActor(mEndDialog);
    }

    private void initPlayer() {
        mMyPlayer = new Player();
        mMyPlayer.setPosition(20, 20);
        PlayerInfo myPlayerInfo = PlayerInfoController.getInstance().getOwnerInfo();
        mMyPlayer.setPlayerInfo(myPlayerInfo);
//        mMyOperate.setSex(myPlayerInfo.getSex() == 0);
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
            mRightOperate.setSex(rightPlayerInfo.getSex() == 0);
        } else {
            mRightPlayer.setVisible(false);
        }

        if (topPlayerInfo != null) {
            mTopPlayer.setPlayerInfo(topPlayerInfo);
            mTopOperate.setSex(topPlayerInfo.getSex() == 0);
            mTopPlayer.setVisible(true);
        } else {
            mTopPlayer.setVisible(false);
        }

        if (leftPlayerInfo != null) {
            mLeftPlayer.setPlayerInfo(leftPlayerInfo);
            mLeftOperate.setSex(leftPlayerInfo.getSex() == 0);
            mLeftPlayer.setVisible(true);
        } else {
            mLeftPlayer.setVisible(false);
        }
    }

    @Override
    public void render(float delta) {
        // 测试程序开始
        if (mController.getWinner() >= 0) {
            mEndDialog.setResult(getGameResultByWinner(mController.getWinner()));
            mEndDialog.setVisible(true);
        } else {
            mEndDialog.setVisible(false);
        }
        // 测试程序结束
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
            if (mahjActor.isCanOperate() && mahjActor.isCanStandUp()) {
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


        if (myGroupData != null) {
            mMyPlayer.setTingFlag((myGroupData.getOperateType() & MahjConstant.MAHJ_MATCH_TING) == MahjConstant.MAHJ_MATCH_TING);
        }
        if (rightGroupData != null) {
            mRightPlayer.setTingFlag((rightGroupData.getOperateType() & MahjConstant.MAHJ_MATCH_TING) == MahjConstant.MAHJ_MATCH_TING);
        }
        if (topGroupData != null) {
            mTopPlayer.setTingFlag((topGroupData.getOperateType() & MahjConstant.MAHJ_MATCH_TING) == MahjConstant.MAHJ_MATCH_TING);
        }
        if (leftGroupData != null) {
            mLeftPlayer.setTingFlag((leftGroupData.getOperateType() & MahjConstant.MAHJ_MATCH_TING) == MahjConstant.MAHJ_MATCH_TING);
        }

        if (rightGroupData != null) {
            if (rightGroupData.getOperateType() != 0) {
                mRightOperate.update(rightGroupData.getOperateType());
                // rightGroupData.setOperateType(0);
            }
            mRightOuts.setOutMahjongs(rightGroupData.getOutDatas());
        }
        if (topGroupData != null) {
            if (topGroupData.getOperateType() != 0) {
                mTopOperate.update(topGroupData.getOperateType());
                // topGroupData.setOperateType(0);
            }
            mTopOuts.setOutMahjongs(topGroupData.getOutDatas());
        }
        if (leftGroupData != null) {
            if (leftGroupData.getOperateType() != 0) {
                mLeftOperate.update(leftGroupData.getOperateType());
                // leftGroupData.setOperateType(0);
            }
            mLeftOuts.setOutMahjongs(leftGroupData.getOutDatas());
        }
        if (myGroupData != null) {
            if (myGroupData.getOperateType() != 0) {
                mMyOperate.update(myGroupData.getOperateType());
                // myGroupData.setOperateType(0);
            }
            mMyOuts.setOutMahjongs(myGroupData.getOutDatas());
        }

        if (mController.isGameMassageProcessFinish() && mLatestOutPlayer != mController.getLastOutPlayer()) {
            mLatestOutPlayer = mController.getLastOutPlayer();
            updateLatestOutMark();
            playLatestOutSound(playerDatas);
        }

        // 听牌之后不再显示听牌
        if ((myGroupData.getOperateType() & MahjConstant.MAHJ_MATCH_TING) == MahjConstant.MAHJ_MATCH_TING) {
            mMatchButton.update(mController.getMatchType() & 23);
        } else {
            mMatchButton.update(mController.getMatchType());
        }
    }

    private void updateLatestOutMark() {
        switch (mLatestOutPlayer) {
            case 0:
                mMyOuts.setLatestOutMark(true);
                mRightOuts.setLatestOutMark(false);
                mTopOuts.setLatestOutMark(false);
                mLeftOuts.setLatestOutMark(false);
                break;
            case 1:
                mMyOuts.setLatestOutMark(false);
                mRightOuts.setLatestOutMark(true);
                mTopOuts.setLatestOutMark(false);
                mLeftOuts.setLatestOutMark(false);
                break;
            case 2:
                mMyOuts.setLatestOutMark(false);
                mRightOuts.setLatestOutMark(false);
                mTopOuts.setLatestOutMark(true);
                mLeftOuts.setLatestOutMark(false);
                break;
            case 3:
                mMyOuts.setLatestOutMark(false);
                mRightOuts.setLatestOutMark(false);
                mTopOuts.setLatestOutMark(false);
                mLeftOuts.setLatestOutMark(true);
                break;
            default:
                mMyOuts.setLatestOutMark(false);
                mRightOuts.setLatestOutMark(false);
                mTopOuts.setLatestOutMark(false);
                mLeftOuts.setLatestOutMark(false);
                break;
        }
    }

    private void playLatestOutSound(HashMap<Integer, MahjGroupData> playerDatas) {
        MahjGroupData groupData = playerDatas.get(mController.getLastOutPlayer());
        if (groupData == null || groupData.getOutDatas().size() <= 0) {
            return;
        }

        PlayerInfo playerInfo = mController.getPlayerInfos().get(mLatestOutPlayer);
        boolean male = true;
        if (playerInfo != null) {
            male = playerInfo.getSex() > 0;
        }
        Sound sound = Assets.getInstance().getMajongSound(male, groupData.getLastOutData().getIndex());
        if (sound != null) {
            sound.play();
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

    private int getGameResultByWinner(int winner) {
        if (winner > 4) {
            return GameEndDialog.RESULT_DRAW;
        }

        if (winner == mController.getOwnerPlayerIndex()) {
            return GameEndDialog.RESULT_WIN;
        } else {
            return GameEndDialog.RESULT_LOSE;
        }
    }
}
