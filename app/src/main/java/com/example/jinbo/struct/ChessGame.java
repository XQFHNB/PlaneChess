package com.example.jinbo.struct;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
/*
游戏类，主要定义各种规则
 */
public class ChessGame {
    //棋子
    private Chess[] chess = new Chess[16];
    //棋盘
    private Board board = null;
    //骰子
    private Dice dice = null;
    //最多的格子数目
    private int MAX_SQUARE_NUM = this.board.getMaxSquareNum();
    //优先走的棋子，最多4个
    private List<Chess> preferChess = new ArrayList<Chess>();
    private int startNum[];

    //private List<Chess> allchess =   new ArrayList<Chess>();


    /**
     * 构造方法执行各种初始化
     */
    public ChessGame() {
        initChess();
        initBoard();
        initDice();
        initStartNum();
    }


    /*
    初始化棋子，主要是颜色，同时给每个棋子设置位置，用point作为下标来表示棋子的位置
     */
    private void initChess() {
        for (int i = 0; i < 16; i++) {
            if (i >= 0 && i <= 3) {
                chess[i] = new Chess(1);  //Color.green 1
            }
            if (i >= 4 && i <= 7) {
                chess[i] = new Chess(2);    //red 2
            }
            if (i >= 8 && i <= 11) {
                chess[i] = new Chess(3);  //yellow 3
            }
            if (i >= 12 && i <= 15) {
                chess[i] = new Chess(0);     //blue 0
            }
            chess[i].setPoint(i + 100);
        }
    }

    /*
       初始化棋盘
        */
    private void initBoard() {
        this.board = new Board();
    }

    /*
    初始化骰子
     */
    private void initDice() {
        this.dice = new Dice();
    }


    /**
     * 初始化起始数组部分，多种初始化方式
     */

    //默认方式，指定5，6为起始数字
    private void initStartNum() {
        this.startNum[0] = 5;
        this.startNum[1] = 6;
        return;
    }

    //用一个字符串进行初始化
    private void initStartNum(String s) {
        int l = s.length();
        for (int i = 0; i < l; i++) {
            this.startNum[i] = s.charAt(i) - '0';
        }
        return;
    }

    //用一个整型数组进行初始化
    private void initStartNum(int a[]) {
        int l = a.length;
        for (int i = 0; i < l; i++) {
            this.startNum[i] = a[i];
        }
        return;
    }

    /*
   获取棋子列表--位于ChessGame，把16个已经初始化的棋子添加进来
    */
    public List<Chess> getAllchess() {
        List<Chess> list = new ArrayList<Chess>();
        for (int i = 0; i < 16; i++) {
            list.add(this.chess[i]);
        }
        return list;
    }

    /*
   判断棋子是否在终点--位于ChessGame，好奇怪，终点的数字goal是什么意思，实在是没有看懂
   red:65 green:75
   blue:85 yellow:95
    */
    public boolean finished(Chess chess) {
        int goal = -100;
        switch (chess.getColor()) {
            case Color.RED:
                goal = 65;
                break;
            case Color.GREEN:
                goal = 75;
                break;
            case Color.BLUE:
                goal = 85;
                break;
            case Color.YELLOW:
                goal = 95;
                break;
            default:
                goal = -100;
        }
        if (chess.getPoint() == goal) {
            return true;
        } else {
            return false;
        }
    }


    /*
    获取棋盘
     */
    public Board getBoard() {
        return this.board;
    }

    /*
    获取骰子
     */
    public Dice getDice() {
        return this.dice;
    }

    /*
    获取优先棋子列表
     */
    public List<Chess> getPreferChess() {
        return this.preferChess;
    }


    /**
     * 整型数组里面是不是包含数字num,这个方法用来判断dice色子的数字num，是不是起飞幸运数字
     *
     * @param a
     * @param num
     * @return
     */
    public boolean contain(int a[], int num) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == num) {
                return true;
            }
        }
        return false;
    }

    /*
    起飞规则
    @return boolean true or false 成功起飞则true否则false
     */
    public boolean rule_Start(Chess c, int step) {
        //只有在掷得5点或6点，方可将一枚棋子由“基地”起飞至起飞点。
        int prePoint = c.getPoint();
        int aim = 0;
        //判断当前摇出的数字是不是在我们起飞数组列表中
        if (contain(this.startNum, step)) {
            //Chess c=new Chess();//临时用来初始化，需要交互获知用户选择的棋子
            //moveChess(c,1);//临时用来move
            switch (c.getColor()) {
                case Color.RED:
                    aim = 0;//临时量，暂定为0
                    break;
                case Color.GREEN:
                    aim = 16;//临时量，暂定为16
                    break;
                case Color.BLUE:
                    aim = 32;//临时量，暂定为32
                    break;
                case Color.YELLOW:
                    aim = 48;//临时量，暂定为48
                    break;
                default:
                    //c.setPoint(0);
                    //this.board.getSquare(0).addChess(c);
                    break;
            }

            //如果门口有异色迭子挡路，不能起飞

            //board棋盘类获取第aim个格子上的棋子数目大于1（已经有棋子了）且最下面的棋子还是不同的花色
            if (this.board.getSquare(aim).getPlaneList().size() > 1 && this.board.getSquare(aim).getPlaneList().get(0).getColor() != c.getColor()) {
                return false;
            }
            //如果只有一个异色迭子，则撞飞他
            if (this.board.getSquare(aim).getPlaneList().size() == 1 && this.board.getSquare(aim).getPlaneList().get(0).getColor() != c.getColor()) {
                //撞击清空，并重新添加棋子在格子上，把该棋子原来的格子上的棋子最后一个删除
                crash(this.board.getSquare(aim));
                this.board.getSquare(aim).addChess(c);
                c.setPoint(aim);
                this.board.getSquare(prePoint).removeChess();
                return true;
            }
            //要是上面的情况都没有发生，程序进行 到了这里，表示门口什么东西都没有，于是稳稳当当的移到上面来
            //把该棋子原来的格子上的棋子最后一个删除
            this.board.getSquare(aim).addChess(c);
            c.setPoint(aim);
            this.board.getSquare(prePoint).removeChess();
            return true;
        }
        return false;
    }

    /*
    连续掷骰子规则
    @return 如果可以连续掷骰则返回true否则false
     */
    public boolean isContinueRoll() {
        if (dice.rollDice() == 6) {
            return true;
        }
        return false;
    }

    /*
    前方是否有异色迭子？
    @param chess要移动的棋子
    @param step要移动的步数
    @return true有迭子false无迭子
     */
    public boolean Covered(Chess chess, int step) {
        for (int i = 1; i <= step; i++) {
            if (this.board.getSquare((chess.getPoint() + i + MAX_SQUARE_NUM) % MAX_SQUARE_NUM).getPlaneList().size() >= 2 &&
                    this.board.getSquare((chess.getPoint() + i + MAX_SQUARE_NUM) % MAX_SQUARE_NUM).getPlaneList().get(0).getColor() != chess.getColor()) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    /*
    某一格是否有迭子？
    @param square s要检测的方格
    @return true有迭子false无迭子
    只要有重叠的棋子即返回true不论颜色
     */
    public boolean Covered(Square s) {
        if (s.getPlaneList().size() >= 2)
            return true;
        return false;
    }

    /*
    跳跃规则
    @param chess 要进行跳跃的那个棋子
    @return 如果成功跳跃则返回true否则false
     */
    public boolean jump(Chess chess) {
        int step = 4;
        //同色的格子必定相隔4格
        //如果可以移动到下个4格则跳跃成功，否则失败
        //前方4格无迭子才可跳跃
        //位于普通跑道才可跳跃
        if (!Covered(chess, step) && chess.getPoint() < MAX_SQUARE_NUM && chess.getPoint() >= 0) {
            if (moveChess(chess, step)) {
                return true;
            }
        }
        return false;
    }

    /*
    快速通道规则
    @param chess准备经过快速通道的棋子
    @return 成功通过返回true否则false
     */
    public boolean fly(Chess chess) {
        int step = 12;//12是暂定的
        int raceColor = Color.WHITE;
        int chessOnWay = 0;   //挡路的棋子数量
        switch (chess.getColor()) {
            case Color.RED: {
                raceColor = Color.GREEN;
                break;
            }
            case Color.GREEN: {
                raceColor = Color.GREEN;
                break;
            }
            case Color.BLUE: {
                raceColor = Color.YELLOW;
                break;
            }
            case Color.YELLOW: {
                raceColor = Color.RED;
                break;
            }
            default: {
                raceColor = Color.GREEN;
                break;
            }
        }
        //需要检测的格子暂定为下标3的格子
        chessOnWay = this.board.getRaceSquare(raceColor, 3).getPlaneList().size();
        if (chessOnWay == 0 &&
                moveChess(chess, step, this.board.getSquare((chess.getPoint() + step + MAX_SQUARE_NUM) % MAX_SQUARE_NUM))) {
            //如果没有棋子挡路，直接飞过去    //这个时候需要直接飞到目标格子
            return true;
        } else if (chessOnWay == 1) {
            //一个棋子挡路，把他撞飞，自己过去
            crash(this.board.getRaceSquare(raceColor, 3));
            if (moveChess(chess, step, this.board.getSquare((chess.getPoint() + step + MAX_SQUARE_NUM) % MAX_SQUARE_NUM)))
                //这个时候需要直接飞到目标格子
                return true;
        }
        return false;   //多个棋子挡路，那就飞不过去了
    }

    /*
    撞机规则
    @param Square 发生撞机的格子
     */
    public void crash(Square s) {
        //撞机发生的条件：chess要到达s且s只有一个（改规则：或多个）与chess不同色的棋子
        //point=-1返回基地
        //s.getLastChess().setPoint(-1);
        for (int i = 0; i < s.getPlaneList().size(); i++) {

            //拿到格子上的棋子，把point改为-1返回基地
            s.getPlaneList().get(i).setPoint(-1);
        }
        s.clear();
    }

    /*
    move to Square移动到指定一格
    @param chess 要移动的棋子
    @param int position目标格子的下标
     */
    public void moveToSquare(Chess chess, int position) {
        this.board.getSquare(position).addChess(chess);
        this.board.getSquare(chess.getPoint()).removeChess();
        chess.setPoint(position);
        return;
    }

    /*
    移动一个棋子，且可以与前边同色棋子重叠
    @param chess进行移动的棋子
    @param step移动的步数（这里不检测步数是否合法）
    @return 成功移动返回true否则返回false //现在只能return true了
     */
    public boolean moveChess(Chess chess, int step) {
        //当前位置
        int prePoint = chess.getPoint();
        //目标位置
        int nextPoint = (prePoint + step) % MAX_SQUARE_NUM;
        if (prePoint < 0 || prePoint >= 100) {
            //如果不在跑道上，说明在基地
            if (rule_Start(chess, step)) {
                return true;
            } else {
                return false;
            }
        }

        if (prePoint > MAX_SQUARE_NUM) {   //这个IF是用于终点跑道的倒退检测
            //说明这个时候棋子已经脱离普通跑道，进入终点跑道
            int Ed = prePoint / 10;   //得到十位部分
            int Ed_start = Ed * 10;   //得到终点跑道的第一位
            int beyond = -1;      //超出的部分（如果有超出的话）
            nextPoint = prePoint + step;    //这个时候需要改nextPoint，他和普通跑道没关系了，也就不用取余
            if (nextPoint > Ed_start + 5) {
                beyond = nextPoint - (Ed_start + 5);  //比如现在要走到66，超过65一格，则需要后退一格
                nextPoint = Ed_start + 5 - beyond;
                moveToSquare(chess, nextPoint);
            } else {
                //如果没有超过，则只要前进即可
                moveToSquare(chess, nextPoint);
            }
            //最后必定移动成功
            return true;
        } else {
            //迭子出现的位置
            int coverPoint = -1;
            //终点跑道的起点--暂称为基点
            int basePoint = -2;
            //差值，一般情况没用，但如果要到达终点的时候有用，其值为棋子要到达的地方与基点的差值（>=0)
            int dValue = -3;
            //判断基点的位置
            switch (chess.getColor()) {
                case Color.RED:
                    basePoint = 6;
                    break;
                case Color.GREEN:
                    basePoint = 18;
                    break;
                case Color.BLUE:
                    basePoint = 30;
                    break;
                case Color.YELLOW:
                    basePoint = 42;
                    break;
                default:
                    basePoint = -2;
                    break;
            }
            //前方是否存在异色迭子？  --这里顺便检测会不会经过基点：如果经过->说明要到终点了
            for (int i = 1; i <= step; i++) { //如果某一格的棋子数目大于等于2且重叠棋子的颜色与被选择棋子颜色不同，则判断为异色迭子
                if ((prePoint + i + MAX_SQUARE_NUM) % MAX_SQUARE_NUM == basePoint + 1) {    //这个IF用于检测棋子是否要经过基点进入终点跑道
                    //说明这个时候会经过基点且到达基点之前（包括基点）不会有迭子（即不用考虑被迭子撞退）
                    dValue = step - i + 1;        //step>=i>>>>dValue>=1
                    break;
                } else {
                    if (this.board.getSquare((prePoint + i) % MAX_SQUARE_NUM).getPlaneList().size() >= 2
                            && this.board.getSquare((prePoint + i) % MAX_SQUARE_NUM).getPlaneList().get(0).getColor() != chess.getColor()) {
                        coverPoint = (prePoint + i) % MAX_SQUARE_NUM;     //当coverPoint不再是-1时说明出现异色迭子
                    } else
                        continue;
                }
            }
            if (dValue == -3)    //如果dValue没有被修改，则是一般情况
            {
                //当前方（含目标格子）出现异色迭子时，且骰子投出的数字不为6：
                if (coverPoint != -1 && step != 6) {
                    if (nextPoint == (coverPoint - (nextPoint - coverPoint) + MAX_SQUARE_NUM) % MAX_SQUARE_NUM) {
                        //如果刚好是目标格子出现迭子,撞飞，并移动
                        crash(this.board.getSquare(nextPoint));
                        moveToSquare(chess, nextPoint);
                        return true;
                    } else {
                        //修改目标格子
                        nextPoint = (coverPoint - (nextPoint - coverPoint) + MAX_SQUARE_NUM) % MAX_SQUARE_NUM;
                    }
                }
                //如果骰子投出的数字为6
                else if (coverPoint != -1 && step == 6) {
                    //将棋子放在迭子之上
                    nextPoint = coverPoint;
                    //然后这颗棋子下次必须优先走
                    this.preferChess.add(chess);
                }
                //当前方（不含目标格子）无异色迭子：检查目标位置是否被占用？占用的是否与棋子相同颜色？  //当目标格子有异色迭子时，会和上边重复，但是不影响，因为上边会直接return
                else {
                    if (this.board.getSquare(nextPoint).isOccupied() &&
                            //原来这里出现一点错误，现在已经改正
                            this.board.getSquare(nextPoint).getPlaneList().get(0).getColor() != chess.getColor())
                        //如果前边只有一个棋子，直接撞过去
                        if (this.board.getSquare(nextPoint).chessNumber() == 1) {
                            //
                            crash(this.board.getSquare(nextPoint));
                        }
                        //如果不止一个棋子（即目标格子有异色迭子），则不能占用目标格子
                        else //return false;
                        {   //改规则：将所有迭子撞回基地
                            crash(this.board.getSquare(nextPoint));
                        }
                }
                //如果可以移动到目标位置，先占用那个位置，然后改变棋子的位置，然后清空当前位置
                this.board.getSquare(nextPoint).addChess(chess);
                chess.setPoint(nextPoint);
                this.board.getSquare(prePoint).removeChess();
                return true;    //按照修改后的规则，必定能够move成功
            } else {  //如果dValue有被修改过，说明要到终点了，移动棋子到终点跑道
                switch (chess.getColor()) {
                    case Color.RED:
                        moveToSquare(chess, 60 + dValue - 1);
                        break;
                    case Color.GREEN:
                        moveToSquare(chess, 70 + dValue - 1);
                        break;
                    case Color.BLUE:
                        moveToSquare(chess, 80 + dValue - 1);
                        break;
                    case Color.YELLOW:
                        moveToSquare(chess, 90 + dValue - 1);
                        break;
                    default:
                        break;
                }
                return true;
            }
        }
    }

    /*
    moveChess的重载
    @param Chess chess 要移动的棋子
    @param int step移动步数  --为了计算方便
    @param Square s目标格子
    @return true移动成功false移动失败
    在fly的时候会用到
    无视中间是否有迭子，但需要考虑目标格子是否有棋子
     */
    public boolean moveChess(Chess chess, int step, Square s) {
        int prePoint = chess.getPoint();
        int nextPoint = prePoint + step;
        //这里不检测s是否是this.board.getSquare(nextPoint)
        if (s.getPlaneList().size() == 0) {  //如果目标格子没有棋子，直接到达
            s.addChess(chess);
            chess.setPoint(nextPoint);
            this.board.getSquare(prePoint).removeChess();
            return true;
        }
        if (s.getPlaneList().size() == 1 && s.getPlaneList().get(0).getColor() != chess.getColor()) {   //如果目标格子有1个棋子且异色，撞飞他，自己到达
            crash(s);
            s.addChess(chess);
            chess.setPoint(nextPoint);
            this.board.getSquare(prePoint).removeChess();
            return true;
        }
        if (s.getPlaneList().size() >= 1 && s.getPlaneList().get(0).getColor() == chess.getColor()) { //目标格子有1个或多个同色棋子，叠在上边
            s.addChess(chess);
            chess.setPoint(nextPoint);
            this.board.getSquare(prePoint).removeChess();
            return true;
        }
        //目标格子有多个异色棋子，不能到达
        return false;
    }

    /*
    主要游戏过程
     */
    public void mainFunction() {

    }
}
