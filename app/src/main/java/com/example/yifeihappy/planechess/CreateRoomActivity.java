package com.example.yifeihappy.planechess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_room);
        final CheckBox num1 = (CheckBox)findViewById(R.id.num1);
        final CheckBox num2 = (CheckBox)findViewById(R.id.num2);
        final CheckBox num3 = (CheckBox)findViewById(R.id.num3);
        final CheckBox num4 = (CheckBox)findViewById(R.id.num4);
        final CheckBox num5 = (CheckBox)findViewById(R.id.num5);
        final CheckBox num6 = (CheckBox)findViewById(R.id.num6);

        final RadioGroup radioGroupPlayerNum = (RadioGroup)findViewById(R.id.radioGroupPlayerNum);

        final EditText edtName = (EditText)findViewById(R.id.edtName);

        final RadioGroup radioGroupColor = (RadioGroup)findViewById(R.id.radiogroupColor);

        final Button btnCreateRoom = (Button)findViewById(R.id.btnCreateRoom);

        //If player input the name, the default value will disappear.
        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtName.setText("");
            }
        });

        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startNums = new String();
                String playerNum = new String();
                String playerName;
                String planeColor = new String();

                playerName = edtName.getText().toString();
                if(playerName.equals("")) {
                    Toast.makeText(CreateRoomActivity.this,"请输入玩家姓名",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (num1.isChecked()) startNums += num1.getText().toString();
                if (num2.isChecked()) startNums += num2.getText().toString();
                if (num3.isChecked()) startNums += num3.getText().toString();
                if (num4.isChecked()) startNums += num4.getText().toString();
                if (num5.isChecked()) startNums += num5.getText().toString();
                if (num6.isChecked()) startNums += num6.getText().toString();


                //Toast.makeText(CreateRoomActivity.this,startNums,Toast.LENGTH_SHORT).show();
                if (startNums.equals("")) {
                    Toast.makeText(CreateRoomActivity.this, "请选择飞机起飞数字", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(CreateRoomActivity.this,startNums,Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < radioGroupPlayerNum.getChildCount(); i++) {
                        RadioButton r = (RadioButton) radioGroupPlayerNum.getChildAt(i);
                        if (r.isChecked()) {
                            playerNum = r.getText().toString();
                            break;
                        }
                    }

                    for(int i = 0; i <radioGroupColor.getChildCount(); i++) {
                        RadioButton r = (RadioButton) radioGroupColor.getChildAt(i);
                        if (r.isChecked()) {
                            planeColor = String.valueOf(i);
                            break;
                        }
                    }
                    Intent intent = new Intent(CreateRoomActivity.this, WaitClientsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("startNums",startNums);
                    bundle.putCharSequence("playersNum",playerNum);
                    bundle.putCharSequence("playerName",playerName);
                    bundle.putCharSequence("planeColor",planeColor);

                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }
}
