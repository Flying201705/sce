package com.nova.game.actor;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.nova.game.actor.mahj.HorizontalFlatMahjong;
import java.util.ArrayList;
import nova.common.game.mahjong.data.MahjData;

public class LeftOutMahjongs extends Group {

    public LeftOutMahjongs() {
    }

    public void setOutMahjongs(ArrayList<MahjData> mahjs) {
        clear();

        float currX = getWidth(), currY = getHeight(), offsetY = 15f;
        for (int i = 0; i < mahjs.size(); i++) {
            HorizontalFlatMahjong mahjActor = new HorizontalFlatMahjong(mahjs.get(i).getIndex());
            addActor(mahjActor);

            if (i < 6) {
                currX = getWidth() - mahjActor.getWidth();
                currY = getHeight() - (i + 2) * (mahjActor.getHeight() - offsetY);
            } else if (i >= 6 && i < 14) {
                currX = getWidth() - 2 * mahjActor.getWidth();
                currY = getHeight() - (i - 6 + 1) * (mahjActor.getHeight() - offsetY);
            } else if (i >= 14) {
                currX = getWidth() - 3 * mahjActor.getWidth();
                currY = getHeight() - (i - 14) * (mahjActor.getHeight() - offsetY);
            }
            mahjActor.setPosition(currX, currY);
        }
    }
}
