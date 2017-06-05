package com.example.aty1;

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
public class AtyRule extends AppCompatActivity {


    public static void startAtyRule(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }


    @BindView(R.id.btn_aty_rule_back)
    protected Button mButtonBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_rule);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_aty_rule_back)
    public void onBtnBackClick() {

    }
}
