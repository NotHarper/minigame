package cn.harper.game.manager;

import cn.harper.game.utils.GameUtils;

/**
 * @program: minigame
 * @description:
 * @author: Harper
 * @create: 2026-03-28 14:39
 **/
public class KeyBoardManager {
    public static void moveUp() {
        GameUtils.moveUp();
    }
    public static void moveDown() {
        GameUtils.moveDown();
    }
    public static void moveLeft() {
        GameUtils.moveLeft();
    }
    public static void moveRight() {
        GameUtils.moveRight();
    }
}
