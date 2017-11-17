package com.nova.game.actor;

import com.nova.game.actor.mahj.HorizontalFlatMahjong;
import com.nova.game.actor.mahj.Mahjong;
import com.nova.game.assetmanager.Assets;

import java.util.ArrayList;

import nova.common.game.mahjong.data.MahjData;

public class RightOutMahjongs extends OutMahjongs {

    @Override
    protected void updateOutMahjongs(ArrayList<MahjData> mahjs) {
        clear();

        float currX = 0, currY = 0, offset = 15f;
        for (int i = mahjs.size() - 1; i >= 0; i--) {
            HorizontalFlatMahjong mahjActor = new HorizontalFlatMahjong(mahjs.get(i).getIndex(), Mahjong.MAHJ_DIRECTION_RIGHT);
            addActor(mahjActor);
            if (i >= 14) {
                currX = 2 * mahjActor.getWidth();
                currY = (i - 14) * (mahjActor.getHeight() - offset);
            } else if (i >= 6 && i < 14) {
                currX = mahjActor.getWidth();
                currY = (i - 5) * (mahjActor.getHeight() - offset);
            } else {
                currX = 0;
                currY = (i + 2) * (mahjActor.getHeight() - offset);
            }

            mahjActor.setPosition(currX, currY);

            if (i == mahjs.size() - 1) {
                mLatestX = currX;
                mLatestY = currY;
                mLatestW = mahjActor.getWidth();
                mLatestH = mahjActor.getHeight();
            }
        }
    }
}
