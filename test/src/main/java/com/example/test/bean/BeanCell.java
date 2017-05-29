package com.example.test.bean;

/**
 * @author XQF
 * @created 2017/5/24
 */
public class BeanCell {


    public static final int COLOR_BLUE = 0;
    public static final int COLOR_RED = 1;
    public static final int COLOR_YELLOW = 2;
    public static final int COLOR_GREEN = 3;

    public static final int[] sColors = new int[]{COLOR_BLUE, COLOR_RED, COLOR_YELLOW, COLOR_GREEN};

    int x;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    int y;
    int color;

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

    public BeanCell(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

}
