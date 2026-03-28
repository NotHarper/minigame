package cn.harper.game;

import cn.harper.game.manager.KeyBoardManager;
import cn.harper.game.utils.GameUtils;
import cn.harper.game.utils.renderer.FontUtils;
import cn.harper.game.utils.renderer.RendererUtils;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import javax.swing.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * @program: minigame
 * @description:
 * @author: Harper
 * @create: 2026-03-28 00:41
 **/
public class MiniGame {
    private long window;
    private FontUtils fontUtils;
    public static final int width = 800;
    public static final int height = 800;

    public static void main(String[] args) {
        new MiniGame().run();
    }

    public void run() {
        init();
        loop();

        //释放glfw资源(窗口，回调，事件)
        glfwFreeCallbacks(window);
        //释放窗口
        glfwDestroyWindow(window);
        //终止 GLFW (释放内存)
        glfwTerminate();

        //释放错误回调
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        //设置错误回调
        GLFWErrorCallback.createPrint(System.err).set();
        //初始化GLFW
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        //窗口设置为glfw预设默认值
        glfwDefaultWindowHints();
        //设置窗口大小为300x300，不可见，可调整大小，标题为2048 Mini Game LWJGL + 版本号
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        //允许调整大小
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, "2048 Mini Game LWJGL" + Version.getVersion(), NULL, NULL);
        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

        //监听键盘
        keyboard_handle();
        //窗口居中
        setWindowsCenter();

        //把这个窗口设为当前 OpenGL 渲染目标。在创建窗口之后、进行任何 OpenGL 渲染操作之前，必须调用此函数。
        glfwMakeContextCurrent(window);
        //启用垂直同步
        glfwSwapInterval(1);
        //完成准备步骤，显示窗口
        glfwShowWindow(window);
    }


    private void loop() {
        //初始化OpenGL，使得LWJGL可以使用OpenGL的API
        GL.createCapabilities();
        //设置视口大小
        glViewport(0, 0, width, height);
        //设置投影矩阵
        glMatrixMode(GL_PROJECTION);
        //清空选中的矩阵
        glLoadIdentity();
        //设置正交投影，，无透视
        //从左渲染
        glOrtho(0, width, height, 0, -1, 1);
        //切换回modelview矩阵(后续平移，旋转，缩放都作用域物体）
        glMatrixMode(GL_MODELVIEW);
        //清空选中的矩阵
        glLoadIdentity();
        //设置清除颜色为白色
        glClearColor(255.0f, 255.0f, 255.0f, 0.0f);

        try {
            fontUtils = new FontUtils("/sfui.ttf", 48.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initGrid();

        while (!glfwWindowShouldClose(window)) {
            // 清除颜色缓冲区和深度缓冲区。使得glClearColor设置的颜色显示在屏幕上。
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //绘制网格
            RendererUtils.drawGrid(width, height, fontUtils);
            // 交换前后缓冲区。渲染在后台缓冲区完成，交换后显示到屏幕上，实现无闪烁的显示效果（双缓冲机制）。
            glfwSwapBuffers(window);
            //处理所有待处理的输入事件（键盘、鼠标、窗口大小变化等）。
            glfwPollEvents();

            if (GameUtils.isGameOver()) {
                JOptionPane.showMessageDialog(null, "游戏结束");
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "是否重新开始？", "提示", JOptionPane.YES_NO_OPTION)) {
                    GameUtils.reStart();
                }

            }
        }

    }

    private void setWindowsCenter() {
        try (MemoryStack stack = stackPush()) {
            //获取窗口宽度
            IntBuffer pWidth = stack.mallocInt(1);
            //获取窗口高度
            IntBuffer pHeight = stack.mallocInt(1);
            //获取窗口宽度和高度
            glfwGetWindowSize(window, pWidth, pHeight);
            //获取当前显示器的分辨率
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }
    }

    private void keyboard_handle() {
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                switch (key) {
                    case GLFW_KEY_UP:
                        KeyBoardManager.moveUp();
                        System.out.println("up");
                        break;
                    case GLFW_KEY_DOWN:
                        KeyBoardManager.moveDown();
                        System.out.println("down");
                        break;
                    case GLFW_KEY_LEFT:
                        KeyBoardManager.moveLeft();
                        System.out.println("left");
                        break;
                    case GLFW_KEY_RIGHT:
                        KeyBoardManager.moveRight();
                        System.out.println("right");
                        break;
                }
            }
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });
    }

    private void initGrid() {
        GameUtils.initOriginalData();
    }


}
