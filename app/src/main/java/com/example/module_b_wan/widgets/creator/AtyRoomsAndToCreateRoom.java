package com.example.module_b_wan.widgets.creator;

import android.content.Context;
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

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.bean.BeanRoomInfo;
import com.example.module_b_lan.network.broadcast.DataBroaCastSerlied;
import com.example.module_b_wan.widgets.AtyBase;
import com.example.module_b_wan.widgets.client.AtyClientSetting;
import com.example.yifeihappy.planechess.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class AtyRoomsAndToCreateRoom extends AtyBase {

    public static final int UPDATE_UI = 0;


    @BindView(R.id.btn_aty_rooms_and_tocreateroom_back)
    protected Button mButtonBack;


    @BindView(R.id.recyclerview_aty_rooms_and_tocreateroom)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.btn_aty_rooms_and_tocreateroom)
    protected Button mButtonCreateRoom;

    private MyAdapter mMyAdapter;

    private AVIMClient mAVIMClient;

    private Handler mHandler;


    List<BeanRoomInfo> mRoomInfos = new ArrayList<>();

    SearchRoomThread mSearchRoomThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_rooms_and_tocreateroom);

        mSearchRoomThread = new SearchRoomThread();
        mSearchRoomThread.start();
        mAVIMClient = AVIMClient.getInstance("robot");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMyAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mMyAdapter);


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == UPDATE_UI) {
                    mMyAdapter.addItems(mRoomInfos);
                    mMyAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_item_room_id)
        TextView mTextViewName;

        @BindView(R.id.textview_item_num)
        TextView mTextViewNum;

        @BindView(R.id.btn_join_room)
        Button mButtonJoin;

        String conversationId;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(BeanRoomInfo beanRoomInfo) {
            mTextViewName.setText(beanRoomInfo.getRoomeName());
            conversationId = beanRoomInfo.getConversationId();
            mTextViewNum.setText(beanRoomInfo.getPlayersSum());
        }

        @OnClick(R.id.btn_join_room)
        public void onBtnJoinClick() {


            AtyClientSetting.startAtyClientSetting(AtyRoomsAndToCreateRoom.this, AtyClientSetting.class, conversationId);
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
                mAVIMClient.open(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                        AVIMConversationQuery query = avimClient.getQuery();
                        query.whereEqualTo("attr.type", "planechess");
                        query.findInBackground(new AVIMConversationQueryCallback() {
                            @Override
                            public void done(List<AVIMConversation> list, AVIMException e) {
                                if (null == e) {
                                    List<BeanRoomInfo> rooms = new ArrayList<>();
                                    for (int i = 0; i < list.size(); i++) {
                                        BeanRoomInfo roomInfo = new BeanRoomInfo();
                                        AVIMConversation conversation = list.get(i);
                                        String creator = conversation.getCreator();
                                        int members = conversation.getMembers().size();
                                        String conversationId = conversation.getConversationId();
                                        roomInfo.setPlayersSum("" + members + "/4");
                                        roomInfo.setRoomeName(creator);
                                        roomInfo.setConversationId(conversationId);
                                        rooms.add(roomInfo);
                                    }
                                    if (null != mRoomInfos) {
                                        mRoomInfos.clear();
                                        mRoomInfos.addAll(rooms);
                                    } else {
                                        mRoomInfos = rooms;
                                    }
                                    mHandler.sendEmptyMessage(UPDATE_UI);
                                }
                            }
                        });
                    }
                });

            }

        }
    }


}
