package com.example.test.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.test.R;

import butterknife.BindView;

/**
 * @author XQF
 * @created 2017/6/4
 */
public class AtyStartWifiGame extends AppCompatActivity {

    @BindView(R.id.btn_aty_start_wifi_back)
    protected Button mButtonBack;

    @BindView(R.id.btn_createroom)
    protected Button mButtonCreateRoom;

    @BindView(R.id.recyclerview_rooms)
    protected RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_start_wiifi_game);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }
    class  MyAdapter extends  RecyclerView.Adapter<MyHolder>{

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
