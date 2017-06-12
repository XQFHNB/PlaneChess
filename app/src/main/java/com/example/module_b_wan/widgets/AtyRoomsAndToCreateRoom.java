package com.example.module_b_wan.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.yifeihappy.planechess.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class AtyRoomsAndToCreateRoom extends AppCompatActivity {


    @BindView(R.id.btn_aty_rooms_and_tocreateroom_back)
    protected Button mButtonBack;


    @BindView(R.id.recyclerview_aty_rooms_and_tocreateroom)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.btn_aty_rooms_and_tocreateroom)
    protected Button mButtonCreateRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_rooms_and_tocreateroom);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
}
