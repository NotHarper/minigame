package cn.harper.game.utils;

import cn.harper.game.utils.math.MathUtils;
import cn.harper.game.utils.renderer.RendererUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: minigame
 * @description:
 * @author: Harper
 * @create: 2026-03-28 15:09
 **/
public class GameUtils {
    public static int[][] grid = new int[RendererUtils.grid_size][RendererUtils.grid_size];

    public static void moveUp() {
        //row是行 col是列
        for (int row = 0; row < RendererUtils.grid_size; row++) {
            int[] line = grid[row].clone();
            //compact是压缩后的数组，移除了0；
            List<Integer> compact = removeZero(line);
            merge(compact);
            //补0
            addZero(compact);
            //将压缩后补零的数组赋值给grid
            grid[row] = compact.stream().mapToInt(Integer::intValue).toArray();
        }
        randomAddNumber();
    }

    public static void moveDown() {
        for (int row = 0; row < RendererUtils.grid_size; row++) {
            int[] line = grid[row].clone();
            MathUtils.reverse(line);
            grid[row] = line;
            List<Integer> compact = removeZero(line);
            merge(compact);
            addZero(compact);
            int[] result = compact.stream().mapToInt(Integer::intValue).toArray();
            MathUtils.reverse(result);
            grid[row] = result;

        }
        randomAddNumber();
    }

    public static void moveLeft() {
        for (int col = 0; col < RendererUtils.grid_size; col++) {
            //先提取列
            int[] line = new int[RendererUtils.grid_size];
            for (int row = 0; row < RendererUtils.grid_size; row++) {
                line[row] = grid[row][col];
            }
            List<Integer> compact = removeZero(line);
            merge(compact);
            addZero(compact);
            for (int row = 0; row < RendererUtils.grid_size; row++) {
                grid[row][col] = compact.get(row);
            }
        }
        randomAddNumber();

    }

    public static void moveRight() {
        for (int col = 0; col < RendererUtils.grid_size; col++) {
            int[] line = new int[RendererUtils.grid_size];
            for (int row = 0; row < RendererUtils.grid_size; row++) {
                line[row] = grid[row][col];
            }
            MathUtils.reverse(line);
            List<Integer> compact = removeZero(line);
            merge(compact);
            addZero(compact);
            int[] result = compact.stream().mapToInt(Integer::intValue).toArray();
            MathUtils.reverse(result);
            for (int row = 0; row < RendererUtils.grid_size; row++) {
                grid[row][col] = result[row];
            }
        }
        randomAddNumber();

    }

    private static void addZero(List<Integer> compact) {
        for (int i = compact.size(); i < RendererUtils.grid_size; i++) {
            compact.add(0);
        }
    }

    private static List<Integer> removeZero(int[] line) {
        List<Integer> compact = new ArrayList<>();
        for (int num : line) {
            if (num != 0) {
                compact.add(num);
            }
        }
        return compact;
    }

    private static List<Integer> merge(List<Integer> compact) {
        for (int i = 0; i < compact.size() - 1; i++) {
            if (compact.get(i).equals(compact.get(i + 1))) {
                compact.set(i, compact.get(i) * 2);
                compact.remove(i + 1);
            }
        }
        return compact;
    }

    public static void initOriginalData() {
        clearGrid();
        randomAddNumber();
        randomAddNumber();
    }

    private static void clearGrid() {
        for (int i = 0; i < RendererUtils.grid_size; i++) {
            for (int j = 0; j < RendererUtils.grid_size; j++) {
                grid[i][j] = 0;
            }
        }
    }

    private static void randomAddNumber() {
        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < RendererUtils.grid_size; i++) {      // i = 行
            for (int j = 0; j < RendererUtils.grid_size; j++) {  // j = 列
                if (grid[i][j] == 0) {
                    empty.add(new int[]{i, j});    // 保存的是坐标
                }
            }
        }
        if (empty.isEmpty()) {
            return;
        }
        int[] pos = empty.get((int) (Math.random() * empty.size()));
        // 0.9的概率是2，0.1的概率是4
        grid[pos[0]][pos[1]] = Math.random() < 0.9 ? 2 : 4;

    }

    public static void reStart() {
        initOriginalData();
    }

    public static boolean isGameOver() {
        for (int i = 0; i < RendererUtils.grid_size; i++) {
            for (int j = 0; j < RendererUtils.grid_size; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
                if (grid[i][j] == 2048) {
                    return true;
                }
                if (j < RendererUtils.grid_size - 1 && grid[i][j] == grid[i][j + 1]) {
                    return false;
                }
                if (i < RendererUtils.grid_size - 1 && grid[i][j] == grid[i + 1][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
