package com.nova.game.assetmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.nova.game.Constants;

public class Assets {
    private static Assets mInstance;
    private AssetManager mAssetManager;
    public FreeTypeFontGenerator mGeneratror;
    private BitmapFont mFont;
    private TextureRegion[][] mBigMahjTextureRegions;
    private TextureRegion[][] mMiddleMahjTextureRegions;
    private TextureRegion[][] mSmallMahjTextureRegions;

    private Assets() {
        mAssetManager = new AssetManager();
    }

    public static Assets getInstance() {
        if (mInstance == null) {
            mInstance = new Assets();
        }
        return mInstance;
    }

    public void load() {
        loadFont();
        updateMahjTextureRegions();

//        mAssetManager.finishLoading();
    }

    private void loadFont() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        mAssetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        mAssetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        mGeneratror = new FreeTypeFontGenerator(Gdx.files.internal("Font/font.ttf"));

        FreeTypeFontLoaderParameter size1Params = new FreeTypeFontLoaderParameter();
        size1Params.fontFileName = "Font/font.ttf";
        size1Params.fontParameters.characters += "狠心离开再等一会退出游戏取消房间号聊天点击输入语音消息长按说话松结束发送倔强的电脑彼岸石浩在路上快点吧我等花儿都谢了大家好很高兴见到又断线网络怎么这差和你合作真是太愉们交个朋友能不告诉联系方式呀还吵有什专心玩要走决天亮各意思离开会再想念：";
        size1Params.fontParameters.size = 30;
        mAssetManager.load("size30.ttf", BitmapFont.class, size1Params);
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

    public BitmapFont getFont() {
        return mAssetManager.get("size30.ttf");
    }

    public void clear() {
        if (mAssetManager != null) {
            mAssetManager.dispose();
            mAssetManager = null;
        }
    }

    public float getProgress() {
        return mAssetManager.getProgress();
    }

    public boolean update() {
        return mAssetManager.update();
    }
}
