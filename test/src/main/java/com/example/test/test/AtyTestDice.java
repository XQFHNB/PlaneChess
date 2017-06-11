package com.example.test.test;

import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/8
 */
public class AtyTestDice extends AppCompatActivity {


    @BindView(R.id.btn_dice)
    protected Button mButtonDice;

    @BindView(R.id.btn_stop)
    protected Button mButtonStop;

    int mCurrent = 0;


    AssetManager mAssetManager;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                AnimationDrawable anim = (AnimationDrawable) mButtonDice.getBackground();

                Random random = new Random();
                int dice = random.nextInt(6) + 1;
                mButtonDice.setBackground(anim.getFrame(5));
            } else if (msg.what == 2) {
                mButtonDice.performClick();
                toast("现在是robot： " + mCurrent + " 的时间");
            } else if (msg.what == 3) {
                AlertDialog.Builder builder = UtilTipDialog.showDialog(AtyTestDice.this);
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test_dice);
        ButterKnife.bind(this);
        mAssetManager = getAssets();
        mButtonDice.setBackgroundResource(R.drawable.diceanim);

    }


    @OnClick(R.id.btn_dice)
    public void onDiceClick() {
        mButtonDice.setBackgroundResource(R.drawable.diceanim);
        final AnimationDrawable anim = (AnimationDrawable) mButtonDice.getBackground();
        anim.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(3);
            }
        }, 30000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                anim.stop();
                mHandler.sendEmptyMessage(1);
            }
        }, 50000);

        if (mCurrent != 3) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(2);
                }
            }, 5000);
        }
        mCurrent = (mCurrent + 1) % 4;


    }

    @OnClick(R.id.btn_stop)
    public void onBtnStopClick() {
    }

    public void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
