package com.example.jinbo.struct;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/3/21.
 */
/*
棋子类
 */
public class Chess {
    //棋子的颜色
    private int color;
    //棋子的位置，-1代表还在基地
    private int point=-1;
    //代表棋子最多走的方格数目，256是随意写的
    private static int MAX_SQUARE_NUM=256;

    public Chess() {
        this.color= Color.BLACK;
        this.point=-1;
    }
    public Chess(int color) {
        this.color=color;
    }
    public Chess(int color, int point) {
        this.color=color;
        if(point < -1) {
            this.point=-1;
        }
        else if(point > MAX_SQUARE_NUM) {
            this.point=point%MAX_SQUARE_NUM;
        }
        else
        this.point=point;
    }
    /*
    设置棋子当前在哪一个格子
    @param int point代表所在格子的下标
     */
    public void setPoint(int point) {
        this.point=point;
    }
    /*
    获取棋子当前所在的格子的下标
    @return int 下边
     */
    public int getPoint() {
        return this.point;
    }
    public void setColor(int color) {
        this.color=color;
    }
    public int getColor() {
        return this.color;
    }
}
