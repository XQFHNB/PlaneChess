package com.example.test.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.test.R;

/**
 * @author XQF
 * @created 2017/6/11
 */
public class AtyTestBtnScale extends AppCompatActivity {

    protected Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test_btn_scale);
        mButton = (Button) findViewById(R.id.btn_test);

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.startAnimation(animation);
            }
        });

    }
}
