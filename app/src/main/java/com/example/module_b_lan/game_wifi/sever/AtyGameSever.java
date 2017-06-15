package com.example.module_b_lan.game_wifi.sever;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bean.BeanBoard;
import com.example.bean.BeanCell;
import com.example.bean.BeanPlane;
import com.example.bean.BeanRole;
import com.example.module_b_lan.network.broadcast.DataBroaCastSerlied;
import com.example.module_b_lan.network.model.MsgNet;
import com.example.module_b_lan.network.model.sever.ServerInTele;
import com.example.module_b_lan.utils.UtilDeserializable;
import com.example.yifeihappy.planechess.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/3
 */
public class AtyGameSever extends AppCompatActivity {


    public static void startAtyGameSever(Context context, Class<?> cls, String roomColor, String roomIp) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("roomColor", roomColor);
        intent.putExtra("roomIp", roomIp);
        context.startActivity(intent);
    }


    public static final String TAG = "atygamesever";
    protected int PORT = 1188;
    protected int MAXPLAYER = 4;         //玩家数

    public static final int PLANE_TO_START = 6;

    public static final String MOVE_PLANE = "move";
    public static final String MOVE_NO = "moveno";
    public static final String MOVE_END = "moveend";

    public static final String NEXT_ONE = "nextone";
    public static final String TURN_YOU = "turnyou";

    public static final int WHAT_MOVE_NO = 0;
    public static final int WHAT_MOVE_PLAEN = 1;
    public static final int WHAT_MOVE_END = 2;
    public static final int WHAT_ADD_STAR = 3;
    public static final int WHAT_DICE_END = 4;


    private Button mBtnDice;

    private Button mBtnBlue0;
    private Button mBtnBlue1;
    private Button mBtnBlue2;
    private Button mBtnBlue3;


    private Button mBtnRed0;
    private Button mBtnRed1;
    private Button mBtnRed2;
    private Button mBtnRed3;


    private Button mBtnYellow0;
    private Button mBtnYellow1;
    private Button mBtnYellow2;
    private Button mBtnYellow3;


    private Button mBtnGreen0;
    private Button mBtnGreen1;
    private Button mBtnGreen2;
    private Button mBtnGreen3;


    private Button[] mBtnsBlue;
    private Button[] mBtnsRed;
    private Button[] mBtnsYellow;
    private Button[] mBtnsGreen;

    private int[] mIDBlue = new int[]{R.id.btn_blue_0, R.id.btn_blue_1, R.id.btn_blue_2, R.id.btn_blue_3};
    private int[] mIDRed = new int[]{R.id.btn_red_0, R.id.btn_red_1, R.id.btn_red_2, R.id.btn_red_3};
    private int[] mIDYellow = new int[]{R.id.btn_yellow_0, R.id.btn_yellow_1, R.id.btn_yellow_2, R.id.btn_yellow_3,};
    private int[] mIDGreen = new int[]{R.id.btn_green_0, R.id.btn_green_1, R.id.btn_green_2, R.id.btn_green_3};

    @BindView(R.id.btn_roll_blue)
    Button mButtonRollBlue;
    @BindView(R.id.btn_roll_red)
    Button mButtonRollRed;
    @BindView(R.id.btn_roll_yellow)
    Button mButtonRollYellow;
    @BindView(R.id.btn_roll_green)
    Button mButtonRollGreen;
    Button[] rollBtns = null;

    @BindView(R.id.image_star_red_1)
    ImageView mImageViewRed1;
    @BindView(R.id.image_star_red_2)
    ImageView mImageViewRed2;
    @BindView(R.id.image_star_red_3)
    ImageView mImageViewRed3;
    @BindView(R.id.image_star_red_4)
    ImageView mImageViewRed4;
    ImageView[] redStars = null;

    @BindView(R.id.image_blue_star_1)
    ImageView mImageViewBlue1;
    @BindView(R.id.image_blue_star_2)
    ImageView mImageViewBlue2;
    @BindView(R.id.image_blue_star_3)
    ImageView mImageViewBlue3;
    @BindView(R.id.image_blue_star_4)
    ImageView mImageViewBlue4;
    ImageView[] blueStars = null;

    @BindView(R.id.image_yellow_star_1)
    ImageView mImageViewYellow1;
    @BindView(R.id.image_yellow_star_2)
    ImageView mImageViewYellow2;
    @BindView(R.id.image_yellow_star_3)
    ImageView mImageViewYellow3;
    @BindView(R.id.image_yellow_star_4)
    ImageView mImageViewYellow4;
    ImageView[] yellowStars = null;


    @BindView(R.id.image_green_star_1)
    ImageView mImageViewGreen1;
    @BindView(R.id.image_green_star_2)
    ImageView mImageViewGreen2;
    @BindView(R.id.image_green_star_3)
    ImageView mImageViewGreen3;
    @BindView(R.id.image_green_star_4)
    ImageView mImageViewGreen4;
    ImageView[] greenStars = null;


    @BindView(R.id.btn_avatar_blue)
    Button mBtnAvatarBlue;
    @BindView(R.id.btn_avatar_red)
    Button mBtnAvatarRed;
    @BindView(R.id.btn_avatar_yellow)
    Button mBtnAvatarYellow;
    @BindView(R.id.btn_avatar_green)
    Button mBtnAvatarGreen;

    Button[] avatarBtns = null;


    private float mXScale = 0;
    private float mYScale = 0;

    //飞机资源值和实例对象的映射
    private HashMap<Integer, BeanPlane> mIdMap;
    //角色数组
    private List<BeanRole> mBeanRoleList;
    private List<BeanCell> mBeanCellList;
    //色子初始数据


    private int indexPlaneEnd = 0;

    //色子数字
    private int mDice = -1;
    private int mIndex = BeanCell.COLOR_BLUE;
    private int mCurrent = BeanCell.COLOR_BLUE;
    private boolean isFinish = false;


    private ServerInTele mServerInTele;
    private String mRoomIp;
    private String mRoomColor;
    private int mStart;
    private int mEnd;
    private int mIdBtnClicked;
    ThreadSeverGame severGame;
    BeanRole currentRole;

    private int mStars;

    AnimationDrawable mAnimationDrawable;
    Animation mAnimationBtnScale;


    //handler主要处理客户端发来消息的内容会引起UI的变化信息-----------------------------------------------------------------------------------------------------------
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == WHAT_MOVE_PLAEN) {//移动不属于本客户端（服务器）的飞机-------------------------------------------------------------------------------------------------
                startAnimationAndMovePlane(msg, true);
            } else if (what == WHAT_MOVE_NO) {//客户端发来信息的客户端没有启动，但是还是要设置一下色子数字---------------------------------------------------------------
                startAnimationAndMovePlane(msg, false);
            } else if (what == WHAT_MOVE_END) {//接收到客户端的消息，表示客户端已经结束了游戏，所以服务端就不用设置接收线程了---------------------------------------------------------
                startAnimationAndMovePlane(msg, false);
                severGame.setStopThread();
            } else if (what == WHAT_ADD_STAR) {
                int color = Integer.parseInt(mRoomColor);
                if (color == 0) {
                    blueStars[mStars].setImageResource(R.drawable.star_blue);
                } else if (color == 1) {
                    redStars[mStars].setImageResource(R.drawable.star_red);
                } else if (color == 2) {
                    yellowStars[mStars].setImageResource(R.drawable.star_yellow);
                } else if (color == 3) {
                    greenStars[mStars].setImageResource(R.drawable.star_green);
                }
                mStars++;
            } else if (what == WHAT_DICE_END) {
                Random rand = new Random();
                int dice = rand.nextInt(6) + 1;
                mDice = dice;

                //色子动画
                mBtnDice.setBackground(mAnimationDrawable.getFrame(mDice - 1));

                if (currentRole.isAllPlanesInBase()) {
                    Log.d(TAG, "当前用户: " + currentRole.getColor() + " 所有的飞机都在基地");
                    if (mDice == PLANE_TO_START) {
                        currentRole.movePlaneRoadAndBase();
                        Log.d(TAG, "当前用户: " + currentRole.getColor() + " 抛出了启动色子，所有的飞机包括在基地的飞机都可以点击");
                    } else {
                        Log.d(TAG, "当前用户: " + currentRole.getColor() + " 没有抛出启动色子，并且所有的飞机都在基地，没有办法起飞，直接下一位");


                        //向所有的客户端发送消息,消息内容包括行为命令以及下一位用户的index-----------------------------------------------------------------------
                        int mNextRole = (mCurrent + 1) % 4;
                        DataBroaCastSerlied gameDataToAllClients = new DataBroaCastSerlied(MOVE_NO, mRoomIp, mDice, 0, 0, 0, mCurrent, mNextRole);
                        MsgNet msg1 = new MsgNet(gameDataToAllClients.getGameData(), (byte) 0x00);
                        Log.d(TAG, "服务器点击色子但是没有抛出6直接发出的消息内容：" + msg.toString());
                        mServerInTele.sendToAll(msg1);
                        mCurrent = mNextRole;
                        return;
                    }
                } else {
                    Log.d(TAG, "当前用户: " + currentRole.getColor() + " 所有的飞机不都在基地,可以点击不是在基地和终点的飞机");
                    currentRole.movePlaneRoad();
                }

            }


            if (mCurrent == mIndex) {
                toast("请开始你的表演");
                mBtnDice.setClickable(true);
            }
        }
    };

    public void startAnimationAndMovePlane(Message message, final boolean isMove) {
        for (int i = 0; i < rollBtns.length; i++) {
            if (i == message.arg1) {
                rollBtns[i].startAnimation(mAnimationBtnScale);
                break;
            }
        }
        mBtnDice.setBackgroundResource(R.drawable.dice_anim);
        mAnimationDrawable = (AnimationDrawable) mBtnDice.getBackground();
        mAnimationDrawable.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mAnimationDrawable.stop();
                mBtnDice.setBackground(mAnimationDrawable.getFrame(mDice - 1));
                if (isMove) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            movePlane(mIdBtnClicked, mStart, mEnd);
                        }
                    }, 2000);
                }
            }
        }, 2000);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_game_sever);
        ButterKnife.bind(this);

        avatarBtns = new Button[]{mBtnAvatarBlue, mBtnAvatarRed, mBtnAvatarYellow, mBtnAvatarGreen};
        blueStars = new ImageView[]{mImageViewBlue1, mImageViewBlue2, mImageViewBlue3, mImageViewBlue4};
        redStars = new ImageView[]{mImageViewRed1, mImageViewRed2, mImageViewRed3, mImageViewRed4};
        yellowStars = new ImageView[]{mImageViewYellow1, mImageViewYellow2, mImageViewYellow3, mImageViewYellow4};
        greenStars = new ImageView[]{mImageViewGreen1, mImageViewGreen2, mImageViewGreen3, mImageViewGreen4};
        rollBtns = new Button[]{mButtonRollBlue, mButtonRollRed, mButtonRollYellow, mButtonRollGreen};
        findview();

        mAnimationBtnScale = AnimationUtils.loadAnimation(this, R.anim.anim_rollbtn_scale);
        mBtnDice.setBackgroundResource(R.drawable.dice_anim);
        try {
            mServerInTele = ServerInTele.newInstance(MAXPLAYER, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRoomIp = getIntent().getStringExtra("roomIp");
        mRoomColor = getIntent().getStringExtra("roomColor");
        mIndex = Integer.parseInt(mRoomColor);

        if (mIndex == 0) {
            toast("房主的颜色蓝色");

        } else if (mIndex == 1) {
            toast("房主的颜色红色");

        } else if (mIndex == 2) {
            toast("房主的颜色黄色");

        } else if (mIndex == 3) {
            toast("房主的颜色绿色");

        }

        mCurrent = mIndex;


        /*
        假如是将房主颜色的色子变为可点击，其他变为不可点击
         */
        for (int i = 0; i < rollBtns.length; i++) {
            if (i == mIndex) {
                rollBtns[i].setClickable(true);
            } else {
                rollBtns[i].setClickable(false);
                rollBtns[i].setBackground(null);
            }
        }


        mIdMap = new HashMap<>();
        severGame = new ThreadSeverGame();
        severGame.start();
        //比例
        getScale();


        mBeanCellList = BeanBoard.getAllBeanCell();
        mBeanRoleList = BeanBoard.getRoleList();
        initRoleAndPlanes();
        toggleHideyBar();
        currentRole = mBeanRoleList.get(mIndex);
        toast("房主开始");

    }


    /**
     * 点击任意可与点击的飞机
     *
     * @param v
     */
    public void btnOnClick(View v) {
        mIdBtnClicked = v.getId();
        BeanPlane plane = mIdMap.get(mIdBtnClicked);
        if (!plane.isFinish()) {
            plane.setNum(mDice);
            if (!plane.isNormalEnd()) {
                plane.normalMove();
                plane.setStatus(BeanPlane.STATUS_ON_ROAD);
            } else {
                plane.endMove();
                if (plane.isFinish()) {
                    plane.setStatus(BeanPlane.STATUS_IN_END);
                    Message message = new Message();
                    message.what = WHAT_ADD_STAR;
                    mHandler.sendMessage(message);
                }
            }
        }
        indexPlaneEnd = plane.getAfterMoveIndexFinal();
        toast("下一位用户");
        if (currentRole.isAllPlanesInEnd()) {
            //向所有客户端发送游戏结束的消息----------------------------------------------------------------------------------------------------------
            toast("游戏结束！");
            DataBroaCastSerlied gameDataToAllClients = new DataBroaCastSerlied(MOVE_END, mRoomIp, mDice, 0, 0, 0, 0, 0);
            MsgNet msgToSend = new MsgNet(gameDataToAllClients.getGameData(), (byte) 0x00);
            mServerInTele.sendToAll(msgToSend);
            isFinish = true;
        } else {

            //向所有客户端发送位置移动消息，向下一位使用的客户端发送移动位置的消息提醒----------------------------------------------------------------------
            int mNextRole = (mCurrent + 1) % 4;
            mCurrent = mNextRole;
            DataBroaCastSerlied gameDataToAllClients = new DataBroaCastSerlied(MOVE_PLANE, mRoomIp, mDice, mIdBtnClicked, 0, indexPlaneEnd, mCurrent, mNextRole);
            Log.d(TAG, "服务器作为玩家点击按钮后发出的信息包括对下一位的提醒 " + gameDataToAllClients.getGameData());
            MsgNet msgToSend = new MsgNet(gameDataToAllClients.getGameData(), (byte) 0x00);
            mServerInTele.sendToAll(msgToSend);
        }

    }


    @OnClick(R.id.btn_roll_blue)
    public void onBtnRollBlueClick() {
        onRollbtnClick();

    }

    @OnClick(R.id.btn_roll_red)
    public void onBtnRollRedClick() {
        onRollbtnClick();

    }

    @OnClick(R.id.btn_roll_yellow)
    public void onBtnRollYellowClick() {
        onRollbtnClick();

    }

    @OnClick(R.id.btn_roll_green)
    public void onBtnRollGreenClick() {
        onRollbtnClick();

    }

    private void onRollbtnClick() {


        mAnimationDrawable.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mAnimationDrawable.stop();
                Message message = new Message();
                message.what = WHAT_DICE_END;
                mHandler.sendMessage(message);
            }
        }, 2000);

    }

    /**
     * 游戏过程中获取消息的sever-------------------------------------------------------------------------------------------------------
     */

    class ThreadSeverGame extends Thread {
        public Object myLock = new Object();
        private volatile boolean stopThread = false;

        public void setStopThread() {
            stopThread = true;
            this.interrupt();
            mServerInTele.closeAll();
        }

        @Override
        public void run() {
            super.run();
            try {

                while (!stopThread) {
                    //收到消息并处理
                    MsgNet msg = mServerInTele.getData();
                    Message message = mHandler.obtainMessage();
                    DataBroaCastSerlied gameDataGetFromClient = UtilDeserializable.getFromNetMsgData(msg.getData());
                    DataBroaCastSerlied gameDataToAllClients = null;
                    mDice = gameDataGetFromClient.getDice();

                    String tagFromClient = gameDataGetFromClient.getTag();
                    if (mRoomIp.equals(gameDataGetFromClient.getRoomIP())) {
                        if (tagFromClient.equals(MOVE_NO)) { //客户端发来没有投出启动色子的消息

                            Log.d(TAG, "收到 " + mCurrent + " 发来的不动的消息");
                            int mNextRole = (mCurrent + 1) % 4;
                            gameDataToAllClients = new DataBroaCastSerlied(MOVE_NO, mRoomIp, mDice, 0, 0, 0, mCurrent, mNextRole);
                            message.what = WHAT_MOVE_NO;
                            message.arg1 = mCurrent;
                            mCurrent = mNextRole;
                        } else if (tagFromClient.equals(MOVE_PLANE)) {//正常的移动飞机的消息
                            Log.d(TAG, "收到 " + mCurrent + " 正常移动的消息");

                            int mNextRole = (mCurrent + 1) % 4;
                            mIdBtnClicked = gameDataGetFromClient.getIdPlane();
                            mStart = gameDataGetFromClient.getStartIndex();
                            mEnd = gameDataGetFromClient.getEndIndex();

                            //向所有的客户端广播
                            gameDataToAllClients = new DataBroaCastSerlied(MOVE_PLANE, mRoomIp, mDice, mIdBtnClicked, mStart, mEnd, mCurrent, mNextRole);
                            message.what = WHAT_MOVE_PLAEN;
                            message.arg1 = mCurrent;
                            mCurrent = mNextRole;

                        } else if (tagFromClient.equals(MOVE_END)) {
                            gameDataToAllClients = new DataBroaCastSerlied(MOVE_END, mRoomIp, mDice, 0, 0, 0, 0, 0);
                            message.what = WHAT_MOVE_END;
                            message.arg1 = mCurrent;
                        }
                        mHandler.sendMessage(message);
                        MsgNet msgToSend = new MsgNet(gameDataToAllClients.getGameData(), (byte) 0x00);
                        mServerInTele.sendToAll(msgToSend);
                        Log.d(TAG, "服务器在收到其他玩家发出消息后将消息转发到其他玩家并提醒下一位：" + gameDataToAllClients.toString());
                    }

                }
            } catch (InterruptedException e) {

            }
        }

    }

    /**
     * 获取屏幕比例
     */
    private void getScale() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(point);
        float width = point.x;
        float height = point.y;
        mYScale = height / 360;
        mXScale = width / 640;
    }

    private void findview() {
        mBtnDice = (Button) findViewById(R.id.btn_click);
        mBtnDice.setBackgroundResource(R.drawable.dice_anim);
        mAnimationDrawable = (AnimationDrawable) mBtnDice.getBackground();


        mBtnBlue0 = (Button) findViewById(R.id.btn_blue_0);
        mBtnBlue1 = (Button) findViewById(R.id.btn_blue_1);
        mBtnBlue2 = (Button) findViewById(R.id.btn_blue_2);
        mBtnBlue3 = (Button) findViewById(R.id.btn_blue_3);


        mBtnRed0 = (Button) findViewById(R.id.btn_red_0);
        mBtnRed1 = (Button) findViewById(R.id.btn_red_1);
        mBtnRed2 = (Button) findViewById(R.id.btn_red_2);
        mBtnRed3 = (Button) findViewById(R.id.btn_red_3);


        mBtnYellow0 = (Button) findViewById(R.id.btn_yellow_0);
        mBtnYellow1 = (Button) findViewById(R.id.btn_yellow_1);
        mBtnYellow2 = (Button) findViewById(R.id.btn_yellow_2);
        mBtnYellow3 = (Button) findViewById(R.id.btn_yellow_3);

        mBtnGreen0 = (Button) findViewById(R.id.btn_green_0);
        mBtnGreen1 = (Button) findViewById(R.id.btn_green_1);
        mBtnGreen2 = (Button) findViewById(R.id.btn_green_2);
        mBtnGreen3 = (Button) findViewById(R.id.btn_green_3);
        mBtnsBlue = new Button[]{mBtnBlue0, mBtnBlue1, mBtnBlue2, mBtnBlue3};
        mBtnsRed = new Button[]{mBtnRed0, mBtnRed1, mBtnRed2, mBtnRed3};
        mBtnsYellow = new Button[]{mBtnYellow0, mBtnYellow1, mBtnYellow2, mBtnYellow3};
        mBtnsGreen = new Button[]{mBtnGreen0, mBtnGreen1, mBtnGreen2, mBtnGreen3};

    }


    /**
     * 初始化飞机位置
     */
    private void initRoleAndPlanes() {
        int index = 100;
        for (int i = 0; i < mBeanRoleList.size(); i++) {
            BeanRole role = mBeanRoleList.get(i);
            role.setBtnDice(mBtnDice);
            List<BeanPlane> mBeanPlanes = role.getAllPlanes();
            if (i == BeanCell.COLOR_BLUE) {
                for (int j = 0; j < mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, 0, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_BLUE, mBtnsBlue[j], false);
                    setPlaneScale(plane, mXScale, mYScale);
                    mIdMap.put(mIDBlue[j], plane);
                    mBeanPlanes.add(plane);
                    role.setAllPlanes(mBeanPlanes);
                }
            } else if (i == BeanCell.COLOR_RED) {
                for (int j = 0; j < mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, 13, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_RED, mBtnsRed[j], false);
                    mIdMap.put(mIDRed[j], plane);
                    setPlaneScale(plane, mXScale, mYScale);
                    mBeanPlanes.add(plane);
                    role.setAllPlanes(mBeanPlanes);
                }
            } else if (i == BeanCell.COLOR_YELLOW) {
                for (int j = 0; j < mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, 26, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_YELLOW, mBtnsYellow[j], false);
                    mIdMap.put(mIDYellow[j], plane);
                    setPlaneScale(plane, mXScale, mYScale);
                    mBeanPlanes.add(plane);
                    role.setAllPlanes(mBeanPlanes);
                }
            } else if (i == BeanCell.COLOR_GREEN) {
                for (int j = 0; j < mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, 39, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_GREEN, mBtnsGreen[j], false);
                    mIdMap.put(mIDGreen[j], plane);
                    setPlaneScale(plane, mXScale, mYScale);
                    mBeanPlanes.add(plane);
                    role.setAllPlanes(mBeanPlanes);
                }
            }
        }
    }

    /**
     * @param plane
     * @param x
     * @param y
     */

    private void setPlaneScale(BeanPlane plane, float x, float y) {
        plane.setXScale(x);
        plane.setYScale(y);
    }


    /**
     * 隐藏导航栏
     */
    public void toggleHideyBar() {


        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
//
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }


        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        for (int i = 0; i < mBeanRoleList.size(); i++) {
            BeanRole role = mBeanRoleList.get(i);
            List<BeanPlane> planes = role.getAllPlanes();
            for (int j = 0; j < planes.size(); j++) {
                planes.get(j).init();
            }
        }

    }

    public void toast(String str) {

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    //handler中的飞机移动，只需要知道id和结束的位置就行了----------------------------------------------------------------------------------------------------------------
    private void movePlane(int idBtnClicked, int start, int end) {
        BeanPlane plane = mIdMap.get(idBtnClicked);
        Button btn = plane.getBtn();
        BeanCell cell = mBeanCellList.get(end);
        int x = 3;
        float destX = (cell.getX() - x) * mXScale;
        float destY = (cell.getY() - 2 * x) * mYScale;
        btn.setX(destX);
        btn.setY(destY);
    }


}
