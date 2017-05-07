package com.example.jinbo.struct;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/3/21.
 */
/*
棋盘类，由若干个格子构成
 */
public class Board {
    //最大的方格数目，256是随意添加的数字
    private static int MAX_SQUARE_NUM=52;
    //方格列表
    //0~51为外围轨道，60~65为红色跑道，70~75为绿色跑道，80~85为蓝色跑道，90~95为黄色跑道，其他为白色格子且不能被使用
    private Square[] square=new Square[100];

    //新增加：跑道
    //private Square[] redRace=new Square[6];
    //private Square[] greenRace=new Square[6];
    //private Square[] blueRace=new Square[6];
    //private Square[] yellowRace=new Square[6];
    //end

    /*
    默认构造方法
     */
    public Board() {
        //initSquare();
    }
    //获取最大方格数量
    /*
    @return int 数量
     */
    public int getMaxSquareNum() {
        return this.MAX_SQUARE_NUM;
    }

    /*
    将方格列表初始化（颜色、方向）
     */
    private void initSquare(float x1, float x2, float y1, float y2) {
        //设置颜色
        for(int i=0; i<52; i++) {
            switch (i%4) {
                case 0:
                    square[i]=new Square(Color.BLUE);
                    break;
                case 1:
                    square[i]=new Square(Color.GREEN);
                    break;
                case 2:
                    square[i]=new Square(Color.RED);
                    break;
                case 3:
                    square[i]=new Square(Color.YELLOW);
                    break;
                default:
                    square[i]=new Square(Color.WHITE);
                    break;
            }
            //初始化方向
            //向右
            if((i>=13 && i<=15) || (i>=20 && i<=25) || (i>=29 && i<=32))
                square[i].setDirection(0);
            //向上
            if((i>=0 && i<=2) || (i>=7 && i<=12) || (i>=16 && i<=19))
                square[i].setDirection(1);
            //向左
            if((i>=3 && i<=6) || (i>=39 && i<=41) || (i>=46 && i<=51))
                square[i].setDirection(2);
            //向下
            if((i>=26 && i<=28) || (i>=33 && i<=38) || (i>=42 && i<=45))
                square[i].setDirection(3);
//初始化x,y
        }

        //新增加：初始化跑道，等图确定下来需要增加方向
        for (int i=0; i<6;i++) {
            //redRace[i]=new Square(Color.RED);
            //greenRace[i]=new Square(Color.GREEN);
            //blueRace[i]=new Square(Color.BLUE);
            //yellowRace[i]=new Square(Color.YELLOW);
            square[60+i]=new Square(Color.RED);
            square[70+i]=new Square(Color.GREEN);
            square[80+i]=new Square(Color.BLUE);
            square[90+i]=new Square(Color.YELLOW);
        }
        //end

        return;
    }
    /*
    获取方格列表中的某一个元素（某一个方格）
    @return Square 获取到的方格
     */
    public Square getSquare(int point) {
        return this.square[point];
    }
    /*
    返回跑道的某一格
     */
    public Square getRaceSquare(int color, int point) {
        if(point<0 || point>6)
            return this.square[0];
        switch (color) {
            case Color.RED:
                return this.square[60+point];
            case Color.GREEN:
                return this.square[70+point];
            case Color.BLUE:
                return this.square[80+point];
            case Color.YELLOW:
                return this.square[90+point];
            default:
                return this.square[0];
        }
    }
}
