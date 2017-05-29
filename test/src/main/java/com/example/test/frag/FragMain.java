package com.example.test.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.test.R;

/**
 * @author XQF
 * @created 2017/5/24
 */
public class FragMain extends Fragment {

    private Button mBtn;
    private Button mBtn1;

    float dx, dy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);
        int[] position = new int[2];
        mBtn.getLocationOnScreen(position);
        Log.d("test", "creatview: " + position[0] + " " + position[1]);
        mBtn.setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    dx = motionEvent.getX();
                    dy = motionEvent.getY();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {


                    float tx = motionEvent.getRawX() - dx;
                    float ty = (motionEvent.getRawY() - dy - getStatusBarHeight() - getTitleBarHeight());
                    view.setX(tx);
                    view.setY(ty);
                    Log.d("test", "tx: " + tx + " " + "ty: " + ty);
                }
                return true;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int[] position = new int[2];
        mBtn.getLocationOnScreen(position);
        Log.d("test", "created: " + position[0] + " " + position[1]);
    }

    @Override
    public void onStart() {
        super.onStart();
        int[] position = new int[2];
        mBtn.getLocationOnScreen(position);
        Log.d("test", "start: " + position[0] + " " + position[1]);
    }

    @Override
    public void onResume() {
        super.onResume();
        int[] position = new int[2];
        mBtn.getLocationOnScreen(position);
        Log.d("test", "resume: " + position[0] + " " + position[1]);
    }


    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
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
        Window window = getActivity().getWindow();
        int contentViewTop = getActivity().getWindow()
                .findViewById(Window.ID_ANDROID_CONTENT).getTop();
        // statusBarHeight是上面所求的状态栏的高度
        int titleBarHeight = contentViewTop - getStatusBarHeight();
        return titleBarHeight;
    }
}
