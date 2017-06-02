package com.nova.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public class HandMahjongs extends Group {
    private Assets mAssets;
    private int[] mMahjs;

    public HandMahjongs() {
        mAssets = Assets.getInstance();
    }

    public HandMahjongs(int where, int[] mahjs) {
        this();
        setMahjs(where, mahjs);
    }

    public HandMahjongs(int where, ArrayList<MahjData> mahjs) {
        this();
        HorizontalGroup group_0 = new HorizontalGroup();
        group_0.space(-2);
        for (MahjData mahj : mahjs) {
            MahjActor mahjActor = new MahjActor(mAssets.getMahjHandMeTexture(mahj.getIndex()));
            mahjActor.setScale(0.8f);
            group_0.addActor(mahjActor);
        }
        addActor(group_0);
    }

    public void setMahjs(int where, int[] mahjs) {
        mMahjs = mahjs;
        switch (where) {
            case 0:
                HorizontalGroup group_0 = new HorizontalGroup();
                group_0.space(-2);
                for (int mahj : mMahjs) {
                    MahjActor mahjActor = new MahjActor(mAssets.getMahjHandMeTexture(mahj));
                    mahjActor.setScale(0.8f);
                    group_0.addActor(mahjActor);
                }
                addActor(group_0);
                break;
            case 1:
                VerticalGroup group_1 = new VerticalGroup();
                group_1.space(-52);
                Texture texture = new Texture("ScenceGame/hand_right.png");
                for (int mahj : mMahjs) {
                    MahjActor mahjActor = new MahjActor(texture);
                    group_1.addActor(mahjActor);
                }
                addActor(group_1);
                break;
            case 2:
                HorizontalGroup group_2 = new HorizontalGroup();
                group_2.space(-5);
                Texture texture_2 = new Texture("ScenceGame/hand_top.png");
                for (int mahj : mMahjs) {
                    MahjActor mahjActor = new MahjActor(texture_2);
                    group_2.addActor(mahjActor);
                }
                addActor(group_2);
                break;
            case 3:
                VerticalGroup group_3 = new VerticalGroup();
                group_3.space(-52);
                Texture texture_3 = new Texture("ScenceGame/hand_left.png");
                for (int mahj : mMahjs) {
                    MahjActor mahjActor = new MahjActor(texture_3);
                    group_3.addActor(mahjActor);
                }
                addActor(group_3);
                break;
            default:
                break;
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
