package cn.harper.game.utils.renderer;

import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTruetype;

import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * @program: minigame
 * @description:
 * @author: Harper
 * @create: 2026-03-28 16:05
 **/
public class FontUtils {
    private int texId;
    private STBTTBakedChar.Buffer bakedChars;
    private float scale;

    public FontUtils(String ttfPath, float fontSize) throws Exception {
        this.scale = fontSize;

        // 读取字体文件
        InputStream inputStream = FontUtils.class.getResourceAsStream(ttfPath);
        // 将字体文件读取到 ByteBuffer 中
        ByteBuffer ttf = ByteBuffer.allocateDirect(inputStream.available());
        //  创建一个字节数组来存储字体文件数据
        byte[] buffer = new byte[inputStream.available()];
        // 读取字体文件数据到 buffer 中
        inputStream.read(buffer);
        // 将字体文件数据写入 ByteBuffer 中
        ttf.put(buffer).flip();
        //关闭输入流
        inputStream.close();

        bakedChars = STBTTBakedChar.create(96);
        ByteBuffer bitmap = ByteBuffer.allocateDirect(512 * 512);
        // 生成字体位图
        // 字体，字体大小，位图，位图宽度，位图高度，第一个字符，字符信息
        STBTruetype.stbtt_BakeFontBitmap(ttf, fontSize, bitmap, 512, 512, 32, bakedChars);

        // 把位图创建为OpenGL纹理
        texId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, 512, 512, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
        //缩小过滤，当纹理缩小显示时，使用什么算法插值。
        // GL_LINEAR：双线性插值（平滑）
        // GL_NEAREST：最近邻（像素风格，有锯齿）
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        //放大过滤，当纹理放大显示时，使用什么算法插值。
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    public void drawText(float x, float y, String text) {
        // 启用纹理和混合
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glBindTexture(GL_TEXTURE_2D, texId);

        glBegin(GL_QUADS);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            //跳过非 ASCII 可打印字符（比如中文不在 32~127 范围内）。
            if (c < 32 || c > 127) continue;
            //c - 32 就是算偏移量，找到对应字符的数据。
            STBTTBakedChar aChar = bakedChars.get(c - 32);

            // 计算字符在屏幕上的位置
            //字符的偏移量（有些字符比如 'g' 会往下偏，'j' 会往左偏）
            float x0 = x + aChar.xoff();
            float y0 = y + aChar.yoff();

            //字符的宽高
            //x1 - x0 是字符的宽度，y1 - y0 是字符的高度
            //x0 = x + 1           = 当前位置 + 左偏移
            //y0 = y + 38          = 当前位置 + 上偏移

            float x1 = x0 + (aChar.x1() - aChar.x0());
            float y1 = y0 + (aChar.y1() - aChar.y0());

            // 计算字符在纹理上的位置 (UV坐标)
            float u0 = aChar.x0() / 512.0f;
            float u1 = aChar.x1() / 512.0f;
            float v0 = aChar.y0() / 512.0f;
            float v1 = aChar.y1() / 512.0f;

            // 贴纹理的矩形
            glTexCoord2f(u0, v0);
            glVertex2f(x0, y0);
            glTexCoord2f(u1, v0);
            glVertex2f(x1, y0);
            glTexCoord2f(u1, v1);
            glVertex2f(x1, y1);
            glTexCoord2f(u0, v1);
            glVertex2f(x0, y1);

            // 移动到下一个字符的位置
            x += aChar.xadvance();
        }
        glEnd();

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    public float getScale() {
        return scale;
    }

    public float getCharAdvance() {
        return bakedChars.get('0' - 32).xadvance();
    }
}
