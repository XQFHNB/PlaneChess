package com.example.module_b_wan.widgets;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.module_b_wan.other.event.EventEmpty;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * @author XQF
 * @created 2017/6/13
 */
public class AtyBase extends AppCompatActivity {

    //--------------------------------------------自动使用Butterkinfe

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    //-----------------------------------------使用事件总线区

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    //--------------------------------事件总线排雷区
    public void onEvent(EventEmpty event) {
    }


    /**
     * toast--------------------------------------------------------------
     *
     * @param str
     */
    protected void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }


    //--------------------------------------------------自定义了过滤异常
    protected boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            toast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }

}
