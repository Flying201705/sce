package com.nova.game.actor;

import com.nova.game.actor.mahj.VerticalFlatMahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public class TopOutMahjongs extends OutMahjongs {

    @Override
    protected void updateOutMahjongs(ArrayList<MahjData> mahjs) {
        clear();

        float currX = 0, currY = 0, offsetX = 5f, offsetY = 15f;
        for (int i = mahjs.size() - 1; i >= 0; i--) {
            VerticalFlatMahjong mahjActor = new VerticalFlatMahjong(mahjs.get(i).getIndex());
            if (i >= 14) {
                currX = getWidth() - (i - 14) * (mahjActor.getWidth() - offsetX);
                currY = 2 * (mahjActor.getHeight() - offsetY);
            } else if (i >= 6 && i < 14) {
                currX = getWidth() - (i - 6 + 1) * (mahjActor.getWidth() - offsetX);
                currY = mahjActor.getHeight() - offsetY;
            } else if (i < 6) {
                currX = getWidth() - (i + 2) * (mahjActor.getWidth() - offsetX);
                currY = 0;
            }
            mahjActor.setPosition(currX, currY);
            addActor(mahjActor);

            if (i == mahjs.size() - 1) {
                mLatestX = currX;
                mLatestY = currY;
                mLatestW = mahjActor.getWidth();
                mLatestH = mahjActor.getHeight();
            }
        }
    }
}
