package com.example.test.bean;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XQF
 * @created 2017/5/26
 */
public class BeanRole {

    private int color;
    private int currentIndex;
    private Button btn;

    public BeanRole(int color, int currentIndex) {
        this.color = color;
        this.currentIndex = currentIndex;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public Button getBtn() {
        return btn;
    }

    public static List<BeanRole> getRoleList() {

        List<BeanRole> list = new ArrayList<>();
        BeanRole blue = new BeanRole(BeanCell.COLOR_BLUE, 0);
        BeanRole red = new BeanRole(BeanCell.COLOR_RED, 13);
        BeanRole yello = new BeanRole(BeanCell.COLOR_YELLOW, 26);
        BeanRole green = new BeanRole(BeanCell.COLOR_GREEN, 39);
        list.add(blue);
        list.add(red);
        list.add(yello);
        list.add(green);
        return list;
    }

    public void clickBtn() {
        btn.performClick();
    }
}
