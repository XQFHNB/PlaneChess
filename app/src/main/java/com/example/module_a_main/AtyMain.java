package com.example.module_a_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.module_b_lan.game_rule.AtyRule;
import com.example.module_b_lan.game_select.AtySelectGame;
import com.example.yifeihappy.planechess.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/5
 */
public class AtyMain extends AppCompatActivity {


    @BindView(R.id.btn_aty_main_play)
    protected Button mButtonPlay;
    @BindView(R.id.btn_aty_main_rule)
    protected Button mButtonRule;
    @BindView(R.id.btn_aty_main_back)
    protected Button mButtonBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_aty_main_play)
    public void onBtnPlayClick() {
        AtySelectGame.startAtySelectGame(this, AtySelectGame.class);
    }

    @OnClick(R.id.btn_aty_main_rule)
    public void onBtnRuleClick() {
        AtyRule.startAtyRule(this, AtyRule.class);
    }

    @OnClick(R.id.btn_aty_main_back)
    public void onBtnBackClick() {
        finish();
    }


}
