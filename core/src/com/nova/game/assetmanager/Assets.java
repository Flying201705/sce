package com.nova.game.assetmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.nova.game.font.LazyBitmapFont;

import java.util.HashMap;
import java.util.Map;

public class Assets {
    private static Assets mInstance;
    private AssetManager mAssetManager;
    private LazyBitmapFont mFont;

    // game screen resources
    private TextureRegion[][] mBigMahjTextureRegions;
    private TextureRegion[][] mMiddleMahjTextureRegions;
    private TextureRegion[][] mSmallMahjTextureRegions;
    public TextureRegion[][] mMahjRemainNum;
    public Texture mBaseGameScreenBackground;
    public Texture[] mDefaultHead;
    public Texture[] mHeadTingFlags;
    public TextureRegion[][] mHeadGoldNums;
    public Texture mPlayerInfoBackground;
    public Texture mGameInfoBackground;
    public Texture mInfoMahjBackground;
    public Texture mCoverMahjBackground;
    public Texture mOwnerDefaultMahjBackground;
    public Texture mOwnerFlatMahjBackground;
    public Texture mVerticalFlatMahjBackground;
    public Texture mHorizontalFlatMahjBackground;
    public Texture mTopDefaultMahjBackground;
    public Texture mLeftDefaultMahjBackground;
    public Texture mRightDefaultMahjBackground;

    private Music mBGM;
    private Map<Integer, Sound> mSound = new HashMap<Integer, Sound>();
    private Map<Integer, Sound> mGSound = new HashMap<Integer, Sound>();

    public static Assets getInstance() {
        if (mInstance == null) {
            mInstance = new Assets();
        }
        return mInstance;
    }

    public void load() {
        if (mAssetManager == null) {
            mAssetManager = new AssetManager();
        }

        loadFont();
        loadMusic();
        updateGameScreenResources();
    }

    private void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/font.ttf"));
        mFont = new LazyBitmapFont(generator, 30);
    }

    private void loadMusic() {
        mBGM = Gdx.audio.newMusic(Gdx.files.internal("raw/backMusic.mp3"));
        // 0万1筒2条3东4中5GOD
        mSound.put(1, Gdx.audio.newSound(Gdx.files.internal("raw/1wan.mp3")));
        mSound.put(2, Gdx.audio.newSound(Gdx.files.internal("raw/2wan.mp3")));
        mSound.put(3, Gdx.audio.newSound(Gdx.files.internal("raw/3wan.mp3")));
        mSound.put(4, Gdx.audio.newSound(Gdx.files.internal("raw/4wan.mp3")));
        mSound.put(5, Gdx.audio.newSound(Gdx.files.internal("raw/5wan.mp3")));
        mSound.put(6, Gdx.audio.newSound(Gdx.files.internal("raw/6wan.mp3")));
        mSound.put(7, Gdx.audio.newSound(Gdx.files.internal("raw/7wan.mp3")));
        mSound.put(8, Gdx.audio.newSound(Gdx.files.internal("raw/8wan.mp3")));
        mSound.put(9, Gdx.audio.newSound(Gdx.files.internal("raw/9wan.mp3")));

        mSound.put(11, Gdx.audio.newSound(Gdx.files.internal("raw/1tong.mp3")));
        mSound.put(12, Gdx.audio.newSound(Gdx.files.internal("raw/2tong.mp3")));
        mSound.put(13, Gdx.audio.newSound(Gdx.files.internal("raw/3tong.mp3")));
        mSound.put(14, Gdx.audio.newSound(Gdx.files.internal("raw/4tong.mp3")));
        mSound.put(15, Gdx.audio.newSound(Gdx.files.internal("raw/5tong.mp3")));
        mSound.put(16, Gdx.audio.newSound(Gdx.files.internal("raw/6tong.mp3")));
        mSound.put(17, Gdx.audio.newSound(Gdx.files.internal("raw/7tong.mp3")));
        mSound.put(18, Gdx.audio.newSound(Gdx.files.internal("raw/8tong.mp3")));
        mSound.put(19, Gdx.audio.newSound(Gdx.files.internal("raw/9tong.mp3")));

        mSound.put(21, Gdx.audio.newSound(Gdx.files.internal("raw/1tiao.mp3")));
        mSound.put(22, Gdx.audio.newSound(Gdx.files.internal("raw/2tiao.mp3")));
        mSound.put(23, Gdx.audio.newSound(Gdx.files.internal("raw/3tiao.mp3")));
        mSound.put(24, Gdx.audio.newSound(Gdx.files.internal("raw/4tiao.mp3")));
        mSound.put(25, Gdx.audio.newSound(Gdx.files.internal("raw/5tiao.mp3")));
        mSound.put(26, Gdx.audio.newSound(Gdx.files.internal("raw/6tiao.mp3")));
        mSound.put(27, Gdx.audio.newSound(Gdx.files.internal("raw/7tiao.mp3")));
        mSound.put(28, Gdx.audio.newSound(Gdx.files.internal("raw/8tiao.mp3")));
        mSound.put(29, Gdx.audio.newSound(Gdx.files.internal("raw/9tiao.mp3")));

        mGSound.put(1, Gdx.audio.newSound(Gdx.files.internal("raw/g_1wan.mp3")));
        mGSound.put(2, Gdx.audio.newSound(Gdx.files.internal("raw/g_2wan.mp3")));
        mGSound.put(3, Gdx.audio.newSound(Gdx.files.internal("raw/g_3wan.mp3")));
        mGSound.put(4, Gdx.audio.newSound(Gdx.files.internal("raw/g_4wan.mp3")));
        mGSound.put(5, Gdx.audio.newSound(Gdx.files.internal("raw/g_5wan.mp3")));
        mGSound.put(6, Gdx.audio.newSound(Gdx.files.internal("raw/g_6wan.mp3")));
        mGSound.put(7, Gdx.audio.newSound(Gdx.files.internal("raw/g_7wan.mp3")));
        mGSound.put(8, Gdx.audio.newSound(Gdx.files.internal("raw/g_8wan.mp3")));
        mGSound.put(9, Gdx.audio.newSound(Gdx.files.internal("raw/g_9wan.mp3")));

        mGSound.put(11, Gdx.audio.newSound(Gdx.files.internal("raw/g_1tong.mp3")));
        mGSound.put(12, Gdx.audio.newSound(Gdx.files.internal("raw/g_2tong.mp3")));
        mGSound.put(13, Gdx.audio.newSound(Gdx.files.internal("raw/g_3tong.mp3")));
        mGSound.put(14, Gdx.audio.newSound(Gdx.files.internal("raw/g_4tong.mp3")));
        mGSound.put(15, Gdx.audio.newSound(Gdx.files.internal("raw/g_5tong.mp3")));
        mGSound.put(16, Gdx.audio.newSound(Gdx.files.internal("raw/g_6tong.mp3")));
        mGSound.put(17, Gdx.audio.newSound(Gdx.files.internal("raw/g_7tong.mp3")));
        mGSound.put(18, Gdx.audio.newSound(Gdx.files.internal("raw/g_8tong.mp3")));
        mGSound.put(19, Gdx.audio.newSound(Gdx.files.internal("raw/g_9tong.mp3")));

        mGSound.put(21, Gdx.audio.newSound(Gdx.files.internal("raw/g_1tiao.mp3")));
        mGSound.put(22, Gdx.audio.newSound(Gdx.files.internal("raw/g_2tiao.mp3")));
        mGSound.put(23, Gdx.audio.newSound(Gdx.files.internal("raw/g_3tiao.mp3")));
        mGSound.put(24, Gdx.audio.newSound(Gdx.files.internal("raw/g_4tiao.mp3")));
        mGSound.put(25, Gdx.audio.newSound(Gdx.files.internal("raw/g_5tiao.mp3")));
        mGSound.put(26, Gdx.audio.newSound(Gdx.files.internal("raw/g_6tiao.mp3")));
        mGSound.put(27, Gdx.audio.newSound(Gdx.files.internal("raw/g_7tiao.mp3")));
        mGSound.put(28, Gdx.audio.newSound(Gdx.files.internal("raw/g_8tiao.mp3")));
        mGSound.put(29, Gdx.audio.newSound(Gdx.files.internal("raw/g_9tiao.mp3")));
    }

    private void updateGameScreenResources() {
        updateMahjTextureRegions();
        mMahjRemainNum = TextureRegion.split(new Texture("SceneGame/mahj_remain_num.png"), 15, 21);
        mBaseGameScreenBackground = new Texture("SceneGame/background.jpg");
        mDefaultHead = new Texture[2];
        mDefaultHead[0] = new Texture("Head/Head0.png");
        mDefaultHead[1] = new Texture("Head/Head1.png");
        mHeadTingFlags = new Texture[2];
        mHeadTingFlags[0] = new Texture("Head/ting_horizontal.png");
        mHeadTingFlags[1] = new Texture("Head/ting_vertical.png");
        mHeadGoldNums = TextureRegion.split(new Texture("Head/gold_img.png"), 15, 21);
        mPlayerInfoBackground = new Texture("Head/player_info_background.png");
        mGameInfoBackground = new Texture("SceneGame/game_info_background.png");
        mInfoMahjBackground = new Texture("SceneGame/mahj_info_background.png");
        mCoverMahjBackground = new Texture("SceneGame/mahj_cover_bg.png");
        mOwnerDefaultMahjBackground = new Texture("SceneGame/mahj_me.png");
        mOwnerFlatMahjBackground = new Texture("SceneGame/mahj_flat_me.png");
        mVerticalFlatMahjBackground = new Texture("SceneGame/mahj_flat_vertical.png");
        mHorizontalFlatMahjBackground = new Texture("SceneGame/mahj_flat_horizontal.png");
        mTopDefaultMahjBackground = new Texture("SceneGame/mahj_top.png");
        mLeftDefaultMahjBackground = new Texture("SceneGame/mahj_left.png");
        mRightDefaultMahjBackground = new Texture("SceneGame/mahj_right.png");
    }

    private void updateMahjTextureRegions() {
        mBigMahjTextureRegions = TextureRegion.split(new Texture("SceneGame/mj_big.png"), 75, 85);
        mMiddleMahjTextureRegions = TextureRegion.split(new Texture("SceneGame/mj_middle.png"), 53, 60);
        mSmallMahjTextureRegions = TextureRegion.split(new Texture("SceneGame/mj_small.png"), 37, 42);
    }

    public TextureRegion getMahjTextureRegion(int index, int type) {
        index -= 1;
        int color = index / 10;
        int face = index % 10;
        if (color > 3) {
            color = 3;
            face = face + 4;
        }

        if (type == 0) {
            return mSmallMahjTextureRegions[color][face];
        } else if (type == 1) {
            return mMiddleMahjTextureRegions[color][face];
        } else {
            return mBigMahjTextureRegions[color][face];
        }
    }

    public LazyBitmapFont getFont() {
        return mFont;
    }

    public Music getBGM() {
        return mBGM;
    }

    public Sound getMajongSound(boolean male, int inedx) {
        return male ? mSound.get(inedx) : mGSound.get(inedx);
    }

    public float getProgress() {
        return mAssetManager.getProgress();
    }

    public boolean update() {
        return mAssetManager.update();
    }

    public void clear() {
        if (mAssetManager != null) {
            mAssetManager.dispose();
            mAssetManager = null;
        }

        if (mFont != null) {
            mFont.dispose();
        }

        mBGM.dispose();

        for (int i = 0; i < mSound.size(); i++) {
            Sound sound = mSound.get(i);
            if (sound != null) {
                sound.dispose();
            }
        }

        for (int j = 0; j < mGSound.size(); j++) {
            Sound sound = mGSound.get(j);
            if (sound != null) {
                sound.dispose();
            }
        }

    }
}
