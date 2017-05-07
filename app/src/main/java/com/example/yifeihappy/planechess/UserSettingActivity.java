package com.example.yifeihappy.planechess;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.huangbin.network.IPAdressHelper;
import com.example.junyi.network.Client;
import com.example.junyi.network.NetMsg;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;


public class UserSettingActivity extends AppCompatActivity {

    public static final String ENTER_ROOM = "EN";//ENTERROOM
    public static final String CONNECT = "CO";//CONNECT TO SERVER
    public static final String WELCOME = "WE";//WELCOME
    public static final String REFUSE = "RE";//REFUSE
    public static final String BEGIN = "BE";//BEGIN
    public static final String CBACK = "CBA";//BA
    public static final String RBACK = "RBA";//RBA
    public static final int CONNECT_WHAT = 0X300;//CONNECT TO SERVER
    public static final int WELCOME_WHAT = 0x200;
    public static final int REFUSE_WHAT = 0x400;
    public static final int BEGIN_WHAT = 0x100;
    public static final String ROOM_DATA = "roomData";
    public static final int WELCOME_OTHERS_WHAT = 0x201;
    public static final int REFUSE_OTHERS_WHAT = 0x401;
    public static final int RBACK_WHAT = 0x500;
    public static final int SOCKET_PORT = 20000;
    String roomIP = null;
    String mPlayerIP = null;
    String playersNum = null;
    Client client = null;
    String mPlaneColor = "NULL";
    String mPlayerName = "NULL";
    int mIndex ;
    ClientThread clientThread;

    Button btnEnter = null;
    private RadioGroup radioGroupColor =null;
    private  RadioButton radioButtonRoomerSelected =null;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == CONNECT_WHAT) {
                //Toast.makeText(UserSettingActivity.this,"Yon have connect to the room serversocket.",Toast.LENGTH_LONG).show();
            }

            if(msg.what == WELCOME_WHAT) {
                Toast.makeText(UserSettingActivity.this,"Waiting to begin.",Toast.LENGTH_LONG).show();
                for(int i = 0;i<radioGroupColor.getChildCount();i++) {
                    RadioButton radioButton = (RadioButton)radioGroupColor.getChildAt(i);
                    radioButton.setEnabled(false);
                }
            }

            if(msg.what == WELCOME_OTHERS_WHAT ||msg.what == REFUSE_OTHERS_WHAT) {
                int radioIndex = msg.arg1;
                RadioButton radioButtonSelected = (RadioButton)radioGroupColor.getChildAt(radioIndex);
                radioButtonSelected.setEnabled(false);
                if(radioButtonSelected.isChecked()) radioGroupColor.clearCheck();
            }

            if(msg.what == REFUSE_WHAT ) {
                int radioIndex = msg.arg1;
                RadioButton radioButtonSelected = (RadioButton)radioGroupColor.getChildAt(radioIndex);
                radioButtonSelected.setEnabled(false);
                radioGroupColor.clearCheck();
                btnEnter.setEnabled(true);
            }

            if(msg.what == BEGIN_WHAT) {
                Toast.makeText(UserSettingActivity.this,"Receive begin.",Toast.LENGTH_LONG).show();
            }

            if(msg.what == RBACK_WHAT) {
                Toast.makeText(UserSettingActivity.this,"Room close",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        final SerliBroacastData roomData = (SerliBroacastData)bundle.getSerializable(ROOM_DATA);
        String planeColor = roomData.getPlaneColor();//The color the creater of room has selected.
        roomIP = roomData.getRoomIP();//　This room IP.
        mPlayerIP = IPAdressHelper.getIPByWifi(this);//my IP
        playersNum = roomData.getPlayersNum();

        //set the planeColor which the creater of the room has selected enable = false
        radioGroupColor = (RadioGroup)findViewById(R.id.radiogroupColor);
        radioButtonRoomerSelected= (RadioButton) radioGroupColor.getChildAt(Integer.parseInt(planeColor));
        radioButtonRoomerSelected.setEnabled(false);
        radioGroupColor.clearCheck();

        clientThread =new ClientThread();
        clientThread.start();

        btnEnter = (Button)findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName ;
                String planeColor = null;
                EditText edtName = (EditText) findViewById(R.id.edtName);
                playerName = edtName.getText().toString();
                if (playerName.equals("")) {
                    Toast.makeText(UserSettingActivity.this, "请输入玩家姓名", Toast.LENGTH_SHORT).show();
                    return;
                }

                int radi;
                for (radi = 0; radi < radioGroupColor.getChildCount(); radi++) {
                    RadioButton r = (RadioButton) radioGroupColor.getChildAt(radi);
                    if (r.isChecked()) {
                        planeColor = String.valueOf(radi);
                        break;
                    }
                }
                if(radi == radioGroupColor.getChildCount()) {
                    Toast.makeText(UserSettingActivity.this,"请输选择一种颜色",Toast.LENGTH_SHORT).show();
                    return;
                }

                SerliBroacastData enterRoomData = new SerliBroacastData(ENTER_ROOM,roomIP,playersNum,mPlayerIP,planeColor,playerName);
                NetMsg msg = new NetMsg(enterRoomData.toString(),(byte)0x00);

                try {
                    client.sendToServer(msg);
                } catch (SocketException e) {
                    e.printStackTrace();
                    Toast.makeText(UserSettingActivity.this,"Send to server failed",Toast.LENGTH_LONG).show();
                }
                btnEnter.setEnabled(false);//wait for check

            }
        });

    }



    class ClientThread extends Thread {
        public Object myLock = new Object();
        private volatile boolean stopThread = false;

        public  void stopGetData() {
            stopThread = true;
                client.close();
                this.interrupt();

        }

        @Override
        public void run() {
            super.run();
            try {
                client = new Client( InetAddress.getByName(roomIP),SOCKET_PORT);
            } catch (IOException e) {


                /////////////////////////////////////////////
                e.printStackTrace();
            }


                try {
                    while (!stopThread) {
                        NetMsg msg = client.getData();//If there is not data,this thread will be blocked.
                        Message message = handler.obtainMessage();
                        Deserializable deserializable = new Deserializable();
                        SerliBroacastData enterMessage = deserializable.deSerliBroacastData(msg.getData());
                        if(enterMessage.getRoomIP().startsWith(roomIP)) {
                            if(enterMessage.getTag().startsWith(CONNECT)) {
                                mIndex = Integer.parseInt(enterMessage.getPlayersNum());//get index at server
                                message.what = CONNECT_WHAT;
                                handler.sendMessage(message);
                            }

                            if (enterMessage.getTag().startsWith(WELCOME)) {
                                if(enterMessage.getPlayerIP().startsWith(mPlayerIP)) {
                                    message.what = WELCOME_WHAT;
                                    mPlaneColor = enterMessage.getPlaneColor();//If tag==welcome,the message getPlaneColor==mPlanecolor
                                    mPlayerName = enterMessage.getPlayerName();
                                } else {
                                    message.arg1 = Integer.parseInt(enterMessage.getPlaneColor());//message.arg1 == index of color has selected.
                                    message.what = WELCOME_OTHERS_WHAT;
                                }
                                handler.sendMessage(message);
                            }

                            if(enterMessage.getTag().startsWith(REFUSE)) {
                                if(enterMessage.getPlayerIP().startsWith(mPlayerIP)) {
                                    message.what = REFUSE_WHAT;
                                } else {
                                    message.arg1 = Integer.parseInt(enterMessage.getPlaneColor());//message.arg1 == index of color has selected.
                                    message.what = REFUSE_OTHERS_WHAT;
                                }
                                handler.sendMessage(message);
                            }

                            if(enterMessage.getTag().startsWith(BEGIN)) {
                                message.what = BEGIN_WHAT;
                                playersNum = enterMessage.getPlayersNum();
                                handler.sendMessage(message);
                            }

                            if(enterMessage.getTag().startsWith(RBACK)) {
                                message.what = RBACK_WHAT;
                                handler.sendMessage(message);

                            }
                        }

                    }
                } catch (InterruptedException e) {
                    this.interrupt();
                }



        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //playerNum == mIndex;
        SerliBroacastData enterRoomData = new SerliBroacastData(CBACK,roomIP,String.valueOf(mIndex),mPlayerIP,mPlaneColor,mPlayerName);
        NetMsg msg = new NetMsg(enterRoomData.toString(),(byte)0x06);

        try {

            client.sendToServer(msg);

        } catch (SocketException e) {
            e.printStackTrace();
            Toast.makeText(UserSettingActivity.this,"Back message Send to server failed",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onDestroy() {
        clientThread.stopGetData();
        client.close();
        super.onDestroy();


    }
}
