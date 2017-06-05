package com.example.aty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.network.broadcast.HelperBroadCastBase;
import com.example.network.broadcast.HelperBroascastGroup;
import com.example.utils.UtilDeserializable;
import com.example.yifeihappy.planechess.R;
import com.example.network.broadcast.DataBroaCastSerlied;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 查询房间，查询到的结果通过listView显示
 */
public class AtySearchRoom extends AppCompatActivity {
    public static final String TAG = "test";

    public static final String SEARCH_ROOM = "SE";//SEARCHROOM
    public static final String CREATE_ROOM = "CR";//CREATEROOM
    public static final String WELCOME = "WE";//WELCOME
    public static final String REFUSE = "RE";//REFUSE
    public static final String BEGIN = "BE";//BEGIN
    public static final String RBACK = "RBA";//RBA
    public static final int RBACK_WHAT = 0x100;
    public static final int INPORT_MUL = 31111;
    public static final int GET_ROOM_IP = 0x200;
    public static final String ROOM_DATA = "roomData";


    HelperBroascastGroup broascastGroupHelper = null;
    SearchRoomThread searchRoomThread = null;
    //TextView txtRoomIP=null;
    //Button btnEnter = null;
    Handler handler = null;
    DataBroaCastSerlied roomData = null;
    List<Map<String, Object>> roomList = new ArrayList<Map<String, Object>>();
    List<String> temp = new ArrayList<>();
    SimpleAdapter roominfo;
    ListView mRooms = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_room);
        mRooms = (ListView) findViewById(R.id.rooms);

        roominfo = new SimpleAdapter(this, roomList, R.layout.room_item, new String[]{"playerName"}, new int[]{R.id.item});
        mRooms.setAdapter(roominfo);
        mRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> roomitem = roomList.get(position);
                DataBroaCastSerlied room = (DataBroaCastSerlied) roomitem.get("target");
                Log.d(TAG, "点击子项的数据内容" + room.toString());
                Bundle bundletoUserSetting = new Bundle();
                bundletoUserSetting.putSerializable(ROOM_DATA, room);
                Intent intentToUserSetting = new Intent(AtySearchRoom.this, AtyClientSetting.class);
                intentToUserSetting.putExtras(bundletoUserSetting);
                startActivity(intentToUserSetting);

                searchRoomThread.setStopSearchRoom();
                finish();
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == GET_ROOM_IP || msg.what == RBACK_WHAT) {
                    roominfo.notifyDataSetChanged();
                    // btnEnter.setEnabled(true);
                }
            }
        };

        broascastGroupHelper = new HelperBroascastGroup(INPORT_MUL);
        broascastGroupHelper.joinGroup();
        broascastGroupHelper.setLoopback(true);
        broascastGroupHelper.setOnReceiveMsgListener(new HelperBroadCastBase.OnReceiveMsgListener() {
            @Override
            public void onReceive(HelperBroadCastBase.BroadCastBaseMsg msg) {
                UtilDeserializable deserializable = new UtilDeserializable();
                roomData = (DataBroaCastSerlied) deserializable.deSerliBroacastData(msg.msg);
                //receive Room IP，收到一个房间号的广播
                if (roomData.getTag().startsWith(CREATE_ROOM)) {
                    if (!temp.contains(roomData.getRoomIP())) {
                        temp.add(roomData.getRoomIP());
                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        hashMap.put("playerName", roomData.getPlayerName());
                        hashMap.put("target", (Object) roomData);
                        hashMap.put("ip", roomData.getPlayerIP());
                        roomList.add(hashMap);
                        Message message = handler.obtainMessage();
                        message.what = GET_ROOM_IP;
                        handler.sendMessage(message);
                    }

                }

                //收到房间销毁的广播
                if (roomData.getTag().startsWith(RBACK)) {
                    if (temp.contains(roomData.getRoomIP())) {
                        temp.remove(roomData.getRoomIP());
                        int i;
                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        for (i = 0; i < roomList.size(); i++) {
                            hashMap = (HashMap) roomList.get(i);
                            if (((String) hashMap.get("ip")).startsWith(roomData.getRoomIP())) {
                                break;
                            }
                        }

                        roomList.remove((Object) hashMap);
                    }
                    Message message = handler.obtainMessage();
                    message.what = RBACK_WHAT;
                    handler.sendMessage(message);
                }

            }

        });


        searchRoomThread = new SearchRoomThread();
        searchRoomThread.start();

    }


    class SearchRoomThread extends Thread {
        private volatile boolean stopSearchRoom = false;
        private Object mClock = new Object();

        public void setStopSearchRoom() {
            synchronized (mClock) {
                stopSearchRoom = true;
            }
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                synchronized (mClock) {
                    if (stopSearchRoom) break;
                }
                broascastGroupHelper.receiveMsg();
            }

        }
    }
}
