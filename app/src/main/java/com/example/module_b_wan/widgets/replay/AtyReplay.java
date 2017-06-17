package com.example.module_b_wan.widgets.replay;

import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.example.module_b_wan.utils.DataText;
import com.example.module_b_wan.widgets.AtyBase;
import com.example.module_b_wan.widgets.replay.table.GameRecords;
import com.example.module_b_wan.widgets.replay.table.RoleInfo;
import com.example.yifeihappy.planechess.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/16
 */
public class AtyReplay extends AtyBase {


    private List<GameRecords> mGameRecordses;
    private List<RoleInfo> mRoleInfos;

    public static final int DICE_END = 1;
    public static final int DICE_BTN_CLIK = 2;

    public static final String TAG = "test";
    public static final String TAG1 = "robot";


    public static final String TAG_GAME = "game";
    public static final String TAG_FIRST = "first";
    public static final String TAG_BEGIN = "begin";


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


    private HashMap<Integer, BeanPlane> mIdMap;
    private List<BeanCell> mBeanCellList;
    private List<BeanRole> mBeanRoleList;
    private boolean isFinish = false;

    private int mDice = -1;
    private BeanRole mCurrentRole;

    private String mRolesNames;
    private String mRolesColors;
    private int mNextRole = 0;


    private int mStart;
    private int mEnd;
    private int mIdBtnClicked;
    private int mStars;

    AnimationDrawable mAnimationDrawable;
    Animation mAnimationBtnScale;


    //房间内颜色和名字的映射
    Map<Integer, String> colorAndName = new HashMap<>();


    private int mCurrent = BeanCell.COLOR_BLUE;
    private int[] mColors = new int[]{BeanCell.COLOR_BLUE, BeanCell.COLOR_RED, BeanCell.COLOR_YELLOW, BeanCell.COLOR_GREEN};


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_MOVE_PLAEN) {
                movePlane(mIdBtnClicked, mStart, mEnd);
            }
        }


    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_game_sever);
        mGameRecordses = DBHelper.queryDataGameRecords();
        mRoleInfos = DBHelper.queryDataRoleInfos();

        avatarBtns = new Button[]{mBtnAvatarBlue, mBtnAvatarRed, mBtnAvatarYellow, mBtnAvatarGreen};
        blueStars = new ImageView[]{mImageViewBlue1, mImageViewBlue2, mImageViewBlue3, mImageViewBlue4};
        redStars = new ImageView[]{mImageViewRed1, mImageViewRed2, mImageViewRed3, mImageViewRed4};
        yellowStars = new ImageView[]{mImageViewYellow1, mImageViewYellow2, mImageViewYellow3, mImageViewYellow4};
        greenStars = new ImageView[]{mImageViewGreen1, mImageViewGreen2, mImageViewGreen3, mImageViewGreen4};
        rollBtns = new Button[]{mButtonRollBlue, mButtonRollRed, mButtonRollYellow, mButtonRollGreen};

        toggleHideyBar();
        //加载点击色子按钮的动画布局动画布局
        mAnimationBtnScale = AnimationUtils.loadAnimation(this, R.anim.anim_rollbtn_scale);

        findview();


        mIdMap = new HashMap<>();
        //比例
        getScale();
        mBeanCellList = BeanBoard.getAllBeanCell();
        mBeanRoleList = BeanBoard.getRoleList();
        initRoleAndPlanes();
        initRolesNames();

        for (int i = 0; i < mGameRecordses.size(); i++) {
            GameRecords gameRecords = mGameRecordses.get(i);
            String content = gameRecords.getContent();
            DataText text = DataText.getDataTextFromString(content);

            if (text.getTag().equals(TAG_GAME)) {
                //放进数据库

                if (text.getGameTag().equals(MOVE_NO)) {
                    mDice = text.getGameDice();
                    mCurrent = text.getGameCurrent();
                    mNextRole = text.getGameNextRole();

                    startAnimationAndMovePlane(mCurrent, false);

                } else if (text.getGameTag().equals(MOVE_END)) {
                    mDice = text.getGameDice();
                    mCurrent = text.getGameCurrent();

                    startAnimationAndMovePlane(mCurrent, false);

                } else if (text.getGameTag().equals(MOVE_PLANE)) {
                    mDice = text.getGameDice();
                    mCurrent = text.getGameCurrent();
                    mNextRole = text.getGameNextRole();
                    mIdBtnClicked = text.getGameBtnClickId();
                    mStart = text.getGameStart();
                    mEnd = text.getGameEnd();

                    startAnimationAndMovePlane(mCurrent, true);

                }
            }
        }

    }


    public void startAnimationAndMovePlane(int current, final boolean isMove) {
        for (int i = 0; i < rollBtns.length; i++) {
            if (i == current) {
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
                            mHandler.sendEmptyMessage(WHAT_MOVE_PLAEN);
                        }
                    }, 2000);
                }
            }
        }, 2000);
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

        RoleInfo roleInfo = mRoleInfos.get(0);
        mRolesNames = roleInfo.getNames();
        mRolesColors = roleInfo.getColors();


        int index = 100;
        for (int i = 0; i < mBeanRoleList.size(); i++) {
            BeanRole role = mBeanRoleList.get(i);
            role.setMandatory(true);
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


}
