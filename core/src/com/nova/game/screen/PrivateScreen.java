package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.Constants;
import com.nova.game.actor.Player;
import com.nova.game.assetmanager.Assets;
import com.nova.game.handler.GameRequestDispatcher;
import com.nova.game.model.MahjRoomController;
import com.nova.game.model.PlayerInfoController;
import com.nova.game.widget.SceButton;
import java.util.HashMap;

import nova.common.game.mahjong.util.MahjGameCommand;
import nova.common.room.data.PlayerInfo;

public class PrivateScreen extends BaseScreen {
    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Texture mBg;
    private Player mMyPlayer;
    private Player mRightPlayer;
    private Player mTopPlayer;
    private Player mLeftPlayer;
    private Label mRoomSting;
    private boolean mWaitFriend = false;

    private BitmapFont mFont;

    private MahjRoomController mRoomController = MahjRoomController.getInstance();

    public PrivateScreen(BaseGame game) {
        this(game, false);
    }

    public PrivateScreen(BaseGame game, boolean isCreateRoom) {
        super(game);
        if (isCreateRoom) {
            new GameRequestDispatcher().createRoom();
        }
    }

    @Override
    public void show() {
        MahjRoomController.getInstance().resetRoomResult();
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        mStage.setDebugAll(false);

        mBatch = new SpriteBatch();
        mBg = new Texture("SceneGame/background.jpg");
        mFont = Assets.getInstance().getFont();

        mRoomSting = new Label("房间号：- ", new Label.LabelStyle(mFont, Color.DARK_GRAY));
        mRoomSting.setPosition((Constants.WORLD_WIDTH - mRoomSting.getWidth()) / 2, 200);
        mStage.addActor(mRoomSting);

        SceButton wxInvite = new SceButton("ScenePrivate/bt_weixin_invite.png");
        wxInvite.setPosition((Constants.WORLD_WIDTH - wxInvite.getWidth()) / 2, 100);
        wxInvite.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.getTarget().setVisible(false);
                addPlayers();
                // 临时调试，进入游戏
                mGame.setScreen(new NetMahjGameScreen(mGame, mRoomController.getRoomId()));
            }
        });
        mStage.addActor(wxInvite);

        initPlayer();
    }

    private void initPlayer() {
        mMyPlayer = new Player();
        mMyPlayer.setPosition(15, 100);
        mMyPlayer.setPlayerInfo(PlayerInfoController.getInstance().getOwnerInfo());
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        mBatch.draw(mBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mBatch.end();

        updateRoomId();
        waitFriend();
        // addDebugPlayer();

        mStage.act();
        mStage.draw();
        updateScreen();
    }

    @Override
    public void dispose() {
        mBatch.dispose();
        mBg.dispose();
        mStage.dispose();
        if (mRoomController.getRoomId() >= 0) {
            new GameRequestDispatcher().leaveRoom(mRoomController.getRoomId());
        }
    }

    public void updateRoomId() {
        int roomId = mRoomController.getRoomId();
        mRoomSting.setText("房间号：" + roomId);
    }

    private int getPlayerIdByPosition(int position) {
        int playerId = position + mRoomController.getOwnerPlayerIndex();
        if (playerId >= 4) {
            playerId = playerId - 4;
        }
        return playerId;
    }

    private void addPlayers() {
        mWaitFriend = true;
    }

    private void waitFriend() {
        // if (!mWaitFriend) {
        //     return;
        // }

        HashMap<Integer, PlayerInfo> players = mRoomController.getPlayerInfos();
        PlayerInfo rightPlayerInfo = players.get(getPlayerIdByPosition(1));
        PlayerInfo topPlayerInfo = players.get(getPlayerIdByPosition(2));
        PlayerInfo leftPlayerInfo = players.get(getPlayerIdByPosition(3));
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

        if (mRightPlayer.getPlayerInfo() != null && mLeftPlayer.getPlayerInfo() != null && mTopPlayer.getPlayerInfo() != null) {
            mWaitFriend = false;
        }
    }

    private float time = 0;
    private void addDebugPlayer() {
        if (!mWaitFriend) {
            return;
        }

        time += Gdx.graphics.getDeltaTime();

        if (time >= 1f) {
            time = 0;
            if (mRightPlayer.getPlayerInfo() == null) {
                PlayerInfo playerInfo = new PlayerInfo(1, "关羽", null, 0);
                mRightPlayer.setPlayerInfo(playerInfo);
                mRightPlayer.setVisible(true);
            } else if (mTopPlayer.getPlayerInfo() == null) {
                PlayerInfo playerInfo = new PlayerInfo(2, "张飞", null, 0);
                mTopPlayer.setPlayerInfo(playerInfo);
                mTopPlayer.setVisible(true);
            } else if (mLeftPlayer.getPlayerInfo() == null) {
                PlayerInfo playerInfo = new PlayerInfo(2, "小乔", null, 1);
                mLeftPlayer.setPlayerInfo(playerInfo);
                mLeftPlayer.setVisible(true);
            }
        }
    }

    private void updateScreen() {
        int result = MahjRoomController.getInstance().getRoomResult();
        if (result == MahjGameCommand.RoomState.ROOM_STATE_GAME_START) {
            mGame.setScreen(new NetMahjGameScreen(mGame));
            MahjRoomController.getInstance().resetRoomResult();
        }
    }
}
