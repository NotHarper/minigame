# 2048 Mini Game

基于 **LWJGL + OpenGL** 实现的经典 2048 小游戏，使用 Java 编写。

## 功能特性

- 4x4 网格的经典 2048 玩法
- 键盘方向键控制方块移动与合并
- 不同数字对应不同方块颜色
- 游戏结束检测与重新开始提示
- 800x800 窗口，自动居中显示

## 操作说明

| 按键 | 功能 |
|------|------|
| `↑` `↓` `←` `→` | 方向键控制方块移动 |
| `ESC` | 退出游戏 |

## 项目结构

```
src/main/java/cn/harper/game/
├── MiniGame.java                        # 主类，窗口初始化与游戏主循环
├── manager/
│   └── KeyBoardManager.java             # 键盘输入管理
└── utils/
    ├── GameUtils.java                   # 核心游戏逻辑（移动、合并、生成）
    ├── color/
    │   └── ColorUtils.java              # 方块颜色映射
    ├── math/
    │   └── MathUtils.java               # 数组工具方法
    └── renderer/
        ├── RendererUtils.java           # OpenGL 网格与矩形绘制
        └── FontUtils.java               # TrueType 字体渲染（STB）
```

## 技术栈

- **Java 8+**
- **LWJGL 3.4.1** — Lightweight Java Game Library
- **OpenGL** — 图形渲染（固定管线）
- **GLFW** — 窗口管理与输入处理
- **STB** — 字体位图生成与渲染
- **Gradle** — 构建工具

## 构建与运行

### 环境要求

- JDK 8 或更高版本
- Gradle（项目自带 Gradle Wrapper，无需额外安装）

### 运行

```bash
# Windows
gradlew.bat run

# Linux / macOS
./gradlew run
```

> 注意：当前 `build.gradle` 未配置 `application` 插件，需手动添加 mainClass 或通过 IDE 运行 `MiniGame.main()`。

### 手动构建

```bash
./gradlew build
java -cp build/classes/java/main:$(./gradlew dependencies --configuration runtimeClasspath -q | sed 's/---//g') cn.harper.game.MiniGame
```

## 游戏规则

1. 游戏开始时，4x4 网格中随机出现两个数字（90% 概率为 2，10% 概率为 4）
2. 使用方向键滑动所有方块，相同数字碰撞时合并为两倍
3. 每次移动后会在空位随机生成一个新数字
4. 当任意方块达到 **2048** 时游戏胜利，无法移动时游戏结束

## Author

Harper
