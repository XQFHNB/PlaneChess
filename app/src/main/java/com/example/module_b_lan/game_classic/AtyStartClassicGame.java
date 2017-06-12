package com.example.module_b_lan.game_classic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.yifeihappy.planechess.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/5
 */
public class AtyStartClassicGame extends AppCompatActivity {


    public static void startAtyStartClassicGame(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }


    public static final int LEVEL_EASY = 0;
    public static final int LEVEL_MID = 1;
    public static final int LEVEL_DIFF = 2;

    public static final int START_NUM_0 = 0;
    public static final int START_NUM_1 = 1;
    public static final int START_NUM_2 = 2;


    @BindView(R.id.btn_level_easy)
    protected Button mButtonLevelEasy;
    @BindView(R.id.btn_level_normal)
    protected Button mButtonLevelNormal;

    @BindView(R.id.btn_level_diffcult)
    protected Button mButtonLevelDiff;

    @BindView(R.id.btn_aty_start_wifi_back)
    protected Button mButtonBack;


    @BindView(R.id.btn_num_256)
    protected Button mButton256;

    @BindView(R.id.btn_num_56)
    protected Button mButton56;

    @BindView(R.id.btn_num_6)
    protected Button mButton6;

    @BindView(R.id.btn_start_classic)
    protected Button mButtonStart;


    private Button[] mBtnsLevelClick;
    private Button[] mBtnsStartNumsClick;

    private boolean mBtnLevelEasy = true;
    private boolean mBtnLevelMid;
    private boolean mBtnLevelDiff;

    private boolean mBtnStartNum0 = true;
    private boolean mBtnStartNum1;
    private boolean mBtnStartNum2;

    private boolean[] mBtnsLevel;
    private boolean[] mBtnsStartNum;

    private int mLevelSelected;
    private int mStartNumsSelected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_start_classic_game);
        ButterKnife.bind(this);
        mBtnsLevelClick = new Button[]{mButtonLevelEasy, mButtonLevelNormal, mButtonLevelDiff};
        mBtnsStartNumsClick = new Button[]{mButton256, mButton56, mButton6};
        mBtnsLevel = new boolean[]{mBtnLevelEasy, mBtnLevelMid, mBtnLevelDiff};
        mBtnsStartNum = new boolean[]{mBtnStartNum0, mBtnStartNum1, mBtnStartNum2};
    }


    @OnClick(R.id.btn_aty_start_wifi_back)
    public void onBtnBackClick() {
        finish();
    }

    @OnClick(R.id.btn_level_easy)
    public void onBtnEasyClick() {
        dealLeveBtns(0);
    }


    @OnClick(R.id.btn_level_normal)
    public void onBtnNormalClick() {
        dealLeveBtns(1);

    }

    @OnClick(R.id.btn_level_diffcult)
    public void onBtnDiffClick() {
        dealLeveBtns(2);
    }


    @OnClick(R.id.btn_num_256)
    public void onBtnNum256Click() {
        dealStartNumBtns(0);
    }


    @OnClick(R.id.btn_num_56)
    public void onBtnNum56Click() {
        dealStartNumBtns(1);

    }

    @OnClick(R.id.btn_num_6)
    public void onBtnNum6Click() {
        dealStartNumBtns(2);
    }


    @OnClick(R.id.btn_start_classic)
    public void onBtnStartClick() {

        for (int i = 0; i < mBtnsLevel.length; i++) {
            if (mBtnsLevel[i] == true) {
                if (i == 0) {
                    mLevelSelected = LEVEL_EASY;
                } else if (i == 1) {
                    mLevelSelected = LEVEL_MID;
                } else if (i == 2) {
                    mLevelSelected = LEVEL_DIFF;
                }
            }
        }

        for (int i = 0; i < mBtnsStartNum.length; i++) {
            if (mBtnsStartNum[i] == true) {
                if (i == 0) {
                    mStartNumsSelected = START_NUM_0;
                } else if (i == 1) {
                    mStartNumsSelected = START_NUM_1;
                } else if (i == 2) {
                    mStartNumsSelected = START_NUM_2;
                }
            }
        }


        AtyGameClassic.startAtyGameClassic(this, AtyGameClassic.class, mLevelSelected, mStartNumsSelected);


    }


    /**
     * 处理游戏等级的
     *
     * @param index
     */
    private void dealLeveBtns(int index) {
        for (int i = 0; i < mBtnsLevelClick.length; i++) {
            if (i == index) {
                mBtnsLevel[i] = true;
                mBtnsLevelClick[i].setBackgroundResource(R.drawable.bg_select_circle);
            } else {
                mBtnsLevel[i] = false;
                mBtnsLevelClick[i].setBackground(null);
            }
        }
    }

    /**
     * 处理游戏人数的
     *
     * @param index
     */

    private void dealStartNumBtns(int index) {

        for (int i = 0; i < mBtnsStartNum.length; i++) {
            if (i == index) {
                mBtnsStartNum[i] = true;
                mBtnsStartNumsClick[i].setBackgroundResource(R.drawable.bg_select_circle);
            } else {
                mBtnsStartNum[i] = false;
                mBtnsStartNumsClick[i].setBackground(null);
            }
        }
    }


}
