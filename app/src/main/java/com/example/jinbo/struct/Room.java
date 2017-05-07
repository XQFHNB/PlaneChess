package com.example.jinbo.struct;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/26.
 */
public class Room {

    public boolean ismove;

    public ChessGame game=new ChessGame();

    private int player_num=4;

    private List<Player> players=new ArrayList<Player>();
    //是否结束的标志，false未结束
    public boolean Over=false;
    //轮到谁的回合
    public int turn=0;
    //是否跳过当前自己的回合
    public boolean pass=false;
    //名次
    private int rank=1;

    public int start;

    public int end;

    private Chess chosenChess = null;

    public void setchosechess(Chess chess)
    {
        chosenChess = chess;
    }

    public Room() {
        this.player_num=0;
    }
    public Room(int num) {
        this.player_num=num;
        for(int i=0;i<num;i++) {
            this.players.add(new Player());
        }
    }

    /*新增：myChess(Player) --位于Room
    @param Player 选择棋子的玩家
    @return List<Chess> list返回这个玩家控制的棋子的列表
     */
    public List<Chess> myChess(Player player) {
        int c = player.getColor();
        List<Chess> list=new ArrayList<Chess>();
        for(int i=0;i<16;i++) {
            if(this.game.getAllchess().get(i).getColor() == c){
                list.add(this.game.getAllchess().get(i));
            }
        }
        return list;
    }

    public Room(int num, List<Player> players) {
        if(num!=players.size()) {
            this.player_num=0;
            this.players=null;
        }
        else {
            this.player_num=num;
            this.players=players;
        }
    }

    //新增：构造方法
    public Room(List<Player> players) {
        if(players!=null) {
            this.player_num = players.size();
            this.players = players;
        }
        else {
            this.player_num=0;
            this.players=null;
        }
    }
    //end

    //新增
    public void PlayGame() {
        while(!Over) {
            //玩家点击掷骰
            int step=this.game.getDice().rollDice();
            //设置一个flag：canPass表明能不能pass
            boolean canPass=true;
            if(step==6) {  //如果是6，一定要走
                canPass=false;
                pass=false;
            }
            //当所有棋子都在基地 且
            int playColor=players.get(turn).getColor(); //玩家的颜色
            for(int i=0;i<4;i++) {
                if(myChess(players.get(turn)).get(i).getPoint() != -1 &&
                        !this.game.finished(myChess(players.get(turn)).get(i)) ) { //只要有棋子不在基地且不在终点
                    canPass=true;
                }
            }
            if(canPass) {
                //如果玩家可以按pass 再来检测有没有按  -- 给PASS赋值
            }


            //查看玩家是否按了pass
            if(pass)
            {
                pass=false;
                if(step != 6) {
                    turn = (turn + 1) % this.player_num;
                }
                continue;
            }

            //如果有需要有优先走的棋子
            if(this.game.getPreferChess().size() > 0) {
                for(int i=0;i<this.game.getPreferChess().size();i++) {
                    if(this.game.getPreferChess().get(i).getColor() == players.get(turn).getColor()) {
                        chosenChess=this.game.getPreferChess().get(i);      //这个时候玩家就不用选择棋子了
                        //preferChess里边最多应该只有一个棋子？那么在被选择完之后他的任务就结束了，清空
                        this.game.getPreferChess().clear();
                    }
                    else continue;
                }
            }
            //没有优先的棋子（则此时chosenChess还是null），则通过UI获取玩家点击了哪个棋子
            if(chosenChess == null) {

                while (players.get(turn).getColor() != chosenChess.getColor()
                        || !this.game.moveChess(chosenChess,step)) {
                    //如果玩家点击了其他颜色的棋子需要重新选择
                }
            }
            ismove = false;
            //移动棋子
            if(this.game.moveChess(chosenChess,step)) {
                ismove = true;
                //UI添加动画
            }
/************************************这一部分可能要修改*****************************************/
            if(this.game.getBoard().getSquare(chosenChess.getPoint()).getColor() == chosenChess.getColor()) {
                //如果走到的格子的颜色与棋子颜色相同
                if(chosenChess.getPoint()==8 && chosenChess.getColor()==Color.RED) {    //如果特定颜色走到特定格子，需要先跳再飞
                    if(this.game.jump(chosenChess)) {
                        //UI添加跳跃动画
                        if(this.game.fly(chosenChess)) {
                            //UI添加快速通道动画
                        }
                    }
                }
                //以下与上边这一部分相同，只是chosenChess的颜色要改
                else if(chosenChess.getPoint()==16 && chosenChess.getColor()==Color.GREEN) {    //如果特定颜色走到特定格子，需要先跳再飞
                    if(this.game.jump(chosenChess)) {
                        //UI添加跳跃动画
                        if(this.game.fly(chosenChess)) {
                            //UI添加快速通道动画
                        }
                    }
                }
                else if(chosenChess.getPoint()==24 && chosenChess.getColor()==Color.BLUE) {    //如果特定颜色走到特定格子，需要先跳再飞
                    if(this.game.jump(chosenChess)) {
                        //UI添加跳跃动画
                        if(this.game.fly(chosenChess)) {
                            //UI添加快速通道动画
                        }
                    }
                }
                else if(chosenChess.getPoint()==32 && chosenChess.getColor()==Color.YELLOW) {    //如果特定颜色走到特定格子，需要先跳再飞
                    if(this.game.jump(chosenChess)) {
                        //UI添加跳跃动画
                        if(this.game.fly(chosenChess)) {
                            //UI添加快速通道动画
                        }
                    }
                }
                //到这里，结束先跳再飞的代码
                //以下为先飞再跳的部分
                else if(chosenChess.getPoint()==12 && chosenChess.getColor()==Color.RED) {    //如果特定颜色走到特定格子，需要先飞再跳
                    if(this.game.fly(chosenChess)) {
                        //UI添加快速通道动画
                        if(this.game.jump(chosenChess)) {
                            //UI添加跳跃动画
                        }
                    }
                }
                //以下与上边这一部分相同，只是chosenChess的颜色要改
                else if(chosenChess.getPoint()==20 && chosenChess.getColor()==Color.GREEN) {    //如果特定颜色走到特定格子，需要先飞再跳
                    if(this.game.fly(chosenChess)) {
                        //UI添加快速通道动画
                        if(this.game.jump(chosenChess)) {
                            //UI添加跳跃动画
                        }
                    }
                }
                else if(chosenChess.getPoint()==28 && chosenChess.getColor()==Color.BLUE) {    //如果特定颜色走到特定格子，需要先飞再跳
                    if(this.game.fly(chosenChess)) {
                        //UI添加快速通道动画
                        if(this.game.jump(chosenChess)) {
                            //UI添加跳跃动画
                        }
                    }
                }
                else if(chosenChess.getPoint()==36 && chosenChess.getColor()==Color.YELLOW) {    //如果特定颜色走到特定格子，需要先飞再跳
                    if(this.game.fly(chosenChess)) {
                        //UI添加快速通道动画
                        if(this.game.jump(chosenChess)) {
                            //UI添加跳跃动画
                        }
                    }
                }
                //到这里，结束先飞再跳的代码
                else {
                    if(this.game.jump(chosenChess)) {
                        //UI添加动画，只有跳跃
                    }
                }
            }
/****************************************************************************/
            //如果当前玩家所有棋子到达终点，则给予名次，并移除
            if(this.game.getBoard().getRaceSquare(players.get(this.turn).getColor(),5).chessNumber() == 4) {
                players.get(turn).setRank(this.rank);
                this.rank++;
                players.remove(turn);
                this.player_num=players.size();
            }
            if(this.players.size()<=1){ //当只有一个玩家还没有到达终点时，游戏结束
                Over=true;
            }
            //如果不是6，轮到下个玩家
            if(step != 6) {
                turn = (turn + 1) % this.player_num;
            }
        }
    }
    //end

    public boolean isFull() {
        return this.players.size()>=4;  //如果4人则是满员
    }

    public int getPlayerNum() {
        return this.players.size();
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}
