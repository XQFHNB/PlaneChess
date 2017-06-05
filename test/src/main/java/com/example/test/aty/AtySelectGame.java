package com.example.test.aty;

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
public class AtySelectGame extends AppCompatActivity {


    @BindView(R.id.btn_aty_select_classic)
    protected Button mButtonClassic;
    @BindView(R.id.btn_aty_select_wifi)
    protected Button mButtonWifi;

    @BindView(R.id.btn_aty_select_back)
    protected Button mButtonBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aty_select_game);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.btn_aty_select_back)
    public void onBtnBackClick() {

    }

    @OnClick(R.id.btn_aty_select_classic)
    public void onBtnClassicClick() {

    }

    @OnClick(R.id.btn_aty_select_wifi)
    public void onBtnWifiClick() {

    }
}

