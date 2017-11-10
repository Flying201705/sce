package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nova.game.BaseGame;
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

public class PrivateScreen extends BaseGameScreen {
    private Player mMyPlayer;
    private Player mRightPlayer;
    private Player mTopPlayer;
    private Player mLeftPlayer;
    private Label mRoomString;
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
        super.show();
        MahjRoomController.getInstance().resetRoomResult();
        mFont = Assets.getInstance().getFont();

        mRoomString = new Label("房间号：- ", new Label.LabelStyle(mFont, Color.LIGHT_GRAY));
        mRoomString.setFontScale(1.2f);
        mRoomString.setPosition((Constants.WORLD_WIDTH - mRoomString.getWidth()) / 2, 250);
        mStage.addActor(mRoomString);

        SceButton wxInvite = new SceButton("ScenePrivate/bt_weixin_invite.png");
        wxInvite.setPosition(Constants.WORLD_WIDTH / 2 - wxInvite.getWidth() - 50, 100);
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

        SceButton startGame = new SceButton("ScenePrivate/bt_start_game.png");
        startGame.setPosition(Constants.WORLD_WIDTH / 2 + 50, 100);
        startGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.getTarget().setVisible(false);
                addPlayers();
                // 临时调试，进入游戏
                mGame.setScreen(new NetMahjGameScreen(mGame, mRoomController.getRoomId()));
            }
        });
        mStage.addActor(startGame);

        initPlayer();
    }

    @Override
    public void render(float delta) {
        updateRoomId();
        updatePlayer();
        // addDebugPlayer();
        updateScreen();
        super.render(delta);
    }

    @Override
    public void dispose() {
        if (mRoomController.getRoomId() >= 0) {
            new GameRequestDispatcher().leaveRoom(mRoomController.getRoomId());
        }
    }

    @Override
    public String getQuitTitle() {
        return "离开房间";
    }

    @Override
    public String getQuitPrimary() {
        return "狠心离开";
    }

    @Override
    public String getQuitSecondary() {
        return "再等一会";
    }

    @Override
    public void handleQuitEvent() {
        super.handleQuitEvent();
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

    private void updateRoomId() {
        int roomId = mRoomController.getRoomId();
        mRoomString.setText("房间号：" + roomId);
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

    private void updatePlayer() {
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
