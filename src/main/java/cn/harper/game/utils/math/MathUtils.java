package cn.harper.game.utils.math;

/**
 * @program: minigame
 * @description:
 * @author: Harper
 * @create: 2026-03-28 15:30
 **/
public class MathUtils {
    public static void reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }
}
