package com.nova.game.model;

import com.nova.game.handler.LocalMahjGameHandler;
import nova.common.game.mahjong.handler.TestRecordFileManager;

/**
 * Created by zhangxx on 17-12-5.
 */

public class TestMahjController extends MahjController {

    @Override
    public void startGame(int roomId) {
        super.startGame(roomId);
        TestRecordFileManager manager = new TestRecordFileManager(new LocalMahjGameHandler());
        manager.setFilePath("/sdcard/sce/mahj/temp/test.txt");
        manager.start();
    }
}
