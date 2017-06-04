package com.example.aty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yifeihappy.planechess.R;

public class AtyMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCreateRoom = (Button)findViewById(R.id.btnCreateRoom);
        Button btnSearchRoom = (Button)findViewById(R.id.btnSearchRoom);
        Button btnInstruction = (Button)findViewById(R.id.btnInstruction);

        final Intent intentCreateRoom = new Intent(AtyMain.this,AtyCreateRoom.class);
        final Intent intentSearchRoom = new Intent(AtyMain.this,AtySearchRoom.class);
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
