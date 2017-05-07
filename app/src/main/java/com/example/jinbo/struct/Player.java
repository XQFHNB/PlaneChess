package com.example.jinbo.struct;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/3/26.
 */
/*
玩家类
 */
public class Player {
    //用户名
    private String name=null;
    //能够操作的棋子的颜色
    private int color= Color.WHITE;
    //名次
    private int rank=999999;

    public Player() {
        this.name="Unknown";
    }
    public Player(String name) {
        this.name=name;
    }
    public Player(int color) {
        this.color=color;
    }
    public Player(String name, int color) {
        this.name=name;
        this.color=color;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name=name;
    }
    public void setColor(int color) {
        this.color=color;
    }
    public int getColor() {
        return this.color;
    }

    public int getRank() {
        return this.rank;
    }
    public void setRank(int rank) {
        if(rank>=5 || rank<0)
            return;
        else this.rank=rank;
    }
}
