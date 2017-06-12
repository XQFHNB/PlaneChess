package com.example.module_b_lan.game_select;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.module_b_lan.game_classic.AtyStartClassicGame;
import com.example.module_b_lan.game_wifi.sever.AtyStartWifiGame;
import com.example.yifeihappy.planechess.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/5
 */
public class AtySelectGame extends AppCompatActivity {


    public static void startAtySelectGame(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }


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
        finish();
    }

    @OnClick(R.id.btn_aty_select_classic)
    public void onBtnClassicClick() {
        AtyStartClassicGame.startAtyStartClassicGame(this, AtyStartClassicGame.class);
    }

    @OnClick(R.id.btn_aty_select_wifi)
    public void onBtnWifiClick() {
        AtyStartWifiGame.startAtyStartWifiGame(this, AtyStartWifiGame.class);
    }
}
