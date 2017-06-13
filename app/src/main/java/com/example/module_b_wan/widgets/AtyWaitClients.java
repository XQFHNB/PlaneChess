package com.example.module_b_wan.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.module_b_wan.other.event.EventEnterRoom;
import com.example.module_b_wan.other.event.EventImTypeMessage;
import com.example.module_b_wan.other.manager.ManagerAVImClient;
import com.example.module_b_wan.utils.HandlerCustomConversationEvent;
import com.example.yifeihappy.planechess.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class AtyWaitClients extends AtyBase {

    public static void startAtyWaitClients(Context context, Class<?> cls, String conversationId, String room) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("conversationId", conversationId);
        intent.putExtra("roomname", room);
        context.startActivity(intent);
    }


    @BindView(R.id.btn_aty_wait_clients)
    protected Button mButtonBack;

    @BindView(R.id.btnBegin)
    protected Button mButtonBegin;

    @BindView(R.id.room_name)
    protected TextView mTextViewRoomName;

    @BindView(R.id.player1)
    protected LinearLayout mLinearLayoutPlayer1;
    @BindView(R.id.player2)
    protected LinearLayout mLinearLayoutPlayer2;
    @BindView(R.id.player3)
    protected LinearLayout mLinearLayoutPlayer3;
    @BindView(R.id.player4)
    protected LinearLayout mLinearLayoutPlayer4;

    private LinearLayout[] mPlayersLinear = null;

    private AVIMConversation mAVIMConversation;

    private String mRoomName;

    private AVIMClient mAVIMClient;
    private String conversationId;

    private List<String> mPlayersNames;
    private int[] mPlayerColors = new int[]{R.color.blue, R.color.red, R.color.yellow, R.color.green};

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.wait_clients);
        mButtonBegin.setClickable(false);
        mPlayersLinear = new LinearLayout[]{
                mLinearLayoutPlayer1,
                mLinearLayoutPlayer2,
                mLinearLayoutPlayer3,
                mLinearLayoutPlayer4
        };
        conversationId = getIntent().getStringExtra("conversationId");
        mRoomName = getIntent().getStringExtra("roomname");

        mTextViewRoomName.setText(mRoomName);

        mPlayersNames = new ArrayList<>();
        mPlayersNames.add(mRoomName);
        mPlayersLinear[0].setBackgroundResource(mPlayerColors[0]);
        mAVIMClient = ManagerAVImClient.getInstance().getClient();
        //设置消息通知回调
        AVIMMessageManager.setConversationEventHandler(new HandlerCustomConversationEvent());
    }

    public void onEvent(EventEnterRoom eventEnterRoom) {
        if (null != eventEnterRoom && mPlayersNames.size() < 4) {
            String playerName = eventEnterRoom.getName();
            mPlayersNames.add(playerName);
            int size = mPlayersNames.size();
            mPlayersLinear[size - 1].setBackgroundResource(mPlayerColors[size - 1]);
        }
        if (null != eventEnterRoom && mPlayersNames.size() == 4) {
            mButtonBegin.setClickable(true);
            toast("点击开始游戏");
        }
    }

    @OnClick(R.id.btnBegin)
    public void onBtnBeginClick() {

        //跳转到游戏界面，同时向对话中发出开始命令

    }
}

