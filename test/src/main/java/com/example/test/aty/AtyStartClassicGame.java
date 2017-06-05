package com.example.test.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/4
 */
public class AtyStartClassicGame extends AppCompatActivity {


    @BindView(R.id.btn_level_easy)
    protected Button mButtonLevelEasy;
    @BindView(R.id.btn_level_normal)
    protected Button mButtonLevelNormal;

    @BindView(R.id.btn_level_diffcult)
    protected Button mButtonLevelDiff;


    @BindView(R.id.btn_num_256)
    protected Button mButton256;

    @BindView(R.id.btn_num_56)
    protected Button mButton56;

    @BindView(R.id.btn_num_6)
    protected Button mButton6;

    @BindView(R.id.btn_start_classic)
    protected Button mButtonStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_start_classic_game);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_start_classic)
    public void onBtnStartClick() {
        Intent intent = new Intent(this, AtyTest.class);
        startActivity(intent);
    }
}
