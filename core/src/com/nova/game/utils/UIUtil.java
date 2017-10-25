package com.nova.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nova.game.Constants;
import com.nova.game.assetmanager.Assets;

public class UIUtil {
    public static Texture bytes2Texture(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
        Texture image = new Texture(pixmap);
        pixmap.dispose();

        return image;
    }

    public static Drawable file2Drawable(String filePatch) {
        return new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(filePatch))));
    }
    
    public static Drawable file2Drawable(String filePatch, int left, int right, int top, int bottom) {
    	return new NinePatchDrawable(new NinePatch(new Texture(Gdx.files.internal(filePatch)), left, right, top, bottom));
    }
    
    public static TextFieldStyle getTextFieldStyle() {
        return new TextFieldStyle(Assets.getInstance().getFont(), Color.BLACK, 
                createCursorDrawable(), createBackgroundDrawable(), createBackgroundDrawable());
    }
    
    public static TextButtonStyle getTextButtonStyle() {
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = Assets.getInstance().getFont();
        textButtonStyle.fontColor = Color.YELLOW;
        textButtonStyle.up = UIUtil.file2Drawable("Dialog/btn_up.png", 18, 18, 40, 40);
        textButtonStyle.down = UIUtil.file2Drawable("Dialog/btn_down.png", 18, 18, 40, 40);
        return textButtonStyle;
    }
    
    public static Drawable getTranslucentDrawable() {
    	Pixmap pixmap = new Pixmap(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.0F, 0.0F,  0.0F, 0.5F));
        pixmap.fill();
        SpriteDrawable drawable = new SpriteDrawable(new Sprite(new Texture(pixmap)));
        
        return drawable;
    }
    
    public static WindowStyle getDefaultWindowStyle() {
        return getDefaultWindowStyle(false);
    }
    
    public static WindowStyle getDefaultWindowStyle(boolean isTranslucent) {
        Drawable bg = file2Drawable("Dialog/dialog_bg.png", 50, 50, 50, 50);
        WindowStyle windowStyle = new WindowStyle(Assets.getInstance().getFont(), Color.BLACK, bg);
        if (isTranslucent) {
        	windowStyle.stageBackground = getTranslucentDrawable();
        }
        return windowStyle;
    }
	
    public static WindowStyle getDefaultWindowStyle(Drawable background) {
        WindowStyle windowStyle = new WindowStyle(Assets.getInstance().getFont(), Color.BLACK, background);
        Pixmap pixmap = new Pixmap(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.0F, 0.0F,  0.0F, 0.5F));
        pixmap.fill();
        SpriteDrawable drawable = new SpriteDrawable(new Sprite(new Texture(pixmap)));
        windowStyle.stageBackground = drawable;
        
       return windowStyle;
    }
    
    public static ImageButton generateImageButton(String filePatch, int tileWidth, int tileHeight) {
        Texture texture = new Texture(Gdx.files.internal(filePatch));
        TextureRegion region[][] = TextureRegion.split(texture, tileWidth, tileHeight);
        return new ImageButton(new TextureRegionDrawable(region[0][0]), new TextureRegionDrawable(region[0][1]));
    }
    
    private static Drawable createBackgroundDrawable() {
        return file2Drawable("Dialog/edit_background.png", 4, 4, 4, 4);
    }
    
    private static Drawable createCursorDrawable() {
        Pixmap pixmap = new Pixmap(2, 80, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        SpriteDrawable drawable = new SpriteDrawable(new Sprite(texture));
        return drawable;
    }
}
