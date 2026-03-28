package cn.harper.game.utils.color;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: minigame
 * @description:
 * @author: Harper
 * @create: 2026-03-28 14:44
 **/
public class ColorUtils {
    public static final Map<Integer, float[]> color_map = new HashMap<>();

    static {
        color_map.put(0, new float[]{0.93f, 0.93f, 0.93f});
        color_map.put(2, new float[]{0.93f, 0.93f, 0.93f});
        color_map.put(4, new float[]{0.91f, 0.87f, 0.78f});
        color_map.put(8, new float[]{0.95f, 0.69f, 0.47f});
        color_map.put(16, new float[]{0.95f, 0.58f, 0.37f});
        color_map.put(32, new float[]{0.95f, 0.48f, 0.33f});
        color_map.put(64, new float[]{0.95f, 0.33f, 0.23f});
        color_map.put(128, new float[]{0.93f, 0.88f, 0.51f});
        color_map.put(256, new float[]{0.93f, 0.86f, 0.42f});
        color_map.put(512, new float[]{0.93f, 0.84f, 0.32f});
        color_map.put(1024, new float[]{0.88f, 0.80f, 0.25f});
        color_map.put(2048, new float[]{0.84f, 0.74f, 0.20f});
    }

    // 工具方法：安全获取颜色，避免 NullPointerException
    public static float[] getColor(int value) {
        return color_map.getOrDefault(value, new float[]{0.0f, 0.0f, 0.0f}); // 默认黑色（或抛异常）
    }

}
