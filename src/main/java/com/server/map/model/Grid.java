package com.server.map.model;

import java.util.Objects;

/**
 * @author yuxianming
 * @date 2019/4/29 0:29
 */
public class Grid {

    private int x;
    private int y;

    public static Grid valueOf(int x, int y) {
        Grid grid = new Grid();
        grid.x = x;
        grid.y = y;
        return grid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grid)) return false;
        Grid grid = (Grid) o;
        return x == grid.x &&
                y == grid.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * 判断目标格子是否在自身范围内
     *
     * @param targetGrid
     * @param range
     * @return
     */
    public boolean isInRange(Grid targetGrid, int range) {
        for (int i = Math.max(this.x - range, 0); i < this.x + range; i++) {
            for (int j = Math.max(this.y - range, 0); j <= this.y + range; j++) {
                if (Grid.valueOf(i, j).equals(targetGrid)) {
                    return true;
                }
            }
        }
        return false;
    }
}
