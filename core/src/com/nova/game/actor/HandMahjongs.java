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
    private Group mHands;
    private ArrayList<MahjData> mMahjs;

    public HandMahjongs(int where) {
        mAssets = Assets.getInstance();
        switch (where) {
            case 0:
                HorizontalGroup group_0 = new HorizontalGroup();
                group_0.space(-2);
                mHands = group_0;
                break;
            case 1:
                VerticalGroup group_1 = new VerticalGroup();
                group_1.space(-52);
                mHands = group_1;
                break;
            case 2:
                HorizontalGroup group_2 = new HorizontalGroup();
                group_2.space(-5);
                mHands = group_2;
                break;
            case 3:
                VerticalGroup group_3 = new VerticalGroup();
                group_3.space(-52);
                mHands = group_3;
                break;
        }
        addActor(mHands);
    }

    public HandMahjongs(int where, ArrayList<MahjData> mahjs) {
        this(where);
        setMahjs(where, mahjs);
    }

    public void setMahjs(int where, ArrayList<MahjData> mahjs) {
        if (mahjs != null && mMahjs != null && mahjs.containsAll(mMahjs)) {
            return;
        }

        mMahjs = mahjs;
        mHands.clear();

        switch (where) {
            case 0:
                for (MahjData mahj : mahjs) {
                    MahjActor mahjActor = new MahjActor(mAssets.getMahjHandMeTexture(mahj.getIndex()));
                    mahjActor.setScale(0.8f);
                    mHands.addActor(mahjActor);
                }
                break;
            case 1:
                Texture texture = new Texture("ScenceGame/hand_right.png");
                for (MahjData mahj : mMahjs) {
                    MahjActor mahjActor = new MahjActor(texture);
                    mHands.addActor(mahjActor);
                }
                break;
            case 2:
                Texture texture_2 = new Texture("ScenceGame/hand_top.png");
                for (MahjData mahj : mMahjs) {
                    MahjActor mahjActor = new MahjActor(texture_2);
                    mHands.addActor(mahjActor);
                }
                break;
            case 3:
                Texture texture_3 = new Texture("ScenceGame/hand_left.png");
                for (MahjData mahj : mMahjs) {
                    MahjActor mahjActor = new MahjActor(texture_3);
                    mHands.addActor(mahjActor);
                }
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
