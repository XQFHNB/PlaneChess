package com.example.module_b_lan.game_classic;

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
import com.example.bean.BeanRobot;
import com.example.bean.BeanRole;
import com.example.yifeihappy.planechess.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/5
 */
public class AtyGameClassic extends AppCompatActivity {

    public static final int LEVEL_EASY = 0;
    public static final int LEVEL_MID = 1;
    public static final int LEVEL_DIFF = 2;

    public static final int START_NUM_0 = 0;
    public static final int START_NUM_1 = 1;
    public static final int START_NUM_2 = 3;

    public static void startAtyGameClassic(Context context, Class<?> cls, int level, int startNum) {

        Intent intent = new Intent(context, cls);
        intent.putExtra("level", level);
        intent.putExtra("startnum", startNum);
        context.startActivity(intent);

    }

    public static final int PLANE_TO_START = 6;
    public static final String TAG = "test";
    public static final String TAG1 = "robot";

    public static final int DICE_END = 1;
    public static final int DICE_BTN_CLIK = 2;

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


    private HashMap<Integer, BeanPlane> mIdMap;
    private List<BeanCell> mBeanCellList;
    private List<BeanRole> mBeanRoleList;
    private boolean isFinish = false;

    private int mDice = -1;
    private BeanRole mCurrentRole;


    private int mCurrent = BeanCell.COLOR_BLUE;
    private int[] mColors = new int[]{BeanCell.COLOR_BLUE, BeanCell.COLOR_RED, BeanCell.COLOR_YELLOW, BeanCell.COLOR_GREEN};

    Animation mAnimationBtnScale;

    BeanRobot mRobot = new BeanRobot();
    private int mLevel;
    private int mStartNum;

    private HashSet<Integer> mStartNums;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == DICE_END) {
                AnimationDrawable anim = (AnimationDrawable) mBtnDice.getBackground();
                mDice = getRandNumByLevel(mLevel);
                Log.d(TAG1, "抛出的点数:" + mDice);
                mCurrentRole = mBeanRoleList.get(mCurrent);
                mBtnDice.setBackground(anim.getFrame(mDice - 1));
                if (mCurrentRole.isAllPlanesInBase()) {
                    Log.d(TAG, "当前用户: " + mCurrentRole.getColor() + " 所有的飞机都在基地");
                    if (mStartNums.contains(mDice)) {
                        // TODO: 2017/6/1 飞机除了已经结束的全部可点击
                        mCurrentRole.movePlaneRoadAndBase();
                        Log.d(TAG, "当前用户: " + mCurrentRole.getColor() + " 抛出了启动色子，所有的飞机包括在基地的飞机都可以点击");
                    } else {
                        Log.d(TAG, "当前用户: " + mCurrentRole.getColor() + " 没有抛出启动色子，并且所有的飞机都在基地，没有办法起飞，直接下一位");
                        mCurrent = (mCurrent + 1) % 4;
                        mCurrentRole = mBeanRoleList.get(mCurrent);

                        /**
                         * 假如是机器人的话就直接2s后发送色子被点击消息
                         */
                        if (mCurrentRole.isMandatory()) {
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    mHandler.sendEmptyMessage(DICE_BTN_CLIK);
                                }
                            }, 2000);

                        }
                        return;
                    }
                } else {
                    // TODO: 2017/6/1 获取所有不在基地的飞机可点击
                    Log.d(TAG, "当前用户: " + mCurrentRole.getColor() + " 所有的飞机不都在基地,可以点击不是在基地和终点的飞机");
                    if (mStartNums.contains(mDice)) {
                        // TODO: 2017/6/1 飞机除了已经结束的全部可点击
                        mCurrentRole.movePlaneRoadAndBase();
                        Log.d(TAG, "当前用户: " + mCurrentRole.getColor() + " 抛出了启动色子，所有的飞机包括在基地的飞机都可以点击");
                    } else {
                        mCurrentRole.movePlaneRoad();
                    }
                }
                if (mCurrentRole.isMandatory()) {
                    mRobot.robotClickPlane(mCurrentRole);
                }
                if (mCurrentRole.isAllPlanesInEnd()) {
                    toast("游戏结束！");
                    isFinish = true;
                }
            } else if (msg.what == DICE_BTN_CLIK) {
                if (mCurrent == BeanCell.COLOR_RED) {
                    mRobot.robotClickRollBtn(mButtonRollRed);
                } else if (mCurrent == BeanCell.COLOR_YELLOW) {
                    mRobot.robotClickRollBtn(mButtonRollYellow);
                } else if (mCurrent == BeanCell.COLOR_GREEN) {
                    mRobot.robotClickRollBtn(mButtonRollGreen);
                }
            }
        }

    };


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

        //加载点击色子按钮的动画布局动画布局
        mAnimationBtnScale = AnimationUtils.loadAnimation(this, R.anim.anim_rollbtn_scale);


        mLevel = getIntent().getIntExtra("level", 0);
        mStartNum = getIntent().getIntExtra("startnum", 0);

        mStartNums = new HashSet<>();
        if (mStartNum == START_NUM_0) {
            mStartNums.add(2);
            mStartNums.add(5);
            mStartNums.add(6);
        } else if (mStartNum == START_NUM_1) {
            mStartNums.add(5);
            mStartNums.add(6);
        } else if (mStartNum == START_NUM_2) {
            mStartNums.add(6);
        }
        for (int i = 0; i < avatarBtns.length; i++) {
            Button btn = avatarBtns[i];
            if (i == 0) {
                btn.setText("YOU");
            } else {
                btn.setText("Robot " + i);
            }
        }

        findview();


        mIdMap = new HashMap<>();
        //比例
        getScale();
        mBeanCellList = BeanBoard.getAllBeanCell();
        mBeanRoleList = BeanBoard.getRoleList();
        initRoleAndPlanes();
        toggleHideyBar();

        mCurrentRole = mBeanRoleList.get(mCurrent);

    }


    private void findview() {
        mBtnDice = (Button) findViewById(R.id.btn_click);
        mBtnDice.setBackgroundResource(R.drawable.dice_anim);


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
            if (i != 0) {
                role.setMandatory(true);
            }
            role.setBtnDice(mBtnDice);
            List<BeanPlane> mBeanPlanes = role.getAllPlanes();
            if (i == BeanCell.COLOR_BLUE) {
                role.setBeanStars(blueStars);
                for (int j = 0; j < mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, 0, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_BLUE, mBtnsBlue[j], false);
                    setPlaneScale(plane, mXScale, mYScale);
                    mIdMap.put(mIDBlue[j], plane);
                    mBeanPlanes.add(plane);
                    role.setAllPlanes(mBeanPlanes);
                }
            } else if (i == BeanCell.COLOR_RED) {
                role.setBeanStars(redStars);

                for (int j = 0; j < mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, 13, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_RED, mBtnsRed[j], false);
                    mIdMap.put(mIDRed[j], plane);
                    setPlaneScale(plane, mXScale, mYScale);
                    mBeanPlanes.add(plane);
                    role.setAllPlanes(mBeanPlanes);
                }
            } else if (i == BeanCell.COLOR_YELLOW) {
                role.setBeanStars(yellowStars);
                for (int j = 0; j < mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, 26, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_YELLOW, mBtnsYellow[j], false);
                    mIdMap.put(mIDYellow[j], plane);
                    setPlaneScale(plane, mXScale, mYScale);
                    mBeanPlanes.add(plane);
                    role.setAllPlanes(mBeanPlanes);
                }
            } else if (i == BeanCell.COLOR_GREEN) {
                role.setBeanStars(greenStars);

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

    /**
     * @param plane
     * @param x
     * @param y
     */

    private void setPlaneScale(BeanPlane plane, float x, float y) {
        plane.setXScale(x);
        plane.setYScale(y);
    }

    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    /**
     * 点击任意可与点击的飞机
     *
     * @param v
     */
    public void btnOnClick(View v) throws InterruptedException {
        int id = v.getId();
        BeanPlane plane = mIdMap.get(id);
        if (!plane.isFinish()) {
            plane.setNum(mDice);
            if (!plane.isNormalEnd()) {
                plane.normalMove();
                plane.setStatus(BeanPlane.STATUS_ON_ROAD);
            } else {
                plane.endMove();
                if (plane.isFinish()) {
                    plane.setStatus(BeanPlane.STATUS_IN_END);

                    //设置星星之类
                    List<BeanPlane> planesInEnd = mCurrentRole.getBeanPlanesInEnd();
                    for (int i = 0; i < planesInEnd.size(); i++) {
                        int color = mCurrentRole.getColor();
                        if (color == BeanCell.COLOR_BLUE) {
                            mCurrentRole.getBeanStars()[i].setBackgroundResource(R.drawable.star_blue);
                        } else if (color == BeanCell.COLOR_RED) {
                            mCurrentRole.getBeanStars()[i].setBackgroundResource(R.drawable.star_red);
                        } else if (color == BeanCell.COLOR_YELLOW) {
                            mCurrentRole.getBeanStars()[i].setBackgroundResource(R.drawable.star_yellow);
                        } else if (color == BeanCell.COLOR_GREEN) {
                            mCurrentRole.getBeanStars()[i].setBackgroundResource(R.drawable.star_green);
                        }
                    }
                }
            }
        }
        toast("下一位！");
        mCurrentRole.falseClickAllPlanes();
        mCurrent = (mCurrent + 1) % 4;
        mCurrentRole = mBeanRoleList.get(mCurrent);


        /**
         * 假如是机器人的话就直接2s后发送色子被点击消息
         */
        if (mCurrentRole.isMandatory()) {

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(DICE_BTN_CLIK);
                }
            }, 2000);


        }
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

    @OnClick(R.id.btn_roll_blue)
    public void onBtnRollBlueClick() {
        onRollbtnClick();
        mButtonRollBlue.startAnimation(mAnimationBtnScale);
    }

    @OnClick(R.id.btn_roll_red)
    public void onBtnRollRedClick() {
        onRollbtnClick();
        mButtonRollRed.startAnimation(mAnimationBtnScale);

    }

    @OnClick(R.id.btn_roll_yellow)
    public void onBtnRollYellowClick() {
        onRollbtnClick();
        mButtonRollYellow.startAnimation(mAnimationBtnScale);

    }

    @OnClick(R.id.btn_roll_green)
    public void onBtnRollGreenClick() {
        onRollbtnClick();
        mButtonRollGreen.startAnimation(mAnimationBtnScale);
    }

    private void onRollbtnClick() {
        mBtnDice.setBackgroundResource(R.drawable.dice_anim);
        final AnimationDrawable anim = (AnimationDrawable) mBtnDice.getBackground();
        anim.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                anim.stop();
                mHandler.sendEmptyMessage(DICE_END);
            }
        }, 2000);

    }


    private int getRandNumByLevel(int level) {
        Random rand = new Random();
        int a = 0;
        if (level == LEVEL_EASY) {
            a = rand.nextInt(6);
            if (a <= 1) {
                a = 2;
            }
        } else if (level == LEVEL_DIFF) {
            a = rand.nextInt(10);
            a %= 6;
        }
        return a + 1;
    }
}
