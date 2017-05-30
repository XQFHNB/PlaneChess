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
    private boolean isFinish = false;

    final int x = 3;
    final int y = 2 * x;

    private int mCurrent = BeanCell.COLOR_BLUE;
    int[] mColors;

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
        mColors = new int[]{BeanCell.COLOR_BLUE, BeanCell.COLOR_RED, BeanCell.COLOR_YELLOW, BeanCell.COLOR_GREEN};
        //比例
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(point);
        float width = point.x;
        mXScale = width / 640;
        float height = point.y;
        mYScale = height / 360;
        for (int i = 0; i < mBeanRoleList.size(); i++) {
            mBeanRoleList.get(i).setBtn(mPlanes[i]);
            mBeanRoleList.get(i).setXScale(mXScale);
            mBeanRoleList.get(i).setYScale(mYScale);
        }


        mBtnClick = (Button) findViewById(R.id.btn_click);
        toggleHideyBar();

        mBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rand = new Random();
                int temp = (rand.nextInt(6) + 1);
                mBtnClick.setText(temp + "");

                BeanRole currentRole = mBeanRoleList.get(mCurrent);
                if (!currentRole.isFinish()) {
                    currentRole.setNum(temp);
                    if (!currentRole.isNormalEnd()) {
                        Log.d("test", "角色：" + mCurrent + "  before normalmove " + currentRole.getCurrentIndex() + " 色子：" + temp);
                        currentRole.normalMove();
                        Log.d("test", "角色：" + mCurrent + "  after normalmove " + currentRole.getCurrentIndex());
                    } else {
                        currentRole.endMove();
                        Log.d("test", "角色：" + mCurrent + "  currentIndexInEnd:" + currentRole.getCurrentIndex());
                        if (currentRole.isFinish() == true) {
                            isFinish = true;
                        }
                    }
                }
                if (isFinish == true) {
                    toast("游戏结束");
                } else {
                    mCurrent = (mCurrent + 1) % 4;
                }

            }

        });
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


}
