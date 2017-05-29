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
import com.example.test.bean.BeanRole;

import java.util.List;
import java.util.Random;

/**
 * @author XQF
 * @created 2017/5/24
 */
public class AtyTest extends AppCompatActivity {

    private Button mBtnTest1;
    private Button mBtnTest2;
    private Button mBtnTest3;
    private Button mBtnTest4;

    private Button mBtnClick;
    private float mXScale = 0;
    private float mYScale = 0;
    private int step = 0;
    private List<BeanCell> mBeanCellList;
    private List<BeanRole> mBeanRoleList;
    private Button[] mPlanes;

    final int x = 3;
    final int y = 2 * x;

    private int mCurrent = BeanCell.COLOR_BLUE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test);
        mBeanCellList = BeanBoard.getAllBeanCell();
        mBeanRoleList = BeanRole.getRoleList();
        mBtnTest1 = (Button) findViewById(R.id.btn_test1);
        mBtnTest2 = (Button) findViewById(R.id.btn_test2);
        mBtnTest3 = (Button) findViewById(R.id.btn_test3);
        mBtnTest4 = (Button) findViewById(R.id.btn_test4);
        mPlanes = new Button[]{mBtnTest1, mBtnTest2, mBtnTest3, mBtnTest4};

        for (int i = 0; i < mBeanRoleList.size(); i++) {
            mBeanRoleList.get(i).setBtn(mPlanes[i]);
        }


        mBtnClick = (Button) findViewById(R.id.btn_click);
        toggleHideyBar();

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(point);
//        Log.d("test", "the screen real size is " + point.toString());
        float width = point.x;
        mXScale = width / 640;
        float height = point.y;
        mYScale = height / 360;

//        Log.d("test", "the screen real size is " + mXScale + "  " + mYScale);


        mBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rand = new Random();
                int temp = (rand.nextInt(6) + 1);
                step = mBeanRoleList.get(mCurrent).getCurrentIndex();
                Log.d("test", "当前角色:" + mCurrent + "  移动前的位置  " + step + " 色子点数：" + temp);

                step = BeanBoard.getRealStep(step, temp, mCurrent);
                mBeanRoleList.get(mCurrent).setCurrentIndex(step);
                Log.d("test", "当前角色:" + mCurrent + "  移动后的位置  " + step);
                mBtnClick.setText(temp + " ");
                Button btn = mBeanRoleList.get(mCurrent).getBtn();
                if (mCurrent == BeanCell.COLOR_BLUE) {
                    if (step < 48 || step >= 60) {
                        move(btn, step);
                    } else {
                        // TODO: 2017/5/26 进入结束跑道
                        moveToEndTrack(mCurrent, btn, step);
                    }
                } else if (mCurrent == BeanCell.COLOR_RED) {
                    if ((step < 9 || step >= 13)) {
                        // TODO: 2017/5/26 进入结束跑道
                        move(btn, step);
                    } else {
                        moveToEndTrack(mCurrent, btn, step);
                    }
                } else if (mCurrent == BeanCell.COLOR_YELLOW) {
                    if ((step < 22 || step >= 26)) {
                        move(btn, step);
                        Log.d("test", "yellow最终位置在链表中的位置：" + step);

                    } else {
                        moveToEndTrack(mCurrent, btn, step);
                    }
                } else if (mCurrent == BeanCell.COLOR_GREEN) {
                    if ((step < 35 || step >= 39)) {
                        move(btn, step);
                    } else {
                        moveToEndTrack(mCurrent, btn, step);
                        Log.d("test", "green最终位置在链表中的位置：" + step);
                    }
                }
                mCurrent = (mCurrent + 1) % 4;
            }
        });
    }


    public void move(Button btn, int step) {

        Log.d("test", "最终位置在链表中的位置：" + step);

        BeanCell beanCell = mBeanCellList.get(step);
        int a = beanCell.getX();
        int b = beanCell.getY();
        btn.setX(((a - x) * mXScale));
        btn.setY(((b - y) * mYScale));
    }

    private void moveToEndTrack(int current, Button btn, int step) {
        Log.d("test", "进入结束跑道：" + step);

        if (current == BeanCell.COLOR_BLUE) {
            step = step - 48;
            step = step + 60;
        } else if (current == BeanCell.COLOR_RED) {
            step = step - 9;
            step = step + 70;
        } else if (current == BeanCell.COLOR_YELLOW) {
            step = step - 22;
            step = step + 80;
        } else if (current == BeanCell.COLOR_GREEN) {
            step = step - 35;
            step = step + 40;
        }
        move(btn, step);
    }


    private void getDisplayInfomation() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        Log.d("test", "the screen size is " + point.toString());
    }

    private void getDisplayInfomation1() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        Log.d("test", "the screen size is " + point.toString());
        getWindowManager().getDefaultDisplay().getRealSize(point);
        Log.d("test", "the screen real size is " + point.toString());
    }

    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        Log.d("test", "the statusbar size is " + result);

        return result;
    }

    public int getTitleBarHeight() {
        Window window = getWindow();
        int contentViewTop = getWindow()
                .findViewById(Window.ID_ANDROID_CONTENT).getTop();
        // statusBarHeight是上面所求的状态栏的高度
        int titleBarHeight = contentViewTop - getStatusBarHeight();
        Log.d("test", "the titlebar size is " + titleBarHeight);

        return titleBarHeight;
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
