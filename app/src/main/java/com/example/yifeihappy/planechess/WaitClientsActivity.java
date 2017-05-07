package com.example.yifeihappy.planechess;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangbin.network.BroacastLuncherThread;
import com.example.huangbin.network.BroascastGroupHelper;
import com.example.huangbin.network.IPAdressHelper;
import com.example.junyi.network.NetMsg;
import com.example.junyi.network.ServerInTele;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class WaitClientsActivity extends AppCompatActivity {

    public static final String CREATE_ROOM = "CR";//CREATEROOM
    public static final String CONNECT = "CO";//CONNECT TO SERVER
    public static final String ENTER_ROOM = "EN";//ENTERROOM
    public static final String WELCOME = "WE";//WELCOME
    public static final String REFUSE = "RE";//REFUSE
    public static final String BEGIN = "BE";//BEGIN
    public static final String CBACK = "CBA";//CBA
    public static final String RBACK = "RBA";//RBA
    public static final int OUTPORT_MUL = 31111;
    public static final int BEGIN_WHAT = 0x100;
    public static final int TO_HOME = 0x600;
    public static final int SOCKET_PORT = 20000;

    ServerInTele serverInTel = null;
    BroascastGroupHelper broascastGroupHelper = null;
    BroacastLuncherThread broacastRooMIPThread = null;
    ServerAcceptThread serverAcceptThread = null;
    Button btnBegin = null;//click to start the game if all players hava entered.
    String roomIP = null;
    int playersNum;//the num of player
    Map playersWait = new TreeMap();//color and ip which players have selected and entered.
    Map waitsName = new TreeMap();//key = playerIp,value = planeColor

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == BEGIN_WHAT) {
                btnBegin.setEnabled(true);
                Toast.makeText(WaitClientsActivity.this, "Click to begin...", Toast.LENGTH_LONG).show();
            }

            if (msg.what == TO_HOME) {
                Intent intentToHome = new Intent(WaitClientsActivity.this, MainActivity.class);
                startActivity(intentToHome);
                Log.e("doits", "before finish");
                finish();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_clients);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        playersNum = Integer.parseInt(bundle.getString("playersNum"));
        roomIP = IPAdressHelper.getIPByWifi(this);

        //add room creater plane color to colorList.key = playerIP,value = color.

        playersWait.put(roomIP, bundle.getCharSequence("planeColor"));
        waitsName.put(roomIP, bundle.getString("playerName"));

        btnBegin = (Button) findViewById(R.id.btnBegin);
        TextView txtRoomPlayerNum = (TextView) findViewById(R.id.txtRoomPlayerNum);

        txtRoomPlayerNum.setText("飞机起飞点数：" + bundle.getString("startNums") + "\n" + "人数：" +
                bundle.getString("playersNum") + "\n" + "Host Name:" + bundle.getString("playerName") + "\n"
                + "Host Color:" + bundle.getString("planeColor") + "\n" + "ip" + roomIP);


        broascastGroupHelper = new BroascastGroupHelper(OUTPORT_MUL);
        broascastGroupHelper.joinGroup();
        broascastGroupHelper.setLoopback(true);

//        发送广播
        //broadcast the ip of the creater of room
        SerliBroacastData roomIPData = new SerliBroacastData(CREATE_ROOM, roomIP, bundle.getString("playersNum"), roomIP, bundle.getString("planeColor"), bundle.getString("playerName"));
        broacastRooMIPThread = new BroacastLuncherThread(broascastGroupHelper, roomIPData.toString());
        broacastRooMIPThread.start();

        /**
         * 用刚才的数据和端口来创建服务器，服务器接收消息
         */
        try {
            serverInTel = new ServerInTele(playersNum, SOCKET_PORT);
            serverInTel.accept();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(WaitClientsActivity.this, "创建服务器失败", Toast.LENGTH_SHORT).show();
        }

        /**
         * 新建线程重服务器的消息队列中取出消息
         */
        serverAcceptThread = new ServerAcceptThread();
        serverAcceptThread.start();
    }


    /**
     * 新建线程重服务器的消息队列中取出消息，取出的数据结果通过handler发送到主线程
     */
    class ServerAcceptThread extends Thread {
        public Object myLock = new Object();
        private volatile boolean stopThread = false;

        public void setStopThread() {

            stopThread = true;
            this.interrupt();
            serverInTel.closeAll();

        }

        @Override
        public void run() {
            super.run();
            try {

                while (!stopThread) {
                    NetMsg msg = serverInTel.getData();

                    /**
                     *0x60应该指的是客户端第一次连接服务器的返回数据
                     */
                    if (msg.getFrom() == 0x60) {//the data client connect to serversocket.

                        //向指定index的客户端发送数据
                        int cIndex = Integer.parseInt(msg.getData());
                        SerliBroacastData dataToSingleClient = new SerliBroacastData(CONNECT, roomIP, msg.getData(), "NULL", "NULL", "NULL");//playersNum == cIndex
                        NetMsg msgToSend = new NetMsg(dataToSingleClient.toString(), (byte) 0x60);
                        //发送房间信息
                        serverInTel.send(cIndex, msgToSend);

                    } else {//the data client select a plane
                        Deserializable deserializable = new Deserializable();
                        SerliBroacastData enterData = deserializable.deSerliBroacastData(msg.getData());

                        //判断房间IP是不是一样的，如果一样就是进入房间，服务器的行为根据后面的数据内容而定
                        if (enterData.getRoomIP().startsWith(roomIP)) {

                            SerliBroacastData dataToAllClients = null;
                            NetMsg msgToSend = null;
                            /**
                             *假如是收到的进入房间的消息
                             */
                            if (enterData.getTag().startsWith(ENTER_ROOM)) {
                                //判断房间内是否已经存在该客户端IP
                                if (!playersWait.containsKey(enterData.getPlayerIP())) {
                                    //判断房间内是不是已经存在被选过的飞机颜色
                                    if (!playersWait.containsValue(enterData.getPlaneColor())) {

                                        //将该客户端加入房间
                                        playersWait.put(enterData.getPlayerIP(), enterData.getPlaneColor());
                                        waitsName.put(enterData.getPlayerIP(), enterData.getPlayerName());

                                        //发送全局广播欢迎
                                        dataToAllClients = new SerliBroacastData(WELCOME, roomIP, String.valueOf(playersNum), enterData.getPlayerIP(), enterData.getPlaneColor(), enterData.getPlayerName());
                                        msgToSend = new NetMsg(dataToAllClients.toString(), (byte) 0x00);
                                    } else {
                                        //发送全局广播拒绝
                                        dataToAllClients = new SerliBroacastData(REFUSE, roomIP, String.valueOf(playersNum), enterData.getPlayerIP(), enterData.getPlaneColor(), enterData.getPlayerName());
                                        msgToSend = new NetMsg(dataToAllClients.toString(), (byte) 0x00);
                                    }
                                    serverInTel.sendToAll(msgToSend);
                                }
                            }
                            /**
                             * 假如接收到的是退出的消息就将客户端移除
                             */
                            if (enterData.getTag().startsWith(CBACK)) {

                                if (playersWait.containsKey(enterData.getPlayerIP())) {
                                    playersWait.remove(enterData.getPlayerIP());
                                }
                                if (waitsName.containsKey(enterData.getPlayerIP())) {
                                    waitsName.remove(enterData.getPlayerIP());
                                }
                                int backIndex = Integer.valueOf(enterData.getPlayersNum());
                                serverInTel.close(backIndex);
                                playersNum--;
                            }
                            /**
                             * 假如玩家已经够了就要开始游戏了
                             */
                            if (playersWait.size() == playersNum && playersNum != 1) {
                                String playersName = new String();
                                String playersIP = new String();
                                String playersColor = new String();
                                Iterator itr = playersWait.entrySet().iterator();
                                while (itr.hasNext()) {
                                    Map.Entry entry = (Map.Entry) itr.next();
                                    playersIP += entry.getKey() + "#";
                                    playersColor += entry.getValue() + "#";
                                    playersName += waitsName.get(entry.getKey()) + "#";
                                }
                                dataToAllClients = new SerliBroacastData(BEGIN, roomIP, String.valueOf(playersNum), playersIP, playersColor, playersName);
                                msgToSend = new NetMsg(dataToAllClients.toString(), (byte) 0x00);
                                serverInTel.sendToAll(msgToSend);
                                Message message = handler.obtainMessage();
                                message.what = BEGIN_WHAT;
                                handler.sendMessage(message);
                                broacastRooMIPThread.stopThread();
                            }
                            /**
                             *玩家数量一直为1返回主界面
                             */
                            if (playersNum == 1) {
                                Message messageToHome = handler.obtainMessage();
                                messageToHome.what = TO_HOME;
                                handler.sendMessage(messageToHome);
                                Log.e("doits", "after send message");
                                broacastRooMIPThread.stopThread();
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {

            }
        }
    }


    /**
     * 强行退出
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final SerliBroacastData roomBackData = new SerliBroacastData(RBACK, roomIP, String.valueOf(playersNum), "NULL", "NULL", "NULL");
        NetMsg msgRBAck = new NetMsg(roomBackData.toString(), (byte) 0x00);
        serverInTel.sendToAll(msgRBAck);

        new Thread(new Runnable() {
            @Override
            public void run() {

                BroascastGroupHelper rBackBroascast = new BroascastGroupHelper(OUTPORT_MUL);
                rBackBroascast.joinGroup();
                rBackBroascast.setLoopback(true);

                rBackBroascast.sendMsg(roomBackData.toString());
                rBackBroascast.sendMsg(roomBackData.toString());
                rBackBroascast.sendMsg(roomBackData.toString());

                Log.e("doit", roomBackData.toString());
                rBackBroascast.destory();
            }
        }).start();

        broacastRooMIPThread.stopThread();
    }

    @Override
    protected void onDestroy() {
        Log.e("doits", "indestroy server");
        serverAcceptThread.setStopThread();
        Log.e("doits", "ondestroy server");
        super.onDestroy();
    }
}

