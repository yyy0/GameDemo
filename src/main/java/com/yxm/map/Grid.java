package com.yxm.map;

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
}
