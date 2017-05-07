package com.example.jinbo.struct;

import java.util.Random;

/**
 * Created by Administrator on 2016/3/21.
 */
/*
骰子
 */
public class Dice {
    private int number=1;
    public Dice() {
        this.number=1;
    }
    public void setNumber(int num) {
        if(num<1 || num>6) {
            this.number=1;
            return;
        }
        this.number=num;
    }
    /*
    获取骰子的数字
     */
    public  int getNumber() {
        return this.number;
    }
    /*
    生成随机数，模拟掷骰的过程
     */
    public int rollDice() {
        //produce a random number
        Random random=new Random();
        int res=random.nextInt(1000)%6;
        this.number=res+1;
        return this.number;
    }
}
