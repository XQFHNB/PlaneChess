package com.example.yifeihappy.planechess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCreateRoom = (Button)findViewById(R.id.btnCreateRoom);
        Button btnSearchRoom = (Button)findViewById(R.id.btnSearchRoom);
        Button btnInstruction = (Button)findViewById(R.id.btnInstruction);

        final Intent intentCreateRoom = new Intent(MainActivity.this,CreateRoomActivity.class);
        final Intent intentSearchRoom = new Intent(MainActivity.this,SearchRoomActivity.class);
        //instruction is not completed

        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentCreateRoom);

            }
        });
        btnSearchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentSearchRoom);
            }
        });
    }
}
