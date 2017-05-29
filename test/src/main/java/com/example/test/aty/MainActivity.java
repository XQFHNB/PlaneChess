package com.example.test.aty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author XQF
 * @created 2017/5/24
 */

class MyButton {
    private int x;
    private int y;

    public MyButton(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class MainActivity extends AppCompatActivity {


    private Button mBtn;
    private Button mBtn1;
    private LinearLayout mLinearLayout;

    int[] resId = new int[]{
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15,
            R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23,
            R.id.btn24, R.id.btn25, R.id.btn26, R.id.btn27, R.id.btn28, R.id.btn29, R.id.btn30, R.id.btn31,
    };

    Button[] btns = new Button[32];

    Button btnCenter;
    Button btnMove;
    List<MyButton> mMyButtonList;

    private int step = 0;
    private int current = 0;
    private int next = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyButtonList = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            btns[i] = (Button) findViewById(resId[i]);
        }

        btnCenter = (Button) findViewById(R.id.btn_center);
        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                step = rand.nextInt(6) + 1;
                btnCenter.setText("");
                btnCenter.setText(step + " ");
                next = current + step;
                MyButton myButton = mMyButtonList.get(next % 32);
                btns[0].setX(myButton.getX());
                btns[0].setY(myButton.getY() - getStatusBarHeight() - getTitleBarHeight());
                current = next;
            }
        });
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.d("test", "width: " + width + " " + "height: " + height);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        for (int i = 0; i < 32; i++) {
            int[] position = new int[2];
            btns[i].getLocationOnScreen(position);
            Log.d("test", "postion: " + position[0] + " " + position[1]);
            mMyButtonList.add(new MyButton(position[0], position[1]));
        }

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getTitleBarHeight() {
        Window window = getWindow();
        int contentViewTop = getWindow()
                .findViewById(Window.ID_ANDROID_CONTENT).getTop();
        // statusBarHeight是上面所求的状态栏的高度
        int titleBarHeight = contentViewTop - getStatusBarHeight();
        return titleBarHeight;
    }
}
