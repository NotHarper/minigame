package cn.harper.game.utils.renderer;

import cn.harper.game.utils.GameUtils;
import cn.harper.game.utils.color.ColorUtils;

import static org.lwjgl.opengl.GL11.*;

/**
 * @program: minigame
 * @description:
 * @author: Harper
 * @create: 2026-03-28 13:41
 **/
public class RendererUtils {
    public static final int grid_size = 4;             // 网格是 4x4
    public static final float grid_padding = 20f;      // 网格距离窗口边缘的内边距（上下左右）
    public static final float block_padding = 10f;     // 每个方块之间的间隙（像素）

    //绘制网格
    public static void drawGrid(int width, int height, FontUtils fontUtils) {
        //计算每个方块的宽度和高度
        float block_size = (width - grid_padding * 2 - block_padding * (grid_size - 1)) / grid_size;
        //绘制网格(4 x 4)
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                //这是起始x和y坐标
                float x = grid_padding + i * (block_size + block_padding);
                float y = grid_padding + j * (block_size + block_padding);
                int value = GameUtils.grid[i][j];
                // 因为是正方形，所有长宽都是block_size
                drawRect(x, y, block_size, block_size, ColorUtils.getColor(value));
                // 渲染文字

                if (value > 0) {
                    glColor3f(0.0f, 0.0f, 0.0f);
                    String text = String.valueOf(value);
                    // 计算文字居中位置
                    float textWidth = text.length() * fontUtils.getCharAdvance();  // 文字总宽度
                    float textX = x + (block_size - textWidth) / 2;               // 水平居中
                    float textY = y + (block_size - fontUtils.getScale()) / 2;    // 垂直居中
                    fontUtils.drawText(textX, textY, text);
                }
            }
        }
    }

    //绘制矩形
    public static void drawRect(float x, float y, float width, float height, float[] color) {
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();

    }


}
