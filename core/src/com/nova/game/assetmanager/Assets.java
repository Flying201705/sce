package com.nova.game.assetmanager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.nova.game.Constants;

public class Assets {
    private static Assets mInstance;
    private AssetManager mAssetManager;

    public static Assets getInstance() {
        if (mInstance == null) {
            mInstance = new Assets();
        }
        return mInstance;
    }

    public void loadMahjTexture() {
        if (mAssetManager == null) {
            mAssetManager = new AssetManager();
        }

        int i, j, index;

        for (i = 0, j = 1, index = 1; i < Constants.MAHJ_IMAGE_EACH_COUNT; i++, j++) {
            if (j > 9) {
                j = 1;
                index++;
            }
            mAssetManager.load("ScenceGame/1/mingmah_" + (index * 10 + j) + ".png", Texture.class);
        }

        for (i = 0, j = 1, index = 1; i < Constants.MAHJ_IMAGE_EACH_COUNT; i++, j++) {
            if (j > 9) {
                j = 1;
                index++;
            }
            mAssetManager.load("ScenceGame/2/handmah_" + (index * 10 + j) + ".png", Texture.class);
        }

        for (i = 0, j = 1, index = 1; i < Constants.MAHJ_IMAGE_EACH_COUNT; i++, j++) {
            if (j > 9) {
                j = 1;
                index++;
            }
            mAssetManager.load("ScenceGame/2/mingmah_" + (index * 10 + j) + ".png", Texture.class);
        }

        for (i = 0, j = 1, index = 1; i < Constants.MAHJ_IMAGE_EACH_COUNT; i++, j++) {
            if (j > 9) {
                j = 1;
                index++;
            }
            mAssetManager.load("ScenceGame/3/mingmah_" + (index * 10 + j) + ".png", Texture.class);
        }
        mAssetManager.finishLoading();
    }

    public void clearMahjTexture() {
        mAssetManager.dispose();
        mAssetManager = null;
    }

    public Texture getMahjHandMeTexture(int index) {
        return mAssetManager.get("ScenceGame/2/handmah_" + index+ ".png");
    }
}
