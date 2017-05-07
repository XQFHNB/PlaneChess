package com.example.jinbo.struct;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
/*
方格类，代表棋盘上的每一个小格子
 */
public class Square {
    //格子的颜色
    private int color=Color.WHITE;
   //将占用这一格的棋子放在一个list中
    private List<Chess> list=new ArrayList<Chess>();
    //方向，用整型表示，0→，1↑，2←，3↓，初始为-1；
    private int direction=-1;
    private float x,y;
    /*
    默认构造方法，颜色变为白色，方向为向右
     */
    public Square() {
        this.color=Color.WHITE;
        this.direction=0;
    }
    /*
    初始化颜色的构造方法，方向默认向右
    @param color 颜色
     */
    public Square(int color) {
        this.color=color;
        this.direction=0;
    }
    /*
    初始化颜色的方向的构造方法
    @param color 颜色
    @param direction 方向
     */
    public Square(int color, int direction) {
        this.color=color;
        if(direction>=0 && direction<=3) {
            this.direction=direction;
        }
        else this.direction=0;
    }
    /*
    获取该方格的颜色
    @return int color
     */
    public int getColor() {
        return this.color;
    }
    /*
    设置该方格的颜色
    @param color 颜色
     */
    public void setColor(int color) {
        this.color=color;
    }
    /*
    判断这一格是否被占用（是否有棋子）
    @return true or false
     */
    public boolean isOccupied() {
        return this.list.size()!=0;
    }
    /*
    将这一格的棋子列表清空（移除所有棋子）
     */
    public void clear() {
        this.list.clear();
    }
    /*
    向这一格增加一个棋子
    @param chess 被增加的棋子
     */
    public void addChess(Chess chess) {
        this.list.add(chess);
    }
    /*
    移除这一格的最后一颗棋子
     */
    public void removeChess() {
        //移走最后一位
        this.list.remove(this.list.size() - 1);
    }
    /*
    获取这一格的棋子数量
    @return int num 棋子数量
     */
    public int chessNumber() {
        return this.list.size();
    }
    /*
    获取最顶一颗棋子
    @return chess 最顶部的一颗棋子
     */
    public Chess getLastChess() {
        return this.list.get(this.list.size() - 1);
    }
    /*
    获取这一格的棋子列表
    @return list 棋子列表
     */
    public List<Chess> getPlaneList() {
        return this.list;
    }
    /*
    获取这一格的方向
    @return int direction 方向
     */
    public int getDirection() {
        return this.direction;
    }
    /*
    设置这一格的方向
    @param d（direction） 方向
     */
    public void setDirection(int d) {
        if(d>=0&&d<=3)
            this.direction=d;
        else
            this.direction=0;
        return;
    }
}
