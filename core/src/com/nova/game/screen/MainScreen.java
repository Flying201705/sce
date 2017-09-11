package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.Constants;
import com.nova.game.assetmanager.Assets;
import com.nova.game.dialog.JoinRoomDialog;
import com.nova.game.handler.GameRequestDispatcher;
import com.nova.game.model.MahjRoomController;
import com.nova.game.utils.Log;
import com.nova.game.utils.WXInfo;
import com.nova.game.widget.SceButton;

import nova.common.game.mahjong.util.MahjGameCommand;

public class MainScreen extends BaseScreen {
    private static final String TAG = "MainScreen";
    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Texture mBg;
    private WXInfo mWXInfo;

    public MainScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void show() {
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        mWXInfo = WXInfo.getInstance();

        mBatch = new SpriteBatch();
        mBg = new Texture("SceneMain/background.jpg");

        Image image = new Image(new Texture("SceneMain/meinv.png"));
        image.setPosition(50, 0);
        image.setScale(1.2f);
        mStage.addActor(image);

        initTop();
        initMiddle();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mBatch.begin();
        mBatch.draw(mBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mBatch.end();

        mStage.act();
        mStage.draw();
        updateScreen();
    }

    @Override
    public void dispose() {
        mBatch.dispose();
        mBg.dispose();
        mStage.dispose();
    }

    private void initTop() {
        Image bg = new Image(new NinePatchDrawable(new NinePatch(new Texture("SceneMain/top_bg.png"), 30, 30, 0, 30)));
        bg.setBounds(0, Constants.WORLD_HEIGHT - 100, Constants.WORLD_WIDTH, 100);
        mStage.addActor(bg);

        if (mWXInfo.getHead() != null) {
            Image head = new Image(mWXInfo.getHead());
            head.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO: 17-9-11
                }
            });
            head.setPosition(10, 640);
            mStage.addActor(head);
        }

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.characters = mWXInfo.getNickName();
        BitmapFont font = Assets.getInstance().mGeneratror.generateFont(parameter);
        Label name = new Label(mWXInfo.getNickName(), new Label.LabelStyle(font, Color.WHITE));
        name.setPosition(180, 680);
        mStage.addActor(name);

        Button quitButton = new SceButton("SceneMain/bt_quit.png");
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        quitButton.setPosition(1200, 640);
        mStage.addActor(quitButton);

        Button settingButton = new SceButton("SceneMain/bt_setting.png");
        settingButton.setPosition(1100, 640);
        mStage.addActor(settingButton);

        Button newsButton = new SceButton("SceneMain/bt_news.png");
        newsButton.setPosition(1000, 640);
        mStage.addActor(newsButton);

        Button recordButton = new SceButton("SceneMain/bt_record.png");
        recordButton.setPosition(900, 640);
        mStage.addActor(recordButton);
    }

    private void initMiddle() {
        Button startButton = new SceButton("SceneMain/bt_start.png");
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // mGame.setScreen(new GameScreen(mGame));
                mGame.setScreen(new NetMahjGameScreen(mGame));
            }
        });
        startButton.setPosition(900, 450);
        mStage.addActor(startButton);

        Button createButton = new SceButton("SceneMain/bt_create.png");
        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mGame.setScreen(new PrivateScreen(mGame, true));
            }
        });
        createButton.setPosition(900, 300);
        mStage.addActor(createButton);

        Button addButton = new SceButton("SceneMain/bt_add.png");
        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final JoinRoomDialog dialog = new JoinRoomDialog("");
                dialog.setJoinClickListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        new GameRequestDispatcher().joinRoom(dialog.getInput());
                        dialog.remove();
                    }
                });
                mStage.addActor(dialog);
            }
        });
        addButton.setPosition(900, 150);
        mStage.addActor(addButton);
    }

    private void updateScreen() {
        int result = MahjRoomController.getInstance().getRoomResult();
        int roomId = MahjRoomController.getInstance().getRoomId();
        if (result == MahjGameCommand.RoomState.ROOM_STATE_JOIN && roomId >= 0) {
            mGame.setScreen(new PrivateScreen(mGame));
            MahjRoomController.getInstance().resetRoomResult();
        }
    }
}
