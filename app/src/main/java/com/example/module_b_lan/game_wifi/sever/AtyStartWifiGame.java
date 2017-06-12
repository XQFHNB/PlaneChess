package com.example.module_b_lan.game_wifi.sever;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.module_b_lan.game_wifi.client.AtyClientSetting;
import com.example.bean.bean_lan.BeanRoomInfo;
import com.example.module_b_lan.network.broadcast.DataBroaCastSerlied;
import com.example.module_b_lan.network.broadcast.HelperBroadCastBase;
import com.example.module_b_lan.network.broadcast.HelperBroascastGroup;
import com.example.module_b_lan.utils.UtilDeserializable;
import com.example.yifeihappy.planechess.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/5
 */
public class AtyStartWifiGame extends AppCompatActivity {

    public static void startAtyStartWifiGame(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }


    public static final String TAG = "test";

    public static final String SEARCH_ROOM = "SE";//SEARCHROOM
    public static final String CREATE_ROOM = "create_room";//CREATEROOM
    public static final String WELCOME = "welcome";//WELCOME
    public static final String REFUSE = "refuse";//REFUSE
    public static final String BEGIN = "begin";//BEGIN
    public static final String RBACK = "RBA";//RBA
    public static final int RBACK_WHAT = 0x100;
    public static final int INPORT_MUL = 31111;
    public static final int GET_ROOM_IP = 0x200;
    public static final String ROOM_DATA = "roomData";


    HelperBroascastGroup broascastGroupHelper = null;
    SearchRoomThread searchRoomThread = null;
    Handler handler = null;
    DataBroaCastSerlied roomData = null;
    List<String> mTempRoomsIp = new ArrayList<>();
    List<BeanRoomInfo> mBeanRoomInfos;
    MyAdapter mMyAdapter = null;


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
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMyAdapter = new MyAdapter(this);
        mBeanRoomInfos = new ArrayList<>();
        mRecyclerView.setAdapter(mMyAdapter);

        //刷新列表
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == GET_ROOM_IP || msg.what == RBACK_WHAT) {

                    mMyAdapter.addItems(mBeanRoomInfos);
                    mMyAdapter.notifyDataSetChanged();
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
                roomData = deserializable.deSerliBroacastData(msg.msg);
                //receive Room IP，收到一个房间号的广播,于是要增加列表项目
                String tag = roomData.getTag();
                if (roomData.getTag().startsWith(CREATE_ROOM)) {
                    if (!mTempRoomsIp.contains(roomData.getRoomIP())) {
                        mTempRoomsIp.add(roomData.getRoomIP());

                        BeanRoomInfo beanRoomInfo = new BeanRoomInfo();
                        beanRoomInfo.setIp(roomData.getPlayerIP());
                        beanRoomInfo.setRoomeName(roomData.getPlayerName());
                        beanRoomInfo.setRoomData(roomData);
                        beanRoomInfo.setPlayersSum(roomData.getPlayersNum());

//                        mMyAdapter.addItems(beanRoomInfo);
                        String result = beanRoomInfo.toString();
                        mBeanRoomInfos.add(beanRoomInfo);
                        Message message = handler.obtainMessage();
                        message.what = GET_ROOM_IP;
                        handler.sendMessage(message);
                    }

                }
                //收到房间销毁的广播，于是要减少列表项目
                if (roomData.getTag().startsWith(RBACK)) {
                    if (mTempRoomsIp.contains(roomData.getRoomIP())) {
                        mTempRoomsIp.remove(roomData.getRoomIP());
                        int i;
                        for (i = 0; i < mBeanRoomInfos.size(); i++) {
                            BeanRoomInfo beanRoomInfo = mBeanRoomInfos.get(i);
                            if (beanRoomInfo.getIp().startsWith(roomData.getRoomIP())) {
                                mBeanRoomInfos.remove(i);
                                Message message = handler.obtainMessage();
                                message.what = RBACK_WHAT;
                                handler.sendMessage(message);
                                break;
                            }
                        }


                    }

                }
            }
        });
        searchRoomThread = new SearchRoomThread();
        searchRoomThread.start();

    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_item_room_id)
        TextView mTextViewName;

        @BindView(R.id.textview_item_num)
        TextView mTextViewNum;

        @BindView(R.id.btn_join_room)
        Button mButtonJoin;

        DataBroaCastSerlied room;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(BeanRoomInfo beanRoomInfo) {
            mTextViewName.setText(beanRoomInfo.getRoomeName());
            room = beanRoomInfo.getRoomData();
            mTextViewNum.setText(beanRoomInfo.getPlayersSum());
//            mTextViewNum.setText(beanRoomInfo.get);
        }

        @OnClick(R.id.btn_join_room)
        public void onBtnJoinClick() {
            Bundle bundletoUserSetting = new Bundle();
            bundletoUserSetting.putSerializable(ROOM_DATA, room);
            Intent intentToUserSetting = new Intent(AtyStartWifiGame.this, AtyClientSetting.class);
            intentToUserSetting.putExtras(bundletoUserSetting);
            startActivity(intentToUserSetting);
            searchRoomThread.setStopSearchRoom();
            finish();
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        List<BeanRoomInfo> mBeanRoomInfosList;
        Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
            mBeanRoomInfosList = new ArrayList<>();
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
            return new MyHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            BeanRoomInfo beanRoomInfo = mBeanRoomInfosList.get(position);
            holder.bind(beanRoomInfo);
        }

        @Override
        public int getItemCount() {
            return mBeanRoomInfosList.size();
        }

        public void addItems(List<BeanRoomInfo> list) {
            if (mBeanRoomInfosList.size() != 0) {
                mBeanRoomInfosList.clear();
                mBeanRoomInfosList.addAll(list);
            } else {
                mBeanRoomInfosList.addAll(list);
            }
        }

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

    @OnClick(R.id.btn_createroom)
    public void onBtnCreateRoomClick() {
        AtyCreateRoom.startAtyCreateRoom(this, AtyCreateRoom.class);
    }

    @OnClick(R.id.btn_aty_start_wifi_back)
    public void onBtnBack() {
        finish();
    }
}


