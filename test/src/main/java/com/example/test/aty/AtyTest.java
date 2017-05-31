package com.example.test.aty;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.bean.BeanBoard;
import com.example.test.bean.BeanCell;
import com.example.test.bean.BeanPlane;
import com.example.test.bean.BeanRole;

import java.util.List;
import java.util.Random;

/**
 * @author XQF
 * @created 2017/5/24
 */
public class AtyTest extends AppCompatActivity {

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


    private Button[] mBtnsBlue = new Button[]{mBtnBlue0, mBtnBlue1, mBtnBlue2, mBtnBlue3};
    private Button[] mBtnsRed = new Button[]{mBtnRed0, mBtnRed1, mBtnRed2, mBtnRed3};
    private Button[] mBtnsYellow = new Button[]{mBtnYellow0, mBtnYellow1, mBtnYellow2, mBtnYellow3};


    private Button[] mBtnsGreen = new Button[]{mBtnGreen0, mBtnGreen1, mBtnGreen2, mBtnGreen3};

    private float mXScale = 0;
    private float mYScale = 0;

    private List<BeanCell> mBeanCellList;
    private List<BeanRole> mBeanRoleList;
    private boolean isFinish = false;

    private int mDice = -1;


    private int mCurrent = BeanCell.COLOR_BLUE;
    private int[] mColors = new int[]{BeanCell.COLOR_BLUE, BeanCell.COLOR_RED, BeanCell.COLOR_YELLOW, BeanCell.COLOR_GREEN};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test);
        //比例
        getScale();


        mBeanCellList = BeanBoard.getAllBeanCell();
        mBeanRoleList = BeanBoard.getRoleList();
        findview();
        initRoleAndPlanes();


        toggleHideyBar();

        /**
         * 抛色子
         */
        mBtnDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rand = new Random();
                mDice = (rand.nextInt(6) + 1);
                mBtnDice.setText(mDice + "");
            }

        });
    }


    //                BeanRole currentRole = mBeanRoleList.get(mCurrent);
//                if (!currentRole.isFinish()) {
//                    currentRole.setNum(temp);
//                    if (!currentRole.isNormalEnd()) {
//                        Log.d("test", "角色：" + mCurrent + "  before normalmove " + currentRole.getCurrentIndex() + " 色子：" + temp);
//                        currentRole.normalMove();
//                        Log.d("test", "角色：" + mCurrent + "  after normalmove " + currentRole.getCurrentIndex());
//                    } else {
//                        currentRole.endMove();
//                        Log.d("test", "角色：" + mCurrent + "  currentIndexInEnd:" + currentRole.getCurrentIndex());
//                        if (currentRole.isFinish() == true) {
//                            isFinish = true;
//                        }
//                    }
//                }
//                if (isFinish == true) {
//                    toast("游戏结束");
//                } else {
//                    mCurrent = (mCurrent + 1) % 4;
//                }

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
    }

    private void initRoleAndPlanes() {
        int index = 100;
        for (int i = 0; i < mBeanRoleList.size(); i++) {
            BeanRole role = new BeanRole(mColors[i]);
            role.setBtnDice(mBtnDice);
            List<BeanPlane> planes = role.getAllPlanes();
            if (i == BeanCell.COLOR_BLUE) {
                for (int j = 0; j <= mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_BLUE, mBtnsBlue[i], false);
                    setScale(plane, mXScale, mYScale);
                    planes.add(plane);
                }
            } else if (i == BeanCell.COLOR_RED) {
                for (int j = 0; j <= mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_RED, mBtnsRed[i], false);
                    setScale(plane, mXScale, mYScale);
                    planes.add(plane);
                }
            } else if (i == BeanCell.COLOR_YELLOW) {
                for (int j = 0; j <= mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_YELLOW, mBtnsYellow[i], false);
                    setScale(plane, mXScale, mYScale);
                    planes.add(plane);
                }
            } else if (i == BeanCell.COLOR_GREEN) {
                for (int j = 0; j <= mBtnsBlue.length; j++) {
                    BeanPlane plane = new BeanPlane(index++, BeanPlane.STATUS_IN_BASE, BeanCell.COLOR_GREEN, mBtnsGreen[i], false);
                    setScale(plane, mXScale, mYScale);
                    planes.add(plane);

                }
            }
        }
    }

    private void getScale() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(point);
        float width = point.x;
        float height = point.y;
        mYScale = height / 360;
        mXScale = width / 640;
    }

    private void setScale(BeanPlane plane, float x, float y) {
        plane.setXScale(x);
        plane.setYScale(y);
    }

    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    public void btnOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_blue_0:
            case R.id.btn_blue_1:
            case R.id.btn_blue_2:
            case R.id.btn_blue_3:

            case R.id.btn_red_0:
            case R.id.btn_red_1:
            case R.id.btn_red_2:
            case R.id.btn_red_3:

            case R.id.btn_yellow_0:
            case R.id.btn_yellow_1:
            case R.id.btn_yellow_2:
            case R.id.btn_yellow_3:

            case R.id.btn_green_0:
            case R.id.btn_green_1:
            case R.id.btn_green_2:
            case R.id.btn_green_3:

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


}
