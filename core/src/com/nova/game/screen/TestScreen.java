package com.nova.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nova.game.BaseGame;
import com.nova.game.BaseScreen;
import com.nova.game.BaseStage;
import com.nova.game.actor.mahj.HorizontalFlatMahjong;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.actor.mahj.OwnerFlatMahjong;
import com.nova.game.actor.mahj.OwnerMahjong;
import com.nova.game.actor.mahj.VerticalFlatMahjong;
import com.nova.game.assetmanager.Assets;

public class TestScreen extends BaseScreen {
    private BaseStage mStage;
    private SpriteBatch mBatch;
    private Assets mAssents;

    private Texture mBg;
    private Texture mBg2;

    private static final int  HORIZONTAL_WIDTH_SMALL = 48;
    private static final int  HORIZONTAL_HEIGHT_SMALL = 30;
    private static final int  VERTICAL_WIDTH_SMALL = 36;
    private static final int  VERTICAL_HEIGHT_SMALL = 42;

    public TestScreen(BaseGame game) {
        super(game);
        mAssents = Assets.getInstance();
        mAssents.load();
    }

    @Override
    public void show() {
        mStage = new BaseStage(this);
        Gdx.input.setInputProcessor(mStage);
        Gdx.input.setCatchBackKey(true);

        mBatch = new SpriteBatch();

        mBg = new Texture("SceneGame/background.jpg");
        mBg2 = new Texture("Actors/TimeBack.png");

        Operator operator = new Operator(mBg2);
        mStage.addActor(operator);

        //自己碰的牌
        for (int i = 0; i < 3; i++) {
            OwnerFlatMahjong ownerFlatMahj = new OwnerFlatMahjong(24);
            ownerFlatMahj.setPosition(200 + i * 54, 10);
            mStage.addActor(ownerFlatMahj);
        }
        // 自己的牌
        for (int i = 0; i < 10; i++) {
            int index = i + 1;
            if (index > 9) {
                index = index - 9 + 10;
            }
            OwnerMahjong ownerMahj = new OwnerMahjong(index);
            ownerMahj.setPosition(400 + i * 73, 10);
            mStage.addActor(ownerMahj);
        }

        // 自己出的牌
        for (int j = 2; j >= 0; j--) {
            int max = 6;
            int offset = 0;
            if (j == 1) {
                max = 8;
                offset = VERTICAL_WIDTH_SMALL;
            } else if (j == 0) {
                max = 7;
                offset = VERTICAL_WIDTH_SMALL;
            }
            for (int i = 0; i < max; i++) {
                VerticalFlatMahjong ownerOutMahj = new VerticalFlatMahjong(j * 10 + i + 1);
                ownerOutMahj.setPosition(500 - offset + i * VERTICAL_WIDTH_SMALL, 150 + j * VERTICAL_HEIGHT_SMALL);
                mStage.addActor(ownerOutMahj);
            }
        }


        // 左边碰的牌
        for (int i = 0; i < 3; i++) {
            HorizontalFlatMahjong leftFlatMahj = new HorizontalFlatMahjong(25);
            leftFlatMahj.setPosition(130, 550 - i * HORIZONTAL_HEIGHT_SMALL);
            mStage.addActor(leftFlatMahj);
        }

        // 左边的牌
        for (int i = 0; i < 10; i++) {
            Mahjong mj = new Mahjong(new Texture("SceneGame/mahj_left.png"));
            mj.setPosition(150, 430 - i * 29f);
            mStage.addActor(mj);
        }

        // 左边出的牌
        for (int j = 2; j >= 0; j--) {
            int max = 6;
            int offset = 0;
            if (j == 1) {
                max = 8;
                offset = HORIZONTAL_HEIGHT_SMALL;
            } else if (j == 2) {
                max = 7;
                offset = HORIZONTAL_HEIGHT_SMALL;
            }
            for (int i = 0; i < max; i++) {
                HorizontalFlatMahjong leftOutMahj = new HorizontalFlatMahjong(j * 10 + i + 1);
                leftOutMahj.setPosition(420 - j * HORIZONTAL_WIDTH_SMALL, 430 + offset - i * HORIZONTAL_HEIGHT_SMALL);
                mStage.addActor(leftOutMahj);
            }
        }

        // 右边碰的牌
        for (int i = 0; i < 3; i++) {
            HorizontalFlatMahjong rightFlatMahj = new HorizontalFlatMahjong(26, Mahjong.MAHJ_DIRECTION_RIGHT);
            rightFlatMahj.setPosition(1050, 280 - i * HORIZONTAL_HEIGHT_SMALL);
            mStage.addActor(rightFlatMahj);
        }
        // 右边的牌
        for (int i = 0; i < 10; i++) {
            Mahjong mj = new Mahjong(new Texture("SceneGame/mahj_right.png"));
            mj.setPosition(1050, 600 - i * 29f);
            mStage.addActor(mj);
        }

        // 右边出的牌
        for (int j = 2; j >= 0; j--) {
            int max = 6;
            int offset = 0;
            if (j == 1) {
                max = 8;
                offset = HORIZONTAL_HEIGHT_SMALL;
            } else if (j == 2) {
                max = 7;
                offset = HORIZONTAL_HEIGHT_SMALL;
            }
            for (int i = 0; i < max; i++) {
                HorizontalFlatMahjong rightOutMahj = new HorizontalFlatMahjong(j * 10 + i + 1, Mahjong.MAHJ_DIRECTION_RIGHT);
                rightOutMahj.setPosition(760 + j * HORIZONTAL_WIDTH_SMALL, 430 + offset - i * HORIZONTAL_HEIGHT_SMALL);
                mStage.addActor(rightOutMahj);
            }
        }

        // 上面碰的牌
        for (int i = 0; i < 3; i++) {
            VerticalFlatMahjong upFlatMahj = new VerticalFlatMahjong(33);
            upFlatMahj.setPosition(850 + i * VERTICAL_WIDTH_SMALL, 650);
            mStage.addActor(upFlatMahj);
        }
        // 上边的牌
        for (int i = 0; i < 14; i++) {
            Mahjong mj = new Mahjong(new Texture("SceneGame/mahj_top.png"));
            mj.setPosition(300 + i * 38f, 650);
            mStage.addActor(mj);
        }

        // 上边出的牌
        for (int j = 2; j >= 0; j--) {
            int max = 6;
            int offset = 0;
            if (j == 1) {
                max = 8;
                offset = VERTICAL_WIDTH_SMALL;
            } else if (j == 2) {
                max = 7;
                offset = VERTICAL_WIDTH_SMALL;
            }
            for (int i = 0; i < max; i++) {
                VerticalFlatMahjong upOutMahj = new VerticalFlatMahjong(j * 10 + i + 1);
                upOutMahj.setPosition(500 - offset + i * VERTICAL_WIDTH_SMALL, 460 + j * VERTICAL_HEIGHT_SMALL);
                mStage.addActor(upOutMahj);
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mBatch.begin();
        mBatch.draw(mBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mBatch.end();

        mStage.act();
        mStage.draw();
    }

    @Override
    public void dispose() {
        mBg.dispose();

        mBatch.dispose();
        mStage.dispose();
    }

    private class Operator extends Actor {
        private Sprite mOperatorBg;
        private Sprite mOperatorBg2;
        private Sprite mOperatorBg3;

        public Operator(Texture bg) {
            mOperatorBg = new Sprite(bg);
            mOperatorBg2 = new Sprite(new Texture("Actors/TimeBack2.png"));
            mOperatorBg3 = new Sprite(new Texture("Actors/TimePoint0.png"));
        }
        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            mOperatorBg.setPosition(520, 300);
            mOperatorBg.draw(batch);
            mOperatorBg2.setPosition(520, 300);
            mOperatorBg2.draw(batch);
            mOperatorBg3.setPosition(520, 300);
            mOperatorBg3.draw(batch);
        }

    }
}
