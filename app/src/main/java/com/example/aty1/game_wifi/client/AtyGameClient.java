package com.example.aty1.game_wifi.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bean.BeanBoard;
import com.example.bean.BeanCell;
import com.example.bean.BeanPlane;
import com.example.bean.BeanRole;
import com.example.network.broadcast.DataBroaCastSerlied;
import com.example.network.model.MsgNet;
import com.example.network.model.client.Client;
import com.example.utils.UtilDeserializable;
import com.example.yifeihappy.planechess.R;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/3
 */
public class AtyGameClient extends AppCompatActivity {

    public static final String ROLE_COLOR = "roleColor";
    public static final String ROLE_NAMES = "roleNames";
    public static final String ROLE_COLORS = "roleColors";
    public static final String ROLE_ROOMIP = "roomip";


    public static void startAtyGameClient(Context context, Class<?> cls, String roleColor, String roomIp, String roleNames, String roleColors) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(ROLE_COLOR, roleColor);
        intent.putExtra(ROLE_ROOMIP, roomIp);
        intent.putExtra(ROLE_NAMES, roleNames);
        intent.putExtra(ROLE_COLORS, roleColors);
        context.startActivity(intent);
    }


    public static final int SOCKET_PORT = 20000;

    public static final int WHAT_MOVE_NO = 0;
    public static final int WHAT_MOVE_PLAEN = 1;
    public static final int WHAT_MOVE_END = 2;
    public static final int WHAT_ADD_STAR = 3;


    public static final int WHAT_TURN_YOU = 3;

    public static final String TAG = "atygameclient";
    public static final String TAG1 = "";


    public static final int PLANE_TO_START = 6;
    public static final String MOVE_PLANE = "move";
    public static final String MOVE_NO = "moveno";
    public static final String MOVE_END = "moveend";
    public static final String NEXT_ONE = "nextone";
    public static final String TURN_YOU = "turnyou";


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
    private int mCurrent = 0;
    private int mNextRole = 0;
    private int mIndex = 0;
    private boolean isFinish = false;


    private Client mClient;
    private String mRoomIp;
    private int mStart;
    private int mEnd;
    private int mIdBtnClicked;
    private ThreadClientGame mThreadClientGame;
    private int mStars;

    private BeanRole currentRole;


    //房间内所有人员的颜色和名字
    private String mRolesNames;
    private String mRolesColors;
    //房间内颜色和名字的映射
    Map<Integer, String> colorAndName = new HashMap<>();

    //handler主要处理服务器发来消息的内容会引起UI的变化信息-----------------------------------------------------------------------------------------------------------
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == WHAT_MOVE_PLAEN) {//移动不属于本客户端的飞机-------------------------------------------------------------------------------------------------
                // TODO: 2017/6/4 移动棋子
                movePlane(mIdBtnClicked, mStart, mEnd);
                mBtnDice.setText(mDice + " ");
                toast("下一位" + mNextRole + "的回合");
            } else if (what == WHAT_MOVE_NO) {//服务器发来信息的客户端没有启动，但是还是要设置一下色子数字---------------------------------------------------------------
                // TODO: 2017/6/4 移动棋子
                mBtnDice.setText(mDice + " ");
                toast("下一位" + mNextRole + "的回合");

            } else if (what == WHAT_MOVE_END) {//服务器发来的游戏结束的消息，收到消息后进行对应调整，停止客户端的接收线程---------------------------------------------------
                mThreadClientGame.stopGetData();
                // TODO: 2017/6/4
                return;
            } else if (what == WHAT_ADD_STAR) {
                int color = mIndex;
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
            }

            //若服务器附带的消息表明本客户端就是接下来应该表演的客户端，于是提示用户，并进行相应设置，比如设置色子可点击--------------------------------------------------------
            if (mNextRole == mIndex) {
                currentRole = mBeanRoleList.get(mIndex);
                toast("请开始你的表演");
            }
        }
    };

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_game_sever);
        ButterKnife.bind(this);
        findview();


        avatarBtns = new Button[]{mBtnAvatarBlue, mBtnAvatarRed, mBtnAvatarYellow, mBtnAvatarGreen};
        blueStars = new ImageView[]{mImageViewBlue1, mImageViewBlue2, mImageViewBlue3, mImageViewBlue4};
        redStars = new ImageView[]{mImageViewRed1, mImageViewRed2, mImageViewRed3, mImageViewRed4};
        yellowStars = new ImageView[]{mImageViewYellow1, mImageViewYellow2, mImageViewYellow3, mImageViewYellow4};
        greenStars = new ImageView[]{mImageViewGreen1, mImageViewGreen2, mImageViewGreen3, mImageViewGreen4};
        rollBtns = new Button[]{mButtonRollBlue, mButtonRollRed, mButtonRollYellow, mButtonRollGreen};


        mRoomIp = getIntent().getStringExtra(ROLE_ROOMIP);
        mIndex = Integer.parseInt(getIntent().getStringExtra(ROLE_COLOR));

        mRolesColors = getIntent().getStringExtra(ROLE_COLORS);
        mRolesNames = getIntent().getStringExtra(ROLE_NAMES);

        initRolesNames();


        Log.d(TAG, "进入之后获取的mIndex" + mIndex);
        mIdMap = new HashMap<>();

        if (mIndex == BeanCell.COLOR_BLUE) {
            toast("我是蓝色");
        } else if (mIndex == BeanCell.COLOR_RED) {
            toast("我是红色");
        } else if (mIndex == BeanCell.COLOR_YELLOW) {
            toast("我是黄色");
        } else if (mIndex == BeanCell.COLOR_GREEN) {
            toast("我是绿色");
        }
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
        Log.d(TAG, "我就是一条狗");


        mThreadClientGame = new ThreadClientGame();
        mThreadClientGame.start();

        //比例
        getScale();


        mBeanCellList = BeanBoard.getAllBeanCell();
        mBeanRoleList = BeanBoard.getRoleList();


        initRoleAndPlanes();
        toggleHideyBar();

    }

    private void initRolesNames() {

        String[] names = mRolesNames.split("#");
        String[] colors = mRolesColors.split("#");

        for (int i = 0; i < colors.length; i++) {
            int color = Integer.parseInt(colors[i]);
            colorAndName.put(color, names[i]);
        }
        for (int i = 0; i < avatarBtns.length; i++) {
            String name = colorAndName.get(i);
            avatarBtns[i].setText(name);
        }

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
        if (currentRole.isAllPlanesInEnd()) {
            toast("游戏结束！");


            //向服务器发送游戏结束的消息---------------------------------------------------------------------------------------------------------
            DataBroaCastSerlied dataToSever = new DataBroaCastSerlied(MOVE_END, mRoomIp, mDice, 0, 0, 0, 0, 0);
            MsgNet msg = new MsgNet(dataToSever.toString(), (byte) 0x00);
            try {
                mClient.sendToServer(msg);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            isFinish = true;


        } else {

            //向服务器发送本client移动飞机的位置信息--------------------------------------------------------------------------------------------
            DataBroaCastSerlied dataToSever = new DataBroaCastSerlied(MOVE_PLANE, mRoomIp, mDice, mIdBtnClicked, 0, indexPlaneEnd, 0, 0);

            Log.d(TAG, "玩家正常移动飞机向服务器发送消息内容：" + dataToSever.getGameData());
            MsgNet msg = new MsgNet(dataToSever.getGameData(), (byte) 0x00);
            try {
                mClient.sendToServer(msg);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        toast("下一位用户");

    }

    class ThreadClientGame extends Thread {
        public Object myLock = new Object();
        private volatile boolean stopThread = false;

        public void stopGetData() {
            stopThread = true;
            mClient.close();
            this.interrupt();

        }

        @Override
        public void run() {
            super.run();
            try {
                mClient = Client.newInstance(InetAddress.getByName(mRoomIp), SOCKET_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!stopThread) {
                try {


                    MsgNet msg = mClient.getData();
                    Log.d(TAG, "客户端接受到了消息--------------------------" + msg.toString());
                    Log.d(TAG, "再次确认客户端接受到了消息" + msg.toString());
                    DataBroaCastSerlied gameDataFormSever = UtilDeserializable.getFromNetMsgData(msg.getData());
                    Log.d(TAG, "gamedataFromSever:" + gameDataFormSever.getGameData());
                    String roomIpFromSever = gameDataFormSever.getRoomIP();
                    Log.d(TAG, "roomIp:" + roomIpFromSever);
                    String tagFromSever = gameDataFormSever.getTag();
                    Log.d(TAG, "tag:" + tagFromSever);
                    Message message = mHandler.obtainMessage();
                    if (roomIpFromSever.startsWith(mRoomIp)) {
                        if (tagFromSever.startsWith(MOVE_PLANE)) {//接收服务器发来的移动飞机的信息，包括设置色子数字和-飞机位置---------------------------------------------------------------------
                            Log.d(TAG, "客户端接受到了消息移动飞机");

                            message.what = WHAT_MOVE_PLAEN;
                            mDice = gameDataFormSever.getDice();
                            mIdBtnClicked = gameDataFormSever.getIdPlane();
                            mStart = gameDataFormSever.getStartIndex();
                            mEnd = gameDataFormSever.getEndIndex();
                            mNextRole = gameDataFormSever.getNextRole();
                        } else if (tagFromSever.startsWith(MOVE_NO)) {//接收服务器发来的不移动飞机的信息，但是要设置色子的数字---------------------------------------------------------
                            Log.d(TAG, "客户端接受到了消息不移动飞机");

                            message.what = WHAT_MOVE_NO;
                            mDice = gameDataFormSever.getDice();
                            mNextRole = gameDataFormSever.getNextRole();
                        } else if (tagFromSever.startsWith(MOVE_END)) {//接收服务器发来的游戏结束的消息，设置一下色子数---------------------------------------------------------------------
                            message.what = WHAT_MOVE_END;
                            mDice = gameDataFormSever.getDice();
                        }
                    }
                    mHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
//                mBeanPlanes = role.getAllPlanes();
                for (int j = 0; j < mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, 13, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_RED, mBtnsRed[j], false);
                    mIdMap.put(mIDRed[j], plane);
                    setPlaneScale(plane, mXScale, mYScale);
                    mBeanPlanes.add(plane);
                    role.setAllPlanes(mBeanPlanes);
                }
            } else if (i == BeanCell.COLOR_YELLOW) {
//                mBeanPlanes = role.getAllPlanes();
                for (int j = 0; j < mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, 26, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_YELLOW, mBtnsYellow[j], false);
                    mIdMap.put(mIDYellow[j], plane);
                    setPlaneScale(plane, mXScale, mYScale);
                    mBeanPlanes.add(plane);
                    role.setAllPlanes(mBeanPlanes);
                }
            } else if (i == BeanCell.COLOR_GREEN) {
//                mBeanPlanes = role.getAllPlanes();
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

        mDice = 6;
        mBtnDice.setText(mDice + "");

        BeanRole currentRole = mBeanRoleList.get(mIndex);

        if (currentRole.isAllPlanesInBase()) {
            Log.d(TAG, "当前用户: " + currentRole.getColor() + " 所有的飞机都在基地");
            if (mDice == PLANE_TO_START) {
                currentRole.movePlaneRoadAndBase();
                Log.d(TAG, "当前用户: " + currentRole.getColor() + " 抛出了启动色子，所有的飞机包括在基地的飞机都可以点击");
            } else {
                Log.d(TAG, "当前用户: " + currentRole.getColor() + " 没有抛出启动色子，并且所有的飞机都在基地，没有办法起飞，直接下一位");


                //向服务器发送不动的消息------------------------------------------------------------------------------------------------------
                DataBroaCastSerlied dataToSever = new DataBroaCastSerlied(MOVE_NO, mRoomIp, mDice, 0, 0, 0, 0, 0);
                MsgNet msg = new MsgNet(dataToSever.toString(), (byte) 0x00);
                try {
                    mClient.sendToServer(msg);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                return;
            }
        } else {
            Log.d(TAG, "当前用户: " + currentRole.getColor() + " 所有的飞机不都在基地,可以点击不是在基地和终点的飞机");
            currentRole.movePlaneRoad();
        }
//
    }

}
