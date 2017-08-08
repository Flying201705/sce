package com.nova.game.assetmanager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.nova.game.Constants;

public class Assets {
    private static Assets mInstance;
    private AssetManager mAssetManager;
    private FreeTypeFontGenerator mGeneratror;
    private BitmapFont mFont;

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

        loadMahjTexture();
        loadFont();

//        mAssetManager.finishLoading();
    }

    private void loadMahjTexture() {
        addTexture("SceneGame/1/mingmah_");
        addTexture("SceneGame/2/handmah_");
        addTexture("SceneGame/2/mingmah_");
        addTexture("SceneGame/3/mingmah_");
    }

    private void loadFont() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        mAssetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        mAssetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreeTypeFontLoaderParameter size1Params = new FreeTypeFontLoaderParameter();
        size1Params.fontFileName = "Font/font.ttf";
        size1Params.fontParameters.characters += "刘备关羽张飞赵云小乔房间号黄黑红绿人：";
        size1Params.fontParameters.size = 30;
        mAssetManager.load("size30.ttf", BitmapFont.class, size1Params);
    }

    private void addTexture(String path) {
        int i, j, index;
        for (i = 0, j = 1, index = 0; i < Constants.MAHJ_IMAGE_EACH_COUNT; i++, j++) {
            if (index > 2) {
                if (j > 4) {
                    j = 1;
                    index++;
                }
            } else if (j > 9) {
                j = 1;
                index++;
            }
            mAssetManager.load(path + (index * 10 + j) + ".png", Texture.class);
        }
    }

    public Texture getMahjMatchLeftTexture(int index) {
        return mAssetManager.get("SceneGame/3/mingmah_" + index + ".png");
    }

    public Texture getMahjMatchMeTexture(int index) {
        return mAssetManager.get("SceneGame/2/mingmah_" + index + ".png");
    }

    public Texture getMahjHandMeTexture(int index) {
        return mAssetManager.get("SceneGame/2/handmah_" + index + ".png");
    }

    public Texture getMahjMatchRightTexture(int index) {
        return mAssetManager.get("SceneGame/1/mingmah_" + index + ".png");
    }

    public BitmapFont getFont() {
        return mAssetManager.get("size30.ttf");
    }

    public void clear() {
        mAssetManager.dispose();
        mAssetManager = null;
    }

    public float getProgress() {
        return mAssetManager.getProgress();
    }

    public boolean update() {
        return mAssetManager.update();
    }
}
